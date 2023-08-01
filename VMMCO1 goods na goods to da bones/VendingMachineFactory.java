import java.util.*;

public class VendingMachineFactory {
    private static final int MAX_VM = 4;
    private static final int MAX_ITEMS = 8;

    /**
     * This class represents a vending machine factory application.
     * It allows users to create regular vending machines, test their features, and view information about the program.
     * 
     * @param args
     */
    public static void main(String[] args) {
        VendingMachineFactory vm = new VendingMachineFactory();
        List<Regular> vendingMachines = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int vendingMachineCount = 0;

        while (true) {
            System.out.println("\n--------------------------------------");
            System.out.println("\n\n  WELCOME TO THIRSTY DRINKY FACTORY\n\n");
            System.out.println("--------------------------------------");
            System.out.println("Menu:");
            System.out.println("[1] Create a Vending Machine");
            System.out.println("[2] About the Vending Machine");
            System.out.println("[3] Exit");
        
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\nSelect the Type of the Vending Machine: ");
                    if (vendingMachineCount < MAX_VM) {
                        System.out.println("  Type [1] to Create a Regular Vending Machine");
                        //System.out.println("  Type [2] to Create a Special Vending Machine");
                        System.out.print("Enter your choice: ");
                        int machineChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (machineChoice == 1) {
                            int quantity = 0;
                            while (true) {
                                System.out.print("\nEnter the quantity of Regular Vending Machines to create (1-4): ");
                                quantity = scanner.nextInt();
                                scanner.nextLine(); 
                                if (quantity >= 1 && quantity <= MAX_VM) {
                                    break;
                                }
                                System.out.println("Invalid quantity. Please try again.");
                            }
                            int i;
                            for (i = 0; i < quantity; i++) {
                                Regular vendingMachine = vm.createRegularVendingMachine(scanner);
                                vendingMachines.add(vendingMachine);
                                vendingMachineCount++; 
                                int choose = 0;
                                int yes = 0;
                            
                                while(choose != 3){ 
                                    System.out.println("\nChoose to test Vending Machine " + (i+1) + ": (Choose [1] first to set-up vending machine)");
                                    System.out.println("[1] Test Maintenance");
                                    System.out.println("[2] Test Actual Vending Machine");
                                    System.out.println("[3] Exit");
                                    System.out.print("Enter your choice: ");
                                    choose = scanner.nextInt();
                                    scanner.nextLine();
                            
                                    if(choose ==1) {
                                        vendingMachine.testMaintenanceFeatures();
                                        yes =1;
                                    }
                                    else if(choose==2 && yes == 1)
                                        vendingMachine.testVendingFeatures();
                                    else if(choose ==3)
                                        break;
                                    else{
                                        System.out.println("Invalid Input! Please try again");
                                        System.out.println("[1]Test Maintenance");
                                        System.out.println("[2]Test Actual Vending Machine");
                                        System.out.println("[3]Exit");
                                        System.out.print("Enter your choice: ");
                                        choose = scanner.nextInt();
                                        scanner.nextLine(); 
                                    }
                                }
                            }
                            
                            System.out.println("\n" + quantity + " Regular Vending Machine(s) created.");
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                        }
                    } else {
                        System.out.println("Maximum number of vending machines reached.");
                    }
                    break;
                case 2:
                    System.out.println("\nAbout the Program\n\n");

                    System.out.println("Hello! We are ThirstyDrinky! We are a company who offers vending machine");
                    System.out.println("\nfor beverages. Currently we have a regular vending machine consisting of");
                    System.out.println("\nsimple drinks such as water, juices and milks. We do also offer a special");
                    System.out.println("\nvending machine that is mainly for coffee wherein your customers can");
                    System.out.println("\ncustomize their own drinks. We do hope that we will be able to satisfy");
                    System.out.println("\nyour needs in creating a vending machine. Thank you!\n");
                    System.out.print("\n\nType [1] to go back to the Main Page: ");
                    int back = scanner.nextInt();
                    scanner.nextLine(); 
                    if(back == 1){
                        break;
                    }

                    else{
                        while(back != 1) {
                        System.out.println("Invalid Input! Please try again");
                        System.out.print("\n\nType [1] to go back to the Main Page: ");
                        back = scanner.nextInt();   
                        } 
                    }
                    break;
                case 3:
                    System.out.println("\nThank you for using Thirsty Drinky!");
                    System.out.println("Exiting the program...\n");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Creates a regular vending machine through selecting of items and setting up the accepted cash.
     * 
     * @param scanner the Scanner object used to read user input.
     * @return a Regular vending machine with the selected products and accepted cash.
     */
    public Regular createRegularVendingMachine(Scanner scanner) {

        List<Inventory> inventory = new ArrayList<>();
        Set<String> chosenItems = new HashSet<>(); 

        System.out.println("\nSelect 8 products to add to the vending machine:");
        System.out.println("ID\tProduct\t\tPrice\t\tCalories(250ml)");
        System.out.println("[1]\tWater\t\t20\t\t0");
        System.out.println("[2]\tIced tea\t30\t\t95");
        System.out.println("[3]\tApple juice\t35\t\t118");
        System.out.println("[4]\tOrange juice\t35\t\t118");
        System.out.println("[5]\tGrape juice\t35\t\t161");
        System.out.println("[6]\tCoconut juice\t35\t\t48");
        System.out.println("[7]\tLemon juice\t40\t\t59");
        System.out.println("[8]\tCow milk\t45\t\t109");
        System.out.println("[9]\tCoconut Milk\t45\t\t575");
        System.out.println("[10]\tMatcha\t\t60\t\t68");
        System.out.println("[11]\tSoy Milk\t60\t\t135");
        System.out.println("[12]\tAlmond Milk\t75\t\t43\n");

        for (int i = 0; i < MAX_ITEMS; i++) {
            int productChoice;
            boolean validChoice = false;

            while (!validChoice) {
                System.out.print("Enter the ID for product of slot " + (i + 1) + ": ");
                productChoice = scanner.nextInt();
                scanner.nextLine();

                String product = getProductByChoice(productChoice);
                if (product == null) {
                    System.out.println("Invalid choice. Please try again.");
                } else if (chosenItems.contains(product)) {
                    System.out.println("You have already chosen that item. Please choose a different one.");
                } else {
                    chosenItems.add(product);
                    inventory.add(new Inventory(new Item(product, getPriceByChoice(productChoice), getCaloriesByChoice(productChoice)), 0));
                    validChoice = true;
                }
            }
        }

        Map<String, Double> acceptedCash = new HashMap<>();
        acceptedCash.put("0.10", 0.5);
        acceptedCash.put("0.25", 0.10);
        acceptedCash.put("0.50", 0.75);
        acceptedCash.put("0.75", 0.25);
        acceptedCash.put("5", 5.0);
        acceptedCash.put("10", 10.0);
        acceptedCash.put("20", 20.0);
        acceptedCash.put("50", 50.0);
        acceptedCash.put("100", 100.0);
        acceptedCash.put("500", 500.0);

        return new Regular(inventory, acceptedCash);
    }

    /**
     * Returns the product name based on the given choice.
     * 
     * @param choice the ID representing the selected product.
     * @return the product name associated with the choice, or null if the choice is invalid.
     */
    private String getProductByChoice(int choice) {
        switch (choice) {
            case 1:
                return "Water";
            case 2:
                return "Iced tea";
            case 3:
                return "Apple juice";
            case 4:
                return "Orange juice";
            case 5:
                return "Grape juice";
            case 6:
                return "Coconut juice";
            case 7:
                return "Lemon juice";
            case 8:
                return "Cow milk";
            case 9:
                return "Coconut Milk";
            case 10:
                return "Matcha";
            case 11:
                return "Soy Milk";
            case 12:
                return "Almond Milk";
            default:
                return null;
        }
    }

    /**
     * Returns the price based on the given choice.
     * 
     * @param choice the ID representing the selected product.
     * @return the price associated with the choice, or 0.0 if the choice is invalid.
     */
    private double getPriceByChoice(int choice) {
        switch (choice) {
            case 1:
                return 20.50;
            case 2:
                return 30.0;
            case 3:
            case 4:
            case 5:
            case 6:
                return 35.0;
            case 7:
                return 40.0;
            case 8:
            case 9:
                return 45.0;
            case 10:
                return 60.0;
            case 11:
                return 60.10;
            case 12:
                return 75.25;
            default:
                return 0.0;
        }
    }

    /**
     * Returns the calories based on the given choice.
     * 
     * @param choice the ID representing the selected product.
     * @return the calories associated with the choice, or 0 if the choice is invalid.
     */
    private int getCaloriesByChoice(int choice) {
        switch (choice) {
            case 1:
                return 0;
            case 2:
                return 95;
            case 3:
            case 4:
                return 118;
            case 5:
                return 161;
            case 6:
                return 48;
            case 7:
                return 59;
            case 8:
                return 109;
            case 9:
                return 575;
            case 10:
                return 68;
            case 11:
                return 135;
            case 12:
                return 43;
            default:
                return 0;
        }
    }
}
