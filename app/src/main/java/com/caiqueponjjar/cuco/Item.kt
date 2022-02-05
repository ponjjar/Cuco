package com.caiqueponjjar.cuco;

public class Item {
    var itemTitle : String? = ""
    var itemSubtitle : String? = ""
    var itemKey : String = ""

    constructor(itemTitle: String, itemSubtitle : String, itemKey : String)
    {
        this.itemTitle = itemTitle
        this.itemSubtitle = itemSubtitle
        this.itemKey = itemKey

    }
}