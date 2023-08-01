public class Denomination {
    private int value;

    /**
     * Constructs a Denomination object with the specified value.
     * @param value the numerical value of the denomination
     */
    public Denomination(int value) {
        this.value = value;
    }

    /**
     * 
     * @return the value of the denomination
     */
    public int getValue() {
        return value;
    }

    /**
     * 
     * @return a string representation of the denomination
     */
    public String toString() {
        return String.valueOf(value);
    }
}