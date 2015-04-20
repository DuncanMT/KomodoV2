package com.duncan.komodomyversion;

/**
 * Created by alihassan on 16/04/2015.
 */
public class CableInfo {
    protected String title;
    protected String price;
    protected String description;
    protected String quantity;
    protected String imgURL;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImgURL() {
        return this.imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getTitle() {
        return this.title;
    }


    public String getPrice() {
        return this.price;
    }

    public void setTitle(String title) {

        this.title = title;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    protected static final String TITLE_PREFIX = "Title: ";
    protected static final String TYPE_PREFIX = "Type: ";
    protected static final String PRICE_PREFIX = "Price: £";
}