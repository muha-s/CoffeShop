package com.gmail.muha.coffee_shop.entity;

public enum StandardDrink {

    AMERICANO("Americano", 150, 0, 9),
    ESPRESSO("Espresso", 30, 0, 9),
    CAPPUCCINO("Cappuccino", 30, 100, 9),
    LATTE("Latte", 30, 200, 9),
    FLAT_WHITE("Flat White", 60, 120, 15);

    private final String name;
    private final int aqua;
    private final int milk;
    private final int beans;

    StandardDrink(String name, int aqua, int milk, int beans) {
        this.name = name;
        this.aqua = aqua;
        this.milk = milk;
        this.beans = beans;
    }

    public String getName() {
        return name;
    }

    public int getAqua() {
        return aqua;
    }

    public int getMilk() {
        return milk;
    }

    public int getBeans() {
        return beans;
    }
}
