import java.util.Scanner;

public class POS {
    static Scanner sc = new Scanner(System.in);

    static String[] products = {
            "Taco shirt",
            "Grizzly",
            "Just Do It",
            "Supreme",
            "ILY"};

    static double[] productPrice = {
            500.00,
            900.00,
            950.00,
            999.00,
            650.00};
    static int total,
            quantity;

    static int choiceInt;

    static int[] itemSlot = new int[10], //Purchase limit: 9.
            quantitySlot = new int[10];

    static int countProducts = 0,
            nextIndex = 0;

    public static void main(String[] args) {
        // ============================LOGIN=====================================
        boolean access;
        do {
            access = false;
            System.out.println("\t\t\t\t" + "  __   __");
            System.out.println("\t\t\t\t" + " /  `-'  \\" + "   Just");
            System.out.println("\t\t\t\t" + "/_│     │_\\" + "  Clothes");
            System.out.println("\t\t\t\t" + "  │     │ ");
            System.out.println("\t\t\t\t" + "  └─────┘");
            System.out.println("\t\t\t╦═══════════════════════════╗");
            System.out.println("\t\t\t║ Just Clothes Inc. | Login ║ ");
            System.out.println("\t\t\t╚═══════════════════════════╝");
            System.out.print("\t\t\t\tUsername ► ");
            String username = sc.nextLine().toLowerCase();
            System.out.print("\t\t\t\tPassword ► ");
            String password = sc.nextLine();

            if (username.equals("1") && password.equals("1")) {
                access = true;
            } else {
                clearConsole();
                System.out.println("\t\t\t- Invalid Username or Password -");
            }

        } while (!access);
        // ======================================================================

        clearConsole();
        while (true) {
            showMenu();
            System.out.print("Enter your choice ► ");
            String choice = sc.nextLine().toLowerCase();

            switch (choice) { // Products
                case "1", "2", "3", "4", "5" -> {
                    choiceInt = Integer.parseInt(choice);

                    if (countProducts == 9) {
                        clearConsole();
                        System.out.println("\t\t\t\t\t ▒ Purchase limit reached ▒\n");
                        continue;
                    }

                    countProducts++;
                    askQuantity();
                    clearConsole();
                    add(choiceInt - 1, "itemArray");
                    add(quantity, "quantityArray");
                    nextIndex++;
                    continue;
                }

                case "r" -> { //[R] - Remove an item
                    clearConsole();
                    if (countProducts == 0) {
                        System.out.println("\t\t\t\t\t  ▒ No items in the cart ▒\n");
                    } else {
                        remove();
                        clearConsole();
                    }
                    continue;
                }

                case "s" -> { //[S] - Show Items
                    clearConsole();
                    if (countProducts == 0) {
                        System.out.println("\t\t\t\t\t  ▒ No items in the cart ▒\n");
                    } else {
                        showItems();
                        System.out.println("\n◄ Back [0]\n");

                        int goBack;
                        while (true) {
                            System.out.print(": ");
                            try {
                                goBack = Integer.parseInt(sc.nextLine());
                                if (goBack != 0) {
                                    System.out.println("Type '0' to go back\n");
                                } else {
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println("Type '0' to go back\n");
                            }
                        }
                    }
                    continue;
                }

                case "p" -> { //[P] - Print Receipt
                    if (countProducts == 0) {
                        clearConsole();
                        System.out.println("\t\t\t\t\t\t▒ Nothing to print ▒\n");
                        continue;
                    } else {
                        printReceipt();
                    }
                }

                default -> {
                    clearConsole();
                    System.out.println("\t\t\t\t\t\t ▒ Invalid Choice ▒\n");
                    continue;
                }
            }
            break;
        }
    }

    static void showMenu() {
        String format = "%-28s%s%,.2f%n";

        System.out.println("\t\t\t════╦═════════════════════════════════════╗");
        int a = 1, i = 0;
        while (a <= products.length) {
            System.out.printf("\t\t\t" + "║ " + a + " ║ " + format, products[i], "₱ ", productPrice[i]);
            a++;
            i++;
        }
        System.out.println("\t\t\t╚═════════════════════════════════════════╝");
        System.out.println("\n[R] - Remove an item\t [S] - Show Items\t [P] - Print Receipt");
    }

    static void add(int index, String nameOfArray) {
        if (nameOfArray.equals("quantityArray"))
            quantitySlot[nextIndex] = index;
        else if (nameOfArray.equals("itemArray"))
            itemSlot[nextIndex] = index;
    }

    static void askQuantity() {
        int numOfItems;

        while (true) {
            System.out.print("How many? ► ");
            try {
                numOfItems = Integer.parseInt(sc.nextLine());
                if (numOfItems <= 0 || numOfItems > 30) {
                    System.out.println("Invalid Quantity");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("That's not a number!");
            }
        }
        quantity = numOfItems;
    }

    //Ask user which item to remove
    static void remove() {
        showItems();
        System.out.println("\nEnter the number of the item you want to remove");
        while (true) {
            try {
                int index = Integer.parseInt(sc.nextLine());
                if (index <= countProducts) {
                    for (int i = index - 1; i < countProducts; i++) {
                        itemSlot[i] = itemSlot[i + 1];
                        quantitySlot[i] = quantitySlot[i + 1];
                    }
                    countProducts--;
                    nextIndex--;
                    break;
                } else {
                    System.out.println("Invalid number");
                }
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        }
    }

    static void showItems() {
        String format = ". %-22sx%s%3s%n";

        clearConsole();

        System.out.println("\t\t\t╔═══════════════════════════════╗");

        int a = 1;
        for (int i = 0; i < nextIndex; i++) {
            if (quantitySlot[i] < 10) {
                System.out.printf("\t\t\t" + "║ " + a + ". %-22sx%s%4s%n", products[itemSlot[i]], quantitySlot[i], "║");
            } else {
                System.out.printf("\t\t\t" + "║ " + a + format, products[itemSlot[i]], quantitySlot[i], "║");
            }
            a++;
        }

        System.out.println("\t\t\t╚═══════════════════════════════╝");
    }

    static void printReceipt() {
        //Generates a 9 digit random number
        int refNum = (int) ((Math.random() * 999999999) + 100000000);

        //Calculates total price
        for (int i = 0; i < itemSlot.length; i++)
            total += productPrice[itemSlot[i]] * quantitySlot[i];

        String format = "%-30sx%s%n", format2 = "%-28s%s%,d%n";

        clearConsole();
        System.out.println("\t\t\t\t\t" + "██████████████ Receipt ██████████████");
        System.out.println("\n\t\t\t\t\t\t" + "Welcome to Just Clothes Corp.");
        System.out.println("\t\t\t\t\t\t\t" + "  __   __");
        System.out.println("\t\t\t\t\t\t\t" + " /  `-'  \\" + "   Just");
        System.out.println("\t\t\t\t\t\t\t" + "/_│     │_\\" + "  Clothes");
        System.out.println("\t\t\t\t\t\t\t" + "  │     │ ");
        System.out.println("\t\t\t\t\t\t\t" + "  └─────┘");
        System.out.println("\t\t\t\t\t" + "█████████████████████████████████████");

        System.out.println("\t\t\t\t\t\t" + "Items" + "\t\t\t\t" + "  Quantities");

        System.out.println("\t\t\t\t\t" + "─────────────────────────────────────");
        for (int i = 0; i < countProducts; i++)
            System.out.printf("\t\t\t\t\t" + format, products[itemSlot[i]], quantitySlot[i]);

        System.out.println("\t\t\t\t\t" + "─────────────────────────────────────");
        System.out.printf("\t\t\t\t\t" + format2, "TOTAL :", "₱ ", total);
        System.out.print("\t\t\t\t\t" + "Payment used : Jeff Bezos' Wallet");
        System.out.print("\n\t\t\t\t\t" + "Ref # : " + refNum);
        System.out.println("\n\t\t\t\t\t" + "─────────────────────────────────────");
        System.out.println("\n\t\t\t\t\t\t" + "Thank you for shopping with us");
        System.out.println("\t\t\t\t\t\t" + "   We hope to see you again!\n");
        System.out.println("\t\t\t\t\t" + "███████████ End of Receipt ███████████");

        System.out.println("\n\t\t\t\t   Made with ❤ by Kumar, Opsima & Pantojan");
    }

    static void clearConsole() {
        int k = 0;
        while (k < 10) {
            System.out.println("\n");
            k++;
        }
    }
}
