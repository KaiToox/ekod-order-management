package fr.ekod;

import fr.ekod.exceptions.InvalidDiscountCodeException;

public class Order {
    private ShoppingCart shoppingCart;
    private double totalPrice;
    private double discount;
    private double deliveryFee;

    public Order(ShoppingCart shoppingCart, double deliveryFee) {
        this.shoppingCart = shoppingCart;
        this.discount = 0.0;
        this.deliveryFee = deliveryFee;
        // Établir la liaison bidirectionnelle
        shoppingCart.setAssociatedOrder(this);
        updateFromCart();
    }

    public void updateFromCart() {
        calculateTotal();
    }

    public void calculateTotal() {
        double subtotal = shoppingCart.getTotalPrice();
        if (discount > 0) {
            // Appliquer la remise sur le sous-total seulement
            totalPrice = (subtotal * (1 - discount / 100.0)) + deliveryFee;
        } else {
            totalPrice = subtotal + deliveryFee;
        }
    }

    public void setDiscount(double discount) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("La remise doit être entre 0 et 100%");
        }
        this.discount = discount;
        calculateTotal();
    }

    public void setDeliveryFee(double deliveryFee) {
        if (deliveryFee < 0) {
            throw new IllegalArgumentException("Les frais de livraison ne peuvent pas être négatifs");
        }
        this.deliveryFee = deliveryFee;
        calculateTotal();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public double getDiscount() {
        return discount;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}