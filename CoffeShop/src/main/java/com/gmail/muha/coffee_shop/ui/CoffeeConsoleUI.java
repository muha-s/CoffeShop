package com.gmail.muha.coffee_shop.ui;

import com.gmail.muha.coffee_shop.entity.*;
import com.gmail.muha.coffee_shop.repository.DrinkRepository;
import com.gmail.muha.coffee_shop.service.CoffeeMachineService;
import com.gmail.muha.coffee_shop.service.DrinkService;

import java.util.Scanner;

public class CoffeeConsoleUI {

    private Scanner scanner = new Scanner(System.in);
    private CoffeeMachineService machineService;
    private DrinkService drinkService;

    public CoffeeConsoleUI(CoffeeMachineService machineService) {
        this.machineService = machineService;
        this.drinkService = new DrinkService(new DrinkRepository());
    }

    public void run() {
        menuOne();
    }

    private void menuOne() {

        System.out.println("\n1. Добавить кофемашину");
        System.out.println("2. Выбрать кофемашину");
        System.out.print("Ваш выбор: ");

        int id = isValidInMenuOne(scanner);

        switch (id) {
            case 1 -> {
                addCoffeeMachine();
                menuOne();
            }
            case 2 -> menuTwo();
            default -> {
                System.out.println("Не верные данные. Попробуйте еще раз");
                menuOne();
            }
        }
    }

    private void menuTwo() {

        if (!findAllCoffeeMachine()) {
            menuOne();
            return;
        }

        System.out.print("Для выхода в главное меню нажмите: \"0\"");
        System.out.print(". Либо выберете из списка кофемашину по ID: ");

        int value = isValidMenuTwo(scanner);

        if (value == 0) {
            menuOne();
            return;
        }

        if (machineService.findById(value) == null) {
            System.out.print("Неверные данные, такого ID не существует\n");
            menuTwo();
            return;
        }
        menuThree(value);
    }

    private void menuThree(int id) {

        CoffeeMachine coffeeMachine = machineService.findById(id);

        System.out.println("\nРаботаем с кофемашиной c ID: " + coffeeMachine.getId());
        System.out.println("1. Добавить готовый рецепт кофе в машину");
        System.out.println("2. Посмотреть наличие продуктов");
        System.out.println("3. Добавит воды");
        System.out.println("4. Добавить молока");
        System.out.println("5. Добавить зерен кофе");
        System.out.println("6. Заварить кофе");
        System.out.println("7. Назад");
        System.out.println("8. Выход в главное меню");
        System.out.println("9. Добавить свой рецепт кофе в машину");

        System.out.print("Ваш выбор: ");

        int selection = isValid(scanner);

        switch (selection) {
            case 1 -> addRecipe(coffeeMachine);
            case 2 -> printListOfProducts(coffeeMachine);
            case 3 -> addAqua(coffeeMachine);
            case 4 -> addAMilk(coffeeMachine);
            case 5 -> addBeans(coffeeMachine);
            case 6 -> brewCoffee(coffeeMachine);
            case 7 -> menuTwo();
            case 8 -> menuOne();
            case 9 -> addCustomDrink(coffeeMachine.getId());

            default -> {
                System.out.println("Не верные данные");
                menuThree(id);
            }
        }
    }

    private void addCoffeeMachine() {

        System.out.println("\nСоздаем новую кофемашину");
        System.out.print("Добавьте воды в мл: ");
        int amountOfAqua = isValid(scanner);

        System.out.print("Добавьте молока в мл: ");
        int amountOfMilk = isValid(scanner);

        System.out.print("Засыпьте зерен кофе в кг: ");
        int amountOfBeans = isValid(scanner);

        machineService.addCoffeeMachine(amountOfAqua, amountOfMilk, amountOfBeans);
        System.out.println("Кофемашина успешно создана");
    }

    private void brewCoffee(CoffeeMachine coffeeMachine) {

        if (coffeeMachine.getDrinks().isEmpty()) {
            System.out.println("У этой машины нет готовых рецептов, загрузите рецепт");
            menuThree(coffeeMachine.getId());
            return;
        }

        for (int i = 0; i < coffeeMachine.getDrinks().size(); i++) {
            if (i == 0){
                System.out.println();
            }
            System.out.println(i + 1 + ". " + coffeeMachine.getDrinks().get(i).getName());
        }
        System.out.print("Выберете напиток из доступных: ");

        int numberDrink = isValid(scanner);

        if (coffeeMachine.getDrinks().size() < numberDrink || numberDrink == 0) {
            System.out.println("Такого напитка в меню нет");
            brewCoffee(coffeeMachine);
        }

        machineService.brew(coffeeMachine.getId(), coffeeMachine.getDrinks().get(numberDrink - 1));
        menuThree(coffeeMachine.getId());

    }

