package Solitaire;
/*
 * This program is the Card class
 * @version 1 2022-29-9
 * @author Yevhenii Mormul
 */

import java.util.Arrays;
/*
 * A {@code Card} object represents a card
 */
public class Card {

    /*
    * A {@code Value} enum object represents a card value
    */
    public enum Value {ACE("A"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"), JACK("J"), QUEEN("Q"), KING("K");
                        private String name;
                        /*
                         * Constructor for the class.
                         * @param name the equivalent string of the value (i.e. value "TEN" is represented with "10")
                         */
                        private Value(String name)
                        {
                            this.name=name;
                        }

                        /*
                         * Returns the string representation of enum
                         * @return name
                         */
                        @Override
                        public String toString(){
                            return this.name;
                        }
                    };

    /*
    * A {@code Suit} enum object represents a card suit
    */
    public enum Suit {D, H, C, S};

    public static final String BLACK = "\033[0m";//black unicode color
	public static final String RED = "\033[0;31m";//red unicode color

    public static final int CARD_SIZE = 7;//dimention of the full card
    private Value value;
    private Suit suit;
    private boolean isVisible;


    /*
    * Default constructor for the class.
    * Initiates value=ACE of suit=DIAMONDS
    */
    public Card()
    {
        this.suit = Suit.D;
        this.value = Value.ACE;
        this.isVisible = false;
    }

    /*
    * Constructor for the class.
    * @param suit defines the suit of the card
    * @param value defines the value of the card
    */
    public Card(Suit suit, Value value)
    {
        this.suit = suit;
        this.value = value;
        this.isVisible = false;
    }

    /*
     * Override default to string method.
     * @return boolean value+suit
     */
    public String toString()
    {
        return value.toString()+suit.toString();
    }

    /*
     * Method, which returns true if card is visible, false otherwise
     * @return boolean isVisible
     */
    public boolean isVisible()
    {
        return  this.isVisible;
    }

    /*
     * Method, which returns true if suit of the card is red, false otherwise
     * @return true if Hearts or Diamonds, false otherwise
     */
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

    /*
     * Method, which returns unicode representation of suit color
     * @return Card.RED if Hearts or Diamonds, Card.BLACK otherwise
     */
    public String getColor()
    {
        if (this.isRed())
        {
            return Card.RED;
        }
        else
        {
            return Card.BLACK;
        }
    }


    /*
     * Method, which returns Suit[] of suits, whose color is opposite to the suit of the current card
     * @return {C, S} if card is red, {H, D} otherwise
     */
    public Suit[] returnOppositeSuits()
    {
        if (this.isRed())
        {
            return new Suit[] {Suit.C, Suit.S};
        }
        else
        {
            return new Suit[] {Suit.H, Suit.D};
        }   
    }

    /*
     * Method, which changes the status of the card to visible
     * @return void
     */
    public void setVisible()
    {
        this.isVisible = true;
        return;
    }

    /*
     * Method, which changes the status of the card to invisible
     * @return void
     */
    public void setInvisible()
    {
        this.isVisible = false;
        return;
    }

    /*
     * Method, which returns an array of strings representing the suit of the card
     * @return String[] body of the card
     */
    public String[] stringCardBody()
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

    /*
     * Method, which returns a 2 line representation of the card in the form of array:
     * ---------
     * |VS     |
     * where V - value
     * S - suit
     * @return String[] 2 top lines of the card
     */
    public String[] stringCardShort()
    {
        if (this.isVisible)
        {
            return new String[] {"--------- ", String.format("|%s%2s%s%s    | ", this.getColor(), this.value.toString(), this.suit, BLACK)};
        }
        else
        {
            return new String[] {"--------- ", 
                                 "|XXXXXXX| ",};
        }
            
    }

    /*
     * Method which concantenates 2 arrays of strings
     * @param base - first string array
     * @param appendable - second string array
     * @return - result of array concatentations
     */
    public static String[] concatStringArrays(String[] base, String[] appendable)
    {
        String[] result = new String[base.length+appendable.length];
        System.arraycopy(base, 0, result, 0, base.length);
        System.arraycopy(appendable, 0, result, base.length, appendable.length);
        return result;
    }

    /*
     * Method which returns the card in its array of strings representation
     * @return - String[] card representation
     */
    public String[] stringCardLong()
    {
        if (this.isVisible == true)
        {
            return concatStringArrays(this.stringCardShort(), this.stringCardBody());
        }
        else
        { 
            return new String[] {"--------- ", 
                                "|XXXXXXX| ",
                                "|XXXXXXX| ",
                                "|XXXXXXX| ",
                                "|XXXXXXX| ",
                                "|XXXXXXX| ",
                                "--------- "};
        }
    }

    /*
     * Method which returns the empty card in its array of strings representation
     * @return - String[] card representation
     */
    public static String[] stringCardEmpty()
    {
        return new String[]{"--------- ", 
                            "|       | ",
                            "|       | ",
                            "|       | ",
                            "|       | ",
                            "|       | ",
                            "--------- "};
    }

    /*
     * Method which returns the empty card with a particular suit in its array of strings representation
     * @return - String[] card representation
     */
    public String[] stringCardToken()
    {
        String color= this.getColor();
        return Card.concatStringArrays(new String[] {String.format("%s%s%s         ",color, this.suit, Card.BLACK), "--------- ", String.format("|%s%s%s      | ",color, this.suit, Card.BLACK)}, this.stringCardBody());
    }

    /*
     * Method which returns the suit of the card
     * @return - Suit
     */
    public Card.Suit getSuit()
    {
        return this.suit;
    }

    /*
     * Method which returns the Value of the card
     * @return - Value
     */
    public Card.Value getValue()
    {
        return this.value;
    }
    
}
