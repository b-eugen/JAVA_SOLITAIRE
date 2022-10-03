package Solitaire;

/*
 * This program is the Table class
 * @version 1 2022-29-9
 * @author Yevhenii Mormul
 */
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/*
 * A {@code Table} object represents a Solitaire game table, which performs the management of the deck, columns and suitpiles.
 * It also counts the score, number of moves and displays the board 
 */
public class Table {

    public final static int N_COLUMNS = 7;
    
    public final static int COLUMN_TO_SUITS = 20;
    public final static int COLUMN_TO_COLUMN = 5;
    public final static int DECK_TO_SUITS = 10;

    private Deck deck;
    private Column[] columns;
    private SuitPile[] suitPiles;
    private int nMoves;
    private int score;

    /*
    * Default constructor for the class.
    * Initiates a table fully set up for the solitare game
    */
    public Table()
    {
        //initiate private parameters
        nMoves = 0;
        score=0;
        
        columns = new Column[N_COLUMNS];
        int nValues = Card.Value.values().length;
        int nSuits = Card.Suit.values().length;
        int nCards = nValues*nSuits;
        suitPiles = new SuitPile[nValues];

        //make a deck of 52 unique cards
        Card[] cards = new Card[nCards];
        for (int val=0; val<nValues; val++)
        {
            for (int suit=0; suit<nSuits; suit++)
            {
                cards[suit+nSuits*val]= new Card(Card.Suit.values()[suit], Card.Value.values()[val]);
                
                
            }
        }

        //Shuffle the deck of cards
        List<Card> cardList = Arrays.asList(cards);
        Collections.shuffle(cardList);
        cardList.toArray(cards);
        
        //distribute cards among columns
        for (int col=1; col<=N_COLUMNS; col++)
        {
            Column column = new Column(col);
            for (int row=0; row<col; row++)
            {
                Card card = cards[cards.length - 1];
                column.addCard(card);
                cards = Arrays.copyOf(cards, cards.length - 1);
            }
            column.prepareForGame();
            columns[col-1] = column;
        }

        //put the rest of cards in the deck
        deck = new Deck(new ArrayList<>(Arrays.asList(cards))); 

        //instanciate the empty Suit piles of every suit
        for(int suit=0; suit<nSuits; suit++)
        {
            suitPiles[suit] = new SuitPile(Card.Suit.values()[suit]);
        }
    }

    /*
     * Overrides toSting method, so that it returns the score and the number of moves
     * @return - String with score and number of moves
     */
    public String toString()
    {
        return String.format("\n_________________ Score: %4d | Number of Moves: %4d________________\n", score, nMoves);
    }

    /*
     * Method that increments the number of moves
     */
    public void addMove()
    {
        nMoves++;
    }

    /*
     * Method that prints out the table of cards at its current state
     */
    public void printOut()
    {
        System.out.println(toString());

        //initiate output array of strings
        int N_ROWS = 256;

        String[] output = new String[N_ROWS];
        for(int ind=0; ind<N_ROWS; ind++)
            output[ind] = "";

        int firstRow = Card.CARD_SIZE+1;

        //add SuitPiles to output horizontally
        for(int ind=0; ind<Card.Suit.values().length; ind++)
        {
            String[] suitPileString = suitPiles[ind].stringSuitPile();
            for (int jnd = 0; jnd <suitPileString.length; jnd++)
            {
                try
                {
                    output[jnd] += suitPileString[jnd];
                }
                catch (Exception e)
                {
                    output[jnd] = suitPileString[jnd];
                }
            }
        }

        //add deck to to output horizontally 
        String[] deckString = deck.stringDeck();
        for (int jnd = 0; jnd <deckString.length; jnd++)
        {
            try
            {
                output[jnd] += "          "+deckString[jnd];
            }
            catch (Exception e)
            {
                output[jnd] = deckString[jnd];
            }
        }
        
        //count the maximal number of rows in the columns
        int max_rows = 0;
        for (int ind = 0; ind<N_COLUMNS; ind++)
        {
            int len = columns[ind].stringColumn().length;
            if (len>max_rows)
                max_rows = len;
        }

        //append columns below the suitPiles and deck
        for (int ind = 0; ind<N_COLUMNS; ind++)
        {
            String[] columnString = columns[ind].stringColumn();
            for(int jnd = 0; jnd<max_rows; jnd++)
            {
                try
                {
                    output[jnd+firstRow] += columnString[jnd];
                }
                catch (Exception e)
                {
                    output[jnd+firstRow] += "          ";
                }
            }
        }


        //output the table
        for (int ind = 0; ind<output.length; ind++)
        {
            if (output[ind] != "")
                System.out.println(output[ind]);
        }


        return;
    }

