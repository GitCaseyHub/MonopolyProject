import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TradingFrame extends JFrame implements ActionListener, WindowListener {
    Color darkPurple = new Color(123, 11, 153);
    Color color = new Color(100,100,100);
    Border compound = BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder());

    JLabel nameLabel = new JLabel("", SwingConstants.CENTER);
    JLabel moneyLabel = new JLabel("", SwingConstants.CENTER);
    JCheckBox gOOJBox = new JCheckBox("Get Out Of Jail Free Card");
    JTextField offeredMoney = new JTextField("", SwingConstants.CENTER);
    Color[] colorArray = {darkPurple,Color.LIGHT_GRAY,Color.MAGENTA,Color.ORANGE,Color.RED,Color.YELLOW, Color.GREEN, Color.BLUE,color,Color.BLACK};
    JLabel[] colorLabels = new JLabel[10];
    String[] colors = {"Dark Purple Group","Light Gray Group","Magenta Group","Orange Group","Red Group","Yellow Group","Green Group","Blue Group","Utilities","Railroads"};
    String[] propNames = {"Mediterranean Avenue","Baltic Avenue","Oriental Avenue","Vermont Avenue","Connecticut Avenue","St. Charles Place","States Avenue","Virginia Avenue","St. James Place","Tennessee Avenue", "New York Avenue","Kentucky Avenue","Indiana Avenue","Illinois Avenue","Atlantic Avenue","Ventnor Avenue","Marvin Gardens","Pacific Avenue","North Carolina Avenue","Pennsylvania Avenue","Park Place","Boardwalk","Electric Company","Water Works","Reading Railroad","Pennsylvania Railroad","B&O Railroad","Short Line Railroad"};
    JCheckBox[] properties = new JCheckBox[propNames.length];
    JButton confirmButton = new JButton("Accept Trade");
    JButton askButton = new JButton("Ask Another Player");
    JPanel wholePanel = new JPanel(new BorderLayout());
    JPanel northPanel = new JPanel(new GridLayout(2,2));
    JPanel propsAndTitles = new JPanel(new GridLayout(5,2));
    JPanel southPanel = new JPanel(new GridLayout(1,2));
    JPanel[] condenseThreeColors = new JPanel[6];
    JPanel[] condenseTwoColors = new JPanel[3];
    JPanel railRoads = new JPanel(new GridLayout(5,1));
    ArrayList<Property> ownedProperties = new ArrayList<Property>();
    boolean askCheck, acceptCheck;
    MonopolyBoard board;
    Player player;
    int counter=0;
    TradingFrame check;

    public MonopolyBoard getBoard() {
        return board;
    }

    public void setBoard(MonopolyBoard board) {
        this.board = board;
    }

    public TradingFrame(Player player, boolean askCheck, boolean acceptCheck, MonopolyBoard board){
        ownedProperties=player.getProperties();
        this.board=board;
        this.setTitle(player.getName()+"'s Trading Resources");
        this.player=player;
        this.askCheck=askCheck;
        this.acceptCheck=acceptCheck;
        nameLabel.setText("Player: "+player.getName());
            nameLabel.setBorder(compound);
        moneyLabel.setText("Your Money: $"+player.getTotalMoney());
            moneyLabel.setBorder(compound);
            gOOJBox.setBorder(compound);
            gOOJBox.setBorderPainted(true);
            gOOJBox.setHorizontalAlignment(JCheckBox.CENTER);
            offeredMoney.setBorder(compound);
            offeredMoney.setHorizontalAlignment(JTextField.CENTER);
            offeredMoney.setToolTipText("Put in the amount of money you would like to offer in the trade.");
        for(int x=0; x<colorLabels.length; x++)
            colorLabels[x] = new JLabel(colors[x], SwingConstants.CENTER);


        for(int x=0; x<propNames.length; x++)
            properties[x] = new JCheckBox(propNames[x]);

        for(int x=0; x<condenseThreeColors.length; x++)
            condenseThreeColors[x] = new JPanel(new GridLayout(5,1));

        for(int x=0; x<condenseTwoColors.length; x++)
            condenseTwoColors[x] = new JPanel(new GridLayout(5,1));

        this.add(wholePanel);
        wholePanel.setBorder(compound);
        wholePanel.add(northPanel, BorderLayout.NORTH);
            northPanel.add(nameLabel);
            northPanel.add(moneyLabel);
            northPanel.add(gOOJBox);
            northPanel.add(offeredMoney);
            northPanel.setBorder(compound);
        propsAndTitles.setBorder(compound);

        gOOJBox.setEnabled(player.isHasGetOutOfJailFreeCard());

        //Dark Purple
        propsAndTitles.add(condenseTwoColors[0]);
        condenseTwoColors[0].setBorder(compound);
            condenseTwoColors[0].add(colorLabels[0]);

        for(int x=0; x<2; x++)
            condenseTwoColors[0].add(properties[x]);

        for(int x=0; x<condenseThreeColors.length; x++) {
            propsAndTitles.add(condenseThreeColors[x]);
            condenseThreeColors[x].add(colorLabels[x + 1]);
            condenseThreeColors[x].setBorder(compound);
        }

        propsAndTitles.add(condenseTwoColors[1]);
        condenseTwoColors[1].add(colorLabels[7]);
        condenseTwoColors[1].setBorder(compound);
        propsAndTitles.add(condenseTwoColors[2]);
        condenseTwoColors[2].add(colorLabels[8]);
        condenseTwoColors[2].setBorder(compound);
        propsAndTitles.add(railRoads);
        railRoads.add(colorLabels[9]);
        railRoads.setBorder(compound);

        //Light Gray
        for(int x=2; x<5; x++)
            condenseThreeColors[0].add(properties[x]);

        //Magenta
        for(int x=5; x<8; x++)
            condenseThreeColors[1].add(properties[x]);

        //Orange
        for(int x=8; x<11; x++)
            condenseThreeColors[2].add(properties[x]);

        //Red
        for(int x=11; x<14; x++)
            condenseThreeColors[3].add(properties[x]);

        //Yellow
        for(int x=14; x<17; x++)
            condenseThreeColors[4].add(properties[x]);

        //Green
        for(int x=17; x<20; x++)
            condenseThreeColors[5].add(properties[x]);

        //Blue
        for(int x=20; x<22; x++)
            condenseTwoColors[1].add(properties[x]);

        //Utilities
        for(int x=22; x<24; x++)
            condenseTwoColors[2].add(properties[x]);

        //Railroads
        for(int x=24; x<28; x++)
            railRoads.add(properties[x]);

        wholePanel.add(southPanel, BorderLayout.SOUTH);
            southPanel.add(askButton);
            southPanel.add(confirmButton);
                confirmButton.setBorder(compound);
                confirmButton.addActionListener(this);
                askButton.setBorder(compound);
                askButton.addActionListener(this);
            wholePanel.add(propsAndTitles, BorderLayout.CENTER);

        for(int x=0; x<properties.length; x++) {
            properties[x].setEnabled(false);
            properties[x].setSelected(false);
        }
        for(int x=0; x<ownedProperties.size(); x++)
            for(int y=0; y<properties.length; y++)
                if(ownedProperties.get(x).getName().equals(properties[y].getText()))
                    properties[y].setEnabled(true);

        gOOJBox.setEnabled((player.hasGetOutOfJailFreeCard));

        for(int x=0; x<colorLabels.length; x++)
            colorLabels[x].setBackground(colorArray[x]);

        for(int x=0; x<properties.length; x++)
            properties[x].setBackground(Color.white);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==askButton){
            counter=0;
            String otherPlayer = JOptionPane.showInputDialog(board.banner, "Which player would you like to trade with?", "Trading Partner", 1);
            ArrayList<Player> otherPlayers = new ArrayList<Player>();
            otherPlayers = board.getOtherPlayers();
            Player tradePartner = new Player();
            try {
                if (otherPlayer.equalsIgnoreCase(player.getName())) {
                    JOptionPane.showMessageDialog(board.banner, "You can't trade with yourself.", "Invalid", 3);
                    counter++;
                }
            }
            catch(NullPointerException f){
                //Workaround for otherPlayer requiring Instantiation
                counter++;
            }

            for (int x = 0; x < otherPlayers.size(); x++) {
                if (otherPlayers.get(x).getName().equalsIgnoreCase(otherPlayer)) {
                    if (otherPlayers.get(x).isBankrupt())
                        JOptionPane.showMessageDialog(board.banner, "That player has bankrupted. Naturally, they have no liquidity, and so you cannot trade with them.", "Improper Trade Request", 3);
                    else {
                        counter++;
                        this.askButton.setEnabled(false);
                        tradePartner = otherPlayers.get(x);
                    }
                }
            }
            for (int x = 0; x < board.tradingFrames.length; x++)
                if (board.tradingFrames[x].getPlayer() == tradePartner) {
                    board.tradingFrames[x].setPlayer(tradePartner);
                    board.tradingFrames[x].setAsked(true);
                    board.tradingFrames[x].setInitiator(false);
                    board.tradingFrames[x].setVisible(true);
                    board.tradingFrames[x].setAlwaysOnTop(true);
                    board.tradingFrames[x].setBounds(495, 100, 370, 500);
                    board.tradingFrames[x].setDefaultValues();
                    board.tradingFrames[x].refreshText();
                    board.tradingFrames[x].addWindowListener(this);
                    board.tradingFrames[x].setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
                    check = board.tradingFrames[x];
                    check.addWindowListener(this);
                }

            if (counter == 0)
                JOptionPane.showMessageDialog(board.banner, "There is no such player. Make sure you are spelling their name correctly.", "No Player Found", 3);
        }

        else if(e.getSource()==confirmButton){
            Player currentPlayer=board.getCurrentPlayer();
            TradingFrame currentFrame=board.getTradingFrame(board.getCurrentPlayer());

            Player player = this.player;
            TradingFrame otherFrame=this;

            board.refreshTrackerInfo();


            ArrayList<String> currentPlayerSelectedProps = currentFrame.collectSelectedProperties();
            ArrayList<Property> currentPlayerProperties = currentPlayer.getProperties();
            ArrayList<String> thisPlayerSelectedProps = this.collectSelectedProperties();
            ArrayList<Property> thisPlayerProperties = this.player.getProperties();

            try {
                counter++;
                int currentPlayerOffer = Integer.parseInt(currentFrame.offeredMoney.getText());
                int otherPlayerOffer = Integer.parseInt(this.offeredMoney.getText());

                if(currentPlayerOffer>board.getCurrentPlayer().getTotalMoney() || otherPlayerOffer>player.getTotalMoney())
                    JOptionPane.showMessageDialog(board.banner,"You cannot offer more money than you are worth. Modify that before offering a trade.","Insolvent",3);

                else {

                    currentPlayer.addMoney(otherPlayerOffer);
                    currentPlayer.subtractMoney(currentPlayerOffer);
                    player.addMoney(currentPlayerOffer);
                    player.subtractMoney(otherPlayerOffer);

                    //Properties Transferring Well (IndexOutOfBoundsException
                    for (int x = 0; x < currentPlayerProperties.size(); x++)
                        for (int y = 0; y < currentPlayerSelectedProps.size(); y++) {
                            if (currentPlayerProperties.get(x).getName().equalsIgnoreCase(currentPlayerSelectedProps.get(y))) {
                                this.player.addProperty(currentPlayerProperties.get(x));
                                currentPlayerProperties.get(x).setOwner(this.player);
                                currentPlayer.removeProperty(currentPlayerProperties.get(x));
                                otherFrame.setPlayer(this.player);
                                currentFrame.setPlayer(currentPlayer);
                                this.setDefaultValues();
                                currentFrame.setDefaultValues();
                                otherFrame.refreshText();
                                currentFrame.refreshText();
                            }
                        }
                    for (int x = 0; x < thisPlayerProperties.size(); x++)
                        for (int y = 0; y < thisPlayerSelectedProps.size(); y++) {
                            if (thisPlayerProperties.get(x).getName().equalsIgnoreCase(thisPlayerSelectedProps.get(y))) {
                                currentPlayer.addProperty(thisPlayerProperties.get(x));
                                thisPlayerProperties.get(x).setOwner(currentPlayer);
                                this.player.removeProperty(thisPlayerProperties.get(x));
                                this.setDefaultValues();
                                otherFrame.setPlayer(this.player);
                                currentFrame.setPlayer(currentPlayer);
                                currentFrame.setDefaultValues();
                                otherFrame.refreshText();
                                currentFrame.refreshText();
                            }
                        }
                    if (currentFrame.gOOJBox.isSelected() && otherFrame.gOOJBox.isSelected())
                        JOptionPane.showMessageDialog(board.banner, "You can't both offer 'Get out of jail free' cards. That part of the trade has been cancelled.", "Unnecessary Trade", 3);

                    else if (currentFrame.gOOJBox.isSelected()) {
                        this.player.setHasGetOutOfJailFreeCard(true);
                        currentPlayer.setHasGetOutOfJailFreeCard(false);
                    } else if (otherFrame.gOOJBox.isSelected()) {
                        this.player.setHasGetOutOfJailFreeCard(false);
                        currentPlayer.setHasGetOutOfJailFreeCard(true);
                    }

                    otherFrame.setVisible(false);
                    currentFrame.setVisible(false);
                    board.resetJComboBox(currentPlayer);
                    board.resetJComboBox(this.player);

                    JOptionPane.showMessageDialog(board.banner,"The trade has been completed. Check your tracker to see the results of the trade.","Trade Completed",1);
                }
            }
            catch(NumberFormatException a){
                JOptionPane.showMessageDialog(board.banner,"Make sure you are inputting a number amount of money.","Number Formatting",3);
            }
        }
        for(int x=0; x<board.currentPlayers.length; x++)
            board.resetJComboBox(board.currentPlayers[x]);
    }

    public void setInitiator(boolean setInit){
        this.askCheck=setInit;
        recheckButtons();
    }
    public void setAsked(boolean setAsk){
        this.acceptCheck=setAsk;
        recheckButtons();
    }

    public void recheckButtons(){
        this.confirmButton.setEnabled(acceptCheck);
        this.askButton.setEnabled(askCheck);
    }

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player player){
        this.player=player;
        refreshText();
    }

    public void refreshText(){
        moneyLabel.setText("Money: $"+player.getTotalMoney());
        for(int x=0; x<ownedProperties.size(); x++)
            for(int y=0; y<properties.length; y++)
                if(ownedProperties.get(x).getName().equals(properties[y].getText()))
                    properties[y].setEnabled(true);
    }

    public void setDefaultValues(){
        this.offeredMoney.setText("");
        for(int x=0; x<properties.length; x++) {
            properties[x].setSelected(false);
            properties[x].setEnabled(false);
        }
    }

    public ArrayList<String> collectSelectedProperties(){
        ArrayList<String> selectedProps = new ArrayList<String>();
        for(int x=0; x<properties.length; x++)
            if(properties[x].isSelected())
                selectedProps.add(properties[x].getText());

        return selectedProps;
    }

    public void windowClosing(WindowEvent e) {
        if(e.getSource()==this && askButton.isEnabled()) {
            this.setVisible(false);
            check.setVisible(false);
        }
        else if(e.getSource()==check){
            askButton.setEnabled(true);
        }
    }

    public void windowOpened(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}