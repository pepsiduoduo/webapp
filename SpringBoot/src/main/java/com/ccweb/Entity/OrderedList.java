package com.ccweb.Entity;

public class OrderedList {
    private int position;
    private String items;

    public OrderedList(int position, String items){
        this.position = position;
        this.items = items;
    }

    public OrderedList(){};

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
