package com.gmail.muha.coffee_shop.main;

import com.gmail.muha.coffee_shop.entity.*;
import com.gmail.muha.coffee_shop.repository.CoffeeMachineRepository;
import com.gmail.muha.coffee_shop.service.CoffeeMachineService;
import com.gmail.muha.coffee_shop.ui.CoffeeConsoleUI;

public class Main {
    public static void main(String[] args) {

        CoffeeMachineRepository repository = new CoffeeMachineRepository();
        CoffeeMachineService service = new CoffeeMachineService(repository);
        CoffeeConsoleUI ui = new CoffeeConsoleUI(service);

        ui.run();




    }
}