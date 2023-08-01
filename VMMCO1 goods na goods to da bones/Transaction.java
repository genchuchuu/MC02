import java.util.*;
public class Transaction {
    private List<Item> items;
    private double amountPaid;

    /**
     * Constructs a transaction with the specified items and amount paid.
     * @param items the list of items in the transaction
     * @param amountPaid the amount paid for the transaction
     */
    public Transaction(List<Item> items, double amountPaid) {
        this.items = items;
        this.amountPaid = amountPaid;
    }

    /**
     * 
     * @return the list of items in the transaction
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * 
     * @return the quantity of items in the transaction
     */
    public int getQuantity() {
        return items.size();
    }

    /**
     * 
     * @return the amount paid for the transaction
     */
    public double getAmountPaid() {
        return amountPaid;
    }
}
