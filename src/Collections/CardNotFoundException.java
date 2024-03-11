package Collections;

public class CardNotFoundException extends Exception {
    public CardNotFoundException() {
        System.out.println("Error! Card not found!");
    }
}