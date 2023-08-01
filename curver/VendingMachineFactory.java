import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class VendingMachineFactory extends JFrame {
    private static final int MAX_VM = 4;
    private static final int MAX_ITEMS = 8;

    private JTabbedPane tabbedPane;
    private JPanel vendingPanel;
    private JButton createRegularButton;
    private JButton createSpecialButton;
    private JButton descFactory;
    private JButton exitButton;

    public VendingMachineFactory() {

        setTitle("Vending Machine Factory");

        setSize(600, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize the tabbed pane
        tabbedPane = new JTabbedPane();

        // Create Regular Vending Machine Panel
        vendingPanel = new JPanel();
        vendingPanel.setLayout(new GridBagLayout());
        
        
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets = new Insets(10, 10, 10, 10);
        Insets insets = new Insets(30, 20, 30, 20);
        

        createRegularButton = new JButton("Create Regular Machine");
        createRegularButton.setMargin(insets);
        constraint.gridx = 0;
        constraint.gridy = 0;
        vendingPanel.add(createRegularButton, constraint);
        

        createSpecialButton = new JButton("Create Special Machine");
        createSpecialButton.setMargin(insets);
        constraint.gridx = 1;
        constraint.gridy = 0;
        vendingPanel.add(createSpecialButton, constraint);


        descFactory = new JButton("About the Company");
        descFactory.setMargin(insets);
        constraint.gridx = 2;
        constraint.gridy = 0;
        vendingPanel.add(descFactory, constraint);
        
        exitButton = new JButton("Exit");
        exitButton.setMargin(insets);
        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.gridwidth = 3;
        vendingPanel.add(exitButton, constraint);

        // Add panels to the tabbed pane
        tabbedPane.addTab("Thirsty Drinky", vendingPanel);

        // Add the tabbed pane to the main frame
        add(tabbedPane, BorderLayout.CENTER);


        // Add action listeners to the buttons
        createRegularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRVM();
            }
        });
        createSpecialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSVM();
            }
        });

        descFactory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayWelcomeMessage();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitMessageAndExit();
            }
        });

    }

    private void displayWelcomeMessage() {
        JFrame frame = new JFrame("Thirsty Drinky Welcome Message");
        JOptionPane.showMessageDialog(frame, "\n\n\t\tWELCOME TO THIRSTY DRINKY FACTORY\n\n\t\tHello! We are ThirstyDrinky! We are a company that offers vending\n"
        + "\t\tmachines for beverages. We have regular vending machines consisting\n"
        + "\t\tsimple drinks such as water, juices, and milk. We also offer special\n"
        + "\t\tof vending machines that allow customers to customize their coffee drinks.\n"
        + "\t\tWe hope to satisfy your needs in creating a vending machine.\n"
        + "\t\tThank you!\n");
    }

    private void showExitMessageAndExit() {
        JOptionPane.showMessageDialog(this, "Thank you for using Thirsty Drinky Vending Machine Factory!", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
        // Close the program
        System.exit(0);
    }

    private void createRVM() {
        List<RegularVendingMachine> regularVendingMachines = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        int quantity = 0;
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(this, "Enter the quantity of regular vending machines to create (1-4):", "Number of Regular Vending Machines To Make", JOptionPane.INFORMATION_MESSAGE);
                if (input == null) {
                    return;
                }
                quantity = Integer.parseInt(input);
                if (quantity >= 1 && quantity <= MAX_VM) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid quantity. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                } 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        for (int i = 0; i < quantity; i++) {
            RegularVendingMachine regularVendingMachine = createRegularVendingMachine(scanner);
            regularVendingMachines.add(regularVendingMachine);

            // Create and display RVMGUI
            SwingUtilities.invokeLater(() -> {
                RVMGUI rvmgui = new RVMGUI(regularVendingMachine);
                rvmgui.setVisible(true);
            });
        }
    }

    private void createSVM() {
        List<SpecialVendingMachine> specialVendingMachines = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        int quantity = 0;
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(this, "Enter the quantity of special vending machines to create (1-4):", "Number of Special Vending Machines To Make", JOptionPane.INFORMATION_MESSAGE);
                if (input == null) {
                    return;
                }
                quantity = Integer.parseInt(input);
                if (quantity >= 1 && quantity <= MAX_VM) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid quantity. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        for (int i = 0; i < quantity; i++) {
            SpecialVendingMachine specialVendingMachine = createSpecialVendingMachine(scanner);
            specialVendingMachines.add(specialVendingMachine);

            // Create and display SVMGUI (Assuming SVMGUI is a valid class)
            SVMGUI svmgui = new SVMGUI(specialVendingMachine);
            svmgui.setVisible(true);
        }
    }

    private String getAvailableItemsString() {
            StringBuilder itemsString = new StringBuilder();
            itemsString.append("Available Items:\n");
            itemsString.append("[1] Water               Php 20.0\n");
            itemsString.append("[2] Iced tea            Php 35.0\n");
            itemsString.append("[3] Apple juice         Php 35.0\n");
            itemsString.append("[4] Orange juice        Php 35.0\n");
            itemsString.append("[5] Grape juice         Php 35.0\n");
            itemsString.append("[6] Coconut juice       Php 35.0\n");
            itemsString.append("[7] Lemon juice         Php 40.0\n");
            itemsString.append("[8] Cow milk            Php 45.0\n");
            itemsString.append("[9] Coconut milk        Php 45.0\n");
            itemsString.append("[10] Matcha             Php 60.0\n");
            itemsString.append("[11] Soy milk           Php 60.0\n");
            itemsString.append("[12] Almond milk        Php 75.0\n");
            return itemsString.toString();
        }

    private RegularVendingMachine createRegularVendingMachine(Scanner scanner) {
        // Implement the logic for creating a regular vending machine
        List<Inventory> inventory = new ArrayList<>();
        Map<Double, Denomination> acceptedCash = new HashMap<>();
        List<String> chosenItems = new ArrayList<>();


        for (int i = 0; i < MAX_ITEMS; i++) {
            int productChoice;
            boolean validChoice = false;

            while (!validChoice) {
                try {
                    String input = JOptionPane.showInputDialog(this, getAvailableItemsString() + "\nEnter the ID for product of slot " + (i + 1) + ":", "Fill your Vending Machine Slots", JOptionPane.INFORMATION_MESSAGE);
                    if (input == null) {
                        return null;
                    }
                    productChoice = Integer.parseInt(input);

                    String product = getProductByChoice(productChoice);
                    if (product == null) {
                        JOptionPane.showMessageDialog(this, "Invalid choice. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (chosenItems.contains(product)) {
                        JOptionPane.showMessageDialog(this, "You have already chosen that item. Please choose a different one.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        chosenItems.add(product);
                        inventory.add(new Inventory(new Item(product, getPriceByChoice(productChoice), getCaloriesByChoice(productChoice)), 0));
                        validChoice = true;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        acceptedCash.put(0.10, new Denomination(0.5));
        acceptedCash.put(0.25, new Denomination(0.10));
        acceptedCash.put(0.50, new Denomination(0.75));
        acceptedCash.put(0.75, new Denomination(0.25));
        acceptedCash.put(5.0, new Denomination(5));
        acceptedCash.put(10.0, new Denomination(10));
        acceptedCash.put(20.0, new Denomination(20));
        acceptedCash.put(50.0, new Denomination(50));
        acceptedCash.put(100.0, new Denomination(100));
        acceptedCash.put(500.0, new Denomination(500));
        
        JOptionPane.showMessageDialog(this, "Please proceed first to the Maintenance Features Tab before Testing the Vending Machine Features", "Take Note", JOptionPane.INFORMATION_MESSAGE);
        return new RegularVendingMachine(inventory, acceptedCash);
    }

    private SpecialVendingMachine createSpecialVendingMachine(Scanner scanner) {
        List<Inventory> inventory = new ArrayList<>();
        Map<Double, Denomination> acceptedCash = new HashMap<>();

        inventory.add(createWhiteChocolateCoffee());
        inventory.add(createCaramelMacchiato());
        inventory.add(createVanillaSyrup());
        inventory.add(createCups("Small"));
        inventory.add(createCups("Medium"));
        inventory.add(createCups("Large"));
        inventory.add(createIce());
        inventory.add(createEspresso());
        inventory.add(createSugar());
        inventory.add(createMilk("Cow"));
        inventory.add(createMilk("Soy"));
        inventory.add(createMilk("Almond"));

        acceptedCash.put(0.10, new Denomination(0.5));
        acceptedCash.put(0.25, new Denomination(0.10));
        acceptedCash.put(0.50, new Denomination(0.75));
        acceptedCash.put(0.75, new Denomination(0.25));
        acceptedCash.put(5.0, new Denomination(5));
        acceptedCash.put(10.0, new Denomination(10));
        acceptedCash.put(20.0, new Denomination(20));
        acceptedCash.put(50.0, new Denomination(50));
        acceptedCash.put(100.0, new Denomination(100));
        acceptedCash.put(500.0, new Denomination(500));

        JOptionPane.showMessageDialog(this, "Please proceed first to the Maintenance Features Tab before Testing the Vending Machine Features", "Take Note", JOptionPane.INFORMATION_MESSAGE);
        return new SpecialVendingMachine(inventory, acceptedCash);
    }

    private Inventory createMilk(String milkType) {
        double price = 25.0;
        int calories = 60;
        String description = milkType + " Milk"; // Update the description with milk type
        
        if(milkType == "Cow"){
            price = 25.0;
            calories = 60;  
        }

        else if(milkType == "Soy"){
            price = 30.0;
            calories = 70;
        }

        else if(milkType == "Almond"){
            price = 35.0;
            calories = 80;
        }

        return new Inventory(new Item(description, price, calories), 0);
    }

    private Inventory createEspresso() {
        double price = 40.0;
        int calories = 5;
        String description = "Espresso";
    
        return new Inventory(new Item(description, price, calories), 0);
    }
    
    private Inventory createSugar() {
        double price = 5.0;
        int calories = 15;
        String description = "Sugar";
    
        return new Inventory(new Item(description, price, calories), 0);
    }

    private Inventory createVanillaSyrup() {
        double price = 50.0;
        int calories = 100;
        String description = "Vanilla Syrup";
        return new Inventory(new Item(description, price, calories), 0); // Change the quantity as required
    }

    private Inventory createCups(String size) {
        double price = 5.0;
        int calories = 0;
        String description = "Cup - " + size;
        return new Inventory(new Item(description, price, calories), 0); // Change the quantity as required
    }

    private Inventory createIce() {
        double price = 5.0;
        int calories = 0;
        String description = "Ice";
        return new Inventory(new Item(description, price, calories), 0); // Change the quantity as required
    }

    private Inventory createWhiteChocolateCoffee() {
        double price = 85.0;
        int calories = 75;
        String description = "White Chocolate Powder";

        return new Inventory(new Item(description, price, calories), 0);
    }

    private Inventory createCaramelMacchiato() {
        double price = 80.0;
        int calories = 50;
        String description = "Caramel Macchiato Powder";

        return new Inventory(new Item(description, price, calories), 0);
    }

    private String getProductByChoice(int choice) {
        switch (choice) {
            case 1:
                return "Water";
            case 2:
                return "Iced tea";
            case 3:
                return "Apple juice";
            case 4:
                return "Orange juice";
            case 5:
                return "Grape juice";
            case 6:
                return "Coconut juice";
            case 7:
                return "Lemon juice";
            case 8:
                return "Cow milk";
            case 9:
                return "Coconut Milk";
            case 10:
                return "Matcha";
            case 11:
                return "Soy Milk";
            case 12:
                return "Almond Milk";
            default:
                return null;
        }
    }

    private double getPriceByChoice(int choice) {
        switch (choice) {
            case 1:
                return 20.0;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return 35.0;
            case 7:
                return 40.0;
            case 8:
            case 9:
                return 45.0;
            case 10:
            case 11:
                return 60.0;
            case 12:
                return 75.0;
            default:
                return 0.0;
        }
    }

    private int getCaloriesByChoice(int choice) {
        switch (choice) {
            case 1:
                return 0;
            case 2:
                return 95;
            case 3:
            case 4:
                return 118;
            case 5:
                return 161;
            case 6:
                return 48;
            case 7:
                return 59;
            case 8:
                return 109;
            case 9:
                return 575;
            case 10:
                return 68;
            case 11:
                return 135;
            case 12:
                return 43;
            default:
                return 0;
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VendingMachineFactory factory = new VendingMachineFactory();
            factory.setVisible(true);

            
        });
    }
}
