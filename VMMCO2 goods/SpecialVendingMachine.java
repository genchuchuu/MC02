import java.util.*;

class SpecialVendingMachine extends VendingMachine {
    private static final Map<Integer, String> CUP_SIZE_MAP = new HashMap<>();
    static {
        CUP_SIZE_MAP.put(1, "Small");
        CUP_SIZE_MAP.put(2, "Medium");
        CUP_SIZE_MAP.put(3, "Large");
    }
    private static final Map<Integer, String> TEMPERATURE_MAP = new HashMap<>();
    static {
        TEMPERATURE_MAP.put(1, "Iced");
        TEMPERATURE_MAP.put(2, "Hot");
    }

    // Define the mapping of choice to sugar level
    private static final Map<Integer, String> SUGAR_LEVEL_MAP = new HashMap<>();
    static {
        SUGAR_LEVEL_MAP.put(1, "With sugar");
        SUGAR_LEVEL_MAP.put(2, "Without sugar");
    }

    // Define the mapping of choice to milk choice
    private static final Map<Integer, String> MILK_CHOICE_MAP = new HashMap<>();
    static {
        MILK_CHOICE_MAP.put(1, "Cow");
        MILK_CHOICE_MAP.put(2, "Soy");
        MILK_CHOICE_MAP.put(3, "Almond");
    }
    protected List<Inventory> inv;

    public SpecialVendingMachine(List<Inventory> inventory, Map<Double, Denomination> acceptedCash) {
        super(acceptedCash); 
        this.inv = new ArrayList<>(inventory);
    }

