package Collections;

public class Hand extends CardCollection {

    public Hand(Deck deck) {
        super();
        for (int i = 0; i < 4; i++) {
            drawCard(deck);
        }
    }

    public void drawCard(Deck deck) {
        try {
            Card card = deck.getCard[deck.getSizeOfDeck() - 1];
            deck.removeCardFromTop(card);
            addCardToTop(card);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error! Deck is empty!");
        } catch (CardNotFoundException e) {
            System.out.println("Error! Card not found!");
        }
    }

    public void throwCard(Pile pile) {
        try {
            Card card = getCard[0];
            removeCardFromTop(card);
            pile.addCardToTop();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error! Hand is empty!");
        } catch (CardNotFoundException e) {
            System.out.println("Error! Card not found!");
        }
    }
}