    private void addRecipe(CoffeeMachine coffeeMachine) {

        System.out.println("\nРецепт какого кофе нужно добавить в кофемашину с ID: " + coffeeMachine.getId() + "?");
        System.out.println("1. Americano");
        System.out.println("2. Cappuccino");
        System.out.println("3. Espresso");
        System.out.println("4. FlatWhite");
        System.out.println("5. Latte");
        System.out.println("6. Выход");
        System.out.print("Ваш выбор: ");

        int numberCoffee = isValid(scanner);
        switch (numberCoffee) {
            case 1 -> {
                coffeeMachine.getDrinks().add(new Americano());
                addRecipe(coffeeMachine);
            }
            case 2 -> {
                coffeeMachine.getDrinks().add(new Cappuccino());
                addRecipe(coffeeMachine);
            }
            case 3 -> {
                coffeeMachine.getDrinks().add(new Espresso());
                addRecipe(coffeeMachine);
            }
            case 4 -> {
                coffeeMachine.getDrinks().add(new FlatWhite());
                addRecipe(coffeeMachine);
            }
            case 5 -> {
                coffeeMachine.getDrinks().add(new Latte());
                addRecipe(coffeeMachine);
            }
            case 6 -> menuThree(coffeeMachine.getId());
            default -> {
                System.out.println("Такого рецепта не существует");
                addRecipe(coffeeMachine);
            }
        }
    }

    private boolean findAllCoffeeMachine() {
        if (machineService.findAll().isEmpty()) {
            System.out.println("\nРаботающих кофемашин нет");
            return false;
        }

        System.out.println("\nСписок доступных кофемашин:");
        for (int i = 0; i < machineService.findAll().size(); i++) {
            System.out.println(i + 1 + ". ID: " + machineService.findAll().get(i).getId());
        }
        return true;
    }

    private int isValidInMenuOne(Scanner scanner) {

        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Это не целое число. Попробуйте еще раз.");
                menuOne();
            }
        }
    }

    private int isValidMenuTwo(Scanner scanner) {

        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Это не целое число. Попробуйте еще раз.\n");
                menuTwo();
            }
        }
    }

    private int isValid(Scanner scanner) {

        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Это не целое число. Попробуйте еще раз: ");
            }
        }
    }

    private void printListOfProducts(CoffeeMachine coffeeMachine) {

        System.out.println("\nВ кофемашине под ID: " + coffeeMachine.getId() + "\n" +
                coffeeMachine.getAqua() + " мл воды; " +
                coffeeMachine.getMilk() + " мл молока; " +
                coffeeMachine.getBeans() + " гр кофейных зерен.");
        menuThree(coffeeMachine.getId());
    }

    private void addAqua(CoffeeMachine coffeeMachine) {

        System.out.print("\nСколько мл воды добавить в кофемашину с ID " + coffeeMachine.getId() + ": ");
        int valueAqua = isValid(scanner);

        if (valueAqua < 0) {
            System.out.println("Нельзя добавить отрицательное количество воды");
            addAqua(coffeeMachine);
        }
        coffeeMachine.setAqua(coffeeMachine.getAqua() + valueAqua);

        menuThree(coffeeMachine.getId());
    }

    private void addAMilk(CoffeeMachine coffeeMachine) {

        System.out.print("\nСколько мл молока добавить в кофемашину с ID " + coffeeMachine.getId() + ": ");
        int valueMilk = isValid(scanner);

        if (valueMilk < 0) {
            System.out.println("Нельзя добавить отрицательное количество молока");
            addAMilk(coffeeMachine);
        }
        coffeeMachine.setMilk(coffeeMachine.getMilk() + valueMilk);

        menuThree(coffeeMachine.getId());
    }

    private void addBeans(CoffeeMachine coffeeMachine) {

        System.out.print("\nСколько гр кофейных зерен добавить добавить в кофемашину с ID " + coffeeMachine.getId() + ": ");
        int valueBeans = isValid(scanner);

        if (valueBeans < 0) {
            System.out.println("Нельзя добавить отрицательное количество зерен кофе");
            addBeans(coffeeMachine);
        }
        coffeeMachine.setBeans(coffeeMachine.getBeans() + valueBeans);

        menuThree(coffeeMachine.getId());
    }

    private void addCustomDrink(int coffeeMachineID){

        System.out.println("\nПридумайте название для своего напитка ");
        String name = scanner.nextLine();

        System.out.println("Сколько добавить мл воды");
        int quantityAqua = isValid(scanner);

        System.out.println("Сколько добавить мл молока");
        int quantityMilk = isValid(scanner);

        System.out.println("Сколько добавить гр кофейных зерен");
        int quantityBeans = isValid(scanner);

        drinkService.addDrink(name, quantityAqua, quantityMilk, quantityBeans);
        Drink drink = drinkService.findByName(name);

        machineService.addDrink(coffeeMachineID, drink);

        System.out.println("Ваш рецепт успешно добавлен");
        menuThree(coffeeMachineID);
    }

}




