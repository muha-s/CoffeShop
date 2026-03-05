package com.gmail.muha.coffee_shop.gui;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class GuiUtils {

    private static final URL AMERICANO;
    private static final URL ESPRESSO;
    private static final URL LATTE;
    private static final URL CAPPUCCINO;
    private static final URL FLAT_WHITE;
    private static final URL CUSTOM_DRINK;

    static {
        try {
            AMERICANO = new URI("https://png.pngtree.com/png-clipart/20230425/original/pngtree-americano-coffee-beans-transparent-white-background-png-image_9097377.png").toURL();
            ESPRESSO = new URI("https://play-lh.googleusercontent.com/K7BCnhPMWIvhjbATk0bhiH1cOBFDBSDfftxz4N4nVoZqHkerQu5qEKz4D2nAO20DgAQ").toURL();
            LATTE = new URI("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEFm97Q-KXpgVnsBydBeCw3o71reMc8icYQA&s").toURL();
            CAPPUCCINO = new URI("https://photoshop-kopona.com/uploads/posts/2019-02/1551097452_coffee_cup15.jpg").toURL();
            FLAT_WHITE = new URI("https://www.tankcoffee.com/wp-content/uploads/2024/11/What-Makes-The-Perfect-Flat-White.png").toURL();
            CUSTOM_DRINK = new URI("https://i.pinimg.com/736x/32/c0/c6/32c0c611333a15fd13671c06ef12e8c0.jpg").toURL();

        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static void showImageCoffee(Component parent, URL url, String name, int id) {

        ImageIcon icon = new ImageIcon(url);

        Image scaled = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        ImageIcon finalIcon = new ImageIcon(scaled);

        String message = "Работает кофемашина с ID: " + id + ". Ваш " + name;
        JOptionPane.showMessageDialog(
                parent, "Приятного кофепития", message, JOptionPane.PLAIN_MESSAGE, finalIcon);
    }

    public static void showImage(Component parent, String name, int id) {

        switch (name) {
            case "Americano" -> showImageCoffee(parent, AMERICANO, name, id);
            case "Espresso" -> showImageCoffee(parent, ESPRESSO, name, id);
            case "Latte" -> showImageCoffee(parent, LATTE, name, id);
            case "Flat White" -> showImageCoffee(parent, FLAT_WHITE, name, id);
            case "Cappuccino" -> showImageCoffee(parent, CAPPUCCINO, name, id);
            default -> showImageCoffee(parent, CUSTOM_DRINK, name, id);
        }
    }
}
