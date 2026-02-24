package com.gmail.muha.coffee_shop.service;

import com.gmail.muha.coffee_shop.entity.CoffeeMachine;
import com.gmail.muha.coffee_shop.entity.Drink;
import com.gmail.muha.coffee_shop.entity.StandardDrink;
import com.gmail.muha.coffee_shop.repository.CoffeeMachineRepository;
import com.gmail.muha.coffee_shop.validationUtils.ValidationDrink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoffeeMachineService {

    private final CoffeeMachineRepository repository;

    public CoffeeMachineService(CoffeeMachineRepository repository) {
        this.repository = repository;
    }

    public void createCustomDrink(int id, String name, int aqua, int milk, int beans) {
        CoffeeMachine machine = repository.findById(id);

        Drink drink = new Drink(name, aqua, milk, beans);

        machine.getDrinks().put(drink.getName(), drink);
    }

    public void addStandardDrink(int id, StandardDrink standardDrink) {
        CoffeeMachine machine = repository.findById(id);
        ValidationDrink.checkFoundDrink(standardDrink);

        Drink drink = new Drink(standardDrink);

        machine.getDrinks().put(drink.getName(), drink);
    }

    public Drink brew(int id, String name) {

        CoffeeMachine machine = repository.findById(id);
        return machine.brew(name);
    }

    public void addCoffeeMachine(int aqua, int milk, int beans) {
        Map<String, Drink> drinks = new HashMap<>();
        CoffeeMachine coffeeMachine = new CoffeeMachine(aqua, milk, beans, drinks);
        repository.add(coffeeMachine);
    }

    public List<CoffeeMachine> findAllMachine() {
        return repository.findAll();
    }

    public CoffeeMachine findById(int id) {
        return repository.findById(id);
    }

}
