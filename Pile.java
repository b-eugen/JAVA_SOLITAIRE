package Solitare;
import java.util.ArrayList;

public class Pile {
    // private boolean isEmpty;
    private ArrayList<Card> cards;
    private boolean cardReveal;

    public Pile()
    {
        this.cards = new ArrayList<Card>();
        cardReveal = false;
    }

    public Pile(ArrayList<Card> cards)
    {
        this.cards = cards;
        cardReveal = false;
    }

    public Pile(Pile pile)
    {
        this.cards = pile.cards;
        cardReveal = false;
    }

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

    public int lenPile()
    {
        return cards.size();
    }

    public Card getCard(int index)
    {
        return this.cards.get(index);
    }

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

    public Card getTopCard()
    {
        return this.getCard(this.lenPile()-1);
    }

    public Card popTopCard()
    {
        Card card = this.getCard(this.lenPile()-1);
        this.removeCard(this.lenPile()-1);
        return card;
    }

    public int indCardVisible(Card card)
    {
        for (int cardInd = 0; cardInd < this.lenPile(); cardInd++)
        {
            System.out.println("indCardVisible: "+card + " "+cards.get(cardInd) +" "+cards.get(cardInd).isVisible());
            if (card.getSuit() == cards.get(cardInd).getSuit() && card.getValue() == cards.get(cardInd).getValue())
            {
                if (cards.get(cardInd).isVisible())
                    return cardInd;
                else
                    break;
            }
        }
        return -1;
    }

    public ArrayList<Card> getSlice(int ind)
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
                    cardReveal = true;
                }
                
            }
        }
        return returnCards;
    }

    public boolean isCardRevealed()
    {
        return cardReveal;
    }

    public void cardRevealed()
    {
        cardReveal=true;
    }

    public void cardNotRevealed()
    {
        cardReveal=false;
    }

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
        System.out.println("Failed to add cards slice starting with "+cards.get(0)+" to pile");
        return false;
    }

    

}
