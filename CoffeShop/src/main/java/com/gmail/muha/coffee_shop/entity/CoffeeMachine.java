package com.gmail.muha.coffee_shop.entity;

import java.util.Map;
import java.util.Objects;

public class CoffeeMachine {

    private final int id;
    private int aqua;
    private int milk;
    private int beans;
    private final Map<String, Drink> drinks;
    private static int counter = 1;


    public CoffeeMachine(int aqua, int milk, int coffee, Map<String, Drink> drinks) {
        this.id = counter++;
        this.aqua = aqua;
        this.milk = milk;
        this.beans = coffee;
        this.drinks = drinks;
    }

    public void addAqua(int weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Нельзя добавить нисколько воды");
        }
        this.aqua += weight;
    }

    public void addMilk(int weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Нельзя добавить нисколько молока");
        }
        this.milk += weight;
    }

    public void addBeans(int weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Нельзя добавить нисколько кофе");
        }
        this.beans += weight;
    }

    public Drink brew(String name) {

        if (!drinks.containsKey(name)) {
            throw new IllegalArgumentException("Такого напитка в списке кофемашины под ID: " + id + " нет");
        }
        Drink drink = drinks.get(name);

        if (!hasEnoughResources(drink)) {
            throw new IllegalArgumentException("Не хватает ингредиентов в кофемашине под ID: " + id);
        }

       aqua -= drink.getAqua();
       milk -= drink.getMilk();
       beans -= drink.getBeans();

        return drink;
    }

    private boolean hasEnoughResources( Drink drink) {

        return aqua >= drink.getAqua()
                && milk >= drink.getMilk()
                && beans >= drink.getBeans();
    }


    public int getId() {
        return id;
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

    public Map<String, Drink> getDrinks() {
        return drinks;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CoffeeMachine that = (CoffeeMachine) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CoffeeMachine{" +
                "id=" + id +
                ", aqua=" + aqua +
                ", milk=" + milk +
                ", beans=" + beans +
                ", drinks=" + drinks +
                '}';
    }
}
