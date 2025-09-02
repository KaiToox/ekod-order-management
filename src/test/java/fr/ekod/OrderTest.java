package fr.ekod;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import fr.ekod.exceptions.OutOfStockException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de la classe Order")
public class OrderTest {
    
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
    }

    @Test
    @DisplayName("Test de création d'une commande")
    void testOrderCreation() {
        assertEquals(cart, order.getShoppingCart());
        assertEquals(10.0, order.getDeliveryFee(), 0.01);
        assertEquals(0.0, order.getDiscount(), 0.01);
        assertEquals(10.0, order.getTotalPrice(), 0.01); // Seulement les frais de livraison
    }

    @Test
    @DisplayName("Test de calcul du total de la commande")
    void testOrderTotalCalculation() throws OutOfStockException {
        cart.addProduct(laptop);  // 1000€
        cart.addProduct(phone);   // 500€
        
        // Total attendu : 1000 + 500 + 10 (frais de livraison) = 1510€
        assertEquals(1510.0, order.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Test d'application d'une remise")
    void testApplyDiscount() throws OutOfStockException {
        cart.addProduct(laptop);  // 1000€
        order.setDiscount(10.0);  // 10% de remise
        
        // Total avec remise : (1000 * 0.9) + 10 = 910€
        assertEquals(910.0, order.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Test de gestion des frais de livraison")
    void testDeliveryFee() {
        assertEquals(10.0, order.getDeliveryFee(), 0.01);
        
        order.setDeliveryFee(15.0);
        assertEquals(15.0, order.getDeliveryFee(), 0.01);
    }

    @Test
    @DisplayName("Test de remise de 0%")
    void testZeroDiscount() throws OutOfStockException {
        cart.addProduct(laptop);  // 1000€
        order.setDiscount(0.0);   // 0% de remise
        
        // Total sans remise : 1000 + 10 = 1010€
        assertEquals(1010.0, order.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Test de remise de 100%")
    void testFullDiscount() throws OutOfStockException {
        cart.addProduct(laptop);  // 1000€
        order.setDiscount(100.0); // 100% de remise
        
        // Total avec remise complète : (1000 * 0.0) + 10 = 10€
        assertEquals(10.0, order.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Test de remise de 50%")
    void testHalfDiscount() throws OutOfStockException {
        cart.addProduct(laptop);  // 1000€
        cart.addProduct(phone);   // 500€
        order.setDiscount(50.0);  // 50% de remise
        
        // Total avec remise : (1500 * 0.5) + 10 = 760€
        assertEquals(760.0, order.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Test de modification des frais de livraison")
    void testModifyDeliveryFee() throws OutOfStockException {
        cart.addProduct(laptop);  // 1000€
        assertEquals(1010.0, order.getTotalPrice(), 0.01);
        
        order.setDeliveryFee(20.0);
        assertEquals(1020.0, order.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Test de validation des frais de livraison négatifs")
    void testNegativeDeliveryFee() {
        assertThrows(IllegalArgumentException.class, () -> order.setDeliveryFee(-5.0));
        assertEquals(10.0, order.getDeliveryFee(), 0.01); // Valeur inchangée
    }

    @Test
    @DisplayName("Test de validation des remises négatives")
    void testNegativeDiscount() {
        assertThrows(IllegalArgumentException.class, () -> order.setDiscount(-10.0));
        assertEquals(0.0, order.getDiscount(), 0.01); // Valeur inchangée
    }

    @Test
    @DisplayName("Test de validation des remises supérieures à 100%")
    void testDiscountOver100() {
        assertThrows(IllegalArgumentException.class, () -> order.setDiscount(101.0));
        assertEquals(0.0, order.getDiscount(), 0.01); // Valeur inchangée
    }

    @Test
    @DisplayName("Test de commande avec panier vide")
    void testEmptyCartOrder() {
        assertEquals(10.0, order.getTotalPrice(), 0.01); // Seulement les frais de livraison
    }

    @Test
    @DisplayName("Test de commande avec plusieurs produits et remise")
    void testMultipleProductsWithDiscount() throws OutOfStockException {
        cart.addProduct(laptop);  // 1000€
        cart.addProduct(phone);   // 500€
        cart.addProduct(book);    // 25€
        order.setDiscount(20.0);  // 20% de remise
        
        // Total avec remise : (1525 * 0.8) + 10 = 1230€
        assertEquals(1230.0, order.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Test de récupération des informations de la commande")
    void testOrderInformationRetrieval() {
        assertEquals(cart, order.getShoppingCart());
        assertEquals(0.0, order.getDiscount());
        assertTrue(order.getTotalPrice() >= 0.0);
        assertTrue(order.getDeliveryFee() >= 0.0);
    }
} 