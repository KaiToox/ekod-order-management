package fr.ekod;

import java.util.ArrayList;
import java.util.List;

import fr.ekod.exceptions.OutOfStockException;

public class ShoppingCart {
    private List<Product> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) throws OutOfStockException {
        if (product.getStock() <= 0) {
            throw new OutOfStockException("Le produit " + product.getName() + " est en rupture de stock");
        }
        products.add(product);
        product.decreaseStock();
    }

    public void removeProduct(Product product) {
        if (products.remove(product)) {
            product.increaseStock();
        }
    }

    public double getTotalPrice() {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public List<Product> getProductList() {
        return new ArrayList<>(products);
    }

    public int getNumberOfItems() {
        return products.size();
    }
}