package Solitaire;

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
            return Card.concatStringArrays( output, Card.stringCardEmpty());
        }

        for (int ind=0; ind<lenColumn; ind++)//else display all cards
        {
            if (ind<lenColumn-1)//if card is not the top, go for short format
            {
                output = Card.concatStringArrays( output, this.getCard(ind).stringCardShort());
            }
            else//top card is displayed in the long format
            {
                output = Card.concatStringArrays( output, this.getCard(ind).stringCardLong());
            }
        }
        return output;

    }

    /*
     * Method, which returns true if appending card to column is legal
     * @param card - card to append. if invisible, just append, if visible, make sure that the move is legal
     * @return boolean -- true if card can be moved or if card is invisble
     */
    public boolean isLegal(Card card)
    {
        boolean result = false;
        if (card.isVisible())
        {
            if (this.lenPile()>0)//if column is not empty
            {
                Card topCard = this.getCard(this.lenPile()-1);
                //make sure that suits dont match and the value is 1 unit less
                if (card.isRed() != topCard.isRed() && (topCard.getValue().ordinal()-card.getValue().ordinal()==1))
                {
                    result = true;
                }
                else //report an error
                {
                    System.out.println(Card.RED+"Column.isLegal: Invalid play, failed to append a card "+card+" to empty column "+this+", can only put a card of oposite suit and value reduced by 1"+Card.BLACK);
                }
            }
            else//if column is empty only append king
            {
                if (card.getValue() == Card.Value.KING)
                {
                    result = true;
                }
                else //report an error
                {
                    System.out.println(Card.RED+"Column.isLegal: Invalid play, failed to append a card "+card+" to empty column "+this+", can only put a KING into the empty column"+Card.BLACK);
                }
            }
            
        }
        else//if card is invisible perform default append
        {
            result = true;
        }
        return result;
    }


         /*
     * Method which appends a card to the column
     * @param card - card to append. if invisible, just append, if visible, make sure that the move is legal
     * @return boolean - true if successful
     */
    public boolean addCard(Card card)
    {
        boolean result=false;
        if (this.isLegal(card))//make sure that the move is legal and add a card
        {
            result = super.addCard(card);
        }
        return result;
    }

    /*
     * Method which pops the top card of the column
     * @return Card - top card of the column
     */
    public Card popTopCard()
    {
        if (this.lenPile() > 1)//make next top pile visible
        {
            if (this.getCard(this.lenPile()-2).isVisible()==false)
            {
                this.getCard(this.lenPile()-2).setVisible();
            }    
        }
        return super.popTopCard();
    }


    /*
     * Method which adds the ArrayList of Card's to the column
     * @param cards - ArrayList to append
     * @return boolean - true if successful
     */
    public boolean addSlice(ArrayList<Card> cards)
    {
        boolean result = false;
        if (cards.size() > 0)
        {
            Card card0 = cards.get(0);//get the first card

            if (this.isLegal(card0))//make sure that adding a card is legal
            {
                result = super.addSlice(cards);
            }
        }
        if (!result)
        {
            System.out.println(Card.RED+"Column.addSlice Error: Failed to add cards to column "+ this.number+Card.BLACK);
        }
        
        return result;
    }


    /*
     * Method which returns the number of cards, which cab be moved from this column to the desitnation column, if there is a legal move
     * @param destination - target column
     * @return int - -1, if impossible to make a move, ind of the first card to be moved otherwise
     */
    public int getLegalInd(Column destination)
    {
        int ind = -1;
        if (destination.isEmpty())//if target is empty, move is legal only if this column has an KING card, which is visible
        {
            for (Card.Suit suit: Card.Suit.values())
            {
                ind = this.indCardVisible(new Card(suit, Card.Value.KING));
                if (ind != -1)
                {
                    break;
                }
            }
        }
        else//target is not empty
        {
            Card card = destination.getCard(destination.lenPile()-1);//get top card of the target
            if (card.getValue() == Card.Value.ACE)//if target ends with ace report an error
            {
                System.out.println(Card.RED+"Column.getLegalInd: Invalid play, failed to move cards from "+this.number+" to "+destination.number + ". You are not allowed to append anything to column with tailing ace"+Card.BLACK);
            }
            else
            {
                for (Card.Suit suit: card.returnOppositeSuits())//loop through oposite suits of the card
                {
                    Card card2 = new Card(suit, Card.Value.values()[card.getValue().ordinal()-1]);//instanciate a card whose value is smaller by 1 unit than the value of the destination top card, with oposite suit
                    ind = this.indCardVisible(card2);
                    if (ind != -1)
                    {
                        break;
                    }
                }
            }
        }
        return ind;
    }

    /*
     * Method which automatically moves cards from this column to the destination column, if there is a legal move
     * @param destination - target column
     * @return boolean - true if successful
     */
    public int moveToColumn(Column destination)
    {
        int ind = this.getLegalInd(destination);
        int numberCardsMoved = 0;

        if (ind != -1)//move cards if ind is not -1
        {
            int length = this.lenPile();
            if (destination.addSlice(this.popSlice(ind)))
            {
                numberCardsMoved=length-ind;
            }
        }
        else
        {
            //if the move is illegal report an error
            System.out.println(Card.RED+"Column.moveToColumn: Invalid play, failed to move cards from "+this.number+" to "+destination.number+Card.BLACK);
        }
        
        return numberCardsMoved;
    }

}
