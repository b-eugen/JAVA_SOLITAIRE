package Solitare;
//import org.apache.commons.lang3.ArrayUtils;
import java.util.Arrays;

import java.util.ArrayList;

public class Deck extends Pile{
    int currentCard;
    public Deck()
    {
        super();
        currentCard = 0;
    }

    public Deck(Pile pile)
    {
        super(pile);
        currentCard = pile.lenPile();
    }

    public Deck(ArrayList<Card> cards)
    {
        super(cards);
        currentCard = cards.size();
    }

    public String toString()
    {
        return "Deck";
    }

    public boolean addCard(Card card)
    {
        boolean result = super.addCard(card);
        if (result)
        {
            currentCard++;
            return true;
        }
        return false;
    }

    public Card getTopCard()
    {
        return this.getCard(currentCard);
    }

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

    public boolean getNext()
    {
        int deckLen = this.lenPile();
        if (deckLen == 0)
        {
            return false;
        }
        int next_card = (currentCard+1)%(deckLen+1);
        System.out.println(currentCard+" "+next_card +" "+ deckLen);

        
        if (next_card != deckLen)
        {
            
            this.getCard(next_card).setVisible();
        }
        else if (currentCard != deckLen)
        {
            this.getCard(currentCard).setInvisible();
        }

        currentCard = next_card;
        return true;
    }

    public String[] stringDeck()
    {
        String[] left = new String[Card.CARD_SIZE];
        String[] right = new String[Card.CARD_SIZE];
        String[] result = new String[Card.CARD_SIZE];

        if (currentCard==this.lenPile())
        {
            left = Card.cardPrintEmptyString();
        }
        else
        {
            Card card = this.getCard(currentCard);
            // card.setVisible();
            left = card.cardPrintString(true);
            // card.setInvisible();
        }

        if (this.lenPile()==0)
        {
            right = Card.cardPrintEmptyString();
            System.out.println('1');
        }
        else if (currentCard == this.lenPile()-1)
        {
            right = Card.cardPrintEmptyString();
        }
        else if (currentCard == this.lenPile())
        {
            Card temp = new Card();
            right = temp.cardPrintString(true);
        }
        else
        {

            Card card = this.getCard(currentCard+1);
            card.setInvisible();
            right = card.cardPrintString(true);
            
        }
        

        for(int index = 0; index<Card.CARD_SIZE; index++)
        {
            result[index] = left[index]+right[index];
        }

        return result;
    }
}