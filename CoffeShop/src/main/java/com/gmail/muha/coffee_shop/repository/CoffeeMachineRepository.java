package com.gmail.muha.coffee_shop.repository;

import com.gmail.muha.coffee_shop.entity.CoffeeMachine;
import com.gmail.muha.coffee_shop.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoffeeMachineRepository {

    private final Map<Integer, CoffeeMachine> coffeeMachines = new HashMap<>();


    public void add(CoffeeMachine machine) {
        coffeeMachines.put(machine.getId(), machine);
    }

    public CoffeeMachine findById(int id) {

        CoffeeMachine machine = coffeeMachines.get(id);
        if (machine == null) {
            throw new EntityNotFoundException("Кофемашина с id " + id + " не найдена");
        }
        return machine;
    }

    public List<CoffeeMachine> findAll() {
        return new ArrayList<>(coffeeMachines.values());
    }

}
