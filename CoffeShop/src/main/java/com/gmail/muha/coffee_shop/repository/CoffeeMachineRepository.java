package com.gmail.muha.coffee_shop.repository;

import com.gmail.muha.coffee_shop.entity.CoffeeMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CoffeeMachineRepository {

    private final Map<Integer, CoffeeMachine> coffeeMachine = new HashMap<>();


    public void add(CoffeeMachine machine) {
        coffeeMachine.put(machine.getId(), machine);
    }

    public CoffeeMachine findById(int id) {
        return coffeeMachine.get(id);
    }

    public List<CoffeeMachine> findAll() {
        return new ArrayList<>(coffeeMachine.values());
    }

}
