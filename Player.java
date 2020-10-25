import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Player {
    ArrayList<Property> ownedProperties = new ArrayList<Property>();
    boolean inJail, hasCurrentTurn, hasGetOutOfJailFreeCard, isBankrupt, currentRollDoubles;

    int positionIndex;
    int totalMoney;
    int playerNumber;
    int jailRolls;

    public boolean isCurrentRollDoubles() {
        return currentRollDoubles;
    }

    public void setCurrentRollDoubles(boolean currentRollDoubles) {
        this.currentRollDoubles = currentRollDoubles;
    }

    public int getDoublesCount() {
        return doublesCount;
    }

    public void setDoublesCount(int doublesCount) {
        this.doublesCount = doublesCount;
    }

    int doublesCount;
    String piece, name;
    Color identifier;

    public int getJailRolls() {
        return jailRolls;
    }

    public void setJailRolls(int jailRolls) {
        this.jailRolls = jailRolls;
    }

    public void addJailRolls(){
        this.jailRolls+=1;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public Player(String name, boolean inJail, String piece, boolean hasCurrentTurn, boolean hasGetOutOfJailFreeCard, int positionIndex, int totalMoney, ArrayList<Property> ownedProperties, Color identifier, int playerNumber, int jailRolls, boolean isBankrupt, int doublesCount, boolean currentRollDoubles){
        this.ownedProperties=ownedProperties;
        this.name=name;
        this.doublesCount=doublesCount;
        this.currentRollDoubles=currentRollDoubles;
        this.isBankrupt=isBankrupt;
        this.jailRolls=jailRolls;
        this.playerNumber=playerNumber;
        this.inJail=inJail;
        this.positionIndex=positionIndex;
        this.totalMoney=totalMoney;
        this.hasCurrentTurn=hasCurrentTurn;
        this.hasGetOutOfJailFreeCard = hasGetOutOfJailFreeCard;
        this.piece=piece;
        this.identifier=identifier;
    }

    public Player(){}

    public String getName(){
        return name;
    }

    public int getPlayerNumber(){
        return playerNumber;
    }

    public void setPlayerNumber(int num){
        this.playerNumber=num;
    }

    public Color getIdentifier(){
        return identifier;
    }

    public void setIdentifier(Color identifier){
        this.identifier = identifier;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }
    public boolean isHasGetOutOfJailFreeCard() {
        return hasGetOutOfJailFreeCard;
    }

    public void setHasGetOutOfJailFreeCard(boolean hasGetOutOfJailFreeCard) {
        this.hasGetOutOfJailFreeCard = hasGetOutOfJailFreeCard;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public void setHasCurrentTurn(boolean hasCurrentTurn) {
        this.hasCurrentTurn = hasCurrentTurn;
    }

    public void setPositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
    }

    public boolean isInJail() {
        return inJail;
    }

    public boolean isHasCurrentTurn() {
        return hasCurrentTurn;
    }

    public int getPositionIndex() {
        return positionIndex;
    }

    public ArrayList<Property> getProperties(){
        return ownedProperties;
    }

    public void addProperty(int index,Property newProperty){
        this.ownedProperties.add(index, newProperty);
    }
    public void addProperty(Property newProperty){
        this.ownedProperties.add(newProperty);
    }

    public void removeProperty(Property removedProperty){
        this.ownedProperties.remove(removedProperty);
    }

    public void addMoney(int addedValue){
        this.setTotalMoney(this.getTotalMoney()+addedValue);
    }
    public void subtractMoney(int subtractedValue){
        this.setTotalMoney(this.getTotalMoney()-subtractedValue);
    }

    public boolean isBankrupt(Player player){
        int aggregate = 0;
        for(int x=0; x<ownedProperties.size(); x++)
            aggregate+=ownedProperties.get(x).getMortgageValue();

        if(aggregate+player.getTotalMoney()>0)
            isBankrupt=false;

        else
            isBankrupt=true;

        return isBankrupt;
    }

    public int returnTotalPropertyWorth(){
        int aggregate = 0;
        for(int x=0; x<ownedProperties.size(); x++)
            aggregate+=ownedProperties.get(x).getMortgageValue();

        return aggregate;
    }

    public JComboBox<String> createPropertyBox(){
        JComboBox<String> box = new JComboBox<String>();
        box.addItem(this.getName()+"'s Properties");
        for(int x=0; x<ownedProperties.size(); x++)
            box.addItem(ownedProperties.get(x).getName());

        return box;
    }
}