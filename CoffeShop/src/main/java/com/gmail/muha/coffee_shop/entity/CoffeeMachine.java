package com.gmail.muha.coffee_shop.entity;

import java.util.List;
import java.util.Objects;

public class CoffeeMachine {


    private final int id;
    private int aqua;
    private int milk;
    private int beans;
    private List<Drink> drinks;
    private static int counter = 1;


    public CoffeeMachine(int aqua, int milk, int coffee, List<Drink> drinks) {
        this.id = counter++;
        this.aqua = aqua;
        this.milk = milk;
        this.beans = coffee;
        this.drinks = drinks;
    }


    public int getId() {
        return id;
    }

    public int getAqua() {
        return aqua;
    }

    public void setAqua(int aqua) {
        this.aqua = aqua;
    }

    public int getMilk() {
        return milk;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    public int getBeans() {
        return beans;
    }

    public void setBeans(int beans) {
        this.beans = beans;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CoffeeMachine that = (CoffeeMachine) o;
        return aqua == that.aqua && milk == that.milk && beans == that.beans;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aqua, milk, beans);
    }

    @Override
    public String toString() {
        return "CoffeeMachines{" +
                "aqua=" + aqua +
                ", milk=" + milk +
                ", coffee=" + beans +
                '}';
    }
}
