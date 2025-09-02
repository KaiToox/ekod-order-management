package fr.ekod;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import fr.ekod.exceptions.OutOfStockException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de la classe ShoppingCart")
public class ShoppingCartTest {
    
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
    }

    @Test
    @DisplayName("Test de création d'un panier vide")
    void testEmptyCart() {
        assertTrue(cart.getProductList().isEmpty());
        assertEquals(0.0, cart.getTotalPrice(), 0.01);
        assertEquals(0, cart.getNumberOfItems());
    }

    @Test
    @DisplayName("Test d'ajout de produits au panier")
    void testAddProducts() throws OutOfStockException {
        cart.addProduct(laptop);
        cart.addProduct(phone);
        
        assertEquals(2, cart.getProductList().size());
        assertEquals(2, cart.getNumberOfItems());
        assertTrue(cart.getProductList().contains(laptop));
        assertTrue(cart.getProductList().contains(phone));
    }

    @Test
    @DisplayName("Test de suppression de produits du panier")
    void testRemoveProducts() throws OutOfStockException {
        cart.addProduct(laptop);
        cart.addProduct(phone);
        
        cart.removeProduct(laptop);
        assertEquals(1, cart.getProductList().size());
        assertEquals(1, cart.getNumberOfItems());
        assertFalse(cart.getProductList().contains(laptop));
        assertTrue(cart.getProductList().contains(phone));
    }

    @Test
    @DisplayName("Test de calcul du prix total du panier")
    void testCartTotalPrice() throws OutOfStockException {
        cart.addProduct(laptop);  // 1000€
        cart.addProduct(phone);   // 500€
        cart.addProduct(book);    // 25€
        
        // Total attendu : 1000 + 500 + 25 = 1525€
        assertEquals(1525.0, cart.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Test de gestion des exceptions de stock")
    void testStockException() throws OutOfStockException {
        // Créer un produit avec un stock de 1
        Product limitedProduct = new Product("Limited", 100.0, 1);
        cart.addProduct(limitedProduct);
        
        // Ajouter le même produit une deuxième fois devrait lever une exception
        assertThrows(OutOfStockException.class, () -> cart.addProduct(limitedProduct));
    }

    @Test
    @DisplayName("Test de récupération de la liste des produits")
    void testGetProductList() throws OutOfStockException {
        cart.addProduct(laptop);
        cart.addProduct(phone);
        
        var products = cart.getProductList();
        assertEquals(2, products.size());
        assertTrue(products.contains(laptop));
        assertTrue(products.contains(phone));
    }

    @Test
    @DisplayName("Test du nombre d'articles")
    void testNumberOfItems() throws OutOfStockException {
        assertEquals(0, cart.getNumberOfItems());
        
        cart.addProduct(laptop);
        assertEquals(1, cart.getNumberOfItems());
        
        cart.addProduct(phone);
        assertEquals(2, cart.getNumberOfItems());
    }

    @Test
    @DisplayName("Test d'ajout d'un produit en rupture de stock")
    void testAddOutOfStockProduct() {
        Product outOfStockProduct = new Product("OutOfStock", 100.0, 0);
        
        assertThrows(OutOfStockException.class, () -> cart.addProduct(outOfStockProduct));
        assertEquals(0, cart.getNumberOfItems());
        assertEquals(0.0, cart.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Test de suppression d'un produit inexistant")
    void testRemoveNonExistentProduct() {
        Product nonExistentProduct = new Product("NonExistent", 100.0, 5);
        
        // Supprimer un produit qui n'est pas dans le panier
        cart.removeProduct(nonExistentProduct);
        assertEquals(0, cart.getNumberOfItems());
        assertEquals(0.0, cart.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Test de gestion du stock lors de l'ajout/suppression")
    void testStockManagement() throws OutOfStockException {
        int initialLaptopStock = laptop.getStock();
        int initialPhoneStock = phone.getStock();
        
        cart.addProduct(laptop);
        cart.addProduct(phone);
        
        // Vérification que le stock a été décrémenté
        assertEquals(initialLaptopStock - 1, laptop.getStock());
        assertEquals(initialPhoneStock - 1, phone.getStock());
        
        cart.removeProduct(laptop);
        
        // Vérification que le stock a été récupéré
        assertEquals(initialLaptopStock, laptop.getStock());
        assertEquals(initialPhoneStock - 1, phone.getStock()); // Phone toujours dans le panier
    }

    @Test
    @DisplayName("Test de panier avec un seul produit")
    void testSingleProductCart() throws OutOfStockException {
        cart.addProduct(laptop);
        
        assertEquals(1, cart.getNumberOfItems());
        assertEquals(1000.0, cart.getTotalPrice(), 0.01);
        assertTrue(cart.getProductList().contains(laptop));
    }

    @Test
    @DisplayName("Test de vidage du panier")
    void testEmptyCartAfterRemoval() throws OutOfStockException {
        cart.addProduct(laptop);
        cart.addProduct(phone);
        
        cart.removeProduct(laptop);
        cart.removeProduct(phone);
        
        assertEquals(0, cart.getNumberOfItems());
        assertEquals(0.0, cart.getTotalPrice(), 0.01);
        assertTrue(cart.getProductList().isEmpty());
    }
} 