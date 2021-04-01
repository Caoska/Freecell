import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    private static boolean autoFreeMovement = true;
    private static Layout layout = new Layout();
    private static Movement movement = new Movement(layout);

    private static List<Card> deck = new ArrayList();

    private static void printDeck() {
        for (Card card : deck) {
            card.printCard();
        }
    }

    private static void setupAndShuffleDeck() {
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 13; j++) {
                switch (i) {
                    case 0:
                        deck.add(new Card("Hearts", j));
                        break;
                    case 1:
                        deck.add(new Card("Spades", j));
                        break;
                    case 2:
                        deck.add(new Card("Diamonds", j));
                        break;
                    case 3:
                        deck.add(new Card("Clubs", j));
                        break;
                }
            }
        }
        Collections.shuffle(deck);
    }

    public static void main(String[] args) {
        String userInput;

        setupAndShuffleDeck();
        layout.setup(deck);

        System.out.println("Enter t to toggle autoFreeMovement, otherwise, just hit enter");
        Scanner myObj = new Scanner(System.in);
        userInput = myObj.nextLine();
        if (userInput.equalsIgnoreCase("t")) {
            autoFreeMovement = !autoFreeMovement;
        }

        while (autoFreeMovement && movement.checkForFreeMovement()) {
            layout.printColumns();
        }

        layout.printColumns();
        printOptions();
        Scanner myObj2 = new Scanner(System.in);
        userInput = myObj2.nextLine();
        while (!userInput.equalsIgnoreCase("123")) {
            if (userInput.equalsIgnoreCase("cell") ||
                userInput.equalsIgnoreCase("9")) {
                fromCellMove();
            } else {
                try {
                    int cardToBeMoved = Integer.parseInt(userInput) - 1;
                    List<Card> stack = movement.checkDepth(cardToBeMoved);
                    if (stack.size() > 1) {
                        multiCardMove(cardToBeMoved, stack);
                    } else {
                        singleCardMove(cardToBeMoved);
                    }
                } catch (NumberFormatException e) {
                    if (!userInput.equalsIgnoreCase("t") &&
                        !userInput.equalsIgnoreCase("cell")) {
                        System.out.println(userInput + " is not a number, try again, yo");
                    }
                }
            }
            userInput = menuAndUserInput();
            if (userInput.equalsIgnoreCase("t")) {
                autoFreeMovement = !autoFreeMovement;
            }
        }
    }

    private static void fromCellMove() {
        String userInput;
        if (layout.getCell1().getValue() == 0 &&
            layout.getCell2().getValue() == 0 &&
            layout.getCell3().getValue() == 0 &&
            layout.getCell4().getValue() == 0) {
            System.out.println("Cell empty!");
        } else {
            layout.printCells();
            System.out.println("Select which cell to pull from (123 to exit)");
            Scanner cellNumber = new Scanner(System.in);
            userInput = cellNumber.nextLine();
            try {
                int cellNum = Integer.parseInt(userInput);
                if (!userInput.equalsIgnoreCase("123")) {
                    if (layout.getCell1().getValue() != 0) {
                        System.out.println("Select which column to put into (123 to exit)");
                        Scanner columnNumber = new Scanner(System.in);
                        userInput = columnNumber.nextLine();
                        try {
                            int colNum = Integer.parseInt(userInput) - 1;
                            movement.moveCellToColumn(cellNum, colNum);
                        } catch (NumberFormatException e) {
                            System.out.println(userInput + " is not a number, try again, yo");
                        }
                    } else {
                        System.out.println("Cell empty!");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println(userInput + " is not a number, try again, yo");
            }
        }
    }

    private static void singleCardMove(int cardToBeMoved) {
        String userInput;
        System.out.println("Cool. Either enter the column you would like to move it to");
        System.out.println("  Or enter 'cell' to move it to the cell");
        Scanner myObj3 = new Scanner(System.in);
        userInput = myObj3.nextLine();
        if (userInput.equalsIgnoreCase("cell") ||
            userInput.equalsIgnoreCase("9")) {
            if (!movement.moveCardToCell(cardToBeMoved)) {
                System.out.println("Cell full!");
            }
        } else {
            try {
                int column = Integer.parseInt(userInput) - 1;
                if (movement.checkValidMovement(layout.getBottomCard(cardToBeMoved), column)) {
                    layout.addToColumn(layout.getBottomCard(cardToBeMoved), column);
                    layout.removeBottomCard(cardToBeMoved);
                } else {
                    System.out.println("Invalid Movement!");
                }
            } catch (NumberFormatException e) {
                System.out.println(userInput + " is not a number, try again, yo");
            }
        }
    }

    private static void multiCardMove(int cardToBeMoved, List<Card> stack) {
        String userInput;
        layout.printStack(stack);
        System.out.println("Select position in stack want moved: ");
        Scanner stackSize = new Scanner(System.in);
        userInput = stackSize.nextLine();
        try {
            int stackSizeInt = Integer.parseInt(userInput);
            if (stackSizeInt < stack.size()) {
                if (stackSizeInt == 1) {
                    singleCardMove(cardToBeMoved);
                } else {
                    for (int i = stackSizeInt; i < stack.size(); i++) {
                        stack.remove(stack.size() - 1);
                    }
                }
            } else if (stackSizeInt > stack.size()) {
                System.out.println("Needs to be smaller than the actual size, dumb");
            }

            if (stackSizeInt == stack.size()) {
                System.out.println("Select column stack wanted moved to: ");
                Scanner stackColumnDestination = new Scanner(System.in);
                userInput = stackColumnDestination.nextLine();
                try {
                    int column = Integer.parseInt(userInput) - 1;
                    movement.checkAndMoveStack(stack, cardToBeMoved, column);
                } catch (NumberFormatException e) {
                    System.out.println(userInput + " is not a number, try again, yo");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println(userInput + " is not a number, try again, yo");
        }
    }

    private static void printOptions() {
        System.out.println("Options:");
        System.out.println("  Enter the column you'd like to move");
        System.out.println("  Enter 'cell' to move from the cell");
        System.out.println("  Enter 123 to exit");
    }

    private static String menuAndUserInput() {
        while (autoFreeMovement && movement.checkForFreeMovement()) {
            layout.printColumns();
        }
        layout.printColumns();
        printOptions();
        Scanner myObj4 = new Scanner(System.in);
        return myObj4.nextLine();
    }
}
