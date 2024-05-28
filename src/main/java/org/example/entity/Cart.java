package org.example.entity;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> items = new HashMap<>();

    public void addToCart(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public void removeFromCart(Product product) {
        items.remove(product);
    }

    public void updateQuantity(Product product, int quantity) {
        if (items.containsKey(product)) {
            items.put(product, quantity);
        }
    }

    public double getTotalAmount() {
        return items.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public void viewCart() {
        System.out.println("Ваша корзина:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.printf("Товар: %s, Количество: %d, Цена: %.2f\n", product.getName(), quantity, product.getPrice());
        }
        System.out.printf("Итоговая сумма: %.2f\n", getTotalAmount());
    }
}
