package com.example.hanora.vending;

public class Product {
    private String name;
    private String expireDate;
    private int availableNumber;
    private String brandLogo;
    private String description;

    public Product() {

    }

    public Product(String name, String expireDate, int availableNumber, String brandLogo, String description) {
        this.name = name;
        this.expireDate = expireDate;
        this.availableNumber = availableNumber;
        this.brandLogo = brandLogo;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public int getAvailableNumber() {
        return availableNumber;
    }

    public void setAvailableNumber(int availableNumber) {
        this.availableNumber = availableNumber;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", availableNumber=" + availableNumber +
                ", brandLogo='" + brandLogo + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
