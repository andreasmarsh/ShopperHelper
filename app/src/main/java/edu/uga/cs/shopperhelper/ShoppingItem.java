package edu.uga.cs.shopperhelper;

import android.util.Log;

import java.io.Serializable;

/**
 * This class represents a shopping item, including the company name,
 * phone number, URL, and some comments.
 */
@SuppressWarnings("serial") //With this annotation we are going to hide compiler warnings
public class ShoppingItem implements Serializable {

    public static final String DEBUG_TAG = "SI";

    private String itemName;
    private String itemDescription;
    private Double price;
    private String whoAdded;
    private String whoPurchased;

    public ShoppingItem()
    {
        this.itemName = null;
        this.itemDescription = null;
        this.price = null;
        this.whoAdded = null;
        this.whoPurchased = null;
    }

    public ShoppingItem( String itemName, String itemDescription, Double itemPrice, String whoAdded, String whoPurchased ) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = itemPrice;
        this.whoAdded = whoAdded;
        this.whoPurchased = whoPurchased;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        //Log.d( DEBUG_TAG, "item descrip --> " + itemDescription );
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double itemPrice) {
        this.price = itemPrice;
    }

    public String getWhoAdded() {
        return whoAdded;
    }

    public void setWhoAdded(String whoAdded) {
        this.whoAdded = whoAdded;
    }

    public String getWhoPurchased() { return whoPurchased; }

    public void setWhoPurchased(String whoPurchased) { this.whoPurchased = whoPurchased; }

    public String toString() {
        return itemName + " " + itemDescription + " " + price + " " + whoAdded + " " + whoPurchased;
    }
}

