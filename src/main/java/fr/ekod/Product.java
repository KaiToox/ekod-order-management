package fr.ekod;

public class Product {
    private String name;
    private double price;
    private int stockQuantity;

    public Product(String name, double price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        price = price * 1.01;
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void decrementStock() {
        if (stockQuantity > 0) {
            stockQuantity--;
        }
    }

    public void incrementStock() {
        stockQuantity++;
    }
}