package edu.up.cs301.texasHoldem;

import edu.up.cs301.card.Card;

/**
 * Created by bruschwe18 on 3/16/2016.
 */
public class player {
    private int money;
    private int curBet;
    private int numBet;
    private Card[] hand = new Card[5];
    private boolean isActive;
    private boolean isEliminated;
    private Card card1 = null;
    private Card card2 = null;
    private Card card3 = null;
    private Card card4 = null;
    private Card card5 = null;
    private boolean isTie;
    private int handValue;
    private int playerNum;


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCurBet() {
        return curBet;
    }

    public void setCurBet(int curBet) {
        this.curBet = curBet;
    }

    public Card[] getHand() {
        return hand;
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }


    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsActive(){
        return this.getIsActive();
    }

    public Card getCard1() {
        return card1;
    }

    public void setCard1(Card newCard){
        this.card1 = newCard;
    }

    public Card getCard2() {
        return card2;
    }

    public void setCard2(Card newCard){
        this.card2 = newCard;
    }


    public Card getCard3() {
        return card3;
    }

    public void setCard3(Card newCard){
        this.card3 = newCard;
    }

    public Card getCard4() {
        return card4;
    }

    public void setCard4(Card newCard){
        this.card4 = newCard;
    }

    public Card getCard5() {
        return card5;
    }

    public void setCard5(Card newCard){
        this.card5 = newCard;
    }

    public void setIsTie(boolean bool) {
        this.isTie = bool;
    }

    public boolean getIsTie() {
        return this.isTie;
    }

    public int getHandValue() {
        return handValue;
    }

    public void setHandValue(int handValue) {
        this.handValue = handValue;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public int getNumBet() {
        return numBet;
    }

    public void setNumBet(int numBet) {
        this.numBet = numBet;
    }

    public void setHandInd(Card card1, Card card2, Card card3, Card card4, Card card5){
        this.hand[0] = card1;
        this.hand[1] = card2;
        this.hand[2] = card3;
        this.hand[3] = card4;
        this.hand[4] = card5;
    }

    public boolean getIsEliminated() {

        return this.isEliminated;
    }

    public void setIsEliminated(boolean isEliminated) {
        this.isEliminated = isEliminated;
    }



    public player(){
        money = 1000;
        curBet = 0;
        isActive = true;
        isEliminated = false;
        isTie = false;
        numBet = 0;
    }

}

