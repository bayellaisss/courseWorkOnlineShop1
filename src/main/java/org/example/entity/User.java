package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String address;
    private String contactInfo;
    private List<Order> orderHistory = new ArrayList<>();

    public User(int id, String username, String password, String email, String address, String contactInfo, List<Order> orderHistory) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.contactInfo = contactInfo;
        this.orderHistory = orderHistory;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }




    public void updatePersonalData(String address, String contactInfo) {
        this.address = address;
        this.contactInfo = contactInfo;
    }

    public void updateNotificationSettings(boolean emailNotifications) {
        // Логика обновления настроек уведомлений
    }

    public List<Order> viewOrderHistory() {
        return orderHistory;
    }

    public void addOrder(Order order) {
        orderHistory.add(order);
    }
}
