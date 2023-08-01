public class CustomDrink extends Item {
    private String drinkName;
    private String cupSize;
    private String temperature;
    private String sugarLevel;
    private String milkChoice;

    private int numEspressoShots; // Add the property for the number of espresso shots

    public CustomDrink(String drinkName) {
        super(drinkName, 0, 0); // Setting the initial price and calories to 0, as they will be calculated based on customizations
        this.drinkName = drinkName;
    }

    // Getters and setters for the customization options

    public String getDrinkName() {
        return drinkName;
    }

    public String getCupSize() {
        return cupSize;
    }

    public void setCupSize(String cupSize) {
        this.cupSize = cupSize;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(String sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public String getMilkChoice() {
        return milkChoice;
    }

    public void setMilkChoice(String milkChoice) {
        this.milkChoice = milkChoice;
    }

    public int getNumEspressoShots() {
        return numEspressoShots;
    }

    public void setNumEspressoShots(int numEspressoShots) {
        this.numEspressoShots = numEspressoShots;
    }
}
