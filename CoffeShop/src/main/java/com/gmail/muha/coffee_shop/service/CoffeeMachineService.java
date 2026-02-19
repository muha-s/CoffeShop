package com.gmail.muha.coffee_shop.service;

import com.gmail.muha.coffee_shop.entity.CoffeeMachine;
import com.gmail.muha.coffee_shop.entity.Drink;
import com.gmail.muha.coffee_shop.repository.CoffeeMachineRepository;

import java.util.ArrayList;
import java.util.List;

public class CoffeeMachineService {

    private final CoffeeMachineRepository repository;

    public CoffeeMachineService(CoffeeMachineRepository repository) {
        this.repository = repository;
    }

    public void addCoffeeMachine(int aqua, int milk, int beans) {
        List<Drink> drinks = new ArrayList<>();
        CoffeeMachine coffeeMachine = new CoffeeMachine(aqua, milk, beans, drinks);
        repository.add(coffeeMachine);
    }

    public boolean addDrink(int idCoffeeMachines, Drink drink) {

        CoffeeMachine coffeeMachine = repository.findById(idCoffeeMachines);
        if (coffeeMachine == null) {
            return false;
        }
        if (coffeeMachine.getDrinks().contains(drink)) {
            return false;
        }
        coffeeMachine.getDrinks().add(drink);
        return true;
    }

    public void brew(int idCoffeeMachines, Drink drink) {

        CoffeeMachine coffeeMachine = repository.findById(idCoffeeMachines);
        if (coffeeMachine == null) {
            System.out.println("Такой кофемашины не существует");
            return;
        }
        if (!coffeeMachine.getDrinks().contains(drink)) {
            System.out.println("Я не знаю такого рецепта");
            return;
        }

        if (!checkingOfProducts(coffeeMachine, drink)) {
            return;
        }

        coffeeMachine.setAqua(coffeeMachine.getAqua() - drink.getAqua());
        coffeeMachine.setMilk(coffeeMachine.getMilk() - drink.getMilk());
        coffeeMachine.setBeans(coffeeMachine.getBeans() - drink.getBeans());

        System.out.println("Возьмите свой " + drink.getName());

    }

    private boolean isEnoughMilk(CoffeeMachine coffeeMachine, int milk) {

        return coffeeMachine.getMilk() >= milk;
    }

    private boolean isEnoughAqua(CoffeeMachine coffeeMachine, int aqua) {
        return coffeeMachine.getAqua() >= aqua;
    }

    private boolean isEnoughBeans(CoffeeMachine coffeeMachine, int beans) {

        return coffeeMachine.getBeans() >= beans;
    }

    private boolean checkingOfProducts(CoffeeMachine coffeeMachine, Drink drink) {

        int count = 0;
        StringBuilder info = new StringBuilder("");

        if (!isEnoughAqua(coffeeMachine, drink.getAqua())) {
            info.append("У меня закончилась(лись) вода");
            count++;
        }

        if (!isEnoughMilk(coffeeMachine, drink.getMilk())) {
            if (count == 0) {
                info.append("У меня закончилось молоко");
            } else {
                info.append(",  молоко");
            }
            count++;
        }

        if (!isEnoughBeans(coffeeMachine, drink.getBeans())) {
            if (count == 0) {
                info.append("У меня закончились зерна кофе");
            } else {
                info.append(", а также зерна кофе");
            }
            count++;
        }

        info.append(" для приготовления этого напитка");

        if (count == 0) {
            return true;
        } else {
            System.out.println(info.toString());
            return false;

        }
    }

    public List<CoffeeMachine> findAll() {
        return repository.findAll();
    }

    public CoffeeMachine findById(int id) {
        return repository.findById(id);
    }

}
