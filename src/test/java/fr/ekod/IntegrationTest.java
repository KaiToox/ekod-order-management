package fr.ekod;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import fr.ekod.exceptions.OutOfStockException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests d'intégration de l'application de gestion des commandes")
public class IntegrationTest {
    
    private Product laptop;
    private Product phone;
    private Product book;
    private ShoppingCart cart;
    private Order order;
    private Invoice invoice;

    @BeforeEach
    void setUp() {
        laptop = new Product("Laptop", 1000.0, 5);
        phone = new Product("Phone", 500.0, 10);
        book = new Product("Book", 25.0, 20);
        cart = new ShoppingCart();
        order = new Order(cart, 10.0); // Frais de livraison de 10€
        invoice = new Invoice(order);
    }

    @Test
    @DisplayName("Test d'intégration complète : Produit → Panier → Commande → Facture")
    void testCompleteIntegration() throws OutOfStockException {
        // 1. Ajouter des produits au panier
        cart.addProduct(laptop);
        cart.addProduct(phone);
        
        // 2. Vérifier le contenu du panier
        assertEquals(2, cart.getNumberOfItems());
        assertEquals(1500.0, cart.getTotalPrice(), 0.01);
        
        // 3. Vérifier la commande
        assertEquals(1510.0, order.getTotalPrice(), 0.01);
        
        // 4. Appliquer une remise
        order.setDiscount(15.0);
        double expectedTotal = (1500.0 * 0.85) + 10.0; // 15% de remise + frais de livraison
        assertEquals(expectedTotal, order.getTotalPrice(), 0.01);
        
        // 5. Générer la facture
        String invoiceText = invoice.generateInvoice();
        assertNotNull(invoiceText);
        assertTrue(invoiceText.contains("Remise: 15.00%"));
        assertTrue(invoiceText.contains("Total: " + String.format("%.2f", expectedTotal) + " €"));
    }

    @Test
    @DisplayName("Test de gestion des exceptions d'intégration")
    void testIntegrationExceptions() throws OutOfStockException {
        // Test avec un produit à stock limité
        Product limitedProduct = new Product("Limited", 50.0, 1);
        
        cart.addProduct(limitedProduct);
        assertThrows(OutOfStockException.class, () -> cart.addProduct(limitedProduct));
        
        // Vérifier que la commande reste valide
        assertEquals(60.0, order.getTotalPrice(), 0.01); // 50 + 10 (frais de livraison)
    }

    @Test
    @DisplayName("Test de validation des données de commande")
    void testOrderValidation() {
        // Test avec des valeurs négatives
        assertThrows(IllegalArgumentException.class, () -> order.setDeliveryFee(-5.0));
        assertThrows(IllegalArgumentException.class, () -> order.setDiscount(-10.0));
        
        // Test avec des valeurs trop élevées
        assertThrows(IllegalArgumentException.class, () -> order.setDiscount(101.0));
    }

    @Test
    @DisplayName("Test de flux de commande avec modification")
    void testOrderFlowWithModifications() throws OutOfStockException {
        // 1. Créer une commande initiale
        cart.addProduct(laptop);
        assertEquals(1010.0, order.getTotalPrice(), 0.01);
        
        // 2. Modifier les frais de livraison
        order.setDeliveryFee(20.0);
        assertEquals(1020.0, order.getTotalPrice(), 0.01);
        
        // 3. Ajouter un autre produit
        cart.addProduct(phone);
        assertEquals(1520.0, order.getTotalPrice(), 0.01);
        
        // 4. Appliquer une remise
        order.setDiscount(10.0);
        double expectedTotal = (1500.0 * 0.9) + 20.0; // 10% de remise + frais de livraison
        assertEquals(expectedTotal, order.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Test de gestion du stock en cascade")
    void testStockManagementCascade() throws OutOfStockException {
        // 1. Vérifier le stock initial
        assertEquals(5, laptop.getStock());
        assertEquals(10, phone.getStock());
        
        // 2. Ajouter au panier
        cart.addProduct(laptop);
        cart.addProduct(phone);
        assertEquals(3, laptop.getStock()); // 5 - 1 - 1 (ajouté 2 fois)
        assertEquals(8, phone.getStock());  // 10 - 1 - 1
        
        // 3. Supprimer du panier
        cart.removeProduct(laptop);
        assertEquals(4, laptop.getStock()); // Récupéré
        assertEquals(8, phone.getStock());  // Inchangé
        
        // 4. Vérifier que la commande reflète les changements
        assertEquals(510.0, order.getTotalPrice(), 0.01); // 500 + 10
    }

    @Test
    @DisplayName("Test de facture avec modifications en cours de route")
    void testInvoiceWithMidProcessModifications() throws OutOfStockException {
        // 1. Générer une facture initiale
        cart.addProduct(laptop);
        String initialInvoice = invoice.generateInvoice();
        assertTrue(initialInvoice.contains("Total: 1010.00 €"));
        
        // 2. Modifier la commande
        order.setDiscount(20.0);
        String modifiedInvoice = invoice.generateInvoice();
        assertTrue(modifiedInvoice.contains("Total: 810.00 €")); // (1000 * 0.8) + 10
        
        // 3. Vérifier que les deux factures sont différentes
        assertNotEquals(initialInvoice, modifiedInvoice);
    }

    @Test
    @DisplayName("Test de robustesse avec données extrêmes")
    void testRobustnessWithExtremeData() throws OutOfStockException {
        // 1. Produit avec prix très élevé
        Product expensiveProduct = new Product("Diamond", 1000000.0, 1);
        cart.addProduct(expensiveProduct);
        
        // 2. Remise maximale
        order.setDiscount(100.0);
        
        // 3. Vérifier que le système gère correctement
        assertEquals(10.0, order.getTotalPrice(), 0.01); // Seulement les frais de livraison
        
        // 4. Générer la facture
        String invoiceText = invoice.generateInvoice();
        assertNotNull(invoiceText);
        assertTrue(invoiceText.contains("Diamond: 1000000.00 €"));
        assertTrue(invoiceText.contains("Remise: 100.00%"));
    }

    @Test
    @DisplayName("Test de performance avec plusieurs produits")
    void testPerformanceWithMultipleProducts() throws OutOfStockException {
        // Ajouter plusieurs produits
        for (int i = 0; i < 100; i++) {
            Product product = new Product("Product" + i, 10.0, 100);
            try {
                cart.addProduct(product);
            } catch (OutOfStockException e) {
                // Ignorer les exceptions de stock pour ce test
            }
        }
        
        // Vérifier que le système gère correctement
        assertTrue(cart.getNumberOfItems() > 0);
        assertTrue(order.getTotalPrice() > 0);
        
        // Générer la facture
        String invoiceText = invoice.generateInvoice();
        assertNotNull(invoiceText);
        assertTrue(invoiceText.contains("=== FACTURE ==="));
    }
} 