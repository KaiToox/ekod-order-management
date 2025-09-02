package fr.ekod;

import fr.ekod.exceptions.OutOfStockException;

public class Product {
    private String name;
    private double price;
    private int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void decreaseStock() throws OutOfStockException {
        if (stock <= 0) {
            throw new OutOfStockException("Le produit " + name + " est en rupture de stock");
        }
        stock--;
    }

    public void increaseStock() {
        stock++;
    }
}