package GameFlow;

import java.util.*;
import DevelopmentCards.*;
import GameBoard.*;
import Player.Player;

/**
 * GameFlow.Game class combines logic of the game board and players to enable users to play the game.
 * @author Yusuf Nevzat Sengun
 * @version 04.11.2019
 *
 * GameFlow.Game class combines logic of the game board and players to enable users to play the game.
 * @author Cevat Aykan Sevinc
 * @version 14.10.2019
 * Class is created to later combine all the logic.
 * Log 16.10.2019 (Talha)
 * -----------------------
 * Created class attributes, constructore and functions.
 * Added function templates.
 * Implemented some functions.
 * -----------------------
 * @version 07.12.2019
 *  This is a milestone in our project!
 *  Software architecture Yusuf has designed a system to rule all designs!
 *  Game design is changed into singleton and game now controls the data access by managers.
 */
public class Game
{
    // Game Singleton
    private static Game game = null;

    private Queue<Integer> must;

    // Constants
    private final int TOTAL_DEV_CARDS = 25;
    public static final int DICE_SEVEN = 7;

    // Attributes
    private GameBoard board;

    // Game related data
    private int turnNumber;
    private int gameStatus;
    private int currentDice;

    // Player Related data
    private ArrayList<Player> players;
    private int playerCount;
    private int longestRoad;
    private int largestArmy;
    private Player longestRoadPlayer;
    private Player largestArmyPlayer;

    // Dev card related data
    private Stack<Card> devCards;

    // Constructors
    private Game( ArrayList<Player> players )
    {
        longestRoadPlayer = null;
        longestRoad = 4; // minumum requierement to get this card
        largestArmy = 2; // minumum req to earn the army title
        largestArmyPlayer = null;
        gameStatus = 0; // initial phase ( setup mode )
        this.players = players;
        playerCount = players.size();
        board = new GameBoard();
        turnNumber = 0;
        devCards = new Stack<>();

        for( int i = 0; i < TOTAL_DEV_CARDS; i++)
        {
            Card card;
            if ( i < 14)
                card = new Knight();
            else if ( i < 16)
                card = new Monopoly();
            else if ( i < 18)
                card = new RoadBuilding();
            else if ( i < 20)
                card = new YearOfPlenty();
            else
                card = new VictoryPoint();
            devCards.push(card);
        }

        this.must = new LinkedList<>();
    }

    /**
     *
     * @param players
     * @return
     */
    public static Game getInstance( ArrayList<Player> players )
    {
        if ( game == null )
        {
            game = new Game( players);
            game.configureGame();
            return game;
        }
        return game;
    }

    /**
     *
     * @return
     */
    public static Game getInstance()
    {
        if ( game != null )
        {
            return game;
        }
        return null;
    }

    // Functions
    /**
     * Initializes the game via initializing gameboard, players, robber and the turn.
     */
    private void configureGame()
    {
        // The order must not be changed!
        board.configurate();

        Collections.shuffle(players);
        Collections.shuffle(devCards);

        this.must.add( 1); // settlement
        this.must.add( 0); // road
        this.must.add( 6); // end turn
    }



    //*****************************************************************************************************************
    //
    // Data access
    //
    //*****************************************************************************************************************

    /**
     * Returns the development card stack data for processing.
     * @return Stack<Card> - development cards
     */
    protected Stack<Card> getCardStack()
    {
        return devCards;
    }

    /**
     * Returns the current turn number players are playing.
     * @return the turn number.
     */
    protected int getTurn()
    {
        return turnNumber;
    }

    protected void setTurnNumber( int turnNumber){
        this.turnNumber = turnNumber;
    }

    /**
     * Gets the current status of the game, 0 - Initial phase, 1 - Game phase
     * @return int - the current game status
     */
    protected int getGameStatus()
    {
        return gameStatus;
    }

    protected void setGameStatus( int gameStatus){
        this.gameStatus = gameStatus;
    }

    protected Queue<Integer> getMust(){
        return this.must;
    }

    protected int getPlayerCount(){
        return playerCount;
    }

    protected int getCurrentDice(){
        return currentDice;
    }

    protected  void setCurrentDice( int currentDice) {
        this.currentDice = currentDice;
    }

    protected Player getLongestRoadPlayer() {
        return longestRoadPlayer;
    }

    protected void setLongestRoadPlayer( Player longestRoadPlayer){
        this.longestRoad = longestRoad;
    }

    protected Player getLargestArmyPlayer() {
        return largestArmyPlayer;
    }

    protected void setLargestArmyPlayer( Player largestArmyPlayer){
        this.largestArmy = largestArmy;
    }

    protected int getLongestRoad(){
        return longestRoad;
    }

    protected void setLongestRoad( int longestRoad){
        this.longestRoad = longestRoad;
    }

    protected int getLargestArmy(){
        return largestArmy;
    }

    protected void setLargestArmy( int largestArmy){
        this.largestArmy = largestArmy;
    }

    /**
     * Retuns GameBoard for board related data flow.
     * @return GameBoard board.
     */
    protected GameBoard getGameBoard()
    {
        return board;
    }

    /**
     * Return the player arraylist for manager to manipulate & use
     * @return the player array in the game.
     */
    protected ArrayList<Player> getPlayers()
    {
        return this.players;
    }



    //*****************************************************************************************************************
    //
    // Data access from UI to draw gameboard and from managers
    //
    //*****************************************************************************************************************

    /**
     * Returns the tile board[][] 2D array for UI processing.
     * @return Tile[][] board.
     */
    public Tile[][] getTileBoard()
    {
        return board.getBoard();
    }

    /**
     * Returns a specific player given in the player index.
     * @param playerIndex is the specific index of the player.
     * @return the player in the given index.
     */
    public Player getPlayer(int playerIndex)
    {
        return players.get( playerIndex);
    }
}
