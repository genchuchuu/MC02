public class Item {
    private String name;
    private double price;
    private int cal;

    /**
     * Constructs an item with the specified name, price, and calories.
     * 
     * @param name  the name of the item
     * @param price the price of the item
     * @param cal   the calories of the item
     */
    public Item(String name, double price, int cal) {
        this.name = name;
        this.price = price;
        this.cal = cal;
    }

    /**
     * Sets the name of the item.
     * 
     * @param name the new name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price of the item.
     * @param price the new price of the item
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the calories of the item.
     * 
     * @param cal the new calories of the item
     */
    public void setCal(int cal) {
        this.cal = cal;
    }

    /**
     *
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the price of the item
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @return the calories of the item
     */
    public int getCal() {
        return cal;
    }
}