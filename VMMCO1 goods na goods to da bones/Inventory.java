public class Inventory {
    private Item item;
    private int quantity;

    /**
     * Constructs an inventory item with the specified item and quantity.
     * @param item     the item in the inventory
     * @param quantity the quantity of the item in the inventory
     */
    public Inventory(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    /**
     *
     * @return the item in the inventory
     */
    public Item getItem() {
        return item;
    }

    /**
     *
     * @return the quantity of the item in the inventory
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Adds the specified quantity of items to the inventory.
     * @param quantity the quantity of items to add to the inventory
     */
    public void addItem(int quantity) {
        this.quantity += quantity;
    }

    /**
     * Removes the specified quantity of items from the inventory.
     * @param quantity the quantity of items to remove from the inventory
     */
    public void removeItem(int quantity) {
        this.quantity -= quantity;
    }
}