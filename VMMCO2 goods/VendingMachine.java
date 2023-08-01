import java.util.*;

abstract class VendingMachine {
    
    protected Map<Double, Denomination> acceptedCash;
    protected List<Item> sales;
    protected double totalSales;

    public VendingMachine(Map<Double, Denomination> acceptedCash) {
        this.acceptedCash = acceptedCash;
        sales = new ArrayList<>();
        totalSales = 0.0;
    }

    public abstract void testMaintenanceFeatures();


    public abstract void testVendingFeatures();
    

    protected void testMaintenanceFeatures(Scanner scanner, VendingMachine vendingMachine) {
        System.out.println("\nMaintenance Testing Mode");
    
        if (vendingMachine instanceof SpecialVendingMachine) {
            System.out.println("Current Add-On Item Quantities:");
            SpecialVendingMachine specialMachine = (SpecialVendingMachine) vendingMachine;
    
            // Define the restockItems map here
            Map<String, Integer> restockItems = new HashMap<>();
    
            for (Inventory inventoryItem : specialMachine.getInventory()) {
                System.out.print("Enter the quantity to restock for " + inventoryItem.getItem().getName() + ": ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                restockItems.put(inventoryItem.getItem().getName(), quantity);
            }
        }
    
        System.out.println("\nMaintenance Testing Complete.");
    }

    
    protected double acceptPayment(Scanner scanner, double requiredAmount) {
        System.out.print("Enter the amount to pay (or 0 to cancel): ");
        double amountPaid = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        if (amountPaid < requiredAmount) {
            System.out.println("Insufficient payment. Please pay the correct amount.");
            return 0.0;
        } else {
            double change = amountPaid - requiredAmount;
            System.out.println("Payment successful. Change: " + change);
            return change;
        }
    }

    protected void addToSales(double itemPrice) {
        totalSales += itemPrice;
    }
    

    protected void dispenseChange(Map<Double, Integer> changeToDispense) {
        System.out.println("Dispensing change with the following denominations...");
        for (Map.Entry<Double, Integer> entry : changeToDispense.entrySet()) {
            double denomination = entry.getKey();
            int numNotes = entry.getValue();
            System.out.println(numNotes + " x Php " + denomination);
            acceptedCash.get(denomination).decrementQuantity(numNotes);
        }
    }

    protected abstract void collectPayment();

    public void printTransactionSummary() {
        System.out.println("Transaction Summary:");
        for (Item item : sales) {
            System.out.println("Item: " + item.getName() + " - Price: " + item.getPrice());
        }
        System.out.println("Total Sales: " + totalSales);
    }

    
    protected void displayAcceptedCash() {
        System.out.println("Accepted Denominations:");
        for (Map.Entry<Double, Denomination> entry : acceptedCash.entrySet()) {
            double denomination = entry.getKey();
            Denomination denominationObj = entry.getValue();
            int quantity = denominationObj.getQuantity();
            System.out.println("Php " + denomination + ": " + quantity);
        }
    }


    public boolean hasSufficientChange(double amount) {
        List<Double> availableDenominations = new ArrayList<>(acceptedCash.keySet());
        Collections.sort(availableDenominations, Collections.reverseOrder());
    
        double remainingAmount = amount;
        for (double denomination : availableDenominations) {
            int numNotes = (int) (remainingAmount / denomination);
            if (numNotes > 0) {
                Denomination denominationObj = acceptedCash.get(denomination);
                if (denominationObj.getQuantity() >= numNotes) {
                    remainingAmount -= (denomination * numNotes);
                } else {
                    // If there are not enough notes of this denomination, return false
                    return false;
                }
            }
        }
    
        // If the remaining amount is zero, it means we can form the change using available denominations
        return remainingAmount == 0.0;
    }

    public void addChange(Denomination changeDenomination) {
    }
}