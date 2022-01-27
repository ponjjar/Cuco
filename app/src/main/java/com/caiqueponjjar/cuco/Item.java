package com.caiqueponjjar.cuco;

public class Item {
    private String itemTitle;
    private String itemAuthor;

    public Item(String itemTitle, String itemAuthor) {
        this.itemTitle = itemTitle;
        this.itemAuthor = itemAuthor;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemSubtitle() {
        return itemAuthor;
    }
}