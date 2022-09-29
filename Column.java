package Solitare;

/*
 * This program is the Column class
 * @version 1 2022-29-9
 * @author Yevhenii Mormul
 */

import java.util.Arrays;
import java.util.ArrayList;

/*
 * A {@code Column} object represents a Pile child, which is used as a column of cards in solitaire game
 */
public class Column extends Pile{
    private int number;
    

    /*
    * Default constructor for the class.
    * Initiates empty column of cards, whose ordinal number is 1
    */
    public Column()
    {
        super();
        this.number = 1;
    }

    /*
    * Constructor for the class.
    * Initiates empty column of cards, whose ordinal number is provided as an input
    * @param number - int ordinal number of the pile
    */
    public Column(int number)
    {
        super();
        this.number = number;
    }

    /*
    * Constructor for the class.
    * Initiates a column of cards from pile, whose ordinal number is 1
    */
    public Column(Pile pile)
    {
        super(pile);
        this.number = 1;
        this.prepareForGame();
    }

    /*
    * Constructor for the class.
    * Initiates a column of cards from pile, whose ordinal number is provided as an input
    * @param number - int ordinal number of the pile
    */
    public Column(Pile pile, int number)
    {
        super(pile);
        this.number = number;
        this.prepareForGame();
    }

    /*
    * Constructor for the class.
    * Initiates a column of cards from the arraylist of cards, whose ordinal number is 1
    */
    public Column(ArrayList<Card> cards)
    {
        super(cards);
        this.number = 1;
        this.prepareForGame();
    }

    /*
    * Constructor for the class.
    * Initiates a column of cards from the arraylist of cards, whose ordinal number is provided as an input
    * @param number - int ordinal number of the pile
    */
    public Column(ArrayList<Card> cards, int number)
    {
        super(cards);
        this.number = number;
        this.prepareForGame();
    }

    /*
     * Override toString method
     * @return String column ordinal number
     */
    public String toString()
    {
        return Integer.toString(this.number);
    }

    /*
     * Method which prepares the column from the game.
     * It flips all the cards face down, except from the top card, which is flipped face up
     */
    public void prepareForGame()
    {
        for (int ind=0; ind<this.lenPile(); ind++)
        {
            if (ind<this.lenPile()-1)
            {
                this.getCard(ind).setInvisible();
            }
            else
            {
                this.getCard(ind).setVisible();
            }
        }
    }

    /*
     * Method which converts the column to the corresponding array of strings
     * @return String[] - representation of columns as an array of strings
     */
    public String[] stringColumn()
    {
        String[] output = new String[] {String.format("    %1d     ", this.number)};//display ordinal number
        int lenColumn = this.lenPile();
        if (lenColumn == 0)//display empty plceholder if there are no cards in the column
        {
            return Card.concatStringArrays( output, Card.cardPrintEmptyString());
        }

        for (int ind=0; ind<lenColumn; ind++)//else display all cards
        {
            if (ind<lenColumn-1)//if card is not the top, go for short format
            {
                output = Card.concatStringArrays( output, this.getCard(ind).cardPrintString(false));
            }
            else//top card is displayed in the long format
            {
                output = Card.concatStringArrays( output, this.getCard(ind).cardPrintString(true));
            }
        }
        return output;

    }

    /*
     * Method which adds the ArrayList of Card's to the column
     * @param cards - ArrayList to append
     * @return boolean - true if successful
     */
    public boolean addSlice(ArrayList<Card> cards)
    {
        if (cards.size() > 0)
        {
            Card card0 = cards.get(0);//get the first card
            int cardLastIndex = this.lenPile()-1;

            //if this column is not empty allow to append cards only if the value of the first card in cards precedes the value of the last value in column and has an opposite color
            if (cardLastIndex >= 0)
            {
                Card cardLast = this.getCard(cardLastIndex);
                if (card0.isRed() != cardLast.isRed() && (cardLast.getValue().ordinal()-card0.getValue().ordinal())==1)
                {
                    return super.addSlice(cards);
                }
            }
            else if(card0.getValue() == Card.Value.KING)//if this column is empty allow to append cards, only if they start with a king
            {
                return super.addSlice(cards);
            }
        }
        System.out.println(Card.RED+"Failed to add cards to column "+ this.number+Card.BLACK);
        
        return false;
    }

    /*
     * Method which automatically moves cards from this column to the input column, if there is a legal move
     * @param c2 - target column
     * @return boolean - true if successful
     */
    public boolean moveToColumn(Column c2)
    {
        int ind = -1;
        if (c2.isEmpty())//if target is empty, move is legal only if this column has an KING card, which is visible
        {
            
            for (Card.Suit suit: Card.Suit.values())
            {
                ind = this.indCardVisible(new Card(suit, Card.Value.KING));
                if (ind != -1)//if there is at least 1 visible KING get all cards starting from KING's index and move them to the c2
                {
                    c2.addSlice(this.popSlice(ind));
                    return true;
                }
            }
   
        }
        else//target is not empty
        {
            Card card = c2.getCard(c2.lenPile()-1);//get top card of the target
            if (card.getValue() == Card.Value.ACE)//if target ends with ace report an error
            {
                System.out.println(Card.RED+"Failed to move cards from "+this.number+" to "+c2.number + ". You are not allowed to append anything to column with tailing ace"+Card.BLACK);
                return false;
            }

            for (Card.Suit suit: card.returnOppositeSuits())//loop through oposite suits of the card
            {
                Card card2 = new Card(suit, Card.Value.values()[card.getValue().ordinal()-1]);//instanciate a card whose value is smaller by 1 unit than the value of the c2 top card, with oposite suit
                ind = this.indCardVisible(card2);
                if (ind != -1)//if card2 is in source column get a legal slice and move it
                {
                    c2.addSlice(this.popSlice(ind));
                    return true;
                }
            }
        }
        //if the move is illegal report an error
        System.out.println(Card.RED+"Failed to move cards from "+this.number+" to "+c2.number+Card.BLACK);
        return false;
    }

     /*
     * Method which appends a card to the column
     * @param card - card to append. if invisible, just append, if visible, make sure that the move is legal
     * @return boolean - true if successful
     */
    public boolean addCard(Card card)
    {
        if (card.isVisible())
        {
            if (this.lenPile()>0)//if column is not empty
            {
                Card topCard = this.getCard(this.lenPile()-1);
                //make sure that suits dont match and the value is 1 unit less
                if (card.isRed() != topCard.isRed() && (topCard.getValue().ordinal()-card.getValue().ordinal()==1))
                {
                    super.addCard(card);
                    return true;
                }
            }
            else//if column is empty only append king
            {
                if (card.getValue() == Card.Value.KING)
                {
                    super.addCard(card);
                    return true;
                }
            }
            //report an error
            System.out.println(Card.RED+"Failed to append a card "+card+" to empty column "+this+Card.BLACK);
            return false;
            
        }
        else//if card is invisible perform default append
        {
            super.addCard(card);
            return true;
        }
    }

    /*
     * Method which pops the top card of the column
     * @return Card - top card of the column
     */
    public Card popTopCard()
    {
        if (this.lenPile() > 1)
        {
            if (this.getCard(this.lenPile()-2).isVisible()==false)
            {
                this.getCard(this.lenPile()-2).setVisible();
                this.cardRevealed();
            }
            
        }
        return super.popTopCard();
    }
}
