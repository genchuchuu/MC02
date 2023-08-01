import java.util.List;

public class Transaction {
    private List<Item> items;
    private double amountPaid;

    public Transaction(List<Item> items, double amountPaid) {
        this.items = items;
        this.amountPaid = amountPaid;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getQuantity() {
        return items.size();
    }

    public double getAmountPaid() {
        return amountPaid;
    }
}
