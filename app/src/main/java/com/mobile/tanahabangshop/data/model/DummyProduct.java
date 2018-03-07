package com.mobile.tanahabangshop.data.model;

/**
 * Created by Lukas Dylan Adisurya on 3/7/2018.
 */

public class DummyProduct {
    private String productCode;
    private String productName;
    private double productPrice;
    private int productStock;

    public DummyProduct(String productCode, String productName, double productPrice, int productStock) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }
}
