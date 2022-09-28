package Solitare;
// import Solitare.Card;
// import Solitare.Deck;

import java.util.Arrays;
import java.util.List;

import Solitare.Column;
import Solitare.SuitPile;

import java.util.ArrayList;
import java.util.Collections;


public class Table {

    public final static int N_COLUMNS = 7;
    
    private Deck deck;
    private Column[] columns;
    private SuitPile[] suitPiles;

    public Table()
    {
        
        columns = new Column[N_COLUMNS];
        int nValues = Card.Value.values().length;
        int nSuits = Card.Suit.values().length;
        int nCards = nValues*nSuits;
        suitPiles = new SuitPile[nValues];

        Card[] cards = new Card[nCards];
        for (int val=0; val<nValues; val++)
        {
            for (int suit=0; suit<nSuits; suit++)
            {
                cards[suit+nSuits*val]= new Card(Card.Suit.values()[suit], Card.Value.values()[val]);
                
                
            }
        }
        
        List<Card> cardList = Arrays.asList(cards);
        Collections.shuffle(cardList);
        cardList.toArray(cards);
        

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

        deck = new Deck(new ArrayList<>(Arrays.asList(cards))); 

        for(int suit=0; suit<nSuits; suit++)
        {
            suitPiles[suit] = new SuitPile(Card.Suit.values()[suit]);
        }
    }

    public String toString()
    {
        String[] output = new String[256];
        for(int ind=0; ind<256; ind++)
            output[ind] = "";

        int firstRow = Card.CARD_SIZE;

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
        
        int max_rows = 0;
        for (int ind = 0; ind<N_COLUMNS; ind++)
        {
            int len = columns[ind].stringColumn().length;
            if (len>max_rows)
                max_rows = len;
        }

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



        for (int ind = 0; ind<output.length; ind++)
        {
            if (output[ind] != "")
                System.out.println(output[ind]);
        }


        return "";
    }

    public boolean deckGetNext()
    {
        boolean result = deck.getNext();
        if (!result)
        {
            System.out.println("Failed to get a card from the deck, length of deck is 0");
        }
        return result;
    }

    // public boolean moveDeckToColumn(Deck deck, Column col)
    // {

    // }
    public int getPileIndex(char suit)
    {
        
        for(int ind=0; ind<Card.Suit.values().length; ind++)
        {
            
            System.out.println(suitPiles[ind]+" "+suit);
            if (suitPiles[ind].getBaseSuit().toString().equals(Character.toString(suit)))
            {
               return ind;
            }
        }
        return -1;
    }

    

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
            System.out.println("Failed to get a card from Suit Pile " + p1.toString() + " because it is empty");
        }
        return false;
    }

    public boolean moveCards(char o1, char o2)
    {
        int sPile1 = this.getPileIndex(o1);
        int sPile2 = this.getPileIndex(o2);
        int i1 = o1-'0';
        int i2 = o2-'0';
        System.out.println(sPile1 + " " + sPile2);

        if (sPile1 != -1 && sPile2 != -1)
        {
            System.out.println("Error: cannot move from pile "+suitPiles[sPile1]+ " to "+suitPiles[sPile2]);
            return false;
        }
        else if (o1 == 'P' && sPile2 != -1)
        {
            return move(deck, suitPiles[sPile2]);
        }
        else if (o1 == 'P')
        {
            return move(deck, columns[i2-1]);
        }
        else if(sPile1 != -1)
        {
            // return this.moveSuitToColumn(i2-1, sPile1);
            return move(suitPiles[sPile1], columns[i2-1]);
        }
        else if(sPile2 != -1)
        {
            return move(columns[i1-1], suitPiles[sPile2]);
            //return this.moveColumnToSuit(i1-1, sPile2);
        }
        else
        {
            return columns[i1-1].moveToColumn(columns[i2-1]);
        }
        

        


        // return true;

    }


}
