package fr.ekod;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import fr.ekod.exceptions.OutOfStockException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de la classe Product")
public class ProductTest {
    
    private Product laptop;
    private Product phone;
    private Product book;

    @BeforeEach
    void setUp() {
        laptop = new Product("Laptop", 1000.0, 5);
        phone = new Product("Phone", 500.0, 10);
        book = new Product("Book", 25.0, 20);
    }

    @Test
    @DisplayName("Test de création d'un produit avec des valeurs valides")
    void testProductCreation() {
        assertEquals("Laptop", laptop.getName());
        assertEquals(1000.0, laptop.getPrice(), 0.01);
        assertEquals(5, laptop.getStock());
    }

    @Test
    @DisplayName("Test des getters du produit")
    void testProductGetters() {
        assertEquals("Laptop", laptop.getName());
        assertEquals(1000.0, laptop.getPrice(), 0.01);
        assertEquals(5, laptop.getStock());
    }

    @Test
    @DisplayName("Test de décrémentation du stock")
    void testDecreaseStock() throws OutOfStockException {
        int initialStock = laptop.getStock();
        laptop.decreaseStock();
        assertEquals(initialStock - 1, laptop.getStock());
    }

    @Test
    @DisplayName("Test d'incrémentation du stock")
    void testIncreaseStock() {
        int initialStock = laptop.getStock();
        laptop.increaseStock();
        assertEquals(initialStock + 1, laptop.getStock());
    }

    @Test
    @DisplayName("Test de gestion du stock à zéro")
    void testStockAtZero() throws OutOfStockException {
        // Décrémenter jusqu'à zéro
        for (int i = 0; i < 5; i++) {
            laptop.decreaseStock();
        }
        assertEquals(0, laptop.getStock());
        
        // Essayer de décrémenter en dessous de zéro
        assertThrows(OutOfStockException.class, () -> laptop.decreaseStock());
    }

    @Test
    @DisplayName("Test de gestion du stock à zéro avec produit initialement à zéro")
    void testStockAtZeroInitially() {
        Product outOfStockProduct = new Product("OutOfStock", 100.0, 0);
        assertThrows(OutOfStockException.class, () -> outOfStockProduct.decreaseStock());
    }

    @Test
    @DisplayName("Test de modification du stock multiple fois")
    void testMultipleStockOperations() throws OutOfStockException {
        // Décrémenter 2 fois
        laptop.decreaseStock();
        laptop.decreaseStock();
        assertEquals(3, laptop.getStock());
        
        // Incrémenter 1 fois
        laptop.increaseStock();
        assertEquals(4, laptop.getStock());
        
        // Décrémenter 1 fois
        laptop.decreaseStock();
        assertEquals(3, laptop.getStock());
    }

    @Test
    @DisplayName("Test de création de produit avec prix zéro")
    void testProductWithZeroPrice() {
        Product freeProduct = new Product("Free", 0.0, 5);
        assertEquals(0.0, freeProduct.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Test de création de produit avec prix négatif")
    void testProductWithNegativePrice() {
        Product negativePriceProduct = new Product("Negative", -100.0, 5);
        assertEquals(-100.0, negativePriceProduct.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Test de création de produit avec stock négatif")
    void testProductWithNegativeStock() {
        Product negativeStockProduct = new Product("NegativeStock", 100.0, -5);
        assertEquals(-5, negativeStockProduct.getStock());
    }
} 