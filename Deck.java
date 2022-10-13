package Solitaire;

/*
 * This program is the Deck class
 * @version 1 2022-29-9
 * @author Yevhenii Mormul
 */
import java.util.Arrays;
import java.util.ArrayList;

/*
 * A {@code Deck} object represents a Pile child, which is used to draw extra cards in solitare
 */
public class Deck extends Pile{
    int currentCard;
    /*
    * Default constructor for the class.
    * Initiates empty deck of cards
    */
    public Deck()
    {
        super();
        currentCard = 0;
    }

    /*
    * Constructor for the class.
    * Initiates a deck of cards with a pile
    * @param pile – Pile of cards
    */
    public Deck(Pile pile)
    {
        super(pile);
        currentCard = pile.lenPile();
    }

    /*
     * Constructor for the class.
     * Initiates a deck of cards with an array of cards pile
     * @param cards – ArrayList of cards
     */
    public Deck(ArrayList<Card> cards)
    {
        super(cards);
        currentCard = cards.size();
    }

    /*
     * Override of default toString method
     * @return "Deck"
     */
    public String toString()
    {
        return "Deck";
    }

    /*
     * Method, which returns true if appending card to pile is legal (true if card is invisible)
     * @param card - object of class Card to be appended
     * @return boolean -- true if card is invisble
     */
    public boolean isLegal(Card card)
    {
        if(!card.isVisible())
        {
            System.out.println(Card.RED+"Deck.isLegal: Invalid play, failed to append a card "+card+" to deck"+Card.BLACK);
        }
        return !card.isVisible();
    }

    /*
     * Method which adds a card to the deck
     * @parameter card – Card to add
     * @return true if successful
     */
    public boolean addCard(Card card)
    {
        boolean result=false;
        if (this.isLegal(card))
        {
            result = super.addCard(card);
            if (result)
            {
                currentCard++;
            }
        }
        return result;
    }

    /*
     * Method which returns the currently active card
     * @return Card identified by currentCard
     */
    public Card getTopCard()
    {
        return this.getCard(currentCard);
    }

    /*
     * Method which determines if the currently displayed card is empty
     * @return true if current card is empty
     */
    public boolean isEmpty()
    {
        if (currentCard == this.lenPile())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
     * Method which pops the currently displayed card of the deck
     * @return Card
     */
    public Card popTopCard()
    {
        Card result = this.getCard(currentCard);
        this.removeCard(currentCard);
        int len = this.lenPile();
        currentCard = (currentCard +len)%(len+1);
        if (currentCard != len)
        {
            this.getCard(currentCard).setVisible();
        }
        return result;
    }

    /*
     * method which increments and loops the index of the card currently displayed (currentCard)
     * @return false if deck has no cards
     */
    public boolean getNext()
    {
        int deckLen = this.lenPile();
        if (deckLen == 0)
        {
            return false;
        }
        int next_card = (currentCard+1)%(deckLen+1);//update and loop the next card index
        
        if (next_card != deckLen)//only if current card is not the last in the deck
        {
            this.getCard(next_card).setVisible();//make next card visible
        }
        else if (currentCard != deckLen)//only if card is displayed  
        {
            this.getCard(currentCard).setInvisible();//make current card invisible
        }

        currentCard = next_card;//update current card
        return true;
    }

    /*
     * method which returs an array of strings representing a Deck
     * @return Deck representation in String of arrays
     */
    public String[] stringDeck()
    {
        String[] left = new String[Card.CARD_SIZE];//left card/placeholder
        String[] right = new String[Card.CARD_SIZE];//right card/placeholder
        String[] result = new String[Card.CARD_SIZE+1];//resulting deck
        result[0] = "P";
        
        if (currentCard==this.lenPile())//if no card is drawn
        {
            left = Card.stringCardEmpty();//get placeholder
        }
        else
        {
            Card card = this.getCard(currentCard);//get the drawn card
            left = card.stringCardLong();//get the card in String[] representation
        }

        if ((this.lenPile()==0) || (currentCard == this.lenPile()-1))//if there are no more cards left in deck
        {
            right = Card.stringCardEmpty();
        }
        else //if there are cards in the deck, show the card face down
        {
            Card temp = new Card();
            right = temp.stringCardLong();
        }

        //merge 2 arraylists horizontally
        for(int index = 0; index<Card.CARD_SIZE; index++)
        {
            result[index+1] = left[index]+right[index];
        }

        return result;
    }
}