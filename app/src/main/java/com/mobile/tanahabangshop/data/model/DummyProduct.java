package com.mobile.tanahabangshop.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lukas Dylan Adisurya on 3/7/2018.
 */

public class DummyProduct implements Parcelable{
    private String productCode;
    private String productName;
    private double productPrice;
    private int productStock;
    private String productImageUrl;

    public DummyProduct(String productCode, String productName, double productPrice, int productStock, String productImageUrl) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productImageUrl = productImageUrl;
    }

    protected DummyProduct(Parcel in) {
        productCode = in.readString();
        productName = in.readString();
        productPrice = in.readDouble();
        productStock = in.readInt();
        productImageUrl = in.readString();
    }

    public static final Creator<DummyProduct> CREATOR = new Creator<DummyProduct>() {
        @Override
        public DummyProduct createFromParcel(Parcel in) {
            return new DummyProduct(in);
        }

        @Override
        public DummyProduct[] newArray(int size) {
            return new DummyProduct[size];
        }
    };

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

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productCode);
        dest.writeString(productName);
        dest.writeDouble(productPrice);
        dest.writeInt(productStock);
        dest.writeString(productImageUrl);
    }
}
