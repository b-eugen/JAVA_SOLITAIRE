package Solitare;

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
    
    public final static int ADD_TO_SUIT_PILE = 10;
    public final static int CARD_REVEAL = 5;
    public final static int CARD_FROM_DECK = 5;

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
        return String.format("_________________ Score: %4d | Number of Moves: %4d________________", score, nMoves);
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
        String[] output = new String[256];
        for(int ind=0; ind<256; ind++)
            output[ind] = "";

        int firstRow = Card.CARD_SIZE;

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
    public boolean deckGetNext()
    {
        boolean result = deck.getNext();
        if (!result)
        {
            System.out.println(Card.RED+"Failed to get a card from the deck, length of deck is 0"+Card.BLACK);
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
     * @param p1 - Pile source pile
     * @param p2 - Pile target pile
     * @return -true if successful
     */
    public boolean move(Pile p1, Pile p2)
    {
        if (!p1.isEmpty())
        {
            Card card = p1.getTopCard();
            if (p2.addCard(card))
            {
                p1.popTopCard();
                return true;
            }
        }
        else
        {
            System.out.println(Card.RED+"Failed to get a card from Suit Pile " + p1.toString() + " because it is empty"+Card.BLACK);
        }
        return false;
    }

    /*
     * Method that manages the movement of piles given 2 valid char inputs
     * @param o1 - char source ([1-7] = column number, P = deck, [DHCS]=suit piles (diamonds, hearts, clubs, spades))
     * @param o2 - char target ([1-7] = column number, [DHCS]=suit piles (diamonds, hearts, clubs, spades))
     * @return true if successful
     */
    public boolean moveCards(char o1, char o2)
    {
        int sPile1 = this.getPileIndex(o1);//attempt to get the source suit pile index
        int sPile2 = this.getPileIndex(o2);//attempt to get the target suit pile index
        int i1 = o1-'0';//attempt to get the source coloumn index
        int i2 = o2-'0';//attempt to get the source coloumn index

        if (sPile1 != -1 && sPile2 != -1)//cannot move cards between piles
        {
            System.out.println(Card.RED+"Error: cannot move from pile "+suitPiles[sPile1]+ " to "+suitPiles[sPile2]+Card.BLACK);
            return false;
        }
        else if (o1 == 'P' && sPile2 != -1)//move from deck to pile
        {
            boolean result = move(deck, suitPiles[sPile2]);
            if (result)
            {
                this.addMove();
                score+=ADD_TO_SUIT_PILE+CARD_FROM_DECK;
            }
            return result;
        }
        else if (o1 == 'P')//move from deck to column
        {
            boolean result = move(deck, columns[i2-1]);
            if (result)
            {
                this.addMove();
                score+=CARD_FROM_DECK;
            }
            return result;
        }
        else if(sPile1 != -1)//move from suit pile to column
        {
            boolean result = move(suitPiles[sPile1], columns[i2-1]);
            if (result)
            {
                this.addMove();
                score-=ADD_TO_SUIT_PILE;
            }
            return result;
        }
        else if(sPile2 != -1)//move from column to suit pile
        {
            boolean result= move(columns[i1-1], suitPiles[sPile2]);
            if (result)
            {
                this.addMove();
                score+=ADD_TO_SUIT_PILE;
                if (columns[i1-1].isCardRevealed())
                {
                    score+=CARD_REVEAL;
                    columns[i1-1].cardNotRevealed();
                }
            }
            
            return result;
        }
        else//move cards between columns
        {
            boolean result= columns[i1-1].moveToColumn(columns[i2-1]);

            if (result)
            {
                this.addMove();
                if (columns[i1-1].isCardRevealed())
                {
                    score+=CARD_REVEAL;
                    columns[i1-1].cardNotRevealed();
                }
            }
                            
            return result;
        }
        
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
