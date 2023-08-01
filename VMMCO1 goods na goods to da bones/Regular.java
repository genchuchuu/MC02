import java.util.*;

public class Regular {
    private static final int MAX_INT = 15;
    private List<Inventory> inventory;
    private Map<String, Double> acceptedCash;
    private Map<Item, Integer> sales;
    private double totalSales;
    private int startingInventory;
    /**
     * Constructs and initializes the Regular vending machine with the provided inventory and accepted cash.
     * Creates a new HashMap for sales, sets the initial total sales to 0.0, and calculates
     * @param inventory the list of inventory items available in the vending machine.
     * @param acceptedCash the map of accepted cash denominations and their corresponding values.
     */
    public Regular(List<Inventory> inventory, Map<String, Double> acceptedCash) {
        this.inventory = inventory;
        this.acceptedCash = acceptedCash;
        this.sales = new HashMap<>();
        this.totalSales = 0.0;
        this.startingInventory = calculateTotalInventory();
    }

    /**
     * Prompts the user to enter the amount to pay and the slot number of the item to purchase.
     * Verifies the payment amount, item availability, and dispenses the item and change if applicable.
     * Updates the sales and totalSales variables accordingly.
     * @param scanner the Scanner object used for user input.
     */
    private void purchaseItem(Scanner scanner) {
        System.out.print("\nEnter the amount to pay (or 0 to cancel): ");
        double amountPaid = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
    
        if (amountPaid == 0) {
            System.out.println("\nPurchase cancelled.");
            return;
        }
    
        System.out.println("\nAvailable Items:");
        displayInventory();
    
        System.out.print("\nEnter the slot number of the item to purchase: ");
        int slotNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (slotNumber >= 1 && slotNumber <= inventory.size()) {
            Inventory selectedInventory = inventory.get(slotNumber - 1);
            Item item = selectedInventory.getItem();

            // asks user for confirmation about the purchase
            System.out.println("You have selected: " + item.getName());
            System.out.print("Confirm purchase? (Y/N): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            // if user confirms, proceed with purchase and dispensing of the item
            if (confirm.equals("y")) {
                if (selectedInventory.getQuantity() > 0) {
                    System.out.println("\nSelected item: " + item.getName());
                    System.out.println("Price: " + item.getPrice());
                    System.out.println("Calories: " + item.getCal());
    
                    if (amountPaid >= item.getPrice()) {
                        double change = amountPaid - item.getPrice();
                        if (hasSufficientChange(change)) {
                            selectedInventory.removeItem(1);
                            addToSales(item);
                            totalSales += item.getPrice();
                            System.out.println("\nItem purchased successfully.");
                            System.out.println("Change: " + change);
                            System.out.println("\nDispensing Item: " + item.getName());
                            System.out.println("\nDispensing change with the following denominations...");
                            dispenseChange(change);
                        } else {
                            System.out.println("\nInsufficient change. Transaction cancelled.");
                        }
                    } else {
                        System.out.println("\nInsufficient amount paid. Transaction cancelled.");
                    }
                } else {
                    System.out.println("\nSelected item is out of stock.");
                }
            }

            // if user does not confirm, user will be given an option to modify or terminate the purchase 
            else if (confirm.equals("n")) {
                System.out.print("Do you want to (M)odify or (T)erminate this purchase? (M/T): ");
                String modifyOrTerminate = scanner.nextLine().trim().toLowerCase();

                // if user chooses to modify, user will be asked to select a new item from the available options
                if (modifyOrTerminate.equals("m")) {
                    // allows the user to select a new item
                    System.out.println("\nPlease select a new item: ");
                    displayInventory();

                    System.out.print("\nEnter the slot number of the new item to purchase: ");
                    slotNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    if (slotNumber >= 1 && slotNumber <= inventory.size()) {
                        selectedInventory = inventory.get(slotNumber - 1); // updates selected inventory
                        item = selectedInventory.getItem(); // updates selected item

                        if (selectedInventory.getQuantity() > 0) {
                            System.out.println("\nSelected item: " + item.getName());
                            System.out.println("Price: " + item.getPrice());
                            System.out.println("Calories: " + item.getCal());
    
                            if (amountPaid >= item.getPrice()) {
                                double change = amountPaid - item.getPrice();
                                if (hasSufficientChange(change)) {
                                    selectedInventory.removeItem(1);
                                    addToSales(item);
                                    totalSales += item.getPrice();
                                    System.out.println("\nItem purchased successfully.");
                                    System.out.println("Change: " + change);
                                    System.out.println("\nDispensing Item: " + item.getName());
                                    System.out.println("\nDispensing change with the following denominations...");
                                    dispenseChange(change);
                                } else {
                                    System.out.println("\nInsufficient change. Please try again later.");
                                }
                            } else {
                                System.out.println("\nInsufficient amount paid. Transaction cancelled.");
                            }
                        } else {
                            System.out.println("\nSelected item is out of stock.");
                        }
                    } else {
                        System.out.println("Invalid slot number.");
                    }
                }
                // if user chooses to terminate, purchase will be cancelled and money inserted will be returned
                else if (modifyOrTerminate.equals("t")) {
                    // cancels the purchase and returns the money of the user
                    System.out.println("\nPurchase is cancelled.");
                    System.out.println("Returning money: " + amountPaid);
                    amountPaid = 0; // resets the inserted amount
                }
                else {
                    System.out.println("Invalid option. Purchase cancelled.");
                }
            } else {
                System.out.println("Invalid option. Purchase cancelled.");
            }  
        } else {
            System.out.println("Invalid slot number.");
        }
    }

    /**
     * Adds an item to the sales record.
     * If the item is already present in the sales record, the quantity is incremented by 1.
     * If the item is not present, it is added to the sales record with a quantity of 1.
     * @param item the Item object representing the item to be added to the sales record.
     */
    private void addToSales(Item item) {
        if (sales.containsKey(item)) {
            int quantity = sales.get(item);
            sales.put(item, quantity + 1);
        } else {
            sales.put(item, 1);
        }
    }
    
    /**
     * Produces change for the given amount.
     * Checks if there is sufficient change available to provide the requested amount.
     * If there is sufficient change, it dispenses the change with the appropriate denominations.
     * @param scanner the Scanner object used for user input.
     */
    private void produceChange(Scanner scanner) {
        System.out.print("\nEnter the amount for which to produce change: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        if (hasSufficientChange(amount)) {
            System.out.println("\nChange produced successfully: " + amount);
            System.out.println("\nDispensing change with the following denominations...");
            dispenseChange(amount);
        } else {
            System.out.println("\nInsufficient change. Please try again later.");
        }
    }

    /**
     * Restocks the items in the vending machine inventory.
     * Prompts the user to enter the quantity to restock for each item in the inventory.
     * The quantity should be between 10 and 15 (inclusive) for each item.
     * If an invalid quantity is entered, the user is prompted again until a valid input is provided.
     * After successful restocking, the inventory is updated accordingly.
     */
    /**
     * Restocks the items in the vending machine inventory.
     * Prompts the user to enter the quantity to restock for each item in the inventory.
     * The quantity should be between 10 and 15 (inclusive) for each item.
     * If an invalid quantity is entered, the user is prompted again until a valid input is provided.
     * After successful restocking, the inventory is updated accordingly.
     *
     * @param scanner the Scanner object used for user input.
     */
    private void restockItems(Scanner scanner) {
        System.out.println("\nCurrent Inventory:");
        displayInventory();
    
        for (Inventory itemInventory : inventory) {
            if (itemInventory.getQuantity() == MAX_INT) {
                System.out.println("Maximum inventory of " + MAX_INT + " reached for " + itemInventory.getItem().getName() + ". Restocking not allowed.");
                continue;
            }
    
            System.out.print("\nEnter the quantity to restock for " + itemInventory.getItem().getName() + " (10-15 pieces only): ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
    
            while (quantity < 10 || quantity > 15) {
                System.out.println("Invalid input! Please enter a quantity between 10 and 15.");
                System.out.print("\nEnter the quantity to restock for " + itemInventory.getItem().getName() + ": ");
                quantity = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
            }
    
            // Limit the quantity to 15 if the user input exceeds 15
            if (quantity + itemInventory.getQuantity() > MAX_INT) {
                System.out.println("Quantity exceeded the maximum limit. Setting quantity to " + (MAX_INT - itemInventory.getQuantity()));
                quantity = MAX_INT - itemInventory.getQuantity();
            }
    
            itemInventory.addItem(quantity);
        }
    
        System.out.println("\nItems restocked successfully.");
    }
    

    /**
     * Sets the price for an item in the vending machine.
     * 
     * Displays the current inventory and prompts the user to select a slot number
     * to set the price for the corresponding item. If the slot number is valid,
     * it prompts the user to enter the new price for the item and updates the price accordingly.
     * @param scanner the Scanner object used for user input.
     */
    private void setItemPrice(Scanner scanner) {
        System.out.println("\nCurrent Inventory:");
        displayInventory();
    
        System.out.print("\nEnter slot number of the item to set the price: ");
        int slotNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
    
        if (slotNumber >= 1 && slotNumber <= inventory.size()) {
            int selectedIndex = (int) slotNumber - 1;
            Inventory selectedInventory = inventory.get(selectedIndex);
            Item item = selectedInventory.getItem();
    
            System.out.print("Enter the new price for " + item.getName() + ": ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            while(newPrice == 0 || newPrice > 100)
            {
                System.out.println("Please try again");
                System.out.print("Enter the new price for " + item.getName() + ": ");
                newPrice = scanner.nextDouble();
                scanner.nextLine(); 
            }
    
            item.setPrice(newPrice);
            System.out.println("\nPrice for " + item.getName() + " updated successfully.");
        } else {
            System.out.println("Invalid slot number.");
        }
    }
    
    /**
     * Collects the payment for the total sales and resets the total sales amount to zero.
     * This method is called when the vending machine owner collects the earnings.
     */
    private void collectPayment() {
        System.out.println("\nPayment collected successfully: " + totalSales);
        //totalSales = 0.0;
    }

    /**
     * Refills the change inventory by adding the specified quantity of each denomination.
     * This method is used to replenish the available change in the vending machine.
     * @param scanner the Scanner object used for user input
     */
    private void makeChange(Scanner scanner) {
        for (Map.Entry<String, Double> entry : acceptedCash.entrySet()) {
            System.out.print("\nEnter the quantity to refill for Php" + entry.getKey() + ": ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            while (quantity <= 0 || quantity > 10) {
                System.out.println("Invalid input! Please enter a quantity between 1 and 10.");
                System.out.print("\nEnter the quantity to refill for Php" + entry.getKey() + ": ");
                quantity = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
            }
            entry.setValue(entry.getValue() + quantity);
        }
    
        System.out.println("\nChange refill successful.");
    }
    
    /**
     * Dispenses change by calculating the number of each denomination required
     * to fulfill the specified amount. The denominations are sorted in descending
     * order before dispensing.
     * @param amount the amount of change to be dispensed
     */
    private void dispenseChange(double amount) {
        List<Double> sortedDenominations = new ArrayList<>(acceptedCash.values());
        sortedDenominations.sort(Collections.reverseOrder());
    
        for (double denomination : sortedDenominations) {
            String denominationString = String.valueOf(denomination);
            Double quantity = acceptedCash.get(denominationString);
            if (quantity != null) {
                int numNotes = (int) (amount / denomination);
                if (numNotes > quantity) {
                    numNotes = (int) (double) quantity;
                }
                amount -= numNotes * denomination;
                System.out.println(numNotes + " x " + denominationString);
            }
        }
    }
    
    /**
     * Prints a summary of the transaction, including starting and ending inventory,
     * total sales, and the items sold with their respective quantities.
     */
    private void printTransactionSummary() {
        System.out.println("\nTransaction Summary:");
        System.out.println("Starting Inventory: " + startingInventory);
        System.out.println("Ending Inventory: " + calculateTotalInventory());
        System.out.println("Total Sales: Php " + totalSales);
        System.out.println("Items Sold:");
        for (Map.Entry<Item, Integer> entry : sales.entrySet()) {
            Item item = entry.getKey();
            int quantitySold = entry.getValue();
            System.out.println(item.getName() + ": " + quantitySold);
        }
    }

    /**
     * Displays the inventory of the vending machine.
     * Prints the slot number, item name, price, and quantity for each item in the inventory.
     * If an item is out of stock, it is indicated as "Out of stock".
     */
    private void displayInventory() {
        System.out.println("Slot\tItem Name\tPrice\t\tQuantity");
    
        for (int i = 0; i < inventory.size(); i++) {
            Inventory itemInventory = inventory.get(i);
            Item item = itemInventory.getItem();
            String itemName = item.getName();
            double itemPrice = item.getPrice();
            int quantity = itemInventory.getQuantity();
            String slotLabel = "[" + (i + 1) + "]";
            String quantityLabel = (quantity > 0) ? String.valueOf(quantity) : "Out of stock";
    
            System.out.printf("%-8s%-16sPhp%8.2f    %s%n", slotLabel, itemName, itemPrice, quantityLabel);
        }
    }
      
    /**
     * Checks if there is sufficient change available to provide for the specified amount.
     * @param amount the amount for which to check if there is sufficient change
     * @return true if there is sufficient change, false otherwise
     */
    private boolean hasSufficientChange(double amount) {
        double remainingChange = amount;
    
        List<String> sortedDenominations = new ArrayList<>(acceptedCash.keySet());
        sortedDenominations.sort(Collections.reverseOrder());
    
        for (String denomination : sortedDenominations) {
            double quantity = acceptedCash.get(denomination);
            int numNotes = (int) (remainingChange / Double.parseDouble(denomination));
            int notesToDispense = Math.min(numNotes, (int) quantity);
            remainingChange -= notesToDispense * Double.parseDouble(denomination);
    
            if (remainingChange == 0) {
                return true;
            }
        }
    
        return false;
    }

    /**
     * Calculates the total inventory quantity by summing the quantities of all items in the inventory.
     * @return the total inventory quantity
     */
    private int calculateTotalInventory() {
        int total = 0;
        for (Inventory itemInventory : inventory) {
            total += itemInventory.getQuantity();
        }
        return total;
    }

    /**
     * Tests the maintenance features of the vending machine.
     * Allows the user to perform various maintenance actions such as restocking items, setting item prices,
     * collecting payments, adding money for change, and printing transaction summaries.
     * The user can return to the main test menu by choosing the appropriate option.
     */
    public void testMaintenanceFeatures() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMaintenance Features Test:");
            System.out.println("[1] Restock Items");
            System.out.println("[2] Set Item Price");
            System.out.println("[3] Collect Payment");
            System.out.println("[4] Add Money For Change");
            System.out.println("[5] Print Transaction Summary");
            System.out.println("[6] Return to Test a Vending Machine Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

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

    /**
     * Tests the vending features of the vending machine.
     * Allows the user to simulate purchasing items and producing change.
     * The user can return to the main test menu by choosing the appropriate option.
     */
    public void testVendingFeatures() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nVending Features Test:");
            System.out.println("[1] Purchase Item");
            System.out.println("[2] Produce Change");
            System.out.println("[3] Return to Test a Vending Machine Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    purchaseItem(scanner);
                    break;
                case 2:
                    produceChange(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}