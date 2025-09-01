# Order Management

## Overview
This is a Java-based order management system that simulates an e-commerce shopping experience. The system handles product management, shopping cart operations, order processing, and invoice generation with features like discount codes and delivery fee calculations.

## Architecture

The project follows an object-oriented design with the following key components:

### Core classes
- **Product**: Manages product information (name, price, stock)
- **ShoppingCart**: Handles the collection of products and cart operations
- **Order**: Processes orders with discount and delivery fee calculations
- **Invoice**: Generates formatted invoices for orders

### Exception handling
- **OutOfStockException**: Thrown when attempting to add out-of-stock products
- **InvalidDiscountCodeException**: Thrown when invalid discount codes are used

## Features

### 1. Product management
- Product creation with name, price, and stock quantity
- Stock level tracking
- Automatic stock updates when products are added/removed from cart

### 2. Shopping cart
- Add/remove products
- Stock validation
- Total price calculation
- Product list management

### 3. Order processing
- Shopping cart integration
- Discount code application (PROMO10, PROMO20)
- Delivery fee calculation
- Total price computation

### 4. Invoice generation
- Detailed item listing
- Price breakdown
- Discount display
- Final total calculation

## Technical details

### Project structure

```
order-management/
├── src/
│ ├── main/java/fr/ekod/
│ │ ├── Product.java
│ │ ├── ShoppingCart.java
│ │ ├── Order.java
│ │ ├── Invoice.java
│ │ ├── exceptions/
│ │ │ ├── OutOfStockException.java
│ │ │ └── InvalidDiscountCodeException.java
│ │ └── Main.java
│ └── test/java/
│   └── OrderManagementTest.java
└── pom.xml
```

### Dependencies

- JUnit 5.9.2 for testing
- JaCoCo for code coverage

## Getting started

### Prerequisites

- Java 17 or higher
- Maven

### Building the project

```bash
mvn clean install
```

### Running tests

```bash
mvn clean test
```

## Example usage

```java
// Create products
Product laptop = new Product("Laptop", 999.99, 5);
Product phone = new Product("Phone", 499.99, 3);

// Create shopping cart
ShoppingCart cart = new ShoppingCart();

// Add products
cart.addProduct(laptop);
cart.addProduct(phone);

// Create order
Order order = new Order(cart);

// Apply discount
order.applyDiscount("PROMO10");

// Generate invoice
Invoice invoice = new Invoice(order);
System.out.println(invoice.generateInvoice());
```
