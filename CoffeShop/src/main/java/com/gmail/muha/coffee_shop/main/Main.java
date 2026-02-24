package com.gmail.muha.coffee_shop.main;

import com.gmail.muha.coffee_shop.exception.EntityNotFoundException;
import com.gmail.muha.coffee_shop.repository.CoffeeMachineRepository;
import com.gmail.muha.coffee_shop.service.CoffeeMachineService;
import com.gmail.muha.coffee_shop.ui.CoffeeConsoleUI2;

public class Main {
    public static void main(String[] args) {

        CoffeeMachineRepository repository = new CoffeeMachineRepository();
        CoffeeMachineService service = new CoffeeMachineService(repository);
        CoffeeConsoleUI2 ui = new CoffeeConsoleUI2(service);

        ui.run();










    }
}