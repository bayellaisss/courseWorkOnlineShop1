package org.example.entity;
import java.util.Date;

public class Order {
    private int id;
    private User user;
    private Cart cart;
    private String deliveryAddress;
    private String contactInfo;
    private Date orderDate;

    public Order(int id, User user, Cart cart, String deliveryAddress, String contactInfo, Date orderDate) {
        this.id = id;
        this.user = user;
        this.cart = cart;
        this.deliveryAddress = deliveryAddress;
        this.contactInfo = contactInfo;
        this.orderDate = orderDate;
    }

    public Order() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void confirmOrder() {
        System.out.println("Ваш заказ подтвержден.");
        System.out.printf("Адрес доставки: %s\nКонтактная информация: %s\nДата заказа: %s\n",
                deliveryAddress, contactInfo, orderDate.toString());
    }
}