    @Override
    public void testMaintenanceFeatures() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMaintenance Menu:");
            System.out.println("[1] Restock Items");
            System.out.println("[2] Set Item Price");
            System.out.println("[3] Collect Payment");
            System.out.println("[4] Add Money For Change");
            System.out.println("[5] Print Transaction Summary");
            System.out.println("[6] Return to Test a Vending Machine Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    restockItems(scanner);
                    break;
                case 2:
                    setItemPrice(scanner);
                    break;
                case 3:
                    collectPayment();
                    break;
                case 4:
                    makeChange(scanner);
                    break;
                case 5:
                    printTransactionSummary();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void addMoneyForChange(double amount) {
        if (amount > 0) {
            Denomination changeDenomination = new Denomination(amount);
            super.addChange(changeDenomination);
            System.out.println("Added " + amount + " to the change fund.");
        } else {
            System.out.println("Invalid amount. Please enter a positive value.");
        }
    }
    public void restockAddOnItems(Scanner scanner) {
        System.out.println("\nRestocking Add-On Items:");
    
        // Define the restockItems map here
        Map<String, Integer> restockItems = new HashMap<>();


        for (Inventory inventoryItem : inv) {
            System.out.print("Enter the quantity to restock for " + inventoryItem.getItem().getName() + " (11 to 15 pieces only): ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
    
            // Check if the entered quantity is within the valid range (11 to 15)
            while (quantity < 11 || quantity > 15) {
                System.out.println("Invalid quantity. Please enter a quantity between 11 and 15.");
                System.out.print("Enter the quantity to restock for " + inventoryItem.getItem().getName() + " (11 to 15 pieces only): ");
                quantity = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
            }
    
            restockItems.put(inventoryItem.getItem().getName(), quantity);
        }
    
        // Restock the add-on items in the inventory
        for (Map.Entry<String, Integer> entry : restockItems.entrySet()) {
            String itemName = entry.getKey();
            int quantityToAdd = entry.getValue();
    
            boolean itemFound = false;
            for (Inventory inventoryItem : inv) {
                if (inventoryItem.getItem().getName().equals(itemName)) {
                    inventoryItem.addQuantity(quantityToAdd);
                    itemFound = true;
                    if (quantityToAdd > 0) {
                        System.out.println(quantityToAdd + " units of " + itemName + " added to the inventory.");
                    } else if (quantityToAdd < 0) {
                        System.out.println(-quantityToAdd + " units of " + itemName + " removed from the inventory.");
                    } else {
                        System.out.println("No change in inventory for " + itemName + ".");
                    }
                    break;
                }
            }
    
            if (!itemFound) {
                System.out.println("Item " + itemName + " not found in the inventory. Restocking failed.");
            }
        }
    
        System.out.println("Add-On Items restocked successfully.");
    }
    
    protected void dispenseItem(int slotNumber) {
        Inventory item = inv.get(slotNumber - 1);
        System.out.println("Dispensed item: " + item.getItem().getName());
    }

    protected void setItemPrice(Scanner scanner) {
        System.out.print("Enter the slot number to set the price: ");
        int slotNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (slotNumber >= 1 && slotNumber <= inv.size()) {
            Inventory item = inv.get(slotNumber - 1);
            System.out.print("Enter the new price: ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            item.getItem().setPrice(newPrice);
            System.out.println("Price updated successfully.");
        } else {
            System.out.println("Invalid slot number.");
        }
    }

    @Override
    protected void collectPayment() {
        System.out.println("Collecting payment...");
        // Perform the payment collection logic for the special vending machine
        System.out.println("Payment collected successfully.");
    }

    private void makeChange(Scanner scanner) {
        for (Map.Entry<Double, Denomination> entry : acceptedCash.entrySet()) {
            System.out.print("Enter the quantity to refill for Php" + entry.getKey() + ": ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            entry.getValue().setQuantity(entry.getValue().getQuantity() + quantity);
        }
    
        System.out.println("Change refill successful.");
    }

    protected void calculateTotalInventory() {
        int totalItems = 0;
        for (Inventory item : inv) {
            totalItems += item.getQuantity();
        }
        System.out.println("Total inventory: " + totalItems);
    }

    protected void displayInventory() {
        System.out.println("Slot\tItem Name\tPrice\t\tQuantity");
        for (int i = 0; i < inv.size(); i++) {
            Inventory itemInventory = inv.get(i);
            Item item = itemInventory.getItem();
            String itemName = item.getName();
            double itemPrice = item.getPrice();
            int quantity = itemInventory.getQuantity();
            String slotLabel = "[" + (i + 1) + "]";
            String quantityLabel = (quantity > 0) ? String.valueOf(quantity) : "Out of stock";
            System.out.printf("%-8s%-16sPhp%8.2f    %s%n", slotLabel, itemName, itemPrice, quantityLabel);
        }
    }

    protected void restockItems(Scanner scanner) {
        System.out.println("\nCurrent Inventory:");
        displayInventory();

        for (Inventory itemInventory : inv) {
            System.out.print("Enter the quantity to restock for 10 up to 15 pieces only " + itemInventory.getItem().getName() + ": ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            
            if (itemInventory.getQuantity() == 11) {
                System.out.println("Maximum inventory of " + 11 + " reached for " + itemInventory.getItem().getName() + ". Restocking not allowed.");
                System.out.print("Do you want to cancel restocking? (Y/N): ");
                String input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("y")) {
                    System.out.println("Restocking canceled.");
                    return;
                }
            }

            while (quantity < 10 || quantity > 15) {
                System.out.println("Invalid input! Please try again");
                System.out.print("Enter the quantity to restock for " + itemInventory.getItem().getName() + ": ");
                quantity = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
            }

            itemInventory.addItem(quantity);
        }

        System.out.println("Items restocked successfully.");
    }

    @Override
    public void testVendingFeatures() {
        System.out.println("Testing Special Vending Machine Vending Features");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nVending Machine Test Menu:");
            System.out.println("[1] Purchase Item");
            System.out.println("[2] Return to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    purchaseItem(scanner);
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void purchaseItem(Scanner scanner) {
        System.out.print("Enter the amount to pay (or 0 to cancel): ");
        double amountPaid = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
    
        if (amountPaid == 0) {
            System.out.println("Purchase canceled.");
            return;
        }

        System.out.println("\nCustomizing Drink:");
        System.out.println("Available Drinks: ");
        System.out.println("[1] White Chocolate Coffee");
        System.out.println("[2] Caramel Macchiato");
        System.out.print("Enter the drink number to customize (or 0 to cancel): ");
        int drinkChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (drinkChoice == 0) {
            System.out.println("Purchase canceled.");
            return;
        }


        String drinkName = getDrinkNameByChoice(drinkChoice);
        if (drinkName == null) {
            System.out.println("Invalid drink choice. Purchase canceled.");
            return;
        }

    

        // Create a CustomDrink object to store customization options
        CustomDrink customDrink = new CustomDrink(drinkName);

        // Get the available customization options
        System.out.println("Customization options for " + drinkName + ":");

        // 1. Cup Size customization logic (small, medium, large)
        System.out.println("\nCup Size");
        System.out.println("\n[1] SMALL\t PHP 85");
        System.out.println("[2] MEDIUM\t PHP 100");
        System.out.println("[3] LARGE\t PHP 120");
        System.out.print("Enter your choice: ");
        int cupSizeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        customDrink.setCupSize(getCupSizeByChoice(cupSizeChoice));

        // 2. Temperature customization logic (iced or hot)
        System.out.println("\nTemperature");
        System.out.println("\n[1] ICED");
        System.out.println("[2] HOT");
        System.out.print("Enter your choice: ");
        int temperatureChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        customDrink.setTemperature(getTemperatureByChoice(temperatureChoice));

        // 3. Sugar Level customization logic (with or without sugar)
        System.out.println("\nSugar Level");
        System.out.println("\n[1] WITH SUGAR\t PHP 10");
        System.out.println("[2] WITHOUT SUGAR");
        System.out.print("Enter your choice: ");
        int sugarChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        customDrink.setSugarLevel(getSugarLevelByChoice(sugarChoice));

        // 4. Milk Choice customization logic (cow, soy, almond)
        System.out.println("\nMilk Choice");
        System.out.println("\n[1] COW\t PHP 25");
        System.out.println("[2] SOY\t PHP 30");
        System.out.println("[3] ALMOND\t PHP 35");
        System.out.print("Enter your choice: ");
        int milkChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        customDrink.setMilkChoice(getMilkChoiceByChoice(milkChoice));

        // 5. Shots of Espresso customization logic (hot drinks)
        if (customDrink.getTemperature().equals("Hot")) {
            System.out.println("\nShots of Espresso (Hot)");
            System.out.println("\n[1] shot");
            System.out.println("[2] shots");
            System.out.print("Enter your choice: ");
            int espressoChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            customDrink.setNumEspressoShots(getEspressoShotsByChoice(espressoChoice));
        }

        // 6. Shots of Espresso customization logic (iced drinks)
        if (customDrink.getTemperature().equals("Iced")) {
            System.out.println("Shots of Espresso (Iced)");
            System.out.println("\n[1] shot");
            System.out.println("[2] shots");
            System.out.print("Enter your choice: ");
            int espressoChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            customDrink.setNumEspressoShots(getEspressoShotsByChoice(espressoChoice));
        }

    int totalCalories = calculateTotalCalories(customDrink);
    double totalPrice = calculateTotalPrice(customDrink);

    // Check if the amount paid is enough
    if (amountPaid < totalPrice) {
        System.out.println("Insufficient payment. Purchase canceled.");
        return;
    }

    // Confirm the order and proceed with payment
    confirmOrder(scanner, customDrink, totalCalories, totalPrice, amountPaid);
    }

    // Helper methods for customization logic (implement these)

    private String getCupSizeByChoice(int choice) {
        return CUP_SIZE_MAP.getOrDefault(choice, "Invalid Size");
    }

    private String getTemperatureByChoice(int choice) {
        return TEMPERATURE_MAP.getOrDefault(choice, "Invalid Temperature");
    }

    private String getSugarLevelByChoice(int choice) {
        return SUGAR_LEVEL_MAP.getOrDefault(choice, "Invalid Sugar Level");
    }

    private String getMilkChoiceByChoice(int choice) {
        return MILK_CHOICE_MAP.getOrDefault(choice, "Invalid Milk Choice");
    }

    private int getEspressoShotsByChoice(int choice) {
        switch (choice) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
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


    public void prepareDrink(CustomDrink customDrink) {
        System.out.println("\nPreparing the drink: " + customDrink.getDrinkName());

        System.out.println("Customizations:");
        System.out.println("Cup Size: " + customDrink.getCupSize());
        System.out.println("Temperature: " + customDrink.getTemperature());
        System.out.println("With: " + customDrink.getSugarLevel());
        System.out.println("Milk Choice: " + customDrink.getMilkChoice());
        System.out.println("Espresso Shots (Hot): " + customDrink.getNumEspressoShots());
        System.out.println("Espresso Shots (Iced): " + customDrink.getNumEspressoShots());
        System.out.println("\nMixing the drink...");
        System.out.println("\nPouring the drink to cup " + customDrink.getCupSize());
    }
    public double getTotalPrice() {
        // Calculate the total price based on customizations
        double totalPrice = 0;

        // Add the price based on cup size, temperature, sugar level, milk choice, and espresso shots
        // Add your logic here to calculate the price based on the selected options.

        return totalPrice;
    }

    public double getTotalPrice(CustomDrink customDrink) {
    // Calculate the total price based on customizations
    double totalPrice = calculateTotalPrice(customDrink);
    return totalPrice;
}


    public double calculateTotalPrice(CustomDrink customDrink) {
        double totalPrice = 0;
    
        // Calculate the base price based on cup size
        switch (customDrink.getCupSize()) {
            case "Small":
                totalPrice += 85;
                break;
            case "Medium":
                totalPrice += 100;
                break;
            case "Large":
                totalPrice += 120;
                break;
            default:
                // Handle any unexpected cup size
                break;
        }
    
        // Add the price based on temperature
        if (customDrink.getTemperature().equals("Iced")) {
            // You can add the price for iced drinks here if needed
            totalPrice += 0;
        } else if (customDrink.getTemperature().equals("Hot")) {
            // You can add the price for hot drinks here if needed
            totalPrice += 0;
        }
    
        // Add the price based on milk choice
        switch (customDrink.getMilkChoice()) {
            case "Cow":
                totalPrice += 25;
                break;
            case "Soy":
                totalPrice += 30;
                break;
            case "Almond":
                totalPrice += 35;
                break;
            default:
                break;
        }
    
        // Add the price based on espresso shots (hot drinks)
        if (customDrink.getTemperature().equals("Hot")) {
            totalPrice += customDrink.getNumEspressoShots() == 1 ? 0 : 50;
        }
    
        // Add the price based on espresso shots (iced drinks)
        if (customDrink.getTemperature().equals("Iced")) {
            totalPrice += customDrink.getNumEspressoShots() == 1 ? 0 : 50;
        }
    
        // Add the price based on sugar level
        switch (customDrink.getSugarLevel()) {
            case "With sugar":
                totalPrice += 10;
                break;
            case "Without sugar":
                totalPrice += 0;
                break;
            default:
                // Handle any unexpected sugar level
                break;
        }
    
        return totalPrice;
    }
    
    
    private void confirmOrder(Scanner scanner, CustomDrink customDrink, int totalCalories, double totalPrice, double amountPaid) {
        System.out.println("Confirming your order:");
        System.out.println("Customizations:");
        System.out.println("Cup Size: " + customDrink.getCupSize());
        System.out.println("Temperature: " + customDrink.getTemperature());
        System.out.println("Sugar Level: " + customDrink.getSugarLevel());
        System.out.println("Milk Choice: " + customDrink.getMilkChoice());
        System.out.println("Espresso Shots: " + customDrink.getNumEspressoShots() + " (Hot) / " + customDrink.getNumEspressoShots() + " (Iced)");
        System.out.println("Total Price: Php " + totalPrice); // Display the total price
        System.out.println("Total Calories: " + totalCalories);
    
        while (amountPaid < totalPrice) {
            System.out.println("Insufficient payment. Please try again.");
            System.out.print("Enter the amount to pay (or 0 to cancel): ");
            amountPaid = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character
    
            if (amountPaid == 0) {
                System.out.println("Purchase canceled.");
                return;
            }
        }
    
        double change = amountPaid - totalPrice;
        System.out.println("Payment successful. Change: " + change);
        prepareDrink(customDrink);
        addToSales(totalPrice);
    
    
        // Find the corresponding Inventory object in the list based on the customDrink
        Inventory selectedInventory = null;
        for (Inventory inventoryItem : inv) {
            if (inventoryItem.getItem().getName().equals(customDrink.getDrinkName())) {
                selectedInventory = inventoryItem;
                break;
            }
        }
    
        if (selectedInventory != null) {
            selectedInventory.removeItem(1); // Remove the purchased item from inventory
        } else {
            System.out.println("Error: Item not found in the inventory.");
            return;
        }
    
        dispenseItem(inv.indexOf(selectedInventory) + 1);
        produceChange(change);
        System.out.println("Drink prepared and purchased successfully.");
    }

    public Map<Double, Denomination> getAcceptedCash() {
        return acceptedCash;
    }

    public List<Inventory> getInventory() {
        return inv;
    }
    
    private void produceChange(double amount) {
        System.out.println("Dispensing change with the following denominations...");
        List<Double> availableDenominations = new ArrayList<>(acceptedCash.keySet());
        Collections.sort(availableDenominations, Collections.reverseOrder());

        for (double denomination : availableDenominations) {
            int numNotes = (int) (amount / denomination);
            if (numNotes > 0) {
                System.out.println("Php " + denomination + " x " + numNotes);
                amount -= (denomination * numNotes);
            }
        }
    }
    public double getTotalSales() {
        return totalSales;
    }

    // Update the addToSales method to increment the totalSales variable
    protected void addToSales(double amount) {
        super.addToSales(amount);
        totalSales += amount;
    }
    

    public int calculateTotalCalories(CustomDrink customDrink) {
        int totalCalories = 0;
    
        // Add calories based on cup size
        switch (customDrink.getCupSize()) {
            case "Small":
                totalCalories += 50;
                break;
            case "Medium":
                totalCalories += 100;
                break;
            case "Large":
                totalCalories += 150;
                break;
            default:
                // Handle any unexpected cup size
                break;
        }
    
        // Add calories based on temperature
        if (customDrink.getTemperature().equals("Iced")) {
            totalCalories += 0; // You can add calories for iced drinks here if needed
        } else if (customDrink.getTemperature().equals("Hot")) {
            totalCalories += 50; // You can add calories for hot drinks here if needed
        }
    
        // Add calories based on sugar level
        switch (customDrink.getSugarLevel()) {
            case "With sugar":
                totalCalories += 15;
                break;
            case "Without sugar":
                totalCalories += 0;
                break;
            default:
                // Handle any unexpected sugar level
                break;
        }
    
        // Add calories based on milk choice
        switch (customDrink.getMilkChoice()) {
            case "Cow":
                totalCalories += 60;
                break;
            case "Soy":
                totalCalories += 70;
                break;
            case "Almond":
                totalCalories += 80;
                break;
            default:
                // Handle any unexpected milk choice
                break;
        }
    
        // Add calories based on espresso shots (hot drinks)
        if (customDrink.getTemperature().equals("Hot")) {
            totalCalories += customDrink.getNumEspressoShots() * 10;
        }
    
        // Add calories based on espresso shots (iced drinks)
        if (customDrink.getTemperature().equals("Iced")) {
            totalCalories += customDrink.getNumEspressoShots() * 10;
        }
    
        return totalCalories;
    }
    
}
