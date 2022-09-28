package Solitare;

// import Solitare.SuitPile;
import java.util.*;

public class SolitareGame {
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        Table table = new Table();
 
        String command;
        outerloop:
        while (true)
        {
            table.toString();
            System.out.println("\nPlease enter your command: ");
            command = in.nextLine();
            if (command.equals("D"))
            {
                table.deckGetNext(); 
            }
            else if (command.equals("Q"))
            {
                break;
            }
            else if (command.matches("[1234567DHCSP][1234567DHCS]"))
            {
                System.out.println("Valid command");
                table.moveCards(command.charAt(0), command.charAt(1));
            }
            else
            {
                System.out.println("ERROR: INVALID COMMAND - \""+command+"\"");
            }
                
            
        }
        
        
    }
}
