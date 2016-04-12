package edu.up.cs301.texasHoldem;

import android.widget.TextView;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.GameState;

/**
 * This class will make moves for the smart computer players throughout the game
 *
 * @author Mark Bruschwein
 * @author Luke McManamon
 * @author Hugh McGlynn
 *
 * @version 3/30/2016.
 */

public class THSmartComputerPlayer extends THRandomComputerPlayer {

    THState state; // Current state that the GUI will use to update
    GameMainActivity activity;  // Will be used to know the current activity
    TextView comPlayerMoney;
    player playerInfo;


    /**
     * The THRandomComputer Player constructor that creates all the instance variables
     */
    public THSmartComputerPlayer(String playerName){
        super(playerName);
    }

    /**
     * This method will recieve new info about the game being updated
     */
    public void recieveInfo(GameInfo gameInfo){

    }

    /**
     * this method will take into account the CPU's moves and update the GUi accordingly
     */
    public void makeMove(GameState state){

    }

    /**
     * This method will update the gui display based on canges in the gaemState
     */
    public void updateGUI()
    {

    }
}
