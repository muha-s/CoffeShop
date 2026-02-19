package com.gmail.muha.coffee_shop.repository;

import com.gmail.muha.coffee_shop.entity.Drink;

import java.util.HashMap;
import java.util.Map;

public class DrinkRepository {

    private final Map<String, Drink> drinks = new HashMap<>();

    public void add(Drink drink) {
        drinks.put(drink.getName(), drink);
    }

    public Drink findByName(String name) {
        return drinks.get(name);
    }

}
