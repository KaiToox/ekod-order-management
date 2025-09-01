package fr.ekod;

import fr.ekod.exceptions.InvalidDiscountCodeException;

public class Order {
    private ShoppingCart shoppingCart;
    private double totalPrice;
    private double discount;
    private double deliveryFee;
    private static final double BASE_DELIVERY_FEE = 5.0;

    public Order(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        this.discount = 0.0;
        this.deliveryFee = BASE_DELIVERY_FEE * shoppingCart.getItemCount();
        calculateTotal();
    }

    public void calculateTotal() {
        double subtotal = shoppingCart.getTotalPrice();
        totalPrice = subtotal + deliveryFee;
        if (discount > 0) {
            totalPrice = (totalPrice + deliveryFee) * (1 - discount);
        }
    }

    public void applyDiscount(String discountCode) throws InvalidDiscountCodeException {
        switch (discountCode) {
            case "PROMO10":
                this.discount = 0.15;
                break;
            case "PROMO20":
                this.discount = 0.20;
                break;
            default:
                throw new InvalidDiscountCodeException("Code de réduction invalide: " + discountCode);
        }
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