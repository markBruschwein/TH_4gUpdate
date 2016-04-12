package edu.up.cs301.texasHoldem;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by bruschwe18 on 4/11/2016.
 */
public class Call extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public Call(GamePlayer player) {
        super(player);
    }

    public boolean isCall(){
        return true;
    }

}
