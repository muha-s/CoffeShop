package com.gmail.muha.coffee_shop.main;

import com.gmail.muha.coffee_shop.repository.CoffeeMachineRepository;
import com.gmail.muha.coffee_shop.repository.DrinkRepository;
import com.gmail.muha.coffee_shop.service.CoffeeMachineService;
import com.gmail.muha.coffee_shop.ui.CoffeeConsoleUI2;
import com.gmail.muha.coffee_shop.gui.Gui;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        CoffeeMachineRepository coffeeMachineRepository = new CoffeeMachineRepository();
        DrinkRepository drinkRepository = new DrinkRepository();
        CoffeeMachineService service = new CoffeeMachineService(coffeeMachineRepository, drinkRepository);
        CoffeeConsoleUI2 ui = new CoffeeConsoleUI2(service);

       //ui.run();

        Gui gui = new Gui(service);


    }
}