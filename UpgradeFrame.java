import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UpgradeFrame extends JFrame implements ActionListener {
    Color darkPurple = new Color(123, 11, 153);
    Color color = new Color(100, 100, 100);
    Border compound = BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder());

    JLabel nameLabel = new JLabel("", SwingConstants.CENTER);
    JLabel moneyLabel = new JLabel("", SwingConstants.CENTER);
    JCheckBox gOOJBox = new JCheckBox("Get Out Of Jail Free Card");
    JTextField offeredMoney = new JTextField("", SwingConstants.CENTER);
    JLabel[] colorLabels = new JLabel[8];
    Color[] colorBacks = {darkPurple, Color.LIGHT_GRAY,Color.magenta, Color.orange, Color.red, Color.yellow, Color.green, Color.blue};
    String[] colors = {"Dark Purple Group", "Light Gray Group", "Magenta Group", "Orange Group", "Red Group", "Yellow Group", "Green Group", "Blue Group"};
    String[] propNames = {"Mediterranean Avenue", "Baltic Avenue", "Oriental Avenue", "Vermont Avenue", "Connecticut Avenue", "St. Charles Place", "States Avenue", "Virginia Avenue", "St. James Place", "Tennessee Avenue", "New York Avenue", "Kentucky Avenue", "Indiana Avenue", "Illinois Avenue", "Atlantic Avenue", "Ventnor Avenue", "Marvin Gardens", "Pacific Avenue", "North Carolina Avenue", "Pennsylvania Avenue", "Park Place", "Boardwalk"};
    JButton[] properties = new JButton[propNames.length];
    JPanel wholePanel = new JPanel(new BorderLayout());
    JPanel northPanel = new JPanel(new GridLayout(1, 2));
    JPanel propsAndTitles = new JPanel(new GridLayout(4, 2));
    JPanel[] condenseThreeColors = new JPanel[6];
    JPanel[] condenseTwoColors = new JPanel[3];
    ArrayList<Property> ownedProperties = new ArrayList<Property>();
    MonopolyBoard board;
    Player player;
    JButton buyBox = new JButton("Buy Upgrades");
    JButton sellBox = new JButton("Sell Upgrades");
    JPanel southPanel = new JPanel(new GridLayout(1,2));
    int counter = 0;
    boolean buyState=true, sellState=false;

    public MonopolyBoard getBoard(){
        return board;
    }

    public void setBoard(MonopolyBoard board){
        this.board = board;
    }

    public UpgradeFrame(Player player, MonopolyBoard board){
        ownedProperties = player.getProperties();
        this.board = board;
        this.setTitle(player.getName() + "'s Trading Resources");
        this.player = player;
        nameLabel.setText("Player: " + player.getName());
        nameLabel.setBorder(compound);
        moneyLabel.setText("Available Money: $" + player.getTotalMoney());
        moneyLabel.setBorder(compound);
        gOOJBox.setBorder(compound);
        gOOJBox.setBorderPainted(true);
        gOOJBox.setHorizontalAlignment(JButton.CENTER);
        offeredMoney.setBorder(compound);
        offeredMoney.setHorizontalAlignment(JTextField.CENTER);
        offeredMoney.setToolTipText("Put in the amount of money you would like to offer in the trade.");
        for(int x = 0; x < colorLabels.length; x++)
            colorLabels[x] = new JLabel(colors[x], SwingConstants.CENTER);

        for(int x = 0; x < propNames.length; x++)
            properties[x] = new JButton(propNames[x]);

        for(int x = 0; x < condenseThreeColors.length; x++)
            condenseThreeColors[x] = new JPanel(new GridLayout(5, 1));

        for(int x = 0; x < condenseTwoColors.length; x++)
            condenseTwoColors[x] = new JPanel(new GridLayout(5, 1));

        for(int x = 0; x < properties.length; x++){
            properties[x].setEnabled(false);
            properties[x].setSelected(false);
            properties[x].addActionListener(this);
            properties[x].setBorder(compound);
        }

        this.add(wholePanel);
        wholePanel.setBorder(compound);
        wholePanel.add(northPanel, BorderLayout.NORTH);
            northPanel.add(nameLabel);
            northPanel.add(moneyLabel);
            northPanel.setBorder(compound);
                propsAndTitles.setBorder(compound);
        wholePanel.add(southPanel, BorderLayout.SOUTH);
            southPanel.add(buyBox);
                buyBox.addActionListener(this);
                buyBox.setBorder(compound);
                buyBox.setEnabled(false);
            southPanel.add(sellBox);
                sellBox.setBorder(compound);
                sellBox.addActionListener(this);

        //Dark Purple
        propsAndTitles.add(condenseTwoColors[0]);
        condenseTwoColors[0].setBorder(compound);
        condenseTwoColors[0].add(colorLabels[0]);

        for(int x = 0; x < 2; x++){
            condenseTwoColors[0].add(properties[x]);
            if(board.checkForMonopoly(darkPurple, player))
                properties[x].setEnabled(true);
        }

        for(int x = 0; x < condenseThreeColors.length; x++){
            propsAndTitles.add(condenseThreeColors[x]);
            condenseThreeColors[x].add(colorLabels[x + 1]);
            condenseThreeColors[x].setBorder(compound);
        }

        for(int x=0; x<colorLabels.length; x++){
            colorLabels[x].setBorder(compound);
            colorLabels[x].setBackground(colorBacks[x]);
        }

        propsAndTitles.add(condenseTwoColors[1]);
        condenseTwoColors[1].add(colorLabels[7]);
        condenseTwoColors[1].setBorder(compound);

        //Light Gray
        for(int x = 2; x < 5; x++){
            condenseThreeColors[0].add(properties[x]);
            if(board.checkForMonopoly(Color.LIGHT_GRAY,player))
                properties[x].setEnabled(true);
        }

        //Magenta
        for(int x = 5; x < 8; x++){
            condenseThreeColors[1].add(properties[x]);
            if(board.checkForMonopoly(Color.magenta,player))
                properties[x].setEnabled(true);
        }

        //Orange
        for(int x = 8; x < 11; x++){
            condenseThreeColors[2].add(properties[x]);
            if(board.checkForMonopoly(Color.orange,player))
                properties[x].setEnabled(true);
        }

        //Red
        for(int x = 11; x < 14; x++){
            condenseThreeColors[3].add(properties[x]);
            if(board.checkForMonopoly(Color.red,player))
                properties[x].setEnabled(true);
        }

        //Yellow
        for(int x = 14; x < 17; x++){
            condenseThreeColors[4].add(properties[x]);
            if(board.checkForMonopoly(Color.yellow,player))
                properties[x].setEnabled(true);
        }

        //Green
        for(int x = 17; x < 20; x++){
            condenseThreeColors[5].add(properties[x]);
            if(board.checkForMonopoly(Color.green,player))
                properties[x].setEnabled(true);
        }

        //Blue
        for(int x = 20; x < 22; x++){
            condenseTwoColors[1].add(properties[x]);
            if(board.checkForMonopoly(Color.blue,player))
                properties[x].setEnabled(true);
        }

        wholePanel.add(propsAndTitles, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(buyState){
            for(int x = 0; x < properties.length; x++)
                if(e.getSource() == properties[x]){
                    Property chosenProp = board.getAssociatedProperty(properties[x].getText());
                    if(chosenProp.getNumHouses() == 4){
                        int hotelQuestion = JOptionPane.showConfirmDialog(board.banner, "Would you like to upgrade this property to a hotel for $" + chosenProp.getPricePerHouse() + "?", "Hotel Purchase", JOptionPane.YES_NO_OPTION);
                        if(hotelQuestion == JOptionPane.YES_OPTION){
                            JOptionPane.showMessageDialog(board.banner, "You have fully upgraded this property. Reap the rewards when other players land here.", "Property Complete", 1);
                            chosenProp.setNumHotels(1);
                            player.subtractMoney(chosenProp.getPricePerHouse());
                            properties[x].setEnabled(false);
                        }
                    } else{
                        try {
                            int housesQuestion = Integer.parseInt(JOptionPane.showInputDialog(board.banner, "How many houses (each costing $" + chosenProp.getPricePerHouse() + ") would you like to purchase?", "House Purchase", 1));
                            if(housesQuestion < 0 || housesQuestion > 4)
                                JOptionPane.showMessageDialog(board.banner, "You need to choose an appropriate number of houses.", "Quit It", 3);

                            else{
                                if(chosenProp.getNumHouses() + housesQuestion > 4)
                                    JOptionPane.showMessageDialog(board.banner, "That is too many houses. You can have a maximum of four houses per property.", "Invalid Choice", 3);

                                else{
                                    JOptionPane.showMessageDialog(board.banner, "Okay. You will pay $" + chosenProp.getPricePerHouse() * housesQuestion + " for the " + housesQuestion + " house(s).", "Successful Purchase", 1);
                                    player.subtractMoney(chosenProp.getPricePerHouse() * housesQuestion);
                                    chosenProp.setNumHouses(chosenProp.getNumHouses() + housesQuestion);
                                }
                            }
                        } catch (NumberFormatException f){
                            JOptionPane.showMessageDialog(board.banner, "That is not a number. You can't buy that 'quantity' of houses'.", "Not a Numerical Choice", 3);
                        }
                    }
                }
        }

        else if(sellState){
            for(int x=0; x<properties.length; x++){
                if(e.getSource()==properties[x]){
                    try {
                        Property chosenProp = board.getAssociatedProperty(properties[x].getText());
                        int sellQuestion = Integer.parseInt(JOptionPane.showInputDialog(board.banner, chosenProp.getName() + " currently has " + chosenProp.getNumHouses() + " House(s) and " + chosenProp.getNumHotels() + " Hotel(s). How many items would you like to sell?", "Sell Option", 1));
                        if(sellQuestion>(chosenProp.getNumHouses()+chosenProp.getNumHotels()))
                            JOptionPane.showMessageDialog(board.banner,"You don't have that many items to sell. Please pick an appropriate number.","Invalid Choice",3);

                        else if(sellQuestion <=0)
                            JOptionPane.showMessageDialog(board.banner,"That is not a valid number of items to sell. Please choose a number up to "+(chosenProp.getNumHouses()+chosenProp.getNumHotels())+".","Invalid Number",3);

                        else{
                            JOptionPane.showMessageDialog(board.banner, "You have successfully sold " + sellQuestion + " items from " + chosenProp.getName() + ".", "Successful Sales", 1);
                            player.addMoney(sellQuestion * chosenProp.getPricePerHouse());
                            if(chosenProp.getNumHotels() == 1){
                                chosenProp.setNumHotels(0);
                                chosenProp.setNumHouses(5 - sellQuestion);
                            }
                            else
                                chosenProp.setNumHouses(chosenProp.getNumHouses()-sellQuestion);

                            checkDevelopment();
                        }
                    }
                    catch(NumberFormatException f){
                        JOptionPane.showMessageDialog(board.banner,"This is not a number. You can't sell that 'quantity' of items.","Not a Numerical Choice",3);
                    }
                }
            }
        }

        if(e.getSource()==buyBox){
            buyBox.setEnabled(false);
            sellBox.setEnabled(true);
            buyState=true;
            sellState=false;
            board.workAroundMaybe();
        }

        else if(e.getSource()==sellBox){
            buyBox.setEnabled(true);
            sellBox.setEnabled(false);
            buyState=false;
            sellState=true;
            checkDevelopment();
        }
        moneyLabel.setText("Available Money: $" + player.getTotalMoney());
    }

    public void checkDevelopment(){
        for(int x=0; x<properties.length; x++){
            properties[x].setEnabled(false);
            if(board.getAssociatedProperty(properties[x].getText()).getNumHouses() > 0)
                properties[x].setEnabled(true);
        }
    }
}