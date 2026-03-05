package com.gmail.muha.coffee_shop.gui;

import com.gmail.muha.coffee_shop.entity.CoffeeMachine;
import com.gmail.muha.coffee_shop.entity.Drink;
import com.gmail.muha.coffee_shop.service.CoffeeMachineService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Gui {

    private int currentMachineId;
    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final CoffeeMachineService service;

    public Gui(CoffeeMachineService machineService) throws IOException {
        this.service = machineService;
        frame = new JFrame("Coffee Shop");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createMenuStart(), "MENU_START");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createMenuStart() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setLayout(new GridLayout(3, 1, 0, 5));

        JButton button1 = new JButton("Добавить кофемашину");
        JButton button2 = new JButton("Выбрать кофемашину");
        JButton button3 = new JButton("Выход из программы");

        button1.addActionListener(e -> {
            mainPanel.add(addCoffeeMachine(), "ADD_COFFEE_MACHINE");
            cardLayout.show(mainPanel, "ADD_COFFEE_MACHINE");
        });
        button2.addActionListener(e -> {
            try {
                List<CoffeeMachine> machines = service.findAllMachine();
                if (machines.isEmpty()) {
                    throw new IOException("База данных пуста. Добавьте сначала машину.");
                }
                mainPanel.add(choosingCoffeeMachine(), "CHOOSING_COFFEE_MACHINE");
                cardLayout.show(mainPanel, "CHOOSING_COFFEE_MACHINE");

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Ошибка при загрузке: " + ex.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        button3.addActionListener(e -> {
            System.exit(0);
        });
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        return panel;
    }

    private JPanel addCoffeeMachine() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.cyan);

        JLabel label1 = new JLabel("Добавьте воды в мл: ");
        JTextField text1 = new JTextField(15);

        JLabel label2 = new JLabel("Добавьте молока в мл: ");
        JTextField text2 = new JTextField(15);

        JLabel label3 = new JLabel("Засыпьте зерен кофе в кг: ");
        JTextField text3 = new JTextField(15);

        panel.add(label1);
        panel.add(text1);

        panel.add(label2);
        panel.add(text2);

        panel.add(label3);
        panel.add(text3);

        JButton button1 = new JButton("Создать");
        JButton button2 = new JButton("Выход в главное меню");
        panel.add(button1);
        panel.add(button2);

        button1.addActionListener(e -> {

            Integer aqua = parseOrError(text1.getText(), "Вода");
            if (aqua == null) {
                return;
            }
            Integer milk = parseOrError(text2.getText(), "Молоко");
            if (milk == null) {
                return;
            }
            Integer beans = parseOrError(text3.getText(), "Кофе");
            if (beans == null) {
                return;
            }
            try {
                service.addCoffeeMachine(aqua, milk, beans);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Не удалось сохранить данные: " + ex.getMessage(),
                        "Ошибка системы",
                        JOptionPane.ERROR_MESSAGE);
            }
            text1.setText("");
            text2.setText("");
            text3.setText("");
            JOptionPane.showMessageDialog(
                    frame, "Кофемашина успешно создалась", "Успех", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "MENU_START");
        });
        button2.addActionListener(e -> {
            text1.setText("");
            text2.setText("");
            text3.setText("");

            cardLayout.show(mainPanel, "MENU_START");
        });

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.add(panel);

        return wrapper;
    }

    private JPanel choosingCoffeeMachine() throws IOException {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.cyan);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        List<CoffeeMachine> coffeeMachines = service.findAllMachine();

        ButtonGroup buttonGroup = new ButtonGroup();

        extracted(coffeeMachines, buttonGroup, panel);

        JButton button1 = new JButton("Подтвердить");
        JButton button2 = new JButton("Выход в главное меню");

        button1.addActionListener(e -> {
            String selectedId = buttonGroup.getSelection().getActionCommand();
            currentMachineId = Integer.parseInt(selectedId);
            buttonGroup.clearSelection();
            mainPanel.add(workingWithCoffeeMachine(currentMachineId), "WORKING_WITH_COFFEE_MACHINE");
            cardLayout.show(mainPanel, "WORKING_WITH_COFFEE_MACHINE");
        });

        button2.addActionListener(e -> {
            cardLayout.show(mainPanel, "MENU_START");
        });

        panel.add(button1);
        panel.add(button2);
        return panel;
    }

    private JPanel workingWithCoffeeMachine(int id) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.cyan);

        JLabel label = new JLabel("Работаем с кофемашиной с ID: " + currentMachineId);

        JRadioButton radioButton1 = new JRadioButton("Добавить свой рецепт кофе в машину");
        JRadioButton radioButton2 = new JRadioButton("Посмотреть наличие продуктов");
        JRadioButton radioButton3 = new JRadioButton("Добавит воды");
        JRadioButton radioButton4 = new JRadioButton("Добавить молока");
        JRadioButton radioButton5 = new JRadioButton("Добавить зерен кофе");
        JRadioButton radioButton6 = new JRadioButton("Заварить кофе");
        JRadioButton radioButton7 = new JRadioButton("Выход в предыдущее меню");

        JButton button1 = new JButton("Выбрать");
        JButton button2 = new JButton("Выход в главное меню");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        buttonGroup.add(radioButton4);
        buttonGroup.add(radioButton5);
        buttonGroup.add(radioButton6);
        buttonGroup.add(radioButton7);

        Box box = Box.createVerticalBox();
        box.add(label);
        box.add(radioButton1);
        box.add(radioButton2);
        box.add(radioButton3);
        box.add(radioButton4);
        box.add(radioButton5);
        box.add(radioButton6);
        box.add(radioButton7);
        box.add(button1);
        box.add(button2);

        button1.addActionListener(e -> {
            if (radioButton1.isSelected()) {
                buttonGroup.clearSelection();
                mainPanel.add(addRecipeCustomDrink(), "ADD_RECIPE_CUSTOM_DRINK");
                cardLayout.show(mainPanel, "ADD_RECIPE_CUSTOM_DRINK");
            }

            if (radioButton2.isSelected()) {
                buttonGroup.clearSelection();
                try {
                    mainPanel.add(viewNumberOfProducts(), "VIEW_NUMBER_OF_PRODUCTS");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                cardLayout.show(mainPanel, "VIEW_NUMBER_OF_PRODUCTS");
            }

            if (radioButton3.isSelected()) {
                buttonGroup.clearSelection();
                try {
                    mainPanel.add(addAqua(), "ADD_AQUA");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                cardLayout.show(mainPanel, "ADD_AQUA");
            }

            if (radioButton4.isSelected()) {
                buttonGroup.clearSelection();
                try {
                    mainPanel.add(addMilk(), "ADD_MILK");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                cardLayout.show(mainPanel, "ADD_MILK");
            }

            if (radioButton5.isSelected()) {
                buttonGroup.clearSelection();
                try {
                    mainPanel.add(addBeans(), "ADD_BEANS");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                cardLayout.show(mainPanel, "ADD_BEANS");
            }
            if (radioButton6.isSelected()) {
                buttonGroup.clearSelection();
                try {
                    mainPanel.add(brewCoffee(), "BREW_COFFEE");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                cardLayout.show(mainPanel, "BREW_COFFEE");
            }
            if (radioButton7.isSelected()) {

                cardLayout.show(mainPanel, "CHOOSING_COFFEE_MACHINE");
            }
        });

        button2.addActionListener(e -> {
            cardLayout.show(mainPanel, "MENU_START");
        });
        panel.add(box);
        return panel;
    }

    private JPanel viewNumberOfProducts() throws IOException {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.cyan);

        CoffeeMachine machine = service.findById(currentMachineId);

        int aqua = machine.getAqua();
        int milk = machine.getMilk();
        int beans = machine.getBeans();

        JLabel label1 = new JLabel("В машине с ID: " + machine.getId());

        JLabel label2 = new JLabel("Воды " + aqua + " мл");
        JLabel label3 = new JLabel("Молока " + milk + " мл");
        JLabel label4 = new JLabel("Кофе " + beans + " гр");

        JButton button = new JButton("Назад");

        Box box = Box.createVerticalBox();
        box.add(label1);
        box.add(label2);
        box.add(label3);
        box.add(label4);

        box.add(button);
        panel.add(box);

        button.addActionListener(e -> {
            cardLayout.show(mainPanel, "WORKING_WITH_COFFEE_MACHINE");
        });

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.add(panel);

        frame.add(wrapper);
        return wrapper;
    }

    private JPanel addRecipeCustomDrink() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.cyan);

        JLabel label1 = new JLabel("Какое название: ");
        JTextField text1 = new JTextField(15);

        JLabel label2 = new JLabel("Сколько добавлять воды в мл: ");
        JTextField text2 = new JTextField(15);

        JLabel label3 = new JLabel("Сколько добавлять молока в мл: ");
        JTextField text3 = new JTextField(15);

        JLabel label4 = new JLabel("Сколько добавлять зерен кофе в гр: ");
        JTextField text4 = new JTextField(15);

        panel.add(label1);
        panel.add(text1);

        panel.add(label2);
        panel.add(text2);

        panel.add(label3);
        panel.add(text3);

        panel.add(label4);
        panel.add(text4);

        JButton button1 = new JButton("Создать");
        panel.add(button1);

        JButton button2 = new JButton("Выход в предыдущее меню");
        panel.add(button2);

        button1.addActionListener(e -> {

            Integer aqua = parseOrError(text2.getText(), "Aqua");
            if (aqua == null) {
                return;
            }

            Integer milk = parseOrError(text3.getText(), "Milk");
            if (milk == null) {
                return;
            }

            Integer beans = parseOrError(text4.getText(), "Beans");
            if (beans == null) {
                return;
            }

            try {
                if (text1.getText().isBlank()) {
                    throw new IllegalArgumentException("Название рецепта не может быть пустым");
                }
                service.createCustomDrink(currentMachineId, text1.getText(), aqua, milk, beans);
                JOptionPane.showMessageDialog(
                        frame, "Рецепт кофе успешно создался", "Успех", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "WORKING_WITH_COFFEE_MACHINE");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Ошибка в поле названия" + "': " + ex.getMessage(),
                        "Ошибка ввода",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        button2.addActionListener(e -> {
            cardLayout.show(mainPanel, "WORKING_WITH_COFFEE_MACHINE");
        });

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.add(panel);

        frame.add(wrapper);
        return wrapper;
    }

    private JPanel addAqua() throws IOException {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.cyan);

        CoffeeMachine machine = service.findById(currentMachineId);

        JLabel label = new JLabel("Сколько воды добавить");

        JTextField numberAquaText = new JTextField("");

        JButton button1 = new JButton("Добавить");
        JButton button2 = new JButton("Выход в предыдущее меню");

        button1.addActionListener(e -> {
            Integer numberAqua = parseOrError(numberAquaText.getText(), "Вода");
            if (numberAqua == null) {
                return;
            }

            try {
                service.addAqua(machine.getId(), numberAqua);
                CoffeeMachine updatedMachine = service.findById(currentMachineId);
                JOptionPane.showMessageDialog(
                        frame, "Вода успешно добавилась. Сейчас воды: " + updatedMachine.getAqua(),
                        "Успех", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "WORKING_WITH_COFFEE_MACHINE");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        button2.addActionListener(e -> {
            cardLayout.show(mainPanel, "WORKING_WITH_COFFEE_MACHINE");
        });

        panel.add(label);
        panel.add(numberAquaText);
        panel.add(button1);
        panel.add(button2);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.add(panel);

        frame.add(wrapper);
        return wrapper;
    }

    private JPanel addMilk() throws IOException {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.cyan);

        CoffeeMachine machine = service.findById(currentMachineId);

        JLabel label = new JLabel("Сколько молока добавить");

        JTextField numberMilkText = new JTextField("");

        JButton button1 = new JButton("Добавить");
        JButton button2 = new JButton("Выход в предыдущее меню");

        button1.addActionListener(e -> {

            Integer numberMilk = parseOrError(numberMilkText.getText(), "Молоко");

            if (numberMilk == null) {
                return;
            }
            try {
                service.addMilk(machine.getId(), numberMilk);
                CoffeeMachine updatedMachine = service.findById(currentMachineId);
                JOptionPane.showMessageDialog(
                        frame, "Молоко успешно добавилось. Сейчас Молока: " + updatedMachine.getMilk(),
                        "Успех", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "WORKING_WITH_COFFEE_MACHINE");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        button2.addActionListener(e -> {
            cardLayout.show(mainPanel, "WORKING_WITH_COFFEE_MACHINE");
        });
        panel.add(label);
        panel.add(numberMilkText);
        panel.add(button1);
        panel.add(button2);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.add(panel);

        frame.add(wrapper);
        return wrapper;
    }

    private JPanel addBeans() throws IOException {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.cyan);

        CoffeeMachine machine = service.findById(currentMachineId);

        JLabel label = new JLabel("Сколько кофе добавить");

        JTextField numberBeansText = new JTextField("");

        JButton button1 = new JButton("Добавить");
        JButton button2 = new JButton("Выход в предыдущее меню");

        button1.addActionListener(e -> {
            Integer numberBeans = parseOrError(numberBeansText.getText(), "Кофе");

            if (numberBeans == null) {
                return;
            }
            try {
                service.addBeans(machine.getId(), numberBeans);
                CoffeeMachine updatedMachine = service.findById(currentMachineId);
                JOptionPane.showMessageDialog(
                        frame, "Кофе успешно добавилось. Сейчас кофе: " + updatedMachine.getBeans(),
                        "Успех", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "WORKING_WITH_COFFEE_MACHINE");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        button2.addActionListener(e -> {
            cardLayout.show(mainPanel, "WORKING_WITH_COFFEE_MACHINE");
        });

        panel.add(label);
        panel.add(numberBeansText);
        panel.add(button1);
        panel.add(button2);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.add(panel);

        frame.add(wrapper);
        return wrapper;
    }

    private JPanel brewCoffee() throws IOException {

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.cyan);

        CoffeeMachine machine = service.findById(currentMachineId);

        Map<String, Drink> drinkMap = machine.getDrinks();

        JLabel label = new JLabel("Какой кофе завариваем");

        ButtonGroup buttonGroup = new ButtonGroup();
        Box box = Box.createVerticalBox();

        for (String string : drinkMap.keySet()) {
            JRadioButton radioButton = new JRadioButton(string);
            radioButton.setActionCommand(string);
            buttonGroup.add(radioButton);
            box.add(radioButton);
        }
        JButton button1 = new JButton("Выбрать");
        JButton button2 = new JButton("Выход в предыдущее меню");

        button1.addActionListener(e -> {
            String selectedDrink = buttonGroup.getSelection().getActionCommand();

            try {
                service.brew(currentMachineId, selectedDrink);
            } catch (IllegalArgumentException ex) {

                JOptionPane.showMessageDialog(
                        frame,
                        ex.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE
                );

                cardLayout.show(mainPanel, "WORKING_WITH_COFFEE_MACHINE");
                return;

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Ошибка записи данных: " + ex.getMessage(),
                        "Системная ошибка",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            GuiUtils.showImage(frame, selectedDrink, machine.getId());
            cardLayout.show(mainPanel, "WORKING_WITH_COFFEE_MACHINE");
        });

        button2.addActionListener(e -> {
            cardLayout.show(mainPanel, "WORKING_WITH_COFFEE_MACHINE");
        });

        box.add(label);
        box.add(button1);
        box.add(button2);

        panel.add(box);
        return panel;
    }

    private Integer parseOrError(String text, String fieldName) {
        try {
            int number = Integer.parseInt(text.trim());

            if (number < 0) {
                throw new Exception("Число не может быть отрицательным");
            }
            return number;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame,
                    "В поле '" + fieldName + "' должно быть целое число!",
                    "Ошибка ввода",
                    JOptionPane.ERROR_MESSAGE);
            return null;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                    "Ошибка в поле '" + fieldName + "': " + e.getMessage(),
                    "Ошибка ввода",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private static void extracted(List<CoffeeMachine> coffeeMachines, ButtonGroup buttonGroup, JPanel panel) {

        int count = 0;

        Box box = Box.createVerticalBox();

        for (CoffeeMachine coffeeMachine : coffeeMachines) {

            if (count == 15) {
                count = 0;
                panel.add(box);
                box = Box.createVerticalBox();
            }
            JRadioButton radioButton = new JRadioButton("ID: " + coffeeMachine.getId());
            radioButton.setActionCommand(String.valueOf(coffeeMachine.getId()));
            buttonGroup.add(radioButton);
            box.add(radioButton);
            count++;
        }
        panel.add(box);
    }

}
