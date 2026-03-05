package com.gmail.muha.coffee_shop.repository;

import com.gmail.muha.coffee_shop.entity.Drink;
import com.gmail.muha.coffee_shop.entity.StandardDrink;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class DrinkRepository {

    private final Path filePath = Path.of("data", "drink.txt");
    private final String fileName = "data/drink.txt";

    public DrinkRepository() throws IOException {

        Files.createDirectories(filePath.getParent());
        if (Files.notExists(filePath) || Files.size(filePath) == 0) {
            prepareDefaultData();
        }
    }

    public void saveDrink(Drink drink) throws IOException {

        String line = drink.toFileString() + System.lineSeparator();

        Files.write(
                Path.of(fileName),
                line.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    public List<Drink> getAllDrinks() throws IOException {
        List<Drink> drinks = new ArrayList<>();

        if (!Files.exists(Path.of(fileName))) {
            return drinks;
        }
        List<String> lines = Files.readAllLines(Path.of(fileName));

        for (String line : lines) {
            drinks.add(Drink.fromFileString(line));
        }
        return drinks;
    }

    public Drink findByName(String name) throws IOException {
        if (!Files.exists(Path.of(fileName))) {
            return null;
        }

        List<String> lines = Files.readAllLines(Path.of(fileName));
        Drink drink = null;
        for (String line : lines) {
            drink = Drink.fromFileString(line);
            if (drink.getName().equalsIgnoreCase(name)) {
                return drink;
            }
        }
        return drink;
    }

    private void prepareDefaultData() throws IOException {

        saveDrink(new Drink(StandardDrink.AMERICANO));
        saveDrink(new Drink(StandardDrink.ESPRESSO));
        saveDrink(new Drink(StandardDrink.LATTE));
        saveDrink(new Drink(StandardDrink.FLAT_WHITE));
        saveDrink(new Drink(StandardDrink.CAPPUCCINO));
    }
}
