package edu.up.cs301.texasHoldem;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * This class will update the Human Player GUI and allow the player to make moves in gameplay
 *
 * @author Mark Bruschwein
 * @author Luke McManamon
 * @author Hugh McGlynn
 *
 * @version 3/30/2016.
 */
public class THHumanPlayer extends GameHumanPlayer{



    THState state; // Current state that the GUI will use to update
    GameMainActivity activity;  // Will be used to know the current activity
    TextView playerMoney;
    player playerInfo;
    ///////////////////////////////////////////////////////////////////////////
    //textviews for everyone's current money
    protected TextView CPU1Money =  null;
    protected TextView CPU2Money = null;
    protected TextView CPU3Money = null;
    protected TextView userMoney = null;

    //instance variables for custom betting
    protected SeekBar betSeekBar = null;
    protected TextView betTextView = null;
    protected Button customBetButton;

    protected Button callBetButton;
    protected Button checkButton;
    protected Button newGameButton;
    protected Button foldButton;



    /**
     * The THHuman Player constructor that creates all the instance variables
     * @param name the player's name
     *
     */
    public THHumanPlayer(String name){
        super(name);
        playerInfo = new player();
    }

    /**
     * This method will recieve new info about the game being updated
     */
    public void receiveInfo(GameInfo gameInfo){
        if(gameInfo instanceof IllegalMoveInfo || gameInfo instanceof NotYourTurnInfo){
            //do nothing
        }
        else if(gameInfo instanceof THState){
            return;
        }
        else{
            this.state = (THState)gameInfo;
            Log.i("human", "receiving");


        }
        //updateDisplay();
    }

    /**
     * this method will take into account the current activity and update the GUi accordingly
     */
    public void setAsGui(GameMainActivity mainActivity){


        activity = mainActivity;
        activity.setContentView(R.layout.th_human_player);
        Card.initImages(activity);
        if (state != null) {
            receiveInfo(state);
        }


        betSeekBar = (SeekBar)activity.findViewById(R.id.betSeekBar);
        betTextView = (TextView)activity.findViewById(R.id.betTextView);
        customBetButton = (Button)activity.findViewById(R.id.customBetButton);
        callBetButton = (Button)activity.findViewById(R.id.callBetButton);
        checkButton = (Button)activity.findViewById(R.id.checkButton);
        newGameButton = (Button)activity.findViewById(R.id.newGameButton);
        foldButton = (Button)activity.findViewById(R.id.foldButton);

        //everyone's money
        CPU1Money = (TextView)activity.findViewById(R.id.CPU1Money);
        CPU2Money = (TextView)activity.findViewById(R.id.CPU2Money);
        CPU3Money = (TextView)activity.findViewById(R.id.CPU3Money);
        userMoney = (TextView)activity.findViewById(R.id.userMoney);


        //setting listeners
        betSeekBar.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);
        callBetButton.setOnClickListener((View.OnClickListener) this);
        checkButton.setOnClickListener((View.OnClickListener) this);
        foldButton.setOnClickListener((View.OnClickListener) this);
        newGameButton.setOnClickListener((View.OnClickListener) this);
        customBetButton.setOnClickListener((View.OnClickListener) this);

    }

    /**
     * This method will update the gui display based on changes in the gameState
     */
    public void updateDisplay() {

    }

    /**
     * This method will handle the event that the buttons on the Gui are clicked
     */
    public void onClick(View button) {
        if(button.getId() == R.id.customBetButton){
            //make a bet
        }
        else if(button.getId() == R.id.callBetButton){
            //call the bet
        }
        else if(button.getId() == R.id.checkButton){
            //check
        }
        else if(button.getId() == R.id.foldButton){
            //fold
        }
        else if(button.getId() == R.id.newGameButton){
            //start a new game
            //or open up menu asking if sure
        }




    }

    //@Override
    public void onProgressChanged(SeekBar betSeekBar, int progress, boolean fromUser){
        progress = betSeekBar.getProgress();
        betTextView.setText("" + progress);
    }

    public View getTopView(){
        return activity.findViewById(R.id.topGUILayout);
    }

    public TextView getPlayerMoney() {
        return playerMoney;
    }

    public void setPlayerMoney(TextView playerMoney) {
        this.playerMoney = playerMoney;
    }

    public GameMainActivity getActivity() {
        return activity;
    }

    public void setActivity(GameMainActivity activity) {
        this.activity = activity;
    }

    public THState getState() {
        return state;
    }

    public void setState(THState state) {
        this.state = state;
    }
}
