package fr.ekod;

public class Invoice {
    private Order order;

    public Invoice(Order order) {
        this.order = order;
    }

    public String generateInvoice() {
        StringBuilder invoice = new StringBuilder();
        invoice.append("=== FACTURE ===\n\n");
        invoice.append("Articles:\n");
        
        for (Product product : order.getShoppingCart().getProductList()) {
            invoice.append(String.format("- %s: %.2f €\n", 
                product.getName(), product.getPrice()));
        }
        
        invoice.append(String.format("\nSous-total: %.2f €\n", 
            order.getShoppingCart().getTotalPrice()));
        invoice.append(String.format("Frais de livraison: %.2f €\n", 
            order.getDeliveryFee()));
        
        if (order.getDiscount() > 0) {
            invoice.append(String.format("Remise: %.2f%%\n", 
                order.getDiscount()));
        }
        
        invoice.append(String.format("\nTotal: %.2f €\n", 
            order.getTotalPrice()));
        
        return invoice.toString();
    }
}
