package Solitaire;
/*
 * This program is the Card class
 * @version 1 2022-29-9
 * @author Yevhenii Mormul
 */

/*
 * A {@code Player} object represents a player in the game
 */
public class Player {
    
    //scores for different moves
    public final static int COLUMN_TO_SUITS = 20;
    public final static int COLUMN_TO_COLUMN = 5;
    public final static int DECK_TO_SUITS = 10;

    private int score;
    private int nMoves;
    private String name;

    /*
    * Default constructor for the class.
    * Initiates a player with 0 score and 0 moves and name "Player"
    */
    public Player()
    {
        this.score = 0;
        this.nMoves = 0;
        this.name = "Player";
    }

    /*
    * Constructor for the class. Initiates a player with 0 score and 0 moves
    * @param name -- (String) defines the name of the player
    */
    public Player(String name)
    {
        this.score = 0;
        this.nMoves = 0;
        if (name.length() > 0)
        {
            this.name = name;
        }
        else
        {
            this.name = "Player";
        }
    }

    /*
     * Method, which returns the score of the player
     * @return score
     */
    public int getScore()
    {
        return this.score;
    }

    /*
     * Method, which returns the score of the number of moves
     * @return nMoves
     */
    public int getNMoves()
    {
        return this.nMoves;
    }

    /*
     * Method, which returns the name of the player
     * @return score
     */
    public String getName()
    {
        return this.name;
    }

    /*
     * Method, which increments the number of moves by 1
     * @return void
     */
    public void addMove()
    {
        nMoves++;
    }

    /*
     * Method, which increments the score by the given increment
     * @return void
     */
    public void incrementScore(int increment)
    {
        score += increment;
    }

    public String toString()
    {
        return String.format("Player: %s     Number of moves: %d      Score: %d", this.getName(), this.getNMoves(), this.getScore());
    }
}
