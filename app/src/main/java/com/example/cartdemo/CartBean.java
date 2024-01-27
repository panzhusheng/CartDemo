package com.example.cartdemo;

public class CartBean {
    private String name;
    private String cover;
    private boolean isSelect;
    private int number;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CartBean(String name, String cover, boolean isSelect, int number, double price) {
        this.name = name;
        this.cover = cover;
        this.isSelect = isSelect;
        this.number = number;
        this.price = price;
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", isSelect=" + isSelect +
                ", number=" + number +
                ", price=" + price +
                '}';
    }
}
