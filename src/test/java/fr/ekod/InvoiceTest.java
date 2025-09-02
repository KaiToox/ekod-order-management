package fr.ekod;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import fr.ekod.exceptions.OutOfStockException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de la classe Invoice")
public class InvoiceTest {
    
    private Invoice invoice;
    private Order order;
    private ShoppingCart cart;
    private Product laptop;
    private Product phone;
    private Product book;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
        laptop = new Product("Laptop", 1000.0, 5);
        phone = new Product("Phone", 500.0, 10);
        book = new Product("Book", 25.0, 20);
        order = new Order(cart, 10.0); // Frais de livraison de 10€
        invoice = new Invoice(order);
    }

    @Test
    @DisplayName("Test de génération de facture avec panier vide")
    void testGenerateInvoiceEmptyCart() {
        String invoiceText = invoice.generateInvoice();
        
        assertNotNull(invoiceText);
        assertTrue(invoiceText.contains("=== FACTURE ==="));
        assertTrue(invoiceText.contains("Articles:"));
        assertTrue(invoiceText.contains("Sous-total: 0.00 €"));
        assertTrue(invoiceText.contains("Frais de livraison: 10.00 €"));
        assertTrue(invoiceText.contains("Total: 10.00 €"));
    }

    @Test
    @DisplayName("Test de génération de facture avec produits")
    void testGenerateInvoiceWithProducts() throws OutOfStockException {
        cart.addProduct(laptop);
        cart.addProduct(phone);
        
        String invoiceText = invoice.generateInvoice();
        
        assertNotNull(invoiceText);
        assertTrue(invoiceText.contains("=== FACTURE ==="));
        assertTrue(invoiceText.contains("Laptop: 1000.00 €"));
        assertTrue(invoiceText.contains("Phone: 500.00 €"));
        assertTrue(invoiceText.contains("Sous-total: 1500.00 €"));
        assertTrue(invoiceText.contains("Frais de livraison: 10.00 €"));
        assertTrue(invoiceText.contains("Total: 1510.00 €"));
    }

    @Test
    @DisplayName("Test de génération de facture avec remise")
    void testGenerateInvoiceWithDiscount() throws OutOfStockException {
        cart.addProduct(laptop);
        order.setDiscount(20.0);  // 20% de remise
        
        String invoiceText = invoice.generateInvoice();
        
        assertNotNull(invoiceText);
        assertTrue(invoiceText.contains("=== FACTURE ==="));
        assertTrue(invoiceText.contains("Laptop: 1000.00 €"));
        assertTrue(invoiceText.contains("Remise: 20.00%"));
        assertTrue(invoiceText.contains("Sous-total: 1000.00 €"));
        assertTrue(invoiceText.contains("Total: 810.00 €")); // (1000 * 0.8) + 10
    }

    @Test
    @DisplayName("Test de formatage de la facture")
    void testInvoiceFormatting() throws OutOfStockException {
        cart.addProduct(book);
        
        String invoiceText = invoice.generateInvoice();
        
        // Vérification de la structure
        String[] lines = invoiceText.split("\n");
        assertTrue(lines.length >= 8); // Au moins 8 lignes pour une facture basique
        
        // Vérification des sections principales
        assertTrue(invoiceText.contains("=== FACTURE ==="));
        assertTrue(invoiceText.contains("Articles:"));
        assertTrue(invoiceText.contains("Sous-total:"));
        assertTrue(invoiceText.contains("Frais de livraison:"));
        assertTrue(invoiceText.contains("Total:"));
    }

    @Test
    @DisplayName("Test de facture avec un seul produit")
    void testInvoiceWithSingleProduct() throws OutOfStockException {
        cart.addProduct(laptop);
        
        String invoiceText = invoice.generateInvoice();
        
        assertNotNull(invoiceText);
        assertTrue(invoiceText.contains("Laptop: 1000.00 €"));
        assertTrue(invoiceText.contains("Sous-total: 1000.00 €"));
        assertTrue(invoiceText.contains("Total: 1010.00 €"));
    }

    @Test
    @DisplayName("Test de facture avec plusieurs produits et remise")
    void testInvoiceWithMultipleProductsAndDiscount() throws OutOfStockException {
        cart.addProduct(laptop);  // 1000€
        cart.addProduct(phone);   // 500€
        cart.addProduct(book);    // 25€
        order.setDiscount(15.0);  // 15% de remise
        
        String invoiceText = invoice.generateInvoice();
        
        assertNotNull(invoiceText);
        assertTrue(invoiceText.contains("Laptop: 1000.00 €"));
        assertTrue(invoiceText.contains("Phone: 500.00 €"));
        assertTrue(invoiceText.contains("Book: 25.00 €"));
        assertTrue(invoiceText.contains("Sous-total: 1525.00 €"));
        assertTrue(invoiceText.contains("Remise: 15.00%"));
        assertTrue(invoiceText.contains("Total: 1306.25 €")); // (1525 * 0.85) + 10
    }

    @Test
    @DisplayName("Test de facture avec remise de 0%")
    void testInvoiceWithZeroDiscount() throws OutOfStockException {
        cart.addProduct(laptop);
        order.setDiscount(0.0);  // 0% de remise
        
        String invoiceText = invoice.generateInvoice();
        
        assertNotNull(invoiceText);
        assertFalse(invoiceText.contains("Remise:")); // Pas de ligne de remise
        assertTrue(invoiceText.contains("Total: 1010.00 €"));
    }

    @Test
    @DisplayName("Test de facture avec remise de 100%")
    void testInvoiceWithFullDiscount() throws OutOfStockException {
        cart.addProduct(laptop);
        order.setDiscount(100.0);  // 100% de remise
        
        String invoiceText = invoice.generateInvoice();
        
        assertNotNull(invoiceText);
        assertTrue(invoiceText.contains("Remise: 100.00%"));
        assertTrue(invoiceText.contains("Total: 10.00 €")); // Seulement les frais de livraison
    }

    @Test
    @DisplayName("Test de contenu de la facture")
    void testInvoiceContent() throws OutOfStockException {
        cart.addProduct(laptop);
        cart.addProduct(phone);
        
        String invoiceText = invoice.generateInvoice();
        
        // Vérifier que tous les produits sont listés
        assertTrue(invoiceText.contains("Laptop"));
        assertTrue(invoiceText.contains("Phone"));
        
        // Vérifier que les prix sont corrects
        assertTrue(invoiceText.contains("1000.00 €"));
        assertTrue(invoiceText.contains("500.00 €"));
        
        // Vérifier que le total est correct
        assertTrue(invoiceText.contains("Total: 1510.00 €"));
    }

    @Test
    @DisplayName("Test de facture avec frais de livraison modifiés")
    void testInvoiceWithModifiedDeliveryFee() throws OutOfStockException {
        cart.addProduct(laptop);
        order.setDeliveryFee(25.0);  // Frais de livraison modifiés
        
        String invoiceText = invoice.generateInvoice();
        
        assertNotNull(invoiceText);
        assertTrue(invoiceText.contains("Frais de livraison: 25.00 €"));
        assertTrue(invoiceText.contains("Total: 1025.00 €")); // 1000 + 25
    }
} 