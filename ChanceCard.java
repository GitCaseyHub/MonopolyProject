import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class ChanceCard {
    //St. Charles Place movement card does not allow for buy action (also potentially other movement cards don't function correctly)
    int index;
    Player player;
    String message;
    ArrayList<Player> otherPlayers;
    MonopolyBoard ref;
    public ChanceCard(String message, int index, Player player, ArrayList<Player> otherPlayers, MonopolyBoard ref){
        this.ref=ref;
        this.index=index;
        this.player=player;
        this.otherPlayers=otherPlayers;

        new UIManager().put("OptionPane.background", Color.ORANGE);
        new UIManager().put("Panel.background", Color.ORANGE);
    }

    public boolean generateChanceCard(String messageInput){
        if(index==0){
            //Advance to Go
            player.setPositionIndex(0);
            player.setTotalMoney(player.getTotalMoney()+200);
        }
        else if(index==1){
            //Advance to Illinois Avenue
            player.setPositionIndex(24);
            player.setTotalMoney((player.getPositionIndex()>24)?player.getTotalMoney()+200:player.getTotalMoney());
            SpecialOptionPane sop = new SpecialOptionPane(ref.banner, messageInput,"Chance",Color.ORANGE,1);
            return true;
        }

        else if(index==2){
            //Advance to St. Charles Place Avenue
            player.setPositionIndex(11);
            player.setTotalMoney((player.getPositionIndex()>11)?player.getTotalMoney()+200:player.getTotalMoney());
            SpecialOptionPane sop = new SpecialOptionPane(ref.banner, messageInput,"Chance",Color.ORANGE,1);
            return true;
        }
        else if(index==3){
            //Advance to Nearest Utility
            player.setPositionIndex((player.getPositionIndex()>12 && player.getPositionIndex()<28)?28:12);
            player.setTotalMoney((player.getPositionIndex()>12 && player.getPositionIndex()<28)?player.getTotalMoney():(player.getPositionIndex()<=39 && player.getPositionIndex()>28)?player.getTotalMoney()+200:player.getTotalMoney());
            SpecialOptionPane sop = new SpecialOptionPane(ref.banner, messageInput,"Chance",Color.ORANGE,1);
            return true;
        }
        else if(index==4 || index==5){
            //Advance to the Nearest Railroad
            String addedMessage = "";
            if(player.getPositionIndex() >35 && player.getPositionIndex() < 5){
                addedMessage = "The Reading Railroad";
                player.setPositionIndex(5);
                player.setTotalMoney(player.getTotalMoney()+200);
            }
            else if(player.getPositionIndex()>5 && player.getPositionIndex()<15){
                addedMessage = "Pennsylvania Railroad";
                player.setPositionIndex(15);
            }
            else if(player.getPositionIndex()>15 && player.getPositionIndex()<25){
                addedMessage = "B & O Railroad";
                player.setPositionIndex(25);
            }
            else if(player.getPositionIndex()>25 && player.getPositionIndex()<35){
                addedMessage = "The Short Line Railroad";
                player.setPositionIndex(35);
            }
            SpecialOptionPane sop = new SpecialOptionPane(ref.banner,"Advance to the nearest railroad, "+addedMessage,"Chance",Color.ORANGE,1);
            return true;
        }
        else if(index==6){
            //Bank pays you dividend of $50
            player.addMoney(50);
        }
        else if(index==7){
            //Get Out of Jail Free Card
            player.hasGetOutOfJailFreeCard=true;
        }
        else if(index==8){
            //Move back 3 spaces
            player.setPositionIndex(player.getPositionIndex()-3);
            SpecialOptionPane sop = new SpecialOptionPane(ref.banner, messageInput,"Chance",Color.ORANGE,1);
            return true;
        }
        else if(index==9){
            //Go to jail
            player.setPositionIndex(10);
            player.setInJail(true);
            player.setDoublesCount(0);
            player.setCurrentRollDoubles(false);
        }
        else if(index==10){
            //You are assessed general repairs; pay $25 per house you own, plus an additional $100 for each hotel
            ArrayList<Property> playerProperties = new ArrayList<Property>();
            int subtractedValue = 0;
            playerProperties= player.getProperties();
            for(int x=0; x<playerProperties.size(); x++)
                subtractedValue+=(playerProperties.get(x).numHouses==4 && playerProperties.get(x).numHotels==1)? 100:25*playerProperties.get(x).numHouses;
            player.subtractMoney(subtractedValue);
            if(ref.playingWithFreeParking)
                ref.freeParking+=subtractedValue;
        }
        else if(index==11){
            //You pay a poor tax; pay $50
            player.subtractMoney(50);
            if(ref.playingWithFreeParking)
                ref.freeParking+=50;
        }
        else if(index==12){
            //Advance to Reading Railroad
            player.setPositionIndex(5);
            player.setTotalMoney((player.getPositionIndex()>5)?player.getTotalMoney()+200:player.getTotalMoney());
            SpecialOptionPane sop = new SpecialOptionPane(ref.banner, messageInput,"Chance",Color.ORANGE,1);
            return true;
        }
        else if(index==13){
            //Go to Boardwalk
            player.setPositionIndex(39);
            SpecialOptionPane sop = new SpecialOptionPane(ref.banner, messageInput,"Chance",Color.ORANGE,1);
            return true;
        }
        else if(index==14){
            //You are a chairman of the board; pay each other player $50
            for(int x=0; x<otherPlayers.size(); x++){
                player.subtractMoney(50);
                otherPlayers.get(x).addMoney(50);
            }
        }
        else if(index==15){
            //Your building and loan matures. Collect $150
            player.addMoney(150);
        }
        else if(index==16){
            //You won a crossword competition. As a true cruciverbalist, you are awarded $100 lol
            player.addMoney(100);
        }
        else
            JOptionPane.showMessageDialog(ref.banner, "There are no chance cards left.","No Chance",3);

        SpecialOptionPane sop = new SpecialOptionPane(ref.banner,messageInput,"Chance",Color.ORANGE,1);
        return false;
    }
}