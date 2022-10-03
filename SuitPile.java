package Solitaire;

/*
 * This program is the SuitPile class
 * @version 1 2022-29-9
 * @author Yevhenii Mormul
 */

 /*
 * A {@code SuitPile} object represents a Pile child, which is used as a pile, where of cards of the same suit are stored in solitaire game
 */
public class SuitPile extends Pile{
    private Card.Suit baseSuit;

    /*
    * Default constructor for the class.
    * Initiates empty SuitPile of cards, whose base suit is Diamonds
    */
    public SuitPile()
    {
        super();
        baseSuit = Card.Suit.D;
    }

    /*
    * Constructor for the class.
    * Initiates empty SuitPile of cards, whose base suit is specified in input
    * @param suit - Card.Suit base suit of the SuitPile 
    */
    public SuitPile(Card.Suit suit)
    {
        super();
        baseSuit = suit;
    }

    /*
     * Overrides toSting method, so that it returns the base suit of the SuitPile
     * @return - String baseSuit  
     */
    public String toString()
    {
        return baseSuit.toString();
    }

    /*
     * Method that returns the base suit of the card
     * @return - Card.Suit baseSuit
     */
    public Card.Suit getBaseSuit()
    {
        return baseSuit;
    }

    /*
     * Method that adds a card to the SuitPile
     * @param card - Card to be added
     * @return - boolean true if successful
     */
    public boolean addCard(Card card)
    {
        if (card.getSuit() != baseSuit)//error if base suit is wrong
        {
            System.out.println(Card.RED+"SuitPile.addCard Error: Failed to put "+card+" in the suit pile "+this+", Suits dont match"+Card.BLACK);
            return false;
        }
        else if (this.isEmpty() && card.getValue()!=Card.Value.ACE)//error if trying to append not ACE to empty SuitPile
        {
            System.out.println(Card.RED+"SuitPile.addCard Error: Failed to add non-ace card "+card+" into "+this+" pile"+Card.BLACK);
            return false;
        }
        else if (this.isEmpty() && card.getValue()==Card.Value.ACE)//success if trying to append an ace to the empty pile
        {
            card.setVisible();
            super.addCard(card);
            return true;
        }
        else if ((this.isEmpty()==false) && (card.getValue().ordinal() - this.getCard(this.lenPile()-1).getValue().ordinal())==1)//success if trying to append the right suit, and card with value greater by one unit than top card
        {
            card.setVisible();
            super.addCard(card);
            return true;
        }
        else
        {
            System.out.println(Card.RED+"SuitPile.addCard Error: Failed to add card "+card+" into "+this+" pile because the last card is "+this.getCard(this.lenPile()-1)+Card.BLACK);
            return false;
        }
    }

    
    /*
     * Method that returns a String[] representation of the SuitPile
     * @return - String[] representation of SuitPile
     */
    public String[] stringSuitPile()
    {
        if (this.isEmpty())//If empty return a placeholder with the given suit
        {
            Card card = new Card(this.baseSuit, Card.Value.ACE);
            return card.stringCardToken();
        }
        else//if not empty return the top level card string representation
        {
            Card card = this.getCard(this.lenPile()-1);
                
            return Card.concatStringArrays(new String[] {String.format("%s%s%s         ",card.getColor(), card.getSuit(), Card.BLACK)}, card.stringCardLong());
        }
    }

    /*
     * Method that returns a true if the SuitPile is full (top card is a KING)
     * @return - true if successful
     */
    public boolean isFull()
    {
        if (this.isEmpty())
        {
            return false;
        }
        else if (this.getTopCard().getValue() == Card.Value.KING)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

 
}
