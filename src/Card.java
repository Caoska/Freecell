public class Card {
    private String suit;
    private int value;

    public Card(String suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return this.suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void printCard() {
        if (this.value == 0) {
            System.out.print("     0     ");
        }
        else {
            System.out.print(this.value + this.suit);
            int len = (this.suit.length() + (this.value > 9 ? 2 : 1));
            for (int j = 0; j < (11 - len); j++) {
                System.out.print(" ");
            }
        }
    }
}
