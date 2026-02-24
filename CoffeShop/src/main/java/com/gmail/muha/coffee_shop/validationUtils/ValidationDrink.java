package com.gmail.muha.coffee_shop.validationUtils;

import com.gmail.muha.coffee_shop.entity.StandardDrink;

public class ValidationDrink {


    public static void checkFoundDrink(StandardDrink drink) {
        if (drink == null) {
            throw new IllegalArgumentException("Напиток не может быть null");
        }
    }

}
