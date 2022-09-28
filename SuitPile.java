package Solitare;

import javax.lang.model.util.ElementScanner6;

import Solitare.Card.Suit;
import Solitare.Card.Value;

public class SuitPile extends Pile{
    private Card.Suit baseSuit;

    public SuitPile()
    {
        super();
        baseSuit = Card.Suit.D;
    }

    public SuitPile(Card.Suit suit)
    {
        super();
        baseSuit = suit;
    }

    public String toString()
    {
        return baseSuit.toString();
    }

    public Card.Suit getBaseSuit()
    {
        return baseSuit;
    }

    public boolean addCard(Card card)
    {
        if (card.getSuit() != baseSuit)
        {
            System.out.println("SuitPile.addCard Error: Failed to put "+card+" in the suit pile "+this+", Suits dont match");
            return false;
        }
        else if (this.isEmpty() && card.getValue()!=Card.Value.ACE)
        {
            System.out.println("SuitPile.addCard Error: Failed to add non-ace card "+card+" into "+this+"pile");
            return false;
        }
        else if (this.isEmpty() && card.getValue()==Card.Value.ACE)
        {
            card.setVisible();
            super.addCard(card);
            return true;
        }
        else if ((this.isEmpty()==false) && (card.getValue().ordinal() - this.getCard(this.lenPile()-1).getValue().ordinal())==1)
        {
            card.setVisible();
            super.addCard(card);
            return true;
        }
        else
        {
            System.out.println("SuitPile.addCard Error: Failed to add card "+card+" into "+this+" pile because the last card is "+this.getCard(this.lenPile()-1));
            return false;
        }
    }

    

    public String[] stringSuitPile()
    {
        if (this.isEmpty())
        {
            Card card = new Card(this.baseSuit, Card.Value.ACE);
            return Card.concatStrings(new String[] {"--------- ", String.format("|%s      | ", this.baseSuit)}, card.cardBody());
        }
        else
        {
            return this.getCard(this.lenPile()-1).cardPrintString(true);
        }
    }

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
