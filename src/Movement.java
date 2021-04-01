import java.util.ArrayList;
import java.util.List;

public class Movement {
    private Layout layout;

    public Movement(Layout layout) {
        this.layout = layout;
    }

    public boolean checkForFreeMovement() {
        boolean flag = false;

        for(int i = 0; i < 8; i++) {
            Card card = layout.getBottomCard(i);
            if (card != null) {
                switch(card.getSuit()) {
                    case "Hearts":
                        if (card.getValue() == layout.getFreeHearts() + 1) {
                            layout.printColumns();
                            layout.removeBottomCard(i);
                            layout.printColumns();
                            layout.setFreeHearts(card.getValue());
                            layout.printColumns();
                            flag = true;
                        }
                        break;
                    case "Spades":
                        if (card.getValue() == layout.getFreeSpades() + 1) {
                            layout.printColumns();
                            layout.removeBottomCard(i);
                            layout.printColumns();
                            layout.setFreeSpades(card.getValue());
                            layout.printColumns();
                            flag = true;
                        }
                        break;
                    case "Diamonds":
                        if (card.getValue() == layout.getFreeDiamonds() + 1) {
                            layout.printColumns();
                            layout.removeBottomCard(i);
                            layout.printColumns();
                            layout.setFreeDiamonds(card.getValue());
                            layout.printColumns();
                            flag = true;
                        }
                        break;
                    case "Clubs":
                        if (card.getValue() == layout.getFreeClubs() + 1) {
                            layout.printColumns();
                            layout.removeBottomCard(i);
                            layout.printColumns();
                            layout.setFreeClubs(card.getValue());
                            layout.printColumns();
                            flag = true;
                        }
                        break;
                }
            }
        }

        return flag;
    }

    public boolean checkValidMovement(Card card, int column) {
        boolean validCheck = false;
        String suit = layout.getBottomCard(column).getSuit();
        int value = layout.getBottomCard(column).getValue();

        switch(card.getSuit()) {
            case "Hearts":
            case "Diamonds":
                if (card.getValue() == (value - 1) &&
                    (suit.equalsIgnoreCase("Clubs") || suit.equalsIgnoreCase("Spades")))
                    validCheck = true;
                break;
            case "Clubs":
            case "Spades":
                if (card.getValue() == (value - 1) &&
                    (suit.equalsIgnoreCase("Hearts") || suit.equalsIgnoreCase("Diamonds")))
                    validCheck = true;
                break;
        }

        return validCheck;
    }

    public boolean moveCardToCell(int column) {
        if (layout.getCell1().getValue() == 0) {
            layout.setCell1(layout.getBottomCard(column));
            layout.removeBottomCard(column);
            return true;
        }
        else if (layout.getCell2().getValue() == 0) {
            layout.setCell2(layout.getBottomCard(column));
            layout.removeBottomCard(column);
            return true;
        }
        else if (layout.getCell3().getValue() == 0) {
            layout.setCell3(layout.getBottomCard(column));
            layout.removeBottomCard(column);
            return true;
        }
        else if (layout.getCell4().getValue() == 0) {
            layout.setCell4(layout.getBottomCard(column));
            layout.removeBottomCard(column);
            return true;
        }
        return false;
    }

    public void moveCellToColumn(int cell, int column) {
        switch(cell) {
            case 1:
                if (checkValidMovement(layout.getCell1(), column)) {
                    layout.addToColumn(layout.getCell1(), column);
                    Card card = new Card("", 0);
                    layout.setCell1(card);
                }
                else {
                    System.out.println("Invalid movement!");
                }
            case 2:
                if (checkValidMovement(layout.getCell2(), column)) {
                    layout.addToColumn(layout.getCell2(), column);
                    Card card = new Card("", 0);
                    layout.setCell2(card);
                }
                else {
                    System.out.println("Invalid movement!");
                }
            case 3:
                if (checkValidMovement(layout.getCell3(), column)) {
                    layout.addToColumn(layout.getCell3(), column);
                    Card card = new Card("", 0);
                    layout.setCell3(card);
                }
                else {
                    System.out.println("Invalid movement!");
                }
            case 4:
                if (checkValidMovement(layout.getCell4(), column)) {
                    layout.addToColumn(layout.getCell4(), column);
                    Card card = new Card("", 0);
                    layout.setCell4(card);
                }
                else {
                    System.out.println("Invalid movement!");
                }
        }
    }

    public List<Card> checkDepth(int column) {
        Card card = layout.getBottomCard(column);
        int index = layout.getColumn(column).size() - 2;
        Card previousCard = layout.getColumn(column).get(index);
        List<Card> stack = new ArrayList();
        stack.add(card);
        boolean flag = true;
        while(flag) {
            switch (card.getSuit()) {
                case "Hearts":
                case "Diamonds":
                    if (previousCard.getValue() == card.getValue() + 1 && (
                        previousCard.getSuit().equalsIgnoreCase("Spades") ||
                            previousCard.getSuit().equalsIgnoreCase("Clubs"))) {
                        stack.add(previousCard);
                        card = previousCard;
                        index--;
                        if (index < 0) {
                            flag = false;
                        }
                        else {
                            previousCard = layout.getColumn(column).get(index);
                        }
                    }
                    else {
                        flag = false;
                    }
                    break;
                case "Spades":
                case "Clubs":
                    if (previousCard.getValue() == card.getValue() + 1 && (
                        previousCard.getSuit().equalsIgnoreCase("Hearts") ||
                            previousCard.getSuit().equalsIgnoreCase("Diamonds"))) {
                        stack.add(previousCard);
                        card = previousCard;
                        index--;
                        if (index < 0) {
                            flag = false;
                        }
                        else {
                            previousCard = layout.getColumn(column).get(index);
                        }
                    }
                    else {
                        flag = false;
                    }
                    break;
            }
        }
        return stack;
    }

    public void checkAndMoveStack(List<Card> stack, int fromColumn, int toColumn) {
        int emptyCells = layout.getEmptyCells() + 1;
        int emptyColumns = layout.getEmptyColumns();
        if ((stack.size() < emptyCells) || (stack.size() < (emptyCells * emptyColumns))) {
            if (checkValidMovement(stack.get(stack.size() - 1), toColumn)) {
                for(int i = 0; i < stack.size(); i++) {
                    layout.removeBottomCard(fromColumn);
                }
                while (!stack.isEmpty()) {
                    layout.addToColumn(stack.get(stack.size() - 1), toColumn);
                    stack.remove(stack.size() - 1);
                }
            }
            else {
                System.out.println("Invalid movement!");
            }
        }
        else {
            System.out.println("Not enough space to move!");
        }
    }
}
