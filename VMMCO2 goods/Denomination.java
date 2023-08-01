class Denomination {
    private double value;
    private int quantity;

    public Denomination(double value) {
        this.value = value;
        this.quantity = 0; // Initialize the quantity to 0 by default
    }

    public double getValue() {
        return value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Method to increment the quantity of this denomination
    public void incrementQuantity(int quantityToAdd) {
        if (quantityToAdd < 0) {
            throw new IllegalArgumentException("Quantity to add must be non-negative.");
        }
        this.quantity += quantityToAdd;
    }

    // Method to decrement the quantity of this denomination
    public void decrementQuantity(int quantityToSubtract) {
        if (quantityToSubtract < 0) {
            throw new IllegalArgumentException("Quantity to subtract must be non-negative.");
        }
        if (quantityToSubtract > this.quantity) {
            throw new IllegalArgumentException("Insufficient quantity to subtract.");
        }
        this.quantity -= quantityToSubtract;
    }
}
