package com.gmail.muha.coffee_shop.service;

import com.gmail.muha.coffee_shop.entity.Drink;
import com.gmail.muha.coffee_shop.exception.EntityNotFoundException;
import com.gmail.muha.coffee_shop.repository.DrinkRepository;

import java.io.IOException;
import java.util.List;

public class DrinkService {
    private DrinkRepository repository;

    public DrinkService(DrinkRepository repository) {
        this.repository = repository;
    }

    public void createDrink(String name, int aqua, int milk, int beans) throws IOException {

        List<Drink> drinks = getAllDrinks();
        for (Drink drink : drinks) {
            if (drink.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Drink with name '" + drink.getName() + "' already exists");
            }
        }
        Drink currentDrink = new Drink(name, aqua, milk, beans);
        repository.saveDrink(currentDrink);
    }

    public List<Drink> getAllDrinks() throws IOException {
        return repository.getAllDrinks();
    }

    public Drink findByName(String name) throws IOException {
       Drink drink = repository.findByName(name);

       if(drink == null){
           throw new EntityNotFoundException("Кофе с именем " + name + " не найдено");
       }
       return drink;
    }
}
