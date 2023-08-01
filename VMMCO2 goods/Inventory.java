public class Inventory {
    private Item item;
    private int quantity;

    public Inventory(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Add a method to decrement the quantity by 1
    public void decrementQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }

    public void addQuantity(int quantityToAdd) {
        this.quantity += quantityToAdd;
        if (this.quantity < 0) {
            this.quantity = 0; // Prevent negative quantity
        }
    }

    // Add a method to increment the quantity by a specified amount
    public void addItem(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be non-negative.");
        }
        this.quantity += quantity;
    }

    // Add a method to decrement the quantity by a specified amount
    public void removeItem(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be non-negative.");
        }
        this.quantity -= quantity;
    }
}
