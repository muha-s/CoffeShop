package com.gmail.muha.coffee_shop.repository;

import com.gmail.muha.coffee_shop.entity.CoffeeMachine;
import com.gmail.muha.coffee_shop.exception.EntityNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CoffeeMachineRepository {

    private final Path filePath = Path.of("data", "coffeeMachine.txt");
    private final String fileName = "data/coffeeMachine.txt";

    public CoffeeMachineRepository() throws IOException {
        Files.createDirectories(filePath.getParent());
    }

    public void save(CoffeeMachine machine) throws IOException{
        String line = machine.toFileString() + System.lineSeparator();

        Files.write(
                Path.of(fileName),
                line.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    public CoffeeMachine findById(int id) throws IOException{

        List<CoffeeMachine> coffeeMachines = getAllCoffeeMachine();

        CoffeeMachine machine = null;
        for (CoffeeMachine coffeeMachine : coffeeMachines) {
            if (coffeeMachine.getId() == id){
                machine = coffeeMachine;
                break;
            }
        }
        if (machine == null){
            throw new EntityNotFoundException("Кофемашина с id " + id + " не найденаssss2");
        }
        return machine;
    }

    public List<CoffeeMachine> getAllCoffeeMachine() throws IOException {
        List<CoffeeMachine> machines = new ArrayList<>();

        if (!Files.exists(Path.of(fileName))) {
            return machines;
        }
        List<String> lines = Files.readAllLines(Path.of(fileName));

        for (String line : lines) {
            machines.add(CoffeeMachine.fromFileString(line));
        }
        return machines;
    }

    public void rewriteCoffeeMachine(List<CoffeeMachine> coffeeMachines) throws IOException {
        Files.delete(Path.of(fileName));
        for (CoffeeMachine machine : coffeeMachines) {
            save(machine);
        }
    }

}
