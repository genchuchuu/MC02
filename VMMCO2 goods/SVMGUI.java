import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class SVMGUI extends JFrame {
    private SpecialVendingMachine vendingMachine;
    private JTextArea outputTextArea;
    private JTextField amountPaidTextField;
    private JComboBox<String> drinkComboBox;
    private JComboBox<String> cupSizeComboBox;
    private JComboBox<String> temperatureComboBox;
    private JComboBox<String> sugarLevelComboBox;
    private JComboBox<String> milkChoiceComboBox;
    private JCheckBox numEspressoCheckBox;
    private JButton purchaseButton;

    public SVMGUI(SpecialVendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        setTitle("Special Vending Machine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);

        initializeComboBoxes();
        setupListeners();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // Create the Test Features and Maintenance panels
        JPanel testFeaturesPanel = createTestFeaturesPanel();
        JPanel maintenanceFeaturesPanel = createMaintenanceFeaturesPanel();

        // Create a tabbed pane to switch between Test Features and Maintenance menu
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Test Vending Features", testFeaturesPanel);
        tabbedPane.addTab("Maintenance Menu", maintenanceFeaturesPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    private void initializeComboBoxes() {
        String[] drinkOptions = {
            "Select a Drink",
            "White Chocolate Coffee",
            "Caramel Macchiato"
        };
        drinkComboBox = new JComboBox<>(drinkOptions);

        String[] cupSizeOptions = {
            "Small",
            "Medium",
            "Large"
        };
        cupSizeComboBox = new JComboBox<>(cupSizeOptions);

        String[] temperatureOptions = {
            "Hot",
            "Iced"
        };
        temperatureComboBox = new JComboBox<>(temperatureOptions);

        String[] sugarLevelOptions = {
            "No Sugar",
            "With Sugar",
        };
        sugarLevelComboBox = new JComboBox<>(sugarLevelOptions);

        String[] milkChoiceOptions = {
            "Cow Milk",
            "Soy Milk",
            "Almond Milk"
        };
        milkChoiceComboBox = new JComboBox<>(milkChoiceOptions);

        numEspressoCheckBox = new JCheckBox("Add Espresso Shots");
    }

    private JPanel createTestFeaturesPanel() {
        JPanel testPanel = new JPanel(new BorderLayout());
    
        // A text area to display output to the user
        outputTextArea = new JTextArea(10, 30);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        testPanel.add(scrollPane, BorderLayout.CENTER);
    
        // Button for simulating purchasing an item
        purchaseButton = new JButton("Purchase Item");
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchaseItem(outputTextArea);
            }
        });
        testPanel.add(purchaseButton, BorderLayout.SOUTH);
        
        // Text field for entering the amount paid
        JLabel amountPaidLabel = new JLabel("Enter the amount paid:");
        amountPaidTextField = new JTextField();
        JPanel amountPaidPanel = new JPanel(new BorderLayout());
        amountPaidPanel.add(amountPaidLabel, BorderLayout.WEST);
        amountPaidPanel.add(amountPaidTextField, BorderLayout.CENTER);
        testPanel.add(amountPaidPanel, BorderLayout.NORTH);
    
        return testPanel;
    }
    

    private JPanel createMaintenanceFeaturesPanel() {
        JPanel maintenancePanel = new JPanel(new BorderLayout());

        JTextArea outputTextArea = new JTextArea(10, 30);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        maintenancePanel.add(scrollPane, BorderLayout.CENTER);

        JButton restockButton = new JButton("Restock Items");
        restockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restockItems(outputTextArea);
            }
        });

        JButton displayInventoryButton = new JButton("Display Inventory");
        displayInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayInventory(outputTextArea);
            }
        });

        JButton setPriceButton = new JButton("Set Item Price");
        setPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setItemPrice(outputTextArea);
            }
        });

        JButton collectPaymentButton = new JButton("Collect Payment");
        collectPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collectPayment(outputTextArea);
            }
        });

        JButton makeChangeButton = new JButton("Make Change");
        makeChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeChange(outputTextArea);
            }
        });

        JButton printTransactionHistoryButton = new JButton("Print Transaction History");
        printTransactionHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printTransactionHistory(outputTextArea);
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
        buttonPanel.add(restockButton);
        buttonPanel.add(displayInventoryButton);
        buttonPanel.add(setPriceButton);
        buttonPanel.add(collectPaymentButton);
        buttonPanel.add(makeChangeButton);
        buttonPanel.add(printTransactionHistoryButton);

        maintenancePanel.add(buttonPanel, BorderLayout.SOUTH);

        return maintenancePanel;
    }

    private void setupListeners() {
        purchaseButton.addActionListener(e -> handlePurchaseButton());
    }

    private CustomDrink createCustomDrink() {
        String drinkName = getDrinkNameByChoice(drinkComboBox.getSelectedIndex());
        String cupSize = (String) cupSizeComboBox.getSelectedItem();
        String temperature = (String) temperatureComboBox.getSelectedItem();
        String sugarLevel = (String) sugarLevelComboBox.getSelectedItem();
        String milkChoice = (String) milkChoiceComboBox.getSelectedItem();

        CustomDrink customDrink = new CustomDrink(drinkName);
        customDrink.setCupSize(cupSize);
        customDrink.setTemperature(temperature);
        customDrink.setSugarLevel(sugarLevel);
        customDrink.setMilkChoice(milkChoice);

        return customDrink;
    }

    private void handlePurchaseButton() {
        String amountPaidStr = amountPaidTextField.getText();
        double amountPaid;
        try {
            amountPaid = Double.parseDouble(amountPaidStr);
        } catch (NumberFormatException ex) {
            outputTextArea.append("Invalid amount. Please enter a valid number.\n");
            return;
        }

        int drinkChoice = drinkComboBox.getSelectedIndex();
        if (drinkChoice <= 0) {
            outputTextArea.append("Please select a drink.\n");
            return;
        }

        CustomDrink customDrink = createCustomDrink();

        int numEspressoShots = numEspressoCheckBox.isSelected() ? 1 : 0;
        customDrink.setNumEspressoShots(numEspressoShots);

        int totalCalories = vendingMachine.calculateTotalCalories(customDrink);
        double totalPrice = vendingMachine.calculateTotalPrice(customDrink);

        if (amountPaid < totalPrice) {
            outputTextArea.append("Insufficient payment. Purchase canceled.\n");
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, getOrderSummary(customDrink, totalCalories, totalPrice) + "Confirm purchase?", "Confirm Purchase", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            vendingMachine.prepareDrink(customDrink);
            outputTextArea.append("Drink prepared and purchased successfully.\n");

            double change = amountPaid - totalPrice;
            outputTextArea.append("Change: Php " + change + "\n");
            outputTextArea.append("Thank you for your purchase!\n");
        } else {
            outputTextArea.append("Purchase canceled.\n");
        }
    }

    private String getDrinkNameByChoice(int choice) {
        switch (choice) {
            case 1:
                return "White Chocolate Coffee";
            case 2:
                return "Caramel Macchiato";
            default:
                return null;
        }
    }

    private String getOrderSummary(CustomDrink customDrink, int totalCalories, double totalPrice) {
        String orderSummary = "Order Summary:\n";
        orderSummary += "Customizations:\n";
        orderSummary += "Cup Size: " + customDrink.getCupSize() + "\n";
        orderSummary += "Temperature: " + customDrink.getTemperature() + "\n";
        orderSummary += "Sugar Level: " + customDrink.getSugarLevel() + "\n";
        orderSummary += "Milk Choice: " + customDrink.getMilkChoice() + "\n";
        orderSummary += "Espresso Shots: " + customDrink.getNumEspressoShots() + " (Hot) / " + customDrink.getNumEspressoShots() + " (Iced)\n";
        orderSummary += "Total Calories: " + totalCalories + "\n";
        orderSummary += "Total Price: Php " + totalPrice + "\n";
        return orderSummary;
    }

    private void restockItems(JTextArea outputTextArea) {
        // Restock Items
        for (Inventory itemInventory : vendingMachine.getInventory()) {
            String itemName = itemInventory.getItem().getName();
            int currentQuantity = itemInventory.getQuantity();
            int maxCapacity = 15;
            int remainingCapacity = maxCapacity - currentQuantity;

            boolean isValid = false;

            while (!isValid) {
                String input = JOptionPane.showInputDialog(this, "Enter the quantity to restock for " + itemName +
                        " (Remaining capacity: " + remainingCapacity + "): ", "Restock Items", JOptionPane.PLAIN_MESSAGE);

                if (input == null) {
                    // User clicked the "Cancel" button for this item, skip to the next item
                    outputTextArea.append("Restocking for " + itemName + " canceled.\n");
                    break;
                }

                try {
                    int quantity = Integer.parseInt(input);
                    if (quantity < 10 || quantity > 15) {
                        JOptionPane.showMessageDialog(this, "Invalid input! Please enter a quantity between 10 and 15.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int actualRestockQuantity = Math.min(quantity, remainingCapacity);
                        itemInventory.addItem(actualRestockQuantity);
                        outputTextArea.append(actualRestockQuantity + " pieces of " + itemName + " restocked.\n");
                        isValid = true;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        outputTextArea.append("Items restocked successfully.\n\n");
    }

    private void setItemPrice(JTextArea outputTextArea) {
        boolean isValid = false;

        while (!isValid) {
            String slotNumberInput = JOptionPane.showInputDialog(this, "Enter the slot number to set the price: ", "Slot Number of Item", JOptionPane.PLAIN_MESSAGE);

            if (slotNumberInput == null) {
                // User clicked the "Cancel" button
                outputTextArea.append("Setting of item price cancelled.\n\n");
                return; // Exit the method without making any changes
            }

            try {
                int slotNumber = Integer.parseInt(slotNumberInput);
                if (slotNumber >= 1 && slotNumber <= vendingMachine.getInventory().size()) {
                    int selectedIndex = slotNumber - 1;
                    Inventory selectedInventory = vendingMachine.getInventory().get(selectedIndex);
                    Item item = selectedInventory.getItem();

                    String newPriceInput = JOptionPane.showInputDialog(this, "Enter the new price for " + item.getName() + ": ", "Set Item Price", JOptionPane.PLAIN_MESSAGE);

                    if (newPriceInput == null) {
                        // User clicked the "Cancel" button
                        outputTextArea.append("Setting of item price for " + item.getName() + " cancelled.\n\n");
                        return; // Exit the method without making any changes
                    }

                    try {
                        double newPrice = Double.parseDouble(newPriceInput);
                        if (newPrice <= 0) {
                            outputTextArea.append("Invalid price! Please enter a price greater than 0.\n");
                        } else {
                            item.setPrice(newPrice);
                            outputTextArea.append("Price for " + item.getName() + " updated successfully.\n\n");
                        }
                        isValid = true; // Set isValid to true to exit the loop
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    outputTextArea.append("Invalid slot number! Please enter a slot number between 1 and " + vendingMachine.getInventory().size() + ".\n");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void makeChange(JTextArea outputTextArea) {
        StringBuilder changeOutput = new StringBuilder();

        changeOutput.append("Change refill:\n");

        for (Map.Entry<Double, Denomination> entry : vendingMachine.getAcceptedCash().entrySet()) {
            boolean isValid = false;
            while (!isValid) {
                String input = JOptionPane.showInputDialog(this, "Enter the quantity to refill for Php" + entry.getKey() + ": ", "Refill Money for Change", JOptionPane.PLAIN_MESSAGE);

                if (input == null) {
                    // User clicked the "Cancel" button, skip to the next amount
                    outputTextArea.append("Refill for Php" + entry.getKey() + " canceled.\n");
                    break;
                }

                try {
                    int quantity = Integer.parseInt(input);
                    if (quantity < 0) {
                        JOptionPane.showMessageDialog(this, "Invalid input! Please enter a positive quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        entry.getValue().setQuantity(entry.getValue().getQuantity() + quantity);
                        outputTextArea.append("Php " + entry.getKey() + " refilled successfully.\n");
                        isValid = true; // Move to the next denomination
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            changeOutput.setLength(0); // Clear the StringBuilder for the next denomination
        }
    }

    private void collectPayment(JTextArea outputTextArea) {
        outputTextArea.setText("");
    
        // Get the total sales amount from the vending machine
        double totalSales = vendingMachine.getTotalSales();
    
        if (totalSales == 0) {
            outputTextArea.append("No amount to collect. There are no transactions.\n\n");
            return;
        }
    
        outputTextArea.append("Amount to Collect: Php " + totalSales + "\n");
    
    }
    

    private void purchaseItem(JTextArea outputTextArea) {
        JPanel purchasePanel = new JPanel(new GridLayout(0, 2));
        purchasePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Drink selection
        JLabel drinkLabel = new JLabel("Select a Drink:");
        String[] drinkOptions = {"White Chocolate Coffee", "Caramel Macchiato"};
        JComboBox<String> drinkComboBox = new JComboBox<>(drinkOptions);
        purchasePanel.add(drinkLabel);
        purchasePanel.add(drinkComboBox);

        // Cup Size selection
        JLabel cupSizeLabel = new JLabel("Select Cup Size:");
        String[] cupSizeOptions = {"Small", "Medium", "Large"};
        JComboBox<String> cupSizeComboBox = new JComboBox<>(cupSizeOptions);
        purchasePanel.add(cupSizeLabel);
        purchasePanel.add(cupSizeComboBox);

        // Temperature selection
        JLabel temperatureLabel = new JLabel("Select Temperature:");
        String[] temperatureOptions = {"Hot", "Iced"};
        JComboBox<String> temperatureComboBox = new JComboBox<>(temperatureOptions);
        purchasePanel.add(temperatureLabel);
        purchasePanel.add(temperatureComboBox);

        // Sugar Level selection
        JLabel sugarLevelLabel = new JLabel("Select Sugar Level:");
        String[] sugarLevelOptions = {"No Sugar", "With Sugar"};
        JComboBox<String> sugarLevelComboBox = new JComboBox<>(sugarLevelOptions);
        purchasePanel.add(sugarLevelLabel);
        purchasePanel.add(sugarLevelComboBox);

        // Milk Choice selection
        JLabel milkChoiceLabel = new JLabel("Select Milk Choice:");
        String[] milkChoiceOptions = {"Cow Milk", "Soy Milk", "Almond Milk"};
        JComboBox<String> milkChoiceComboBox = new JComboBox<>(milkChoiceOptions);
        purchasePanel.add(milkChoiceLabel);
        purchasePanel.add(milkChoiceComboBox);

        // Espresso Shots checkbox
        JCheckBox numEspressoCheckBox = new JCheckBox("Add Espresso Shots");
        purchasePanel.add(numEspressoCheckBox);

        // Amount Paid input
        JLabel amountPaidLabel = new JLabel("Enter the amount to pay (or 0 to cancel):");
        JTextField amountPaidTextField = new JTextField();
        purchasePanel.add(amountPaidLabel);
        purchasePanel.add(amountPaidTextField);

        int result = JOptionPane.showConfirmDialog(this, purchasePanel, "Purchase Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            double amountPaid;
            try {
                amountPaid = Double.parseDouble(amountPaidTextField.getText());
            } catch (NumberFormatException ex) {
                outputTextArea.append("Invalid amount. Please enter a valid number.\n");
                return;
            }

            int drinkChoice = drinkComboBox.getSelectedIndex();
            if (drinkChoice < 0) {
                outputTextArea.append("Please select a drink.\n");
                return;
            }

            String drinkName = getDrinkNameByChoice(drinkChoice);
            if (drinkName == null) {
                outputTextArea.append("Invalid drink choice. Purchase canceled.\n");
                return;
            }

            String cupSize = (String) cupSizeComboBox.getSelectedItem();
            String temperature = (String) temperatureComboBox.getSelectedItem();
            String sugarLevel = (String) sugarLevelComboBox.getSelectedItem();
            String milkChoice = (String) milkChoiceComboBox.getSelectedItem();
            int numEspressoShots = numEspressoCheckBox.isSelected() ? 1 : 0;

            CustomDrink customDrink = new CustomDrink(drinkName);
            customDrink.setCupSize(cupSize);
            customDrink.setTemperature(temperature);
            customDrink.setSugarLevel(sugarLevel);
            customDrink.setMilkChoice(milkChoice);
            customDrink.setNumEspressoShots(numEspressoShots);

            int totalCalories = vendingMachine.calculateTotalCalories(customDrink);
            double totalPrice = vendingMachine.calculateTotalPrice(customDrink);

            if (amountPaid < totalPrice) {
                outputTextArea.append("Insufficient payment. Purchase canceled.\n");
                return;
            }

            result = JOptionPane.showConfirmDialog(this, getOrderSummary(customDrink, totalCalories, totalPrice) + "Confirm purchase?", "Confirm Purchase", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                vendingMachine.prepareDrink(customDrink);
                outputTextArea.append("Drink prepared and purchased successfully.\n");

                double change = amountPaid - totalPrice;
                outputTextArea.append("Change: Php " + change + "\n");
                outputTextArea.append("Thank you for your purchase!\n");
            } else {
                outputTextArea.append("Purchase canceled.\n");
            }
        } else {
            outputTextArea.append("Purchase canceled.\n");
        }
    }


    private void displayInventory(JTextArea outputTextArea) {
        outputTextArea.append("Slot\tItem Name\tPrice\t\tQuantity\n");
        for (int i = 0; i < vendingMachine.getInventory().size(); i++) {
            Inventory itemInventory = vendingMachine.getInventory().get(i);
            Item item = itemInventory.getItem();
            String itemName = item.getName();
            double itemPrice = item.getPrice();
            int quantity = itemInventory.getQuantity();
            String slotLabel = "[" + (i + 1) + "]";
            String quantityLabel = (quantity > 0) ? String.valueOf(quantity) : "Out of stock";
            outputTextArea.append(String.format("%-8s%-16sPhp%8.2f    %s%n", slotLabel, itemName, itemPrice, quantityLabel));
        }
        outputTextArea.append("\n");
    }

    public void printTransactionHistory(JTextArea outputTextArea) {
        // Calculate the total number of items purchased
        int totalItemsPurchased = 0;
        double totalSalesAmount = 0;
        List<Inventory> invList = vendingMachine.getInventory();

        // Now you can use the invList to access and manipulate the inv list
        for (Inventory itemInventory : invList)  {
            totalItemsPurchased += itemInventory.getQuantity();
            totalSalesAmount += itemInventory.getItem().getPrice() * itemInventory.getQuantity();
        }

        // Create the transaction history message
        String message = "Transaction History:\n";
        message += "Total Items Purchased: " + totalItemsPurchased + "\n";
        message += "Total Sales Amount: Php " + String.format("%.2f", totalSalesAmount) + "\n";

        // Display the message box with the transaction history
        JOptionPane.showMessageDialog(null, message, "Transaction History", JOptionPane.INFORMATION_MESSAGE);
    }
}
