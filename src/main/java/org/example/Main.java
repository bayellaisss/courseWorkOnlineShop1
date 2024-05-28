package org.example;

import org.example.entity.Cart;
import org.example.entity.Order;
import org.example.entity.Product;
import org.example.entity.User;
import org.example.service.AuthService;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.*;

public class Main {
    private static User currentUser;
    private static Cart cart = new Cart();
    private static List<Product> products = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static AuthService authService = new AuthService();

    public static void main(String[] args) {
        initializeProducts();

        boolean running = true;
        while (running) {
            showLoginMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }

            if (currentUser != null) {
                while (running) {
                    showMainMenu();
                    choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    switch (choice) {
                        case 1:
                            showCatalog();
                            break;
                        case 2:
                            viewCart();
                            break;
                        case 3:
                            checkout();
                            break;
                        case 4:
                            viewUserAccount();
                            break;
                        case 5:
                            currentUser = null; // Logout
                            break;
                        case 6:
                            manageProducts();
                            break;
                        case 7:
                            searchProducts();
                            break;
                        default:
                            System.out.println("Неверный выбор. Попробуйте снова.");
                    }

                    if (currentUser == null) break; // Exit to login menu
                }
            }
        }
    }

    private static void initializeProducts() {
        products.add(new Product(1, "Футболка", "Качественная футболка", 19.99, 100));
        products.add(new Product(2, "Кроссовки", "Удобные кроссовки", 59.99, 200));
        // Добавьте больше товаров по мере необходимости
    }

    private static void showLoginMenu() {
        System.out.println("Добро пожаловать в наш магазин!");
        System.out.println("1. Войти");
        System.out.println("2. Зарегистрироваться");
        System.out.println("3. Выход");
        System.out.print("Выберите опцию: ");
    }

    private static void login() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        currentUser = authService.login(username, password);
        if (currentUser != null) {
            System.out.println("Вход выполнен успешно!");
        } else {
            System.out.println("Неверное имя пользователя или пароль.");
        }
    }

    private static void register() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        if (authService.userExists(username)) {
            System.out.println("Пользователь с таким именем уже существует.");
            return;
        }

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();

        if (authService.register(username, password, email)) {
            System.out.println("Регистрация прошла успешно! Теперь вы можете войти.");
        } else {
            System.out.println("Ошибка регистрации. Попробуйте снова.");
        }
    }

    private static void showMainMenu() {
        System.out.println("Добро пожаловать в наш магазин!");
        System.out.println("1. Каталог товаров");
        System.out.println("2. Корзина");
        System.out.println("3. Оформить заказ");
        System.out.println("4. Личный кабинет");
        System.out.println("5. Выйти");
        System.out.println("6. Управление товарами (CRUD)");
        System.out.println("7. Поиск товаров");
        System.out.print("Выберите опцию: ");
    }

    private static void showCatalog() {
        System.out.println("Каталог товаров:");
        for (Product product : products) {
            System.out.printf("ID: %d, Название: %s, Цена: %.2f\n", product.getId(), product.getName(), product.getPrice());
        }
        System.out.print("Введите ID товара для добавления в корзину (0 для возврата): ");
        int productId = scanner.nextInt();
        if (productId == 0) return;
        Product selectedProduct = getProductById(productId);
        if (selectedProduct != null) {
            System.out.print("Введите количество: ");
            int quantity = scanner.nextInt();
            cart.addToCart(selectedProduct, quantity);
            System.out.println("Товар добавлен в корзину.");
        } else {
            System.out.println("Товар не найден.");
        }
    }

    private static Product getProductById(int productId) {
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    private static void viewCart() {
        cart.viewCart();
        System.out.print("Введите ID товара для удаления из корзины (0 для возврата): ");
        int productId = scanner.nextInt();
        if (productId == 0) return;
        Product selectedProduct = getProductById(productId);
        if (selectedProduct != null) {
            cart.removeFromCart(selectedProduct);
            System.out.println("Товар удален из корзины.");
        } else {
            System.out.println("Товар не найден.");
        }
    }

    private static void checkout() {
        if (currentUser == null) {
            System.out.println("Вы должны войти, чтобы оформить заказ.");
            return;
        }

        System.out.print("Введите адрес доставки: ");
        String address = scanner.nextLine();
        System.out.print("Введите контактную информацию: ");
        String contactInfo = scanner.nextLine();
        Order order = new Order(1, currentUser, cart, address, contactInfo, new Date());
        currentUser.addOrder(order);
        order.confirmOrder();
        cart = new Cart(); // Очищаем корзину после заказа
    }

    private static void viewUserAccount() {
        if (currentUser == null) {
            System.out.println("Вы не авторизованы.");
            return;
        }

        System.out.println("Личный кабинет:");
        System.out.println("1. Просмотр истории заказов");
        System.out.println("2. Управление личными данными");
        System.out.print("Выберите опцию: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                viewOrderHistory();
                break;
            case 2:
                managePersonalData();
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private static void viewOrderHistory() {
        System.out.println("История заказов:");
        for (Order order : currentUser.getOrderHistory()) {
            System.out.printf("ID: %d, Дата: %s, Сумма: %.2f\n", order.getId(), order.getOrderDate().toString(), order.getCart().getTotalAmount());
        }
    }

    private static void managePersonalData() {
        System.out.print("Введите новый адрес: ");
        String address = scanner.nextLine();
        System.out.print("Введите новую контактную информацию: ");
        String contactInfo = scanner.nextLine();
        currentUser.updatePersonalData(address, contactInfo);
        System.out.println("Личные данные обновлены.");
    }

    private static void manageProducts() {
        System.out.println("Управление товарами:");
        System.out.println("1. Добавить товар");
        System.out.println("2. Обновить товар");
        System.out.println("3. Удалить товар");
        System.out.println("4. Показать все товары");
        System.out.print("Выберите опцию: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                addProduct();
                break;
            case 2:
                updateProduct();
                break;
            case 3:
                deleteProduct();
                break;
            case 4:
                showCatalog();
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private static void addProduct() {
        System.out.print("Введите название товара: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание товара: ");
        String description = scanner.nextLine();
        System.out.print("Введите цену товара: ");
        double price = scanner.nextDouble();
        System.out.print("Введите количество товара: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // consume newline


        int newId = products.size() + 1;
        Product newProduct = new Product(newId, name, description, price, quantity);
        products.add(newProduct);
        System.out.println("Товар добавлен.");
    }

    private static void updateProduct() {
        System.out.print("Введите ID товара для обновления: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Product product = getProductById(productId);
        if (product == null) {
            System.out.println("Товар не найден.");
            return;
        }

        System.out.print("Введите новое название товара: ");
        String name = scanner.nextLine();
        System.out.print("Введите новое описание товара: ");
        String description = scanner.nextLine();
        System.out.print("Введите новую цену товара: ");
        double price = scanner.nextDouble();
        System.out.print("Введите новое количество товара: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Введите новый путь к изображению товара: ");
        String imagePath = scanner.nextLine();

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        System.out.println("Товар обновлен.");
    }

    private static void deleteProduct() {
        System.out.print("Введите ID товара для удаления: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Product product = getProductById(productId);
        if (product == null) {
            System.out.println("Товар не найден.");
            return;
        }

        products.remove(product);
        System.out.println("Товар удален.");
    }

    private static void searchProducts() {
        System.out.print("Введите ключевое слово для поиска: ");
        String keyword = scanner.nextLine().toLowerCase();

        System.out.println("Результаты поиска:");
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(keyword) || product.getDescription().toLowerCase().contains(keyword)) {
                System.out.printf("ID: %d, Название: %s, Цена: %.2f\n", product.getId(), product.getName(), product.getPrice());
            }
        }
    }
}