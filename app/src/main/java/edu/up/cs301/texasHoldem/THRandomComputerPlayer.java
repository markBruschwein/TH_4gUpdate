package edu.up.cs301.texasHoldem;

import android.widget.TextView;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.GameState;

/**
 * This class will make moves for the random computer players throughout the game
 *
 * @author Mark Bruschwein
 * @author Luke McManamon
 * @author Hugh McGlynn
 *
 * @version 3/30/2016.
 */
public class THRandomComputerPlayer extends GameComputerPlayer{
    THState state; // Current state that the GUI will use to update
    GameMainActivity activity;  // Will be used to know the current activity
    TextView comPlayerMoney;
    player playerInfo;
    int compIndex;

    /**
     * The THRandomComputer Player constructor that creates all the instance variables
     */
    public THRandomComputerPlayer(String playerName){
        super(playerName);
        playerInfo = new player();
    }


    /**
     * This method will update the gui display based on canges in the gaemState
     */
    public void updateGUI()
    {
        //TODO unsure on this whole shinanigan
        comPlayerMoney.setText(state.getPlayer(compIndex).getMoney());
    }


    /**
     * This method will recieve new info about the game being updated
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if(!(info instanceof THState)){
            return; //Not an update of the gamestate so we dont care
        }

        state = (THState)info;

        Random rand = new Random();

        int curMoney = this.playerInfo.getMoney(); // will use this for determining how much to bet

        int randNum = rand.nextInt(5) + 1;

        int minBet = this.playerInfo.getCurBet();

        if(randNum == 1){
            game.sendAction(new Fold(this));
        }
        else if(randNum == 2 || randNum == 3){
            if(minBet == 0){
                game.sendAction(new Check(this));
            }
            else {
                game.sendAction(new Call(this));
            }
        }
        else if(randNum == 4|| randNum == 5){
            int toBet = rand.nextInt(curMoney);
            game.sendAction(new Bet(this, toBet));
        }

    }
}