package com.ashcollege.entities;

public class Product {
    private int id;
    private String name;
    private double price;
    private String image;

    public Product(String lineData) {
        String[] dataItem = lineData.split(",");
        this.name = dataItem[0];
        this.price = Integer.parseInt(dataItem[1]);
        this.image = dataItem[2];

    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
