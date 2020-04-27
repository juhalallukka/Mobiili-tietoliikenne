package com.example.a2_2_stock_monitor;

class Stock {

    public Stock(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    private String name;
    private  String id;
}
