package com.gmail.muha.coffee_shop.entity;

import java.util.*;

public class CoffeeMachine {

    private final int id;
    private int aqua;
    private int milk;
    private int beans;
    private final Map<String, Drink> drinks;


    public CoffeeMachine(int id, int aqua, int milk, int coffee) {
        this.id = id;
        this.aqua = aqua;
        this.milk = milk;
        this.beans = coffee;
        this.drinks = downloadRecipes();
    }

    private CoffeeMachine(int id, int aqua, int milk, int coffee, Map<String, Drink> drinks) {
        this.id = id;
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

    private boolean hasEnoughResources(Drink drink) {

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

    public String toFileString() {

        StringBuilder result = new StringBuilder();
        result.append(id).append(";");
        result.append(aqua).append(";");
        result.append(milk).append(";");
        result.append(beans).append("|");

        List<Drink> drinksList = new ArrayList<>(drinks.values());


        for (Drink drink : drinksList) {
            String drinkList = drink.toFileString();
            result.append(drinkList).append(";");
        }
        return result.toString();
    }

    public static CoffeeMachine fromFileString(String value) {
        String[] coffeeMachine = value.split("\\|");

        String machine = coffeeMachine[0];
        String[] drinks = coffeeMachine[1].split(";");

        coffeeMachine = machine.split(";");

        int id = Integer.parseInt(coffeeMachine[0]);
        int aqua = Integer.parseInt(coffeeMachine[1]);
        int milk = Integer.parseInt(coffeeMachine[2]);
        int beans = Integer.parseInt(coffeeMachine[3]);

        Map<String, Drink> drinkMap = new HashMap<>();

        for (String drink : drinks) {
            Drink drink1 = Drink.fromFileString(drink);
            drinkMap.put(drink1.getName(), drink1);
        }
        return new CoffeeMachine(id, aqua, milk, beans, drinkMap);

    }

    private Map<String, Drink> downloadRecipes() {
        Map<String, Drink> map = new HashMap<>();
        map.put(StandardDrink.AMERICANO.getName(), new Drink(StandardDrink.AMERICANO));
        map.put(StandardDrink.LATTE.getName(), new Drink(StandardDrink.LATTE));
        map.put(StandardDrink.FLAT_WHITE.getName(), new Drink(StandardDrink.FLAT_WHITE));
        map.put(StandardDrink.CAPPUCCINO.getName(), new Drink(StandardDrink.CAPPUCCINO));
        map.put(StandardDrink.ESPRESSO.getName(), new Drink(StandardDrink.ESPRESSO));
        return map;
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
