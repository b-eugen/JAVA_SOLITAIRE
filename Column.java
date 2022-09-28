package Solitare;

import java.util.Arrays;

import Solitare.Card.Value;

import java.util.ArrayList;

public class Column extends Pile{
    private int number;

    public Column()
    {
        super();
        this.number = 1;
    }

    public Column(int number)
    {
        super();
        this.number = number;
    }

    public Column(Pile pile)
    {
        super(pile);
        this.number = 1;
        this.prepareForGame();
        
    }

    public Column(Pile pile, int number)
    {
        super(pile);
        this.number = number;
        this.prepareForGame();
    }

    public Column(ArrayList<Card> cards)
    {
        super(cards);
        this.number = 1;
        this.prepareForGame();
    }

    public Column(ArrayList<Card> cards, int number)
    {
        super(cards);
        this.number = number;
        this.prepareForGame();
    }

    public String toString()
    {
        return Integer.toString(this.number);
    }

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

    public String[] stringColumn()
    {
        String[] output = new String[] {String.format("col=%1d     ", this.number)};
        int lenColumn = this.lenPile();
        if (lenColumn == 0)
        {
            return Card.concatStrings( output, Card.cardPrintEmptyString());
        }

        
        for (int ind=0; ind<lenColumn; ind++)
        {
            if (ind<lenColumn-1)
            {
                output = Card.concatStrings( output, this.getCard(ind).cardPrintString(false));
            }
            else
            {
                output = Card.concatStrings( output, this.getCard(ind).cardPrintString(true));
            }
        }
        return output;

    }

    public boolean addSlice(ArrayList<Card> cards)
    {
        if (cards.size() > 0)
        {
            Card card0 = cards.get(0);
            int cardLastIndex = this.lenPile()-1;
            if (cardLastIndex >= 0)
            {
                Card cardLast = this.getCard(cardLastIndex);
                if (card0.isRed() != cardLast.isRed() && (cardLast.getValue().ordinal()-card0.getValue().ordinal())==1)
                {
                    return super.addSlice(cards);
                }
            }
            else if(card0.getValue() == Card.Value.KING)
            {
                return super.addSlice(cards);
            }
        }
        System.out.println("Failed to add cards to column "+ this.number);
        
        return false;

        
    }

    public boolean moveToColumn(Column c2)
    {
        int ind = -1;
        if (c2.isEmpty())
        {
            
            for (Card.Suit suit: Card.Suit.values())
            {
                ind = this.indCardVisible(new Card(suit, Card.Value.KING));
                if (ind != -1)
                {
                    c2.addSlice(this.getSlice(ind));
                    return true;
                }
            }
   
        }
        else
        {
            Card card = c2.getCard(c2.lenPile()-1);
            System.out.println("card "+card);
            if (card.getValue() == Card.Value.ACE)
            {
                System.out.println("Failed to move cards from "+this.number+" to "+c2.number + ". You are not allowed to append anything to column with tailing ace");
                return false;
            }

            
            for (Card.Suit suit: card.returnOppositeSuits())
            {
                Card card2 = new Card(suit, Card.Value.values()[card.getValue().ordinal()-1]);
                System.out.println("card2 "+card2);
                ind = this.indCardVisible(card2);
                System.out.println("ind "+ind);
                if (ind != -1)
                {
                    c2.addSlice(this.getSlice(ind));
                    return true;
                }
            }
        }
        
        System.out.println("Failed to move cards from "+this.number+" to "+c2.number);
        return false;
        
        
    }

    public boolean addCard(Card card)
    {
        if (card.isVisible())
        {
            if (this.lenPile()>0)
            {
                Card topCard = this.getCard(this.lenPile()-1);
                if (card.isRed() != topCard.isRed() && (topCard.getValue().ordinal()-card.getValue().ordinal()==1))
                {
                    super.addCard(card);
                    return true;
                }
                System.out.println("Failed to append a card "+card+" to column "+this);
                return false;
            }
            else
            {
                if (card.getValue() == Card.Value.KING)
                {
                    super.addCard(card);
                    return true;
                }
                else
                {
                    System.out.println("Failed to append a card "+card+" to empty column "+this);
                    return false;
                }
                
            }
            
        }
        else
        {
            super.addCard(card);
            return true;
        }
    }

    public Card popTopCard()
    {
        if (this.lenPile() > 1)
        {
            this.getCard(this.lenPile()-2).setVisible();
        }
        return super.popTopCard();
    }
}
