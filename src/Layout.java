import java.util.List;
import java.util.ArrayList;

public class Layout {
    private List<ArrayList<Card>> columns = new ArrayList(8);
    private int freeHearts = 0;
    private int freeSpades = 0;
    private int freeDiamonds = 0;
    private int freeClubs = 0;
    private Card cell1 = new Card("", 0);
    private Card cell2 = new Card("", 0);
    private Card cell3 = new Card("", 0);
    private Card cell4 = new Card("", 0);

    public void setup(List<Card> deck) {
        for (int i = 0; i < 8; i++) {
            ArrayList<Card> cards = new ArrayList(21);
            columns.add(cards);
        }
        int i = 0;
        int j;
        for (Card card : deck) {
            j = i  % 8;
            columns.get(j).add(card);
            i++;
        }
    }

    private void printTopBorder() {
        System.out.println("_____H__________S__________D__________C_________________________________________________");
    }

    private void printBlankWithSides() {
        System.out.println("|         ||         ||         ||         ||         ||         ||         ||         |");
    }

    private void printValueWithSides() {
        String str = "|    %s    ||    %s    ||    %s    ||    %s    |";
        System.out.print(str.format(str, freeHearts, freeSpades, freeDiamonds, freeClubs));

        cell1.printCard();
        cell2.printCard();
        cell3.printCard();
        cell4.printCard();
        System.out.println();
    }

    private void printBottomLine() {
        System.out.println("|_________||_________||_________||_________||_________||_________||_________||_________|");
    }

    private void printBottomBorder() {
        System.out.println("     1          2          3          4          5          6          7          8     ");
    }

    public void printColumns() {
        printTopBorder();
        printBlankWithSides();
        printValueWithSides();
        printBottomLine();

        boolean flag = true;
        int i = 0;
        while(flag) {
            flag = false;
            for (List<Card> column : columns) {
                if (column.size() > i) {
                    flag = true;
                    column.get(i).printCard();
                }
                else {
                    System.out.print("           ");
                }
            }
            System.out.println();
            i++;
        }

        printBottomBorder();
    }

    public void printCells() {
        cell1.printCard();
        cell2.printCard();
        cell3.printCard();
        cell4.printCard();
        System.out.println();
        System.out.println("|_________||_________||_________||_________|");
        System.out.println("     1          2          3          4     ");
    }

    public void printStack(List<Card> stack) {
        for (Card card : stack) {
            card.printCard();
        }
        System.out.println();
        String str = new String();
        for (int i = 1; i <= stack.size(); i++) {
            System.out.print("|_________|");
        }
        System.out.println();
        for (int i = 1; i <= stack.size(); i++) {
            if (i < 10)
                str = str + String.format("     %s     ", i);
            else
                str = str + String.format("     %s     ", i);
        }
        System.out.println(str);
    }

    public Card getBottomCard(int column) {
        if (columns.get(column).size() != 0) {
            return columns.get(column).get(columns.get(column).size() - 1);
        }
        return null;
    }

    public void removeBottomCard(int column) {
        columns.get(column).remove(columns.get(column).size() - 1);
    }

    public void addToColumn(Card card, int column) {
        columns.get(column).add(card);
    }

    public List<Card> getColumn(int column) {
        return columns.get(column);
    }

    public int getEmptyCells() {
        int count = 0;
        if (cell1.getValue() == 0) count++;
        if (cell2.getValue() == 0) count++;
        if (cell3.getValue() == 0) count++;
        if (cell4.getValue() == 0) count++;
        return count;
    }

    public int getEmptyColumns() {
        int count = 0;
        for (List<Card> column : columns) {
            if (column.isEmpty()) {
                count ++;
            }
        }
        return count;
    }

    public int getFreeHearts() {
        return freeHearts;
    }

    public int getFreeSpades() {
        return freeSpades;
    }

    public int getFreeDiamonds() {
        return freeDiamonds;
    }

    public int getFreeClubs() {
        return freeClubs;
    }

    public Card getCell1() {
        return cell1;
    }

    public Card getCell2() {
        return cell2;
    }

    public Card getCell3() {
        return cell3;
    }

    public Card getCell4() {
        return cell4;
    }

    public void setFreeHearts(int freeHearts) {
        this.freeHearts = freeHearts;
    }

    public void setFreeSpades(int freeSpades) {
        this.freeSpades = freeSpades;
    }

    public void setFreeDiamonds(int freeDiamonds) {
        this.freeDiamonds = freeDiamonds;
    }

    public void setFreeClubs(int freeClubs) {
        this.freeClubs = freeClubs;
    }

    public void setCell1(Card cell1) {
        this.cell1 = cell1;
    }

    public void setCell2(Card cell2) {
        this.cell2 = cell2;
    }

    public void setCell3(Card cell3) {
        this.cell3 = cell3;
    }

    public void setCell4(Card cell4) {
        this.cell4 = cell4;
    }
}
