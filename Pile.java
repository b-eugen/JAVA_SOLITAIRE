package Solitaire;
/*
 * This program is the Pile class
 * @version 1 2022-29-9
 * @author Yevhenii Mormul
 */
import java.util.ArrayList;

/*
 * A {@code Pile} object represents a pile of cards. It is also able to tell if the card in the pile has just been revealed or no
 */
public abstract class Pile {
    // private boolean isEmpty;
    private ArrayList<Card> cards;

    /*
    * Default constructor for the class.
    * Initiates empty pile of cards
    */
    public Pile()
    {
        this.cards = new ArrayList<Card>();
    }


    /*
    * Constructor for the class.
    * Initiates pile of cards with the given ArrayList of cards
    * @param cards - ArrayList of cards
    */
    public Pile(ArrayList<Card> cards)
    {
        this.cards = cards;
    }

    /*
    * Constructor for the class.
    * Initiates pile of cards with the given Pile
    * @param pile - Pile
    */
    public Pile(Pile pile)
    {
        this.cards = pile.cards;
    }

    /*
     * Method, which returns true if pile isEmpty
     * @return - boolean true if length of pile is empty, else false
     */
    public boolean isEmpty()
    {
        if (this.cards.size() > 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /*
     * Method, which adds a card to the pile
     * @param card – Card to be added
     * @return - boolean true if succesful
     */
    public boolean addCard(Card card)
    {
        try
        {
            this.cards.add(card);
            return true;
        }  
        catch (Exception e)
        {
            System.out.println("Error Pile.addCard: " + e);
            return false;
        }
    }

    /*
     * Method, which returns the length of the pile
     * @return - integer count of cards
     */
    public int lenPile()
    {
        return cards.size();
    }

    /*
     * Method, which returns the card at the given index of the card
     * @param ind - int index of the card to be fetched
     * @return - Card at index
     */
    public Card getCard(int ind)
    {
        return this.cards.get(ind);
    }

    /*
     * Method, which removes card at the given index
     * @param ind - int index of the card to be removed
     * @return - return true if successful 
     */
    public boolean removeCard(int ind)
    {
        if (this.lenPile() == 0 || ind >= this.lenPile() || ind < 0)
        {
            return false;
        }
        else
        {
            this.cards.remove(ind);
            return true;
        }
    }

    /*
     * Method, which returns the top card of the pile
     * @return - Card
     */
    public Card getTopCard()
    {
        return this.getCard(this.lenPile()-1);
    }

    /*
     * Method, which pops the top card of the pile
     * @return - Card
     */
    public Card popTopCard()
    {
        Card card = this.getCard(this.lenPile()-1);
        this.removeCard(this.lenPile()-1);
        return card;
    }


    /*
     * Method, which returns the index of the card if it is present in the pile and visible
     * @param card - Card to search for
     * @return - index of the card if present and visible, else return -1
     */
    public int indCardVisible(Card card)
    {
        int resultCardInd = -1;
        for (int cardInd = 0; cardInd < this.lenPile(); cardInd++)
        {
            if (card.getSuit() == cards.get(cardInd).getSuit() && card.getValue() == cards.get(cardInd).getValue())
            {
                if (cards.get(cardInd).isVisible())
                    resultCardInd=cardInd;
                break;
            }
        }
        return resultCardInd;
    }

    /*
     * Method, which pops the ArrayList of cards from given index till the end of pile ArrayList
     * @param ind - index to start getting cards from
     * @return - ArrayList of cards
     */
    public ArrayList<Card> popSlice(int ind)
    {
        ArrayList<Card> returnCards = new ArrayList<Card>();
        if (ind < this.lenPile())
        {
            returnCards.addAll(this.cards.subList(ind, this.lenPile()));
            this.cards.subList(ind, this.lenPile()).clear();
            if (this.lenPile() > 0)
            {
                if (this.getCard(ind-1).isVisible()==false)
                {
                    this.getCard(ind-1).setVisible();
                }
            }
        }
        return returnCards;
    }

    /*
     * method which appends the given slice of cards to pile
     * @param cards - ArrayList of Card's
     * @return – true if operation is successful, false otherwise
     */
    public boolean addSlice(ArrayList<Card> cards)
    {
        try
        {
            this.cards.addAll(cards);
            return true;
        }
        catch (Exception e)
        {
            System.out.println("Exception: "+e);
        }
        System.out.println(Card.RED+"Pile.addSlice Error: Failed to add cards slice starting with "+cards.get(0)+" to pile"+Card.BLACK);
        return false;
    }

    

}