    /*
     * Method that gets the next card from the deck
     * @return - true if successful
     */
    public boolean getNextCardFromDeck()
    {
        boolean result = deck.getNext();
        if (!result)
        {
            System.out.println(Card.RED+"Table.getNextCardFromDeck Error: Invalid play, failed to get a card from the deck, length of deck is 0"+Card.BLACK);
        }
        else
        {
            this.addMove();
        }
        return result;
    }

    /*
     * Method that gets the index of the pile, if the suit in the form of character is provided
     * @return - int index of the pile (-1 if pile is not present)
     */
    public int getPileIndex(char suit)
    {
        for(int ind=0; ind<Card.Suit.values().length; ind++)
        {            
            if (suitPiles[ind].getBaseSuit().toString().equals(Character.toString(suit)))
            {
               return ind;
            }
        }
        return -1;
    }

    
    /*
     * Method that move cards between 2 piles
     * @param source - Pile source pile
     * @param destination - Pile target pile
     * @return -true if successful
     */
    public boolean moveCards(Pile source, Pile destination)
    {
        if (!source.isEmpty())
        {
            Card card = source.getTopCard();
            if (destination.addCard(card))
            {
                source.popTopCard();
                return true;
            }
        }
        else
        {
            System.out.println(Card.RED+"Table.moveCards Error: Invalid play, failed to get a card from Pile " + source.toString() + " because it is empty"+Card.BLACK);
        }
        return false;
    }

    /*
     * Method that manages the movement of piles given 2 valid char inputs
     * @param o1 - char source ([1-7] = column number, P = deck, [DHCS]=suit piles (diamonds, hearts, clubs, spades))
     * @param o2 - char target ([1-7] = column number, [DHCS]=suit piles (diamonds, hearts, clubs, spades))
     * @return true if successful
     */
    public boolean moveCardsFromInput(char o1, char o2)
    {
        int sPile1 = this.getPileIndex(o1);//attempt to get the source suit pile index
        int sPile2 = this.getPileIndex(o2);//attempt to get the target suit pile index
        int i1 = o1-'0';//attempt to get the source coloumn index
        int i2 = o2-'0';//attempt to get the source coloumn index
        boolean result = false;
        if (sPile1 != -1 && sPile2 != -1)//cannot move cards between piles
        {
            System.out.println(Card.RED+"Table.moveCards Error: Invalid play, cannot move from suit pile "+suitPiles[sPile1]+ " to "+suitPiles[sPile2]+Card.BLACK);
        }
        else if (o1 == 'P' && sPile2 != -1)//move from deck to pile
        {
            result = moveCards(deck, suitPiles[sPile2]);
            if (result)
            {
                this.addMove();
                score+=DECK_TO_SUITS;
            }
        }
        else if (o1 == 'P')//move from deck to column
        {
            result = moveCards(deck, columns[i2-1]);
            if (result)
            {
                this.addMove();
            }
        }
        else if(sPile1 != -1)//move from suit pile to column
        {
            result = moveCards(suitPiles[sPile1], columns[i2-1]);
            if (result)
            {
                this.addMove();
            }
        }
        else if(sPile2 != -1)//move from column to suit pile
        {
            result= moveCards(columns[i1-1], suitPiles[sPile2]);
            if (result)
            {
                this.addMove();
                this.score += COLUMN_TO_SUITS;
            }            
        }
        else//move cards between columns
        {
            int num_cards = columns[i1-1].moveToColumn(columns[i2-1]);
            if (num_cards>0)
            {
                this.addMove();
                result = true;
                this.score += COLUMN_TO_COLUMN*num_cards;
            }
        }
        return result;
        
    }

    /*
     * Method that determines if the user is victorious
     * @return true if successful
     */
    public boolean isVictory()
    {
        //if all piles are full, user wins
        for(int ind=0; ind<Card.Suit.values().length; ind++)
        {
            if (!suitPiles[ind].isFull())
            {
                return false;
            }
        }
        System.out.println("Victory!!!");
        return true;
    }


}
