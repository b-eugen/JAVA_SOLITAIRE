package Solitaire;

/*
 * This program is the SolitaireGame class
 * @version 1 2022-29-9
 * @author Yevhenii Mormul
 */

import java.util.*;
/*
 * A {@code SolitaireGame} object represents a solitaire game session
 */
public class SolitaireGame {
    /*
     * Method main executes the game of solitare
     * @param args unused input, void by default
     */
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        Table table = new Table();//initialise Solitaire table
 
        String command;

        //loop which asks for user input and parses it into action
        outerloop:
        while (true)
        {
            table.printOut();//display the current state of the game
            System.out.println("\nPlease enter your command: ");
            command = in.nextLine();
            if (command.equals("D") || command.equals("d")) //user wants to draw a card from the deck
            {
                table.getNextCardFromDeck();
            }
            else if (command.equals("Q") || command.equals("q")) //user quits the game
            {
                break;
            }
            else if (command.matches("[1234567DHCSPdhcsp][1234567DHCSdhcs]")) // user wants to move cards
            {
                table.moveCardsFromInput(command.charAt(0), command.charAt(1));
            }
            else //invalid command
            {
                System.out.println(Card.RED+"ERROR: INVALID COMMAND - \""+command+"\""+Card.BLACK);
            }

            if (table.isVictory())//check if the game is over
            {
                table.printOut();
                break;
            }
        }
        System.out.println("GAME OVER");//tell the user that the game is over
        
        
    }
}
