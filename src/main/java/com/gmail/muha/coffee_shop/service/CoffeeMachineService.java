package com.gmail.muha.coffee_shop.service;

import com.gmail.muha.coffee_shop.entity.CoffeeMachine;
import com.gmail.muha.coffee_shop.entity.Drink;
import com.gmail.muha.coffee_shop.repository.CoffeeMachineRepository;
import com.gmail.muha.coffee_shop.repository.DrinkRepository;

import java.io.IOException;
import java.util.List;

public class CoffeeMachineService {

    private final CoffeeMachineRepository coffeeMachineRepository;
    private final DrinkRepository drinkRepository;

    public CoffeeMachineService(CoffeeMachineRepository coffeeMachineRepository, DrinkRepository drinkRepository) {
        this.coffeeMachineRepository = coffeeMachineRepository;
        this.drinkRepository = drinkRepository;
    }

    public void createCustomDrink(int id, String name, int aqua, int milk, int beans) throws IOException {
        CoffeeMachine machine = coffeeMachineRepository.findById(id);

        Drink drink = new Drink(name, aqua, milk, beans);
        drinkRepository.saveDrink(drink);
        machine.getDrinks().put(drink.getName(), drink);

        List<CoffeeMachine> coffeeMachines = findAllMachine();

        for (CoffeeMachine coffeeMachine : coffeeMachines) {
            if (coffeeMachine.getId() == machine.getId()) {
                coffeeMachine.getDrinks().put(drink.getName(), drink);
            }
        }
        coffeeMachineRepository.rewriteCoffeeMachine(coffeeMachines);
    }

    public Drink brew(int id, String name) throws IOException {

        List<CoffeeMachine> coffeeMachines = findAllMachine();

        Drink drink = null;

        for (CoffeeMachine coffeeMachine : coffeeMachines) {
            if (coffeeMachine.getId() == id) {
                drink = coffeeMachine.brew(name);
            }
        }
        coffeeMachineRepository.rewriteCoffeeMachine(coffeeMachines);
        return drink;
    }

    public void addCoffeeMachine(int aqua, int milk, int beans) throws IOException {

        List<CoffeeMachine> coffeeMachines = findAllMachine();
        int id = coffeeMachines.size();
        id++;
        CoffeeMachine coffeeMachine = new CoffeeMachine(id, aqua, milk, beans);
        coffeeMachineRepository.save(coffeeMachine);
    }

    public List<CoffeeMachine> findAllMachine() throws IOException {
        return coffeeMachineRepository.getAllCoffeeMachine();
    }

    public CoffeeMachine findById(int id) throws IOException {
        return coffeeMachineRepository.findById(id);
    }

    public void addAqua(int id, int weight) throws IOException {

        List<CoffeeMachine> coffeeMachines = findAllMachine();
        for (CoffeeMachine coffeeMachine : coffeeMachines) {
            if (coffeeMachine.getId() == id) {
                coffeeMachine.addAqua(weight);
            }
        }
        coffeeMachineRepository.rewriteCoffeeMachine(coffeeMachines);
    }

    public void addMilk(int id, int weight) throws IOException {
        List<CoffeeMachine> coffeeMachines = findAllMachine();
        for (CoffeeMachine coffeeMachine : coffeeMachines) {
            if (coffeeMachine.getId() == id) {
                coffeeMachine.addMilk(weight);
            }
        }
        coffeeMachineRepository.rewriteCoffeeMachine(coffeeMachines);
    }

    public void addBeans(int id, int weight) throws IOException {
        List<CoffeeMachine> coffeeMachines = findAllMachine();
        for (CoffeeMachine coffeeMachine : coffeeMachines) {
            if (coffeeMachine.getId() == id) {
                coffeeMachine.addBeans(weight);
            }
        }
        coffeeMachineRepository.rewriteCoffeeMachine(coffeeMachines);
    }

}
