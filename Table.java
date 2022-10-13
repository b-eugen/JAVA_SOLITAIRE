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
    

    private Deck deck;
    private Column[] columns;
    private SuitPile[] suitPiles;

    /*
    * Default constructor for the class.
    * Initiates a table fully set up for the solitare game
    */
    public Table()
    {
        
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
     * @return - "Table"
     */
    public String toString()
    {
        return String.format("Table");
    }

    /*
     * Method that prints out the table of cards at its current state
     */
    public void printOut()
    {
        System.out.println(toString());

        //initiate an exesive output array of empty strings
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


        //output the table row-by-row
        for (int ind = 0; ind<output.length; ind++)
        {
            if (output[ind] != "")
            {
                System.out.println(output[ind]);
            }
            else
            {
                break;
            }
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
        return result;
    }

    /*
     * Method that gets the index of the pile, if the suit in the form of character is provided
     * @return - int index of the pile (-1 if pile is not present)
     */
    public int getPileIndex(char suit)
    {
        char lowerCase =(char)(suit +'A'-'a');
        for(int ind=0; ind<Card.Suit.values().length; ind++)
        {            
            if (suitPiles[ind].getBaseSuit().toString().equals(Character.toString(suit)) || suitPiles[ind].getBaseSuit().toString().equals(Character.toString(lowerCase)))
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
        boolean result = false;
        if (!source.isEmpty())
        {
            Card card = source.getTopCard();
            if (destination.isLegal(card))
            {
                if (destination.addCard(card))
                {
                    source.popTopCard();
                    result= true;
                }
            }
        }
        else
        {
            System.out.println(Card.RED+"Table.moveCards Error: Invalid play, failed to get a card from Pile " + source.toString() + " because it is empty"+Card.BLACK);
        }
        return result;
    }

    /*
     * Method that manages the movement of piles given 2 valid char inputs
     * @param source - char source ([1-7] = column number, P = deck, [DHCS]=suit piles (diamonds, hearts, clubs, spades))
     * @param destination - char target ([1-7] = column number, [DHCS]=suit piles (diamonds, hearts, clubs, spades))
     * @return true if successful
     */
    public int moveCardsFromInput(char source, char destination)
    {
        int sPile1 = this.getPileIndex(source);//attempt to get the source suit pile index
        int sPile2 = this.getPileIndex(destination);//attempt to get the target suit pile index
        int i1 = source-'0';//attempt to get the source coloumn index
        int i2 = destination-'0';//attempt to get the source coloumn index
        boolean result = false;
        int score = -1;
        if (sPile1 != -1 && sPile2 != -1)//cannot move cards between suit piles
        {
            System.out.println(Card.RED+"Table.moveCards Error: Invalid play, cannot move from suit pile "+suitPiles[sPile1]+ " to "+suitPiles[sPile2]+Card.BLACK);
        }
        else if ((source == 'P'|| source=='p') && sPile2 != -1)//move from deck to pile
        {
            result = moveCards(deck, suitPiles[sPile2]);
            if (result)
            {
                score=Player.DECK_TO_SUITS;
            }
        }
        else if (source == 'P'|| source=='p')//move from deck to column
        {
            result = moveCards(deck, columns[i2-1]);
            if (result)
            {
                score = 0;
            }
        }
        else if(sPile1 != -1)//move from suit pile to column
        {
            result = moveCards(suitPiles[sPile1], columns[i2-1]);
            if (result)
            {
                score = 0;
            }
        }
        else if(sPile2 != -1)//move from column to suit pile
        {
            result= moveCards(columns[i1-1], suitPiles[sPile2]);
            if (result)
            {
                score = Player.COLUMN_TO_SUITS;
            }            
        }
        else//move cards between columns
        {
            int num_cards = columns[i1-1].moveToColumn(columns[i2-1]);
            if (num_cards>0)
            {
                score = Player.COLUMN_TO_COLUMN*num_cards;
            }
        }
        return score;
        
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
