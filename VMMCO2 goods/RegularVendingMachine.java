import java.util.*;

class RegularVendingMachine extends VendingMachine {
    protected List<Inventory> in;

    public RegularVendingMachine(List<Inventory> inventory, Map<Double, Denomination> acceptedCash) {
        super( acceptedCash);
        this.in = new ArrayList<>(inventory);
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


    @Override
    public void testVendingFeatures() {
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

    public Map<Double, Denomination> getAcceptedCash() {
        return acceptedCash;
    }
    
    protected String produceChange(double change) {
        StringBuilder changeOutput = new StringBuilder();

        changeOutput.append("Dispensing change with the following denominations...\n");
        
        // Sort the denominations in descending order
        List<Double> denominations = new ArrayList<>(acceptedCash.keySet());
        Collections.sort(denominations, Collections.reverseOrder());

        for (double denomination : denominations) {
            int quantityAvailable = acceptedCash.get(denomination).getQuantity();

            if (change >= denomination && quantityAvailable > 0) {
                int numOfBills = (int) (change / denomination);
                int numOfBillsToDispense = Math.min(numOfBills, quantityAvailable);
                change -= numOfBillsToDispense * denomination;

                // Adjust the cash balance by reducing the quantity of the denomination used
                acceptedCash.get(denomination).setQuantity(quantityAvailable - numOfBillsToDispense);

                changeOutput.append("Php ").append(denomination).append(" x ").append(numOfBillsToDispense).append("\n");
            }
        }
        return changeOutput.toString();
    }

    public double getTotalSales() {
        return totalSales;
    }

    // Update the addToSales method to increment the totalSales variable
    protected void addToSales(double amount) {
        super.addToSales(amount);
        totalSales += amount;
    }

    protected void restockItems(Scanner scanner) {
        System.out.println("\nCurrent Inventory:");
        displayInventory();

        for (Inventory itemInventory : in) {
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

    protected void displayInventory() {
        System.out.println("Slot\tItem Name\tPrice\t\tQuantity");
        for (int i = 0; i < in.size(); i++) {
            Inventory itemInventory = in.get(i);
            Item item = itemInventory.getItem();
            String itemName = item.getName();
            double itemPrice = item.getPrice();
            int quantity = itemInventory.getQuantity();
            String slotLabel = "[" + (i + 1) + "]";
            String quantityLabel = (quantity > 0) ? String.valueOf(quantity) : "Out of stock";
            System.out.printf("%-8s%-16sPhp%8.2f    %s%n", slotLabel, itemName, itemPrice, quantityLabel);
        }
    }

    @Override
    protected void collectPayment() {
        System.out.println("Collecting payment...");
        System.out.println("Payment collected successfully.");
    }

    public void makeChange(Scanner scanner) {
        for (Map.Entry<Double, Denomination> entry : acceptedCash.entrySet()) {
            System.out.print("Enter the quantity to refill for Php" + entry.getKey() + ": ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            entry.getValue().setQuantity(entry.getValue().getQuantity() + quantity);
        }
    
        System.out.println("Change refill successful.");
    }
    
    protected void dispenseItem(int slotNumber) {
        Inventory item = in.get(slotNumber - 1);
        System.out.println("Dispensed item: " + item.getItem().getName());
    }
    

    protected void calculateTotalInventory() {
        int totalItems = 0;
        for (Inventory item : in) {
            totalItems += item.getQuantity();
        }
        System.out.println("Total inventory: " + totalItems);
    }

    public List<Inventory> getInventory() {
        return in;
    }

    protected void setItemPrice(Scanner scanner) {
        System.out.println("\nCurrent Inventory:");
        displayInventory();

        System.out.print("Enter the slot number to set the price: ");
        int slotNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (slotNumber >= 1 && slotNumber <= in.size()) {
            int selectedIndex = slotNumber - 1;
            Inventory selectedInventory = in.get(selectedIndex);
            Item item = selectedInventory.getItem();

            System.out.print("Enter the new price for " + item.getName() + ": ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            item.setPrice(newPrice);
            System.out.println("Price for " + item.getName() + " updated successfully.");
        } else {
            System.out.println("Invalid slot number.");
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
    
        System.out.println("\nAvailable Items:");
        displayInventory();
    
        System.out.print("Enter the slot number to purchase the item: ");
        int slotNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
    
        if (slotNumber >= 1 && slotNumber <= in.size()) {
            Inventory selectedInventory = in.get(slotNumber - 1);
            Item item = selectedInventory.getItem();
    
            if (selectedInventory.getQuantity() > 0) {
                System.out.println("Selected item: " + item.getName());
                System.out.println("Price: " + item.getPrice());
                System.out.println("Calories: " + item.getCalories());
    
                if (amountPaid >= item.getPrice()) {
                    double change = amountPaid - item.getPrice();
                    if (hasSufficientChange(change)) {
                        selectedInventory.removeItem(1);
                        addToSales(item.getPrice()); 
                        totalSales += item.getPrice();
                        System.out.println("Item purchased successfully.");
                        System.out.println("Change: " + change);
                        System.out.println("Dispensing Item: " + item.getName());
                        System.out.println("Dispensing change with the following denominations...");
                        produceChange(change); // <-- Changed this line
                    } else {
                        System.out.println("Insufficient change. Please try again later.");
                    }
                } else {
                    System.out.println("Insufficient amount paid. Transaction canceled.");
                }
            } else {
                System.out.println("Selected item is out of stock.");
            }
        } else {
            System.out.println("Invalid slot number.");
        }
    }
    
}