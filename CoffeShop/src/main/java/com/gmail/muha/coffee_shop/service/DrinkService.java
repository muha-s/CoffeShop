package com.gmail.muha.coffee_shop.service;

import com.gmail.muha.coffee_shop.entity.CoffeeMachine;
import com.gmail.muha.coffee_shop.entity.CustomDrink;
import com.gmail.muha.coffee_shop.entity.Drink;
import com.gmail.muha.coffee_shop.repository.DrinkRepository;

public class DrinkService {

    private final DrinkRepository repository;

    public DrinkService(DrinkRepository repository) {
        this.repository = repository;
    }

    public void addDrink(String name, int aqua, int milk, int beans) {
        Drink drink = new CustomDrink(name, aqua, milk, beans);
        repository.add(drink);
    }

    public Drink findByName(String name) {
        return repository.findByName(name);
    }
}
