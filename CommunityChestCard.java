import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CommunityChestCard {
    int index;
    Player player;
    String message;
    ArrayList<Player> otherPlayers;
    MonopolyBoard ref;
    Color lightBlue = new Color(51,153,255);

    public CommunityChestCard(String message, int index, Player player, ArrayList<Player> otherPlayers, MonopolyBoard ref){
        this.ref=ref;
        this.index=index;
        this.message=message;
        this.player=player;
        this.otherPlayers=otherPlayers;
    }
    public String getMessage(){
        return this.message;
    }
    public void generateCommunityCard(String messageInput){
        if(index==0){
            //Advance to Go
            player.addMoney(200);
            player.setPositionIndex(0);
        }
        else if(index==1){
            //Bank Error; Collect $200
            player.addMoney(200);
        }
        else if(index==2){
            //Doctor's Fees; pay $50
            player.subtractMoney(50);
            if(ref.playingWithFreeParking)
                ref.freeParking+=50;
        }
        else if(index==3){
            //Sale of Stock; Collect $50
            player.addMoney(50);
        }
        else if(index==4){
            //Get Out of Jail Free Card
            player.setHasGetOutOfJailFreeCard(true);
        }
        else if(index==5){
            //Get to Jail
            player.setInJail(true);
            player.setPositionIndex(10);
            player.setDoublesCount(0);
            player.setCurrentRollDoubles(false);
        }
        else if(index==6){
            //Grand opera opening; Collect $50 from each other player.
            for(int x=0; x<otherPlayers.size(); x++){
                player.addMoney(50);
                otherPlayers.get(x).subtractMoney(50);
            }
        }
        else if(index==7){
            //Christmas Fund Matures; collect $100
            player.addMoney(10);
        }
        else if(index==8){
            //Income Tax Refund; Collect $250
            player.addMoney(250);
        }
        else if(index==9){
            //It's your birthday, but your friends are cheap. Collect $10 from each other player.
            for(int x=0; x<otherPlayers.size(); x++){
                player.addMoney(10);
                otherPlayers.get(x).subtractMoney(10);
            }
        }
        else if(index==10){
            //Life Insurance Matures; collect $100
            player.addMoney(100);
        }
        else if(index==11){
            //Hospital Bills; pay $50
            player.subtractMoney(50);
            if(ref.playingWithFreeParking)
                ref.freeParking+=50;
        }
        else if(index==12){
            //Provide consultation to a divorce lawyer; collect $500
            player.addMoney(500);
        }
        else if(index==13){
            //You are assessed street repairs; pay $40 per house you own, plus an additional $115 for each hotel
            ArrayList<Property> playerProperties = new ArrayList<Property>();
            int subtractedValue = 0;
            playerProperties= player.getProperties();
            for(int x=0; x<playerProperties.size(); x++)
                subtractedValue+=(playerProperties.get(x).numHouses==4 && playerProperties.get(x).numHotels==1)? 115:40*playerProperties.get(x).numHouses;

            player.subtractMoney(subtractedValue);
            if(ref.playingWithFreeParking)
                ref.freeParking+=subtractedValue;
        }
        else if(index==14){
            //You won second prize in a beauty contest. Try harder, loser. Here's a pity $10.
            player.addMoney(10);
        }
        else if(index==15){
            //Your least favorite relative kicked the bucket and you inherit $100 from them.
            player.addMoney(100);
        }
        else{
            JOptionPane.showMessageDialog(ref.banner,"There are no more community chest cards.","No cards left",3);
        }
        SpecialOptionPane sop = new SpecialOptionPane(ref.banner,messageInput,"Community Chest",lightBlue,1);
    }
}