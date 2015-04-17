package com.duncan.komodomyversion;

/**
 * Created by alihassan on 16/04/2015.
 */
public class CableInfo {
    protected String title;
    protected String type;
    protected String price;

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    protected static final String TITLE_PREFIX = "Title: ";
    protected static final String TYPE_PREFIX = "Type: ";
    protected static final String PRICE_PREFIX = "Price: £";
}