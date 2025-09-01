package fr.ekod;

import fr.ekod.exceptions.InvalidDiscountCodeException;
import fr.ekod.exceptions.OutOfStockException;

public class Main {
    public static void main(String[] args) {
        try {
            // Créer des produits
            Product laptop = new Product("Ordinateur portable", 999.99, 5);
            Product phone = new Product("Téléphone", 499.99, 3);
            Product tablet = new Product("Tablette", 299.99, 0);

            // Créer un panier
            ShoppingCart cart = new ShoppingCart();

            // Ajouter des produits au panier
            System.out.println("Ajout des produits au panier...");
            cart.addProduct(laptop);
            cart.addProduct(phone);
            
            // Essayer d'ajouter un produit en rupture de stock
            try {
                cart.addProduct(tablet);
            } catch (OutOfStockException e) {
                System.out.println("Erreur: " + e.getMessage());
            }

            // Créer la commande
            Order order = new Order(cart);
            System.out.println("Total initial: " + order.getTotalPrice() + " €");

            // Appliquer une remise
            try {
                order.applyDiscount("PROMO10");
                System.out.println("Remise appliquée avec succès");
            } catch (InvalidDiscountCodeException e) {
                System.out.println("Erreur lors de l'application de la remise: " + e.getMessage());
            }

            // Générer et afficher la facture
            Invoice invoice = new Invoice(order);
            System.out.println("\n" + invoice.generateInvoice());

        } catch (Exception e) {
            System.out.println("Une erreur est survenue: " + e.getMessage());
        }
    }
}