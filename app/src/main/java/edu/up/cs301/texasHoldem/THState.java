package edu.up.cs301.texasHoldem;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.infoMsg.GameState;

/**
 * Created by bruschwe18 on 3/16/2016.
 */
public class THState extends GameState{

    private int pot;
    private int minBet;
    private int curTurn;
    private int curRound;
    private Card[] cardMid;
    private int playerNum;
    private Deck deck;
    private edu.up.cs301.texasHoldem.player[] player;


    public THState(){
        pot = 0;
        minBet = 0;
        curTurn = 0;
        curRound = 0;
        cardMid = new Card[5];
        deck = new Deck();
    }

    public THState(int numPlayers){
        pot = 0;
        minBet = 0;
        curTurn = 0;
        curRound = 0;
        playerNum = numPlayers;
        cardMid = new Card[5];
        player = new player[playerNum];
        deck = new Deck();
    }


    /**
     * This method makes and returns a deep copy of the current gamestate.
     *
     *
     * @return  copied state
     */
    public THState(THState state) {

        //TODO put in the full copy constructor

        THState THCopy = new THState(state.getPlayerNum());

        THCopy.setPot(state.getPot());

        THCopy.setMinBet(state.getMinBet());

        THCopy.setCurTurn(state.getCurTurn());

        THCopy.setCurRound(state.getCurRound());

        THCopy.setPlayerNum(state.getPlayerNum());

        //copy cardMid array
        THCopy.cardMid = new Card[5];
        int i;
        for(i = 0; i < 5; i++){
            THCopy.setCardMid(THCopy.cardMid[i], state.getCardMid(i));
        }

        for(i=0 ; i < state.getPlayerNum() ; i++ ) {

            THCopy.player[i] = state.player[i];

        }

    }

    /*
     * This method changes the turn to the next player
     * This allows the next player to bet
     * this is called after a player has completed their turn
     */
    public void changeTurn() {
        this.setCurTurn(this.getCurTurn() + 1); //set the turn to the next player
        if(this.getCurTurn() == this.player.length) {  //if it was the last player's turn, go back to the first player
            this.setCurTurn(0);
        }
    }

    /*
     * This method allows the player to bet
     * takes the money they bet out of their "bank" and adds it to the pot
     * adds the money to their current bet variable and adds one to their betCount
     */
    public void bet(int amountToBet) {
        this.player[this.getCurTurn()].setMoney(this.player[this.getCurTurn()].getMoney() - amountToBet);  //subtract from their bank
        this.setPot(this.getPot() + amountToBet);  //add to the pot
        this.player[this.getCurTurn()].setCurBet(player[this.getCurTurn()].getCurBet() + amountToBet);
        this.player[this.getCurTurn()].setNumBet(this.player[this.getCurTurn()].getNumBet() + 1);
    }

    /*
     * this method is called when a player wins a pot
     * changes the amount of money the player has to their previous money + the amount that was in the pot
     */
    public void winPot(int winningIndex) {
        player[winningIndex].setMoney(player[winningIndex].getMoney() + this.getPot());
    }

    /*
     * This method shuffles the deck and deals the cards to the players and the middle
     */
    public void dealCards() {
        Deck newdeck = new Deck();  //creates a deck of 52 cards and shuffles it
        this.setDeck(newdeck);
        this.deck.add52();
        this.deck.shuffle();
        int amountOfPlayers = this.player.length;
        for(int i = 0; i < amountOfPlayers; i++) {  //deals a first card to each player
            this.player[i].setCard1(this.deck.removeTopCard());
        }
        for(int j = 0; j < amountOfPlayers; j++) {  //deals a second card to each player
            this.player[j].setCard2(this.deck.removeTopCard());
        }
        Card[] newMid = new Card[5];  //array of 5 cards for the middle
        for(int k = 0; k < 5; k++) {  //deals the 5 cards in the miiddle
            newMid[k] = this.deck.removeTopCard();
            this.setCardMid(this.getCardMid(k), newMid[k]);
        }

    }


    /**
     *  This is called when the min bet on the table is still zero and the player who's turn it is
     *  does not want to raise the bet.
     *
     *  This method basically just changes the turn for that special scenario
     *
     *
     */
    public void check() {

        // make sure that the min bet is still zero
        if( this.getMinBet() == 0){

            this.changeTurn();

        }

        //otherwise do nothing

    } /* End check() */


    /**
     *  This method is called when a player not longer wants to bet any higher.
     *  It will set the player who's current turn it is 'isActive' variable to false,
     *  and change the turn.
     *
     */
    public void fold(){

        this.player[this.getCurTurn()].setIsActive(false);
        this.changeTurn();

    } /* End fold() */


    /**
     *  This method bets bet the current minimum bet that is on the table.
     *  It will add the min bet to the pot, and subtract the min bet from the player who's turn
     *  it is. Also keeps track of how many times a player has bet in a round.
     */
    public void callBet(){

        //checks that the player has enough money
        if(this.player[this.getCurTurn()].getMoney() >= this.getMinBet() ){


            this.player[this.getCurTurn()].setMoney(this.player[this.getCurTurn()].getMoney() - this.getMinBet());
            this.setPot(this.getPot() + this.getMinBet());
            this.player[this.getCurTurn()].setNumBet(this.player[this.getCurTurn()].getNumBet() + 1);

        }

    }



    public void changeCurRound(){

    }


    //takes variable number of players and returns integer that refers to the index of the player who wins the hand
    public int handWinner(){
        int i = 0;
        //iterates through all players
        for(i=0; i<this.player.length; i++){
            //checks to make sure player is still active in the round
            if(this.player[i].getIsActive()){
                //evaluates quality of hand for all possible card combos, sets users hand and handvalue to whatever it catches as the highest
                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(2)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(2), this.getCardMid(3)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(2));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(3)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(3)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(3));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(4)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(4)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(4));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(2), this.getCardMid(3)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(2), this.getCardMid(3)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(2), this.getCardMid(3));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(2), this.getCardMid(4)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(2), this.getCardMid(4)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(2), this.getCardMid(4));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(3), this.getCardMid(4)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(3), this.getCardMid(4)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(3), this.getCardMid(4));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(2), this.getCardMid(3)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(2), this.getCardMid(3)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(2), this.getCardMid(3));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(2), this.getCardMid(4)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(2), this.getCardMid(4)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(2), this.getCardMid(4));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(3), this.getCardMid(4)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(3), this.getCardMid(4)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(3), this.getCardMid(4));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(2), this.getCardMid(3), this.getCardMid(4)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(2), this.getCardMid(3), this.getCardMid(4)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(2), this.getCardMid(3), this.getCardMid(4));
                }
            } else{ //sets inactive player hand value below 0
                this.player[i].setHandValue(-1);
            }
        }
        int valMax = -1;
        int valMaxInd = 0;
        int tie = 0;

        //finds index of highest hand value player
        for(i=0; i<this.player.length; i++){
            if(this.player[i].getHandValue() > valMax){
                valMax = this.player[i].getHandValue();
                valMaxInd = i;
            }

        }

        return valMaxInd;
    }

    //takes 5 cards, returns int corresponding to hand type(straight flush, 4 of a kind, etc.), the higher the better
    public int handEvaluate(Card card1, Card card2, Card card3, Card card4, Card card5){
        int pairCount = 0;

        //checks to see how many value matches between a card and any card later, iterates pairCount if it catches a match
        if(card1.getRank().getRankNum()== card2.getRank().getRankNum()){
            pairCount++;
        }
        if(card1.getRank().getRankNum()== card3.getRank().getRankNum()){
            pairCount++;
        }
        if(card1.getRank().getRankNum()== card4.getRank().getRankNum()){
            pairCount++;
        }
        if(card1.getRank().getRankNum()== card5.getRank().getRankNum()){
            pairCount++;
        }
        if(card1.getRank().getRankNum()== card5.getRank().getRankNum()){
            pairCount++;
        }
        if(card2.getRank().getRankNum()== card3.getRank().getRankNum()){
            pairCount++;
        }
        if(card2.getRank().getRankNum()== card4.getRank().getRankNum()){
            pairCount++;
        }
        if(card2.getRank().getRankNum()== card5.getRank().getRankNum()){
            pairCount++;
        }
        if(card3.getRank().getRankNum()== card4.getRank().getRankNum()){
            pairCount++;
        }
        if(card3.getRank().getRankNum()== card5.getRank().getRankNum()){
            pairCount++;
        }
        if(card4.getRank().getRankNum()== card5.getRank().getRankNum()){
            pairCount++;
        }

        //1 match means 1 pair
        if(pairCount == 1){
            return 1;
        }
        //2 matches means 2 pairs
        if(pairCount == 2){
            return 2;
        }
        //3 matches means 3 of a kind
        if(pairCount == 3){
            return 3;
        }
        //4 matches means full house
        if(pairCount == 4){
            return 6;
        }
        //6 matches means 4 of a kind
        if(pairCount == 6){
            return 7;
        }

        boolean isStraight = false;
        boolean isFlush = false;
        int cardMin = 20;

        //finds minimum card number
        if(card1.getRank().getRankNum() < cardMin){
            cardMin = card1.getRank().getRankNum();
        }
        if(card2.getRank().getRankNum() < cardMin){
            cardMin = card2.getRank().getRankNum();
        }
        if(card3.getRank().getRankNum() < cardMin){
            cardMin = card3.getRank().getRankNum();
        }
        if(card4.getRank().getRankNum() < cardMin){
            cardMin = card4.getRank().getRankNum();
        }
        if(card5.getRank().getRankNum() < cardMin){
            cardMin = card5.getRank().getRankNum();
        }

        //checks all suits are the same
        if(card1.getSuit().getSuitNum() == card2.getSuit().getSuitNum() && card2.getSuit().getSuitNum() == card3.getSuit().getSuitNum() && card3.getSuit().getSuitNum() == card4.getSuit().getSuitNum() && card4.getSuit().getSuitNum() == card5.getSuit().getSuitNum()){
            isFlush = true;
        }


        //checks if there is 1 higher than min value of hand, then 2, then 3, then 4
        if((card1.getRank().getRankNum()== cardMin + 1 || card2.getRank().getRankNum()== cardMin + 1 || card3.getRank().getRankNum()== cardMin + 1 || card4.getRank().getRankNum()== cardMin + 1 || card5.getRank().getRankNum()== cardMin + 1) && (card1.getRank().getRankNum()== cardMin + 2 || card2.getRank().getRankNum()== cardMin + 2 || card3.getRank().getRankNum()== cardMin + 2 || card4.getRank().getRankNum()== cardMin + 2 || card5.getRank().getRankNum()== cardMin + 2) && (card1.getRank().getRankNum()== cardMin + 3 || card2.getRank().getRankNum()== cardMin + 3 || card3.getRank().getRankNum()== cardMin + 3 || card4.getRank().getRankNum()== cardMin + 3 || card5.getRank().getRankNum()== cardMin + 3) && (card1.getRank().getRankNum()== cardMin + 4 || card2.getRank().getRankNum()== cardMin + 4 || card3.getRank().getRankNum()== cardMin + 4 || card4.getRank().getRankNum()== cardMin + 4 || card5.getRank().getRankNum()== cardMin + 4)){
            isStraight = true;
        }
        //straight flush
        if(isStraight && isFlush){
            return 8;
        }
        //flush
        if(isFlush){
            return 5;
        }
        //straight
        if(isStraight){
            return 4;
        }
        //no matches, therefore high card
        return 0;


    }



    public int getPot() {
        return pot;
    }

    public int getMinBet() {
        return minBet;
    }

    public int getCurTurn() {
        return curTurn;
    }

    public int getCurRound() {
        return curRound;
    }

    public Card getCardMid(int index) {
        return this.cardMid[index];
    }

    public edu.up.cs301.texasHoldem.player getPlayer(int index) {
        return player[index];
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public void setMinBet(int minBet) {
        this.minBet = minBet;
    }

    public void setCurTurn(int curTurn) {
        this.curTurn = curTurn;
    }

    public void setCurRound(int curRound) {
        this.curRound = curRound;
    }

    public void setCardMid(Card cardDest, Card cardSource) {
        cardDest = cardSource;
    }

    public void setPlayer(player player, int index) {
        this.player[index] = player;
    }


    public void setDeck(Deck deck) {
        this.deck = deck;
    }



    public Deck getDeck() {
        return deck;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

}
