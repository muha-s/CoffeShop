package com.gmail.muha.coffee_shop.entity;

import java.util.Objects;

public abstract class Drink {

    private String name;
    private final int aqua;
    private final int milk;
    private final int beans;


    public Drink(String name, int aqua, int milk, int beans) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return aqua == drink.aqua && milk == drink.milk && beans == drink.beans && Objects.equals(name, drink.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, aqua, milk, beans);
    }
}
