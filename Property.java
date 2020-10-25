import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Property extends JPanel implements MouseMotionListener{
    JTextArea northField = new JTextArea();
    JTextPane centerArea = new JTextPane();
    String name;
    Player owner;
    Color propertyColor;
    boolean owned, isMortgaged;
    JPanel colorMonitor = new JPanel(new GridLayout(2,2));
    JTextArea[] colors = new JTextArea[4];
    int pricePerHouse, standardRent, oneHouseRent, twoHouseRent, threeHouseRent, fourHouseRent, hotelRent, mortgageValue, costToBuy,numHouses,numHotels,manageIndex;
    Border compound = BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder());

    public int getManageIndex(){
        return manageIndex;
    }

    public void setManageIndex(int manageIndex){
        this.manageIndex = manageIndex;
    }

    public Property(Color propertyColor, String name, int pricePerHouse, int standardRent, int oneHouseRent, int twoHouseRent, int threeHouseRent, int fourHouseRent, int hotelRent, int mortgageValue, boolean owned,boolean isMortgaged, Player owner, int costToBuy, int numHouses, int numHotels, int manageIndex){
        this.name = name;
        this.manageIndex=manageIndex;
        this.numHouses=numHouses;
        this.costToBuy=costToBuy;
        this.owner=owner;
        this.owned=owned;
        this.pricePerHouse=pricePerHouse;
        this.standardRent=standardRent;
        this.oneHouseRent=oneHouseRent;
        this.twoHouseRent=twoHouseRent;
        this.threeHouseRent=threeHouseRent;
        this.fourHouseRent=fourHouseRent;
        this.hotelRent=hotelRent;
        this.mortgageValue = mortgageValue;
        this.propertyColor = propertyColor;
        this.setLayout(new BorderLayout());
        this.add(northField, BorderLayout.NORTH);
        northField.setBackground(propertyColor);
        northField.setEnabled(false);
        this.add(colorMonitor, BorderLayout.CENTER);
        for(int i=0; i<4; i++){
            colors[i] = new JTextArea();
            colors[i].setEnabled(false);
            colorMonitor.add(colors[i]);
            colors[i].setBorder(new TitledBorder(""));
            colors[i].addMouseMotionListener(this);
        }
    }

    public int getNumHouses(){
        return numHouses;
    }

    public void setNumHouses(int numHouses){
        this.numHouses = numHouses;
    }

    public int getNumHotels(){
        return numHotels;
    }

    public void setNumHotels(int numHotels){
        this.numHotels = numHotels;
    }

    public int getCostToBuy(){
        return costToBuy;
    }

    public void setCostToBuy(int costToBuy){
        this.costToBuy = costToBuy;
    }

    public int getPricePerHouse(){
        return pricePerHouse;
    }

    public Player getOwner(){
        return owner;
    }

    public void setOwner(Player owner){
        this.owner=owner;
    }

    public void insertText(String text){
        centerArea.setText(text);
    }

    public void setImage(String path){
        centerArea.insertIcon(new ImageIcon(path));
    }

    public void setPricePerHouse(int pricePerHouse){
        this.pricePerHouse = pricePerHouse;
    }

    public void setStandardRent(int standardRent){
        this.standardRent = standardRent;
    }

    public void setOneHouseRent(int oneHouseRent){
        this.oneHouseRent = oneHouseRent;
    }

    public void setTwoHouseRent(int twoHouseRent){
        this.twoHouseRent = twoHouseRent;
    }

    public void setThreeHouseRent(int threeHouseRent){
        this.threeHouseRent = threeHouseRent;
    }

    public void setFourHouseRent(int fourHouseRent){
        this.fourHouseRent = fourHouseRent;
    }

    public void setHotelRent(int hotelRent){
        this.hotelRent = hotelRent;
    }

    public void setMortgageValue(int mortgageValue){
        this.mortgageValue=mortgageValue;
    }

    public int getStandardRent(){
        return standardRent;
    }

    public int getOneHouseRent(){
        return oneHouseRent;
    }

    public int getTwoHouseRent(){
        return twoHouseRent;
    }

    public int getThreeHouseRent(){
        return threeHouseRent;
    }

    public int getFourHouseRent(){
        return fourHouseRent;
    }

    public boolean isMortgaged(){
        return isMortgaged;
    }

    public void setMortgageState(boolean mortgaged){
        this.isMortgaged=mortgaged;
    }

    public int getHotelRent(){
        return hotelRent;
    }

    public int getMortgageValue(){
        return mortgageValue;
    }

    public boolean isOwned(){
        return owned;
    }

    public void setOwned(boolean owned){
        this.owned=owned;
    }

    public void setBackgroundColor(Color backColor){
        northField.setBackground(backColor);
        this.propertyColor=backColor;
    }

    public void workAround(Color color){
        this.setBackground(color);
    }

    public Color getBackgroundColor(){
        return this.propertyColor;
    }

    public void setBorders(Border border){
        northField.setBorder(border);
        this.setBorder(border);
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void buyHouse(int numHouses){
        this.numHouses+=numHouses;
    }

    public void buyHotel(){
        this.numHotels+=1;
    }

    public int getRent(int numHouses, int numHotels){
        if(numHouses==0)
            return this.standardRent;
        else if(numHouses==1)
            return this.oneHouseRent;
        else if(numHouses==2)
            return this.twoHouseRent;
        else if(numHouses==3)
            return this.threeHouseRent;
        else if(numHouses==4)
            return this.fourHouseRent;
        else if(numHouses==4 && numHotels==1)
            return this.hotelRent;
        return 0;
    }

    public void mouseDragged(MouseEvent e){}
    public void mouseMoved(MouseEvent e){}
}