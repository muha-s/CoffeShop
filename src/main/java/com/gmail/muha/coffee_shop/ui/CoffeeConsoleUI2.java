package com.gmail.muha.coffee_shop.ui;

import com.gmail.muha.coffee_shop.entity.CoffeeMachine;
import com.gmail.muha.coffee_shop.entity.Drink;
import com.gmail.muha.coffee_shop.exception.EntityNotFoundException;
import com.gmail.muha.coffee_shop.service.CoffeeMachineService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoffeeConsoleUI2 {

    private Scanner scanner = new Scanner(System.in);
    private CoffeeMachineService service;


    public CoffeeConsoleUI2(CoffeeMachineService machineService) {
        this.service = machineService;
    }

    public void run() throws IOException {
        menuOne();
    }

    private void menuOne() throws IOException {

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

    private void menuTwo() throws IOException {

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

    private void menuThree(int id) throws IOException {

        while (true) {
            try {
                CoffeeMachine coffeeMachine = service.findById(id);

                System.out.println("\nРаботаем с кофемашиной c ID: " + coffeeMachine.getId());

                System.out.println("1. Добавить свой рецепт кофе в машину");
                System.out.println("2. Посмотреть наличие продуктов");
                System.out.println("3. Добавит воды");
                System.out.println("4. Добавить молока");
                System.out.println("5. Добавить зерен кофе");
                System.out.println("6. Заварить кофе");
                System.out.println("7. Выход в предыдущее меню");

                System.out.print("Ваш выбор: ");

                int selection = isValid(scanner);

                switch (selection) {

                    case 1 -> addRecipeCustomDrink(coffeeMachine);
                    case 2 -> printListOfProducts(coffeeMachine);
                    case 3 -> addAqua(coffeeMachine);
                    case 4 -> addAMilk(coffeeMachine);
                    case 5 -> addBeans(coffeeMachine);
                    case 6 -> brewCoffee(coffeeMachine);
                    case 7 -> {
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

    private void addRecipeCustomDrink(CoffeeMachine coffeeMachine) throws IOException {

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

    private void printListOfProducts(CoffeeMachine coffeeMachine) throws IOException {

        System.out.println("\nВ кофемашине под ID: " + coffeeMachine.getId() + "\n" +
                coffeeMachine.getAqua() + " мл воды; " +
                coffeeMachine.getMilk() + " мл молока; " +
                coffeeMachine.getBeans() + " гр кофейных зерен.");
        menuThree(coffeeMachine.getId());
    }

    private void addAqua(CoffeeMachine coffeeMachine) throws IOException {

        System.out.print("\nСколько мл воды добавить в кофемашину с ID " + coffeeMachine.getId() + ": ");
        int valueAqua = isValid(scanner);
        service.addAqua(coffeeMachine.getId(), valueAqua);
    }

    private void addAMilk(CoffeeMachine coffeeMachine) throws IOException {

        System.out.print("\nСколько мл молока добавить в кофемашину с ID " + coffeeMachine.getId() + ": ");
        int valueMilk = isValid(scanner);

        service.addMilk(coffeeMachine.getId(), valueMilk);
    }

    private void addBeans(CoffeeMachine coffeeMachine) throws IOException {

        System.out.print("\nСколько гр кофейных зерен добавить добавить в кофемашину с ID " + coffeeMachine.getId() + ": ");
        int valueBeans = isValid(scanner);

        service.addBeans(coffeeMachine.getId(), valueBeans);
    }

    private void addCoffeeMachine() throws IOException {

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

    private void brewCoffee(CoffeeMachine coffeeMachine) throws IOException {

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

    private boolean findAllCoffeeMachine() throws IOException {
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




