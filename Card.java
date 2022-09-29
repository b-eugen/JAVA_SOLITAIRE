package Solitare;
//import org.apache.commons.lang3.ArrayUtils;
import java.util.Arrays;

public class Card {
    public enum Value {ACE("A"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"), JACK("J"), QUEEN("Q"), KING("K");
                        private String name;
                        private Value(String name)
                        {
                            this.name=name;
                        }
                        @Override
                        public String toString(){
                            return this.name;
                        }
                    };
    public enum Suit {D, H, C, S};

    public static final String BLACK = "\033[0m";
	public static final String RED = "\033[0;31m";

    public static final int CARD_SIZE = 7;
    private Value value;
    private Suit suit;
    private boolean isVisible;


    public Card()
    {
        this.suit = Suit.D;
        this.value = Value.ACE;
        this.isVisible = false;
    }

    public Card(Suit suit, Value value)
    {
        this.suit = suit;
        this.value = value;
        this.isVisible = false;
    }

    public boolean cardFromString(String cardString)
    {
        for (Card.Value value2 : Card.Value.values())
        {
            for (Card.Suit suit2 : Card.Suit.values())
            {
                System.out.println(value2.toString()+suit2.toString());
                if (cardString.equals(value2.toString()+suit2.toString()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public String toString()
    {
        return value.toString()+suit.toString();
    }

    public boolean isVisible()
    {
        return  this.isVisible;
    }

    public boolean isRed()
    {
        if (this.suit == Suit.H || this.suit == Suit.D)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Suit[] returnOppositeSuits()
    {
        if (this.isRed())
        {
            Suit[] suits={Suit.C, Suit.S};
            return  suits;
        }
        else
        {
            Suit[] suits={Suit.H, Suit.D};
            return  suits;
        }
        
    }


    public void setVisible()
    {
        this.isVisible = true;
        return;
    }

    public void setInvisible()
    {
        this.isVisible = false;
        return;
    }

    public String[] cardBody()
    {
        switch (this.suit)
        {
            
            case D:    return new String[] {"|"+RED+"   /\\  "+BLACK+"| ",
                                            "|"+RED+"  /  \\ "+BLACK+"| ",
                                            "|"+RED+"  \\  / "+BLACK+"| ",
                                            "|"+RED+"   \\/  "+BLACK+"| ",
                                            "--------- "};
            case C:    return new String[] {"|   0   | ",
                                            "|  0-0  | ",
                                            "|  / \\  | ",
                                            "|       | ",
                                            "--------- "};
            case H:    return new String[] {"|"+RED+"  /\\/\\ "+BLACK+"| ",
                                            "|"+RED+"  \\  / "+BLACK+"| ",
                                            "|"+RED+"   \\/  "+BLACK+"| ",
                                            "|       | ",
                                            "--------- "};
            default:   return new String[] {"|   /\\  | ",
                                            "|  (  ) | ",
                                            "|   /\\  | ",
                                            "|       | ",
                                            "--------- "};
        }
    }

    public String[] cardShort()
    {
        // String printValue;
        // switch (this.value)
        // {
        //     case ACE: printValue = "A";break;
        //     case TWO: printValue = "2";break;
        //     case THREE: printValue = "3";break;
        //     case FOUR: printValue = "4";break;
        //     case FIVE: printValue = "5";break;
        //     case SIX: printValue = "6";break;
        //     case SEVEN: printValue = "7";break;
        //     case EIGHT: printValue = "8";break;
        //     case NINE: printValue = "9";break;
        //     case TEN: printValue = "10";break;
        //     case JACK: printValue = "J";break;
        //     case QUEEN: printValue = "Q";break;
        //     default: printValue = "K";break;
        // }
        String color;
        if (this.isRed())
            color=RED;
        else
            color=BLACK;

        return new String[] {"--------- ", String.format("|%s%2s%s%s    | ", color, this.value.toString(), this.suit, BLACK)};
    }

    public static String[] concatStrings(String[] arr1, String[] arr2)
    {
        String[] result = new String[arr1.length+arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    public String[] cardPrintString(boolean top)
    {
        if (this.isVisible == true)
        {
            if (top)
            {
                return concatStrings(this.cardShort(), this.cardBody());
            }
            else
            {
                return this.cardShort();
            }
        }
        else
        { 
            if (top)
            {
                return new String[] {"--------- ", 
                                     "|XXXXXXX| ",
                                     "|XXXXXXX| ",
                                     "|XXXXXXX| ",
                                     "|XXXXXXX| ",
                                     "|XXXXXXX| ",
                                     "--------- "};
            }
            else
            {
                return new String[] {"--------- ", 
                                     "|XXXXXXX| ",};
            }
            
                
        }
    }

    public static String[] cardPrintEmptyString()
    {
        return new String[]{"--------- ", 
                            "|       | ",
                            "|       | ",
                            "|       | ",
                            "|       | ",
                            "|       | ",
                            "--------- "};
    }


    public Card.Suit getSuit()
    {
        return this.suit;
    }

    public Card.Value getValue()
    {
        return this.value;
    }
    
}
