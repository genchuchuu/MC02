import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class RVMGUI extends JFrame {
    private RegularVendingMachine vendingMachine;

    public RVMGUI(RegularVendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        setTitle("Regular Vending Machine");
        setSize(600, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
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

    // Method to create the panel for Test Features
    private JPanel createTestFeaturesPanel() {
        JPanel testPanel = new JPanel(new BorderLayout());

        // A text area to display output to the user
        JTextArea outputTextArea = new JTextArea(10, 30);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        testPanel.add(scrollPane, BorderLayout.CENTER);

        // Button for simulating purchasing an item
        JButton purchaseButton = new JButton("Purchase Item");
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchaseItem(outputTextArea);
            }
        });

        testPanel.add(purchaseButton, BorderLayout.SOUTH);

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
                String result = restockItems(outputTextArea);
                outputTextArea.append(result);
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

    private String restockItems(JTextArea outputTextArea) {
        StringBuilder restockOutput = new StringBuilder();

        restockOutput.append("Restocking items:\n");
        restockOutput.append("Enter the quantity to restock for each item:\n");

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
                    restockOutput.append("Restocking for " + itemName + " canceled.\n");
                    break;
                } 
                
                try {
                    int quantity = Integer.parseInt(input);
                    if (quantity < 10 || quantity > 15) {
                        JOptionPane.showMessageDialog(this, "Invalid input! Please enter a quantity between 10 and 15.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int actualRestockQuantity = Math.min(quantity, remainingCapacity);
                        itemInventory.addItem(actualRestockQuantity);
                        restockOutput.append(actualRestockQuantity + " pieces of " + itemName + " restocked.\n");
                        isValid = true;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        restockOutput.append("Items restocked successfully.\n\n");
        return restockOutput.toString();
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
                        item.setPrice(newPrice);
                         if (newPrice <= 0) {
                            // User clicked the "Cancel" button
                            outputTextArea.append("Setting of item price for " + item.getName() + " canceled.\n\n");
                            return; // Exit the method without making any changes
                        }
                        outputTextArea.append("Price for " + item.getName() + " updated successfully.\n\n");
                        isValid = true; // Set isValid to true to exit the loop
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid price.\n", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Slot Number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
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
        double amountPaid = 0;
        int slotNumber = 0;
    
        while (true) {
            String amountPaidInput = JOptionPane.showInputDialog(this, "Enter the amount to pay: ", "Amount to Pay", JOptionPane.PLAIN_MESSAGE);
    
            if (amountPaidInput == null) {
                // User clicked the "Cancel" button
                outputTextArea.append("Purchase canceled.\n\n");
                return; // Exits the method without making any changes
            }
    
            try {
                amountPaid = Double.parseDouble(amountPaidInput);
                if (amountPaid <= 0) {
                    JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a new amount to pay.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    break; // Exit the loop once a valid amount is entered
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid number for the amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
        outputTextArea.append("Available Items:\n");
        displayInventory(outputTextArea);
    
        while (true) {
            String slotNumberInput = JOptionPane.showInputDialog(this, "Enter the slot number to purchase the item: ", "Slot Number of Item", JOptionPane.PLAIN_MESSAGE);
    
            if (slotNumberInput == null) {
                // User clicked the "Cancel" button
                outputTextArea.append("Purchase canceled.\n\n");
                return; // Exits the method without making any changes
            }
    
            try {
                slotNumber = Integer.parseInt(slotNumberInput);
                if (slotNumber >= 1 && slotNumber <= vendingMachine.getInventory().size()) {
                    break; // Exit the loop once a valid slot number is entered
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid slot number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid number for the slot number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
        int selectedIndex = slotNumber - 1;
        Inventory selectedInventory = vendingMachine.getInventory().get(selectedIndex);
        Item item = selectedInventory.getItem();
    
        if (selectedInventory.getQuantity() > 0) {
            outputTextArea.append("Selected item: " + item.getName() + "\n");
            outputTextArea.append("Price: " + item.getPrice() + "\n");
            outputTextArea.append("Calories: " + item.getCalories() + "\n");
    
            if (amountPaid >= item.getPrice()) {
                double change = amountPaid - item.getPrice();
                if (vendingMachine.hasSufficientChange(change)) {
                    selectedInventory.removeItem(1);
                    vendingMachine.addToSales(item.getPrice());
                    vendingMachine.sales.add(item); // Add the purchased item to the sales list
                    outputTextArea.append("Item purchased successfully.\n");
                    outputTextArea.append("Change: " + change + "\n");
                    outputTextArea.append("Dispensing Item: " + item.getName() + "\n");   
                    // Update vending machine's cash balance and display the denominations of change
                    String changeOutput = vendingMachine.produceChange(change);
                    outputTextArea.append(changeOutput);
                } else {
                    outputTextArea.append("Insufficient change. Please try again later.\n\n");
                }
            } else {
                outputTextArea.append("Insufficient amount paid. Transaction canceled.\n\n");
            }
        } else {
            outputTextArea.append("Selected item is out of stock.\n\n");
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

    private void printTransactionHistory(JTextArea outputTextArea) {
        StringBuilder transactionSummary = new StringBuilder("Transaction Summary:\n");

        for (Item item : vendingMachine.sales) {
            transactionSummary.append("Item: ").append(item.getName()).append(" - Price: ").append(item.getPrice()).append("\n");
        }

        outputTextArea.setText(transactionSummary.toString());
    }
}
