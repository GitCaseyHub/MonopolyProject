import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class StartGame extends JFrame implements ActionListener, FocusListener{
    //PlayerFrames
    PlayerSelectFrame playerOne = new PlayerSelectFrame("","",Color.white,false,this,1,1000,100,435,255);
    PlayerSelectFrame playerTwo = new PlayerSelectFrame("","",Color.white,false,this,2,1000,370,435,255);
    PlayerSelectFrame playerThree = new PlayerSelectFrame("","",Color.white,false,this,3,1460,100,435,255);
    PlayerSelectFrame playerFour = new PlayerSelectFrame("","",Color.white,false,this,4,1460,370,435,255);

    Border compound = BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder());
    JPanel builderPanel = new JPanel(new BorderLayout());
    JLabel selectionLabel = new JLabel("Player Select Menu", SwingConstants.CENTER);
    JPanel selectionPanel = new JPanel();
    JComboBox numPlayers = new JComboBox();
    JTextField startingMoney = new JTextField("Starting Money",SwingConstants.CENTER);
    JTextField freeParkingMoney = new JTextField("Free Parking Money",SwingConstants.CENTER);
    JButton confirmButton = new JButton("Begin Monopoly");
    Color color = new Color(100,100,100);
    boolean oneVisible, twoVisible, threeVisible, fourVisible;
    ArrayList<String> chosenTokens = new ArrayList<String>();
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<Color> colorList = new ArrayList<Color>();
    ArrayList<Integer> orderList = new ArrayList<Integer>();
    Player[] players;
    MonopolyBoard m = new MonopolyBoard();
    int currentIndex=0;
    int freeParkingOption=0;
    boolean choiceParking=false;

    public StartGame(){
        //Colorizes DialogBoxes
        new UIManager().put("OptionPane.background", Color.WHITE);
        new UIManager().put("Panel.background", Color.WHITE);

        //Initialize Conversation
        JOptionPane.showMessageDialog(m.banner,"Welcome to Monopoly","Trademarked",1);
        freeParkingOption = JOptionPane.showConfirmDialog(m.banner,"Would you like to play with a 'Free Parking' fund?","Free Parking",JOptionPane.YES_NO_OPTION);
        choiceParking = (freeParkingOption==JOptionPane.YES_OPTION);
        JOptionPane.showMessageDialog(m.banner,(choiceParking)?"A second field has been added to the upcoming frame to accommodate 'Free Parking'.":"'Free Parking' has been disabled.","Free Parking",1);
        selectionPanel.setLayout(new GridLayout(1,(choiceParking)?3:2));

        //Creates opening frames
        this.setResizable(false);
        this.add(builderPanel);
            builderPanel.setBorder(compound);
            builderPanel.setBackground(Color.white);
                builderPanel.add(selectionLabel, BorderLayout.NORTH);
                    selectionLabel.setBorder(compound);
                    selectionLabel.setBackground(Color.white);
                builderPanel.add(selectionPanel, BorderLayout.CENTER);
                   selectionPanel.add(numPlayers);
                        numPlayers.setBackground(Color.white);
                        numPlayers.setBorder(compound);
                   selectionPanel.add(startingMoney);
                        startingMoney.addFocusListener(this);
                        startingMoney.setForeground(color);
                        startingMoney.setToolTipText("Enter an integer value between $500 and $5000.");
                        startingMoney.setHorizontalAlignment(JTextField.CENTER);
                        startingMoney.setBorder(compound);

                   if(choiceParking){
                       selectionPanel.add(freeParkingMoney);
                       freeParkingMoney.addFocusListener(this);
                       freeParkingMoney.setForeground(color);
                       freeParkingMoney.setToolTipText("Enter an integer value between $100 and $500.");
                       freeParkingMoney.setHorizontalAlignment(JTextField.CENTER);
                       freeParkingMoney.setBorder(compound);
                   }
                    selectionPanel.setBorder(compound);
        numPlayers.addItem("Number of Players");
        for(int i=2; i<5; i++)
            numPlayers.addItem(i);

        numPlayers.addActionListener(this);
            builderPanel.add(confirmButton, BorderLayout.SOUTH);
                confirmButton.setBorder(compound);
                confirmButton.addActionListener(this);
                confirmButton.setBackground(Color.white);
                confirmButton.setEnabled(false);

        initializeAllPlayerFrames();
        orderList.add(0);
    }

    public void addToTokenList(String newToken){
        chosenTokens.add(newToken);
    }

    public void addToOrderList(int order){
        orderList.add(order);
    }

    public ArrayList<Integer> getOrderList(){
        return orderList;
    }

    public ArrayList<String> getTokenList(){
        return chosenTokens;
    }

    public void addNameToList(String name){
        nameList.add(name);
    }

    public ArrayList<String> getNameList(){
        return nameList;
    }

    public ArrayList<Color> getColorList(){
        return colorList;
    }

    public void addColorToList(Color newColor){
        colorList.add(newColor);
    }

    public static void main(String[] args){
        StartGame sg = new StartGame();
        sg.setBounds(40,100,460,132);
        sg.setTitle("Player Select");
        sg.setVisible(true);
        sg.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public void focusGained(FocusEvent e){
        if(e.getSource()==startingMoney && startingMoney.getText().equals("Starting Money")){
            startingMoney.setText("");
            startingMoney.setForeground(Color.black);
        }
        else if(e.getSource()==freeParkingMoney && freeParkingMoney.getText().equals("Free Parking Money")){
            freeParkingMoney.setText("");
            freeParkingMoney.setForeground(Color.black);
        }
    }

    public void focusLost(FocusEvent e){
        if(e.getSource()==startingMoney && startingMoney.getText().equals("")){
            startingMoney.setText("Starting Money");
            startingMoney.setForeground(color);
        }
        else if(e.getSource()==freeParkingMoney && freeParkingMoney.getText().equals("")){
            freeParkingMoney.setText("Free Parking Money");
            freeParkingMoney.setForeground(color);
        }
    }

    public void letTheGamesBegin(){
        m.setBounds(10,10,1000,1000);
        m.setTitle("Monopoly \u2122 Hasbro");
        m.setVisible(true);
    }

    public void initializeAllPlayerFrames(){
        playerOne.setBounds(525,100,450,125);
            playerOne.setTitle("Player 1");
        playerTwo.setBounds(525,250,450,125);
            playerTwo.setTitle("Player 2");
        playerThree.setBounds(525,400,450,125);
            playerThree.setTitle("Player 3");
        playerFour.setBounds(525,550,450,125);
            playerFour.setTitle("Player 4");
    }

    public void makeFrameOneAndTwoVisible(){
        playerOne.setVisible(true);
        playerTwo.setVisible(true);
    }
    public void makeFrameOneAndTwoInvisible(){
        playerOne.setVisible(false);
        playerTwo.setVisible(false);
        playerOne.voidInfo();
        playerTwo.voidInfo();
    }
    public void makeFrameThreeVisible(){
        playerThree.setVisible(true);
    }
    public void makeFrameThreeInvisible(){
        playerThree.setVisible(false);
        playerThree.voidInfo();
    }
    public void makeFrameFourVisible(){
        playerFour.setVisible(true);
    }
    public void makeFrameFourInvisible(){
        playerFour.setVisible(false);
        playerFour.voidInfo();
    }

    public void removePlayerOne(){
        if(this.getTokenList().contains(playerOne.token)){
            this.chosenTokens.remove(playerOne.token);
            this.nameList.remove(playerOne.name);
            this.colorList.remove(playerOne.color);
            this.orderList.remove(playerOne.playerNumber);
        }
    }
    public void removePlayerTwo(){
        if(this.getTokenList().contains(playerTwo.token)){
            this.chosenTokens.remove(playerTwo.token);
            this.nameList.remove(playerTwo.name);
            this.colorList.remove(playerTwo.color);
            this.orderList.remove(playerTwo.playerNumber);
        }
    }
    public void removePlayerThree(){
        if(this.getTokenList().contains(playerThree.token)){
            this.chosenTokens.remove(playerThree.token);
            this.nameList.remove(playerThree.name);
            this.colorList.remove(playerThree.color);
            this.orderList.remove(playerThree.playerNumber);
        }
    }
    public void removePlayerFour(){
        if(this.getTokenList().contains(playerFour.token)){
            this.chosenTokens.remove(playerFour.token);
            this.nameList.remove(playerFour.name);
            this.colorList.remove(playerFour.color);
            this.orderList.remove(playerFour.playerNumber);
        }
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == numPlayers){
            if(numPlayers.getSelectedIndex() == 1){
                makeFrameOneAndTwoVisible();
                makeFrameThreeInvisible();
                makeFrameFourInvisible();
                removePlayerThree();
                removePlayerFour();
                playerOne.nameField.requestFocus();
                currentIndex = 1;
            } else if(numPlayers.getSelectedIndex() == 2){
                makeFrameOneAndTwoVisible();
                makeFrameThreeVisible();
                makeFrameFourInvisible();
                removePlayerFour();
                playerOne.nameField.requestFocus();
                currentIndex = 2;
            } else if(numPlayers.getSelectedIndex() == 3){
                makeFrameOneAndTwoVisible();
                makeFrameThreeVisible();
                makeFrameFourVisible();
                playerOne.nameField.requestFocus();
                currentIndex = 3;
            } else if(numPlayers.getSelectedIndex() == 0){
                JOptionPane.showMessageDialog(m.banner, "That was just part of the player directions. Choose a number of players.", "Number of Players", 3);
                this.startingMoney.requestFocus();
                if(currentIndex == 0){
                    makeFrameOneAndTwoVisible();
                    makeFrameThreeInvisible();
                    makeFrameFourInvisible();
                    removePlayerThree();
                    removePlayerFour();
                    playerOne.nameField.requestFocus();
                    currentIndex = 1;
                }
                numPlayers.setSelectedIndex(currentIndex);
            }
        }
        else if(e.getSource() == confirmButton){
            if(choiceParking){
                if(startingMoney.getText().equals("Starting Money") && freeParkingMoney.getText().equals("Free Parking Money"))
                    JOptionPane.showMessageDialog(m.banner, "You never entered a starting money value or a free parking value. Please choose appropriate values for both before proceeding.", "Blank Fields", 3);

                else if(freeParkingMoney.getText().equals("Free Parking Money"))
                    JOptionPane.showMessageDialog(m.banner, "You never chose a free parking value. Enter a value between $100 and $500.", "Free Parking", 3);

                else
                    runStartOperation();
            }
            else{
                if(startingMoney.getText().equals("Starting Money"))
                    JOptionPane.showMessageDialog(m.banner, "You never chose a starting money value. Enter a value between $500 and $5000.", "Starting Money", 3);

                else
                    runStartOperation();
            }
        }
    }

    public void runStartOperation(){
        try {
            int check = Integer.parseInt(startingMoney.getText());

            if(choiceParking){
                int freeParkingCheck = Integer.parseInt(freeParkingMoney.getText());

                if(Integer.parseInt(startingMoney.getText()) < 500)
                    JOptionPane.showMessageDialog(m.banner, "You need to put in more than $500 starting money. You won't get far without that.", "Choose An Appropriate Amount", 2);

                else if(Integer.parseInt(startingMoney.getText()) > 5000)
                    JOptionPane.showMessageDialog(m.banner, "That's way too much starting money. Where's the challenge? Try less than $5,000.", "Choose An Appropriate Amount", 2);

                else if(Integer.parseInt(freeParkingMoney.getText()) < 100)
                    JOptionPane.showMessageDialog(m.banner, "You need to choose a value between $100 and $500 for 'Free Parking'.", "Choose An Appropriate Amount", 2);

                else if(Integer.parseInt(freeParkingMoney.getText()) > 500)
                    JOptionPane.showMessageDialog(m.banner, "'Free Parking' should be less than $500 but more than $100.", "Choose An Appropriate Amount", 2);

                else {
                    JOptionPane.showMessageDialog(m.banner, "Note that because 'Free Parking' is active, 'Income Tax', 'Luxury Tax', and all Community-\n" +
                                                                     "Chest & Chance cards requiring arbitrary payment, will be added to the free parking fund.");

                    JOptionPane.showMessageDialog(m.banner, "You're ready to play. Have fun.", "Let the Games Begin", 2);
                    makeFrameOneAndTwoInvisible();
                    makeFrameThreeInvisible();
                    makeFrameFourInvisible();
                    this.setVisible(false);

                    int firstPlayer = new Random().nextInt(numPlayers.getSelectedIndex()) + 1;
                    String firstPlayerName = "";
                    players = new Player[numPlayers.getSelectedIndex() + 1];

                    for(int a = 0; a < numPlayers.getSelectedIndex() + 1; a++){
                        players[a] = new Player(nameList.get(a), false, chosenTokens.get(a), false, false, 0, Integer.parseInt(startingMoney.getText()), new ArrayList<Property>(), colorList.get(a), a, 0, false, 0, false);
                        if(players[a].getPlayerNumber() == firstPlayer){
                            players[a].setHasCurrentTurn(true);
                            firstPlayerName = players[a].getName();
                        }
                    }
                    JOptionPane.showMessageDialog(m.banner, firstPlayerName + ", you're first. Then, it will proceed forward in ascending numerical order.", "Ready, set, go!", JOptionPane.INFORMATION_MESSAGE);
                    m = new MonopolyBoard(numPlayers.getSelectedIndex() + 1, players, Integer.parseInt(freeParkingMoney.getText()), choiceParking);
                    for(int x = 0; x < players.length; x++){
                        m.spaces[0].colors[x].setBackground(players[x].getIdentifier());
                    }
                    this.setVisible(false);
                    letTheGamesBegin();
                }
            }

            else{
                if(Integer.parseInt(startingMoney.getText()) < 500)
                    JOptionPane.showMessageDialog(m.banner, "You need to put in more than $500 starting money. You won't get far without that.", "Choose An Appropriate Amount", 2);

                else if(Integer.parseInt(startingMoney.getText()) > 5000)
                    JOptionPane.showMessageDialog(m.banner, "That's way too much starting money. Where's the challenge? Try less than $5,000.", "Choose An Appropriate Amount", 2);

                else{
                    JOptionPane.showMessageDialog(m.banner, "You're ready to play. Have fun. And if you need any help, press CTRL_H.", "Let the Games Begin", 2);
                    makeFrameOneAndTwoInvisible();
                    makeFrameThreeInvisible();
                    makeFrameFourInvisible();
                    this.setVisible(false);

                    int firstPlayer = new Random().nextInt(numPlayers.getSelectedIndex()) + 1;
                    String firstPlayerName = "";
                    players = new Player[numPlayers.getSelectedIndex() + 1];

                    for(int a = 0; a < numPlayers.getSelectedIndex() + 1; a++){
                        players[a] = new Player(nameList.get(a), false, chosenTokens.get(a), false, false, 0, Integer.parseInt(startingMoney.getText()), new ArrayList<Property>(), colorList.get(a), a, 0, false, 0, false);
                        if(players[a].getPlayerNumber() == firstPlayer){
                            players[a].setHasCurrentTurn(true);
                            firstPlayerName = players[a].getName();
                        }
                    }
                    JOptionPane.showMessageDialog(m.banner, firstPlayerName + ", you're first. Then, it will proceed forward in ascending numerical order.", "Ready, set, go!", JOptionPane.INFORMATION_MESSAGE);
                    m = new MonopolyBoard(numPlayers.getSelectedIndex() + 1, players, 0, choiceParking);
                    for(int x = 0; x < players.length; x++){
                        m.spaces[0].colors[x].setBackground(players[x].getIdentifier());
                    }
                    this.setVisible(false);
                    letTheGamesBegin();
                }
            }
        }
        catch(NumberFormatException f){
            JOptionPane.showMessageDialog(m.banner, "That isn't a monetary value. Make sure you are entering numerical values for all fields.", "Choose An Appropriate Numerical Amount", 2);
        }
    }
}