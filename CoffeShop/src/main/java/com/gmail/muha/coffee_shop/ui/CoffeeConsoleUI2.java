package com.gmail.muha.coffee_shop.ui;

import com.gmail.muha.coffee_shop.entity.CoffeeMachine;
import com.gmail.muha.coffee_shop.entity.Drink;
import com.gmail.muha.coffee_shop.entity.StandardDrink;
import com.gmail.muha.coffee_shop.exception.EntityNotFoundException;
import com.gmail.muha.coffee_shop.service.CoffeeMachineService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoffeeConsoleUI2 {

    private Scanner scanner = new Scanner(System.in);
    private CoffeeMachineService service;


    public CoffeeConsoleUI2(CoffeeMachineService machineService) {
        this.service = machineService;
    }

    public void run() {
        menuOne();
    }

    private void menuOne() {

        while (true) {

            System.out.println("\n1. Добавить кофемашину");
            System.out.println("2. Выбрать кофемашину");
            System.out.println("0. Выход из программы");
            System.out.print("Ваш выбор: ");

            int id = isValid(scanner);

            switch (id) {
                case 1 -> {
                    addCoffeeMachine();
                }
                case 2 -> {
                    menuTwo();
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Не верные данные. Попробуйте еще раз");
            }
        }
    }

    private void menuTwo() {

        while (true) {
            try {
                if (!findAllCoffeeMachine()) {
                    return;
                }
                System.out.print("Для выхода в главное меню нажмите: \"0\"");
                System.out.print(". Либо выберете из списка кофемашину по ID: ");

                int value = isValid(scanner);

                if (value == 0) {
                    return;
                }
                menuThree(value);

            } catch (EntityNotFoundException e) {
                System.err.println(e.getMessage());
                System.err.println("Попробуйте еще раз.222222");
            }
        }
    }

    private void menuThree(int id) {

        while (true) {
            try {
                CoffeeMachine coffeeMachine = service.findById(id);

                System.out.println("\nРаботаем с кофемашиной c ID: " + coffeeMachine.getId());
                System.out.println("1. Добавить стандартный рецепт кофе в машину");
                System.out.println("2. Добавить свой рецепт кофе в машину");
                System.out.println("3. Посмотреть наличие продуктов");
                System.out.println("4. Добавит воды");
                System.out.println("5. Добавить молока");
                System.out.println("6. Добавить зерен кофе");
                System.out.println("7. Заварить кофе");
                System.out.println("8. Выход в предыдущее меню");

                System.out.print("Ваш выбор: ");

                int selection = isValid(scanner);

                switch (selection) {
                    case 1 -> addRecipeStandardDrink(coffeeMachine);
                    case 2 -> addRecipeCustomDrink(coffeeMachine);
                    case 3 -> printListOfProducts(coffeeMachine);
                    case 4 -> addAqua(coffeeMachine);
                    case 5 -> addAMilk(coffeeMachine);
                    case 6 -> addBeans(coffeeMachine);
                    case 7 -> brewCoffee(coffeeMachine);
                    case 8 -> {
                        return;
                    }
                    default -> System.out.println("Не верные данные");
                }
            } catch (EntityNotFoundException | IllegalArgumentException e) {
                System.err.println(e.getMessage());
                System.err.println("Попробуйте еще раз.33333");
                return;
            }
        }
    }

    private void addRecipeStandardDrink(CoffeeMachine coffeeMachine) {

        System.out.println("\nРецепт какого Стандартного кофе нужно добавить в кофемашину с ID: " + coffeeMachine.getId() + "?");
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
                service.addStandardDrink(coffeeMachine.getId(), StandardDrink.AMERICANO);
                System.out.println("Ваш рецепт " + StandardDrink.AMERICANO.getName() + " успешно добавлен");
                addRecipeStandardDrink(coffeeMachine);
            }
            case 2 -> {
                service.addStandardDrink(coffeeMachine.getId(), StandardDrink.CAPPUCCINO);
                System.out.println("Ваш рецепт " + StandardDrink.CAPPUCCINO.getName() + " успешно добавлен");
                addRecipeStandardDrink(coffeeMachine);
            }
            case 3 -> {
                service.addStandardDrink(coffeeMachine.getId(), StandardDrink.ESPRESSO);
                System.out.println("Ваш рецепт " + StandardDrink.ESPRESSO.getName() + " успешно добавлен");
                addRecipeStandardDrink(coffeeMachine);
            }
            case 4 -> {
                service.addStandardDrink(coffeeMachine.getId(), StandardDrink.FLAT_WHITE);
                System.out.println("Ваш рецепт " + StandardDrink.FLAT_WHITE.getName() + " успешно добавлен");
                addRecipeStandardDrink(coffeeMachine);
            }
            case 5 -> {
                service.addStandardDrink(coffeeMachine.getId(), StandardDrink.LATTE);
                System.out.println("Ваш рецепт " + StandardDrink.LATTE.getName() + " успешно добавлен");
                addRecipeStandardDrink(coffeeMachine);
            }
            case 6 -> {
                return;
            }

            default -> {
                System.out.println("Такого рецепта не существует");
                addRecipeStandardDrink(coffeeMachine);
            }
        }
    }

    private void addRecipeCustomDrink(CoffeeMachine coffeeMachine) {

        System.out.println("\nПридумайте название для своего напитка ");
        String name = scanner.nextLine();

        System.out.println("Сколько добавить мл воды");
        int quantityAqua = isValid(scanner);

        System.out.println("Сколько добавить мл молока");
        int quantityMilk = isValid(scanner);

        System.out.println("Сколько добавить гр кофейных зерен");
        int quantityBeans = isValid(scanner);

        service.createCustomDrink(coffeeMachine.getId(), name, quantityAqua, quantityMilk, quantityBeans);

        System.out.println("Ваш рецепт " + name + " успешно добавлен");

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
        coffeeMachine.addAqua(valueAqua);
    }

    private void addAMilk(CoffeeMachine coffeeMachine) {

        System.out.print("\nСколько мл молока добавить в кофемашину с ID " + coffeeMachine.getId() + ": ");
        int valueMilk = isValid(scanner);

        coffeeMachine.addMilk(valueMilk);
    }

    private void addBeans(CoffeeMachine coffeeMachine) {

        System.out.print("\nСколько гр кофейных зерен добавить добавить в кофемашину с ID " + coffeeMachine.getId() + ": ");
        int valueBeans = isValid(scanner);

        coffeeMachine.addBeans(valueBeans);
    }

    private void addCoffeeMachine() {

        System.out.println("\nСоздаем новую кофемашину");
        System.out.print("Добавьте воды в мл: ");
        int amountOfAqua = isValid(scanner);

        System.out.print("Добавьте молока в мл: ");
        int amountOfMilk = isValid(scanner);

        System.out.print("Засыпьте зерен кофе в кг: ");
        int amountOfBeans = isValid(scanner);

        service.addCoffeeMachine(amountOfAqua, amountOfMilk, amountOfBeans);
        System.out.println("Кофемашина успешно создана");
    }

    private void brewCoffee(CoffeeMachine coffeeMachine) {

        if (coffeeMachine.getDrinks().isEmpty()) {
            System.out.println("У этой машины нет готовых рецептов, загрузите рецепт");
            return;
        }
        System.out.println();

        List<String> nameCoffeesList = new ArrayList<>();

        nameCoffeesList.addAll(coffeeMachine.getDrinks().keySet());

        for (int i = 0; i < nameCoffeesList.size(); i++) {
            System.out.println(i + 1 + ". " + nameCoffeesList.get(i));
        }

        System.out.print("Выберете напиток из доступных: ");

        int numberDrink = isValid(scanner);

        if (coffeeMachine.getDrinks().size() < numberDrink || numberDrink == 0) {
            System.out.println("Такого напитка в меню нет");
        }
        Drink drink = service.brew(coffeeMachine.getId(), nameCoffeesList.get(numberDrink - 1));
        System.out.println("Возьмите свой " + drink.getName());

    }

    private boolean findAllCoffeeMachine() {
        if (service.findAllMachine().isEmpty()) {
            System.out.println("\nРаботающих кофемашин нет");
            return false;
        }

        System.out.println("\nСписок доступных кофемашин:");
        for (int i = 0; i < service.findAllMachine().size(); i++) {
            System.out.println(i + 1 + ". ID: " + service.findAllMachine().get(i).getId());
        }
        return true;
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

}




