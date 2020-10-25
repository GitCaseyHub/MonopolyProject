import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class MonopolyBoard extends JFrame implements ActionListener, MouseMotionListener, WindowListener, KeyListener {
    //Board/Fancy Border/Baltic_Avenue and Mediterranean_Avenue Color
    JPanel board = new JPanel(new GridLayout(11,11));
    Color darkPurple = new Color(123, 11, 153);
    Border compound = BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder());

    //Property Information
    Property[] spaces = new Property[121];
    int[] pricePerHouse = {0,50,0,50,0,0,50,0,50,50,0,100,0,100,100,0,100,0,100,100,0,150,0,150,150,0,150,150,0,150,0,200,200,0,200,0,0,200,0,200};
    String[] names = {"Go","Mediterranean Avenue","Community Chest","Baltic Avenue","Income Tax","Reading Railroad","Oriental Avenue","Chance","Vermont Avenue","Connecticut Avenue","Jail","St. Charles Place","Electric Company","States Avenue","Virginia Avenue","Pennsylvania Railroad","St. James Place","Community Chest","Tennessee Avenue", "New York Avenue","Free Parking","Kentucky Avenue","Chance","Indiana Avenue","Illinois Avenue","B&O Railroad","Atlantic Avenue","Ventnor Avenue","Water Works","Marvin Gardens","Go to Jail","Pacific Avenue","North Carolina Avenue","Community Chest","Pennsylvania Avenue","Short Line Railroad","Chance","Park Place","Luxury Tax","Boardwalk"};
    int[] buyPrice = {0,60,0,60,0,200,100,0,100,100,0,120,150,140,140,200,180,0,180,200,0,220,0,220,240,200,260,260,150,280,0,300,300,0,320,200,0,350,0,400};
    int[] standardRentPrice = {0,2,0,4,0,0,6,0,6,8,0,10,0,10,12,0,14,0,14,16,0,18,0,18,20,0,22,22,0,24,0,26,26,0,28,0,0,35,0,50};
    int[] rentAfterOneHouse = {0,10,0,20,0,0,30,0,30,40,0,50,0,50,60,0,70,0,70,80,0,90,0,90,100,0,110,110,0,120,0,130,130,0,150,0,0,175,0,200};
    int[] rentAfterTwoHouses = {0,30,0,60,0,0,90,0,90,100,0,150,0,150,180,0,200,0,200,220,0,250,0,250,300,0,330,330,0,360,0,390,390,0,450,0,0,500,0,600};
    int[] rentAfterThreeHouses = {0,90,0,180,0,0,270,0,270,300,0,450,0,450,600,0,550,0,550,600,0,700,0,700,750,0,800,800,0,850,0,900,900,0,1000,0,0,1100,0,1400};
    int[] rentAfterFourHouses = {0,160,0,320,0,0,400,0,400,450,0,625,0,625,700,0,750,0,750,800,0,875,0,875,925,0,975,975,0,1025,0,1100,1100,0,1200,0,0,1300,0,1700};
    int[] rentAfterHotel = {0,250,0,450,0,0,550,0,550,600,0,750,0,750,900,0,950,0,950,1000,0,1050,0,1050,1100,0,1150,1150,0,1200,0,1275,1275,0,1400,0,0,1500,0,2000};
    int[] mortgageValue = {0,30,0,30,0,100,50,0,50,60,0,70,75,70,80,100,90,0,90,100,0,110,0,110,120,100,130,130,75,140,0,150,150,0,160,100,0,175,0,200};
    Color[] importantColor = {Color.WHITE, darkPurple, Color.WHITE,darkPurple,Color.WHITE,Color.BLACK, Color.LIGHT_GRAY,Color.WHITE,Color.LIGHT_GRAY,Color.LIGHT_GRAY,Color.WHITE,Color.MAGENTA,Color.WHITE,Color.MAGENTA,Color.MAGENTA,Color.BLACK,Color.ORANGE,Color.WHITE,Color.ORANGE,Color.ORANGE, Color.WHITE, Color.RED, Color.WHITE, Color.RED, Color.RED, Color.BLACK, Color.YELLOW, Color.YELLOW, Color.WHITE, Color.YELLOW, Color.WHITE, Color.GREEN, Color.GREEN,Color.WHITE, Color.GREEN,Color.BLACK, Color.WHITE, Color. BLUE, Color.WHITE, Color.BLUE};
    int[] importantIndexes = {0,1,2,3,4,5,6,7,8,9,10,21,32,43,54,65,76,87,98,109,120,119,118,117,116,115,114,113,112,111,110,99,88,77,66,55,44,33,22,11};
    ArrayList<Integer> others = new ArrayList<Integer>();
    int[] nonPropertyIndex = {0,2,4,7,10,17,20,22,30,33,36,38};
    ArrayList<Integer> nonPropertyIndexes = new ArrayList<Integer>();
    int[] propertyIndexes = {1,3,5,6,8,9,11,12,13,14,15,16,18,19,21,23,24,25,26,27,28,29,31,32,34,35,37,39};

    //Chance and Community Chest Generation
    Player currentPlayer=new Player();
    String[] chestMessages = {"Advance to Go. Collect $200.","Bank Error; Collect $200","Doctor's Fees; Pay $50","You've Sold Stocks; Collect $50","Receive a 'Get out Of Jail Free Card'","Go Directly to Jail","Grand Opera Opening; Collect $50 From Each Other Player","Your Christmas Fund Matures; Collect $100","Income Tax Refund; Collect $250","It's Your Birthday, But Your Friends Are Cheap. Collect $10 From Each Other Player.","Life Insurance Matures; Collect $100","Hospital Bills; Pay $50","You Provide Consultation To A Divorce Lawyer; Collect $500","You are Assessed Street Repairs; Pay $40 Per House you Own, Plus $115 for Each Hotel You Own","You Won Second Prize In A Local Beauty Contest. Try Harder, Loser. And Clean Yourself Up! Take this $10.","Your Least Favorite Relative Kicked The Bucket And You Inherit $100 From The Bastard"};
    String[] chanceMessages = {"Advance to Go. Collect $200.","Advance to Illinois Avenue","Advance to St. Charles' Place","Advance to the Nearest Utility","Advance to the Nearest Railroad","Advance to the Nearest Railroad","Bank pays you dividend of $50","Receive a 'Get Out of Jail Free Card'","Go Back Three Spaces","Go Directly To Jail","Make General Repairs On All Your Property: For Each House, Pay $40; For Each Hotel, Pay $100","Pay A Poor Tax Of $50","Take A Trip To The Reading Railroad","Take A Walk To Boardwalk","You Have Been Elected Chairman Of The Board: Pay Each Other Player $50","Your Building And Loan Matures: Collect $150","You Have Won A Crossword Competition: Collect $100"};

    //Dice Frame Objects
    Dice[] dice = new Dice[2];
    JPanel dicePanel = new JPanel();
    JPanel centerDice =new JPanel(new GridLayout(1,2));
    JPanel dummyPanel = new JPanel();
    JPanel endRollPanel = new JPanel(new GridLayout(1,2));
    JButton rollButton = new JButton("Roll");
    JButton endTurnButton = new JButton("End the Turn");
    JFrame diceFrame = new JFrame();

    //Property Preview Objects
    JFrame propertyPreview = new JFrame();
    JPanel entirePanel = new JPanel(new BorderLayout());
    JPanel titlePanel = new JPanel(new GridLayout(2,1));
    JLabel titleDeed = new JLabel("TITLE DEED", SwingConstants.CENTER);
    JLabel propertyTitle = new JLabel("",SwingConstants.CENTER);
    JPanel centerPreviewPanel = new JPanel(new BorderLayout());
    JLabel rentLabel = new JLabel("",SwingConstants.CENTER);
    JLabel[] houseCostLabel = new JLabel[4];
    JLabel[] houseCostLabelActualNumber = new JLabel[4];
    JPanel rentContainer = new JPanel(new GridLayout(2,2));
    JPanel smooshRentContainer = new JPanel(new GridLayout(1,2));
    JPanel leftRentContainer = new JPanel(new GridLayout(4,1));
    JPanel rightRentContainer = new JPanel(new GridLayout(4,1));
    JPanel miscelanneousContainer = new JPanel(new BorderLayout());
    JLabel hotelRentLabel =new JLabel("",SwingConstants.CENTER);
    JPanel excessInfoPanel = new JPanel(new GridLayout(3,1));
    JLabel mortgageLabel = new JLabel("",SwingConstants.LEFT);
    JLabel housesCostLabel = new JLabel("",SwingConstants.LEFT);
    JLabel hotelsCostLabel = new JLabel("",SwingConstants.LEFT);
    JLabel ownerLabel = new JLabel("",SwingConstants.CENTER);

    //Setting Tracker Info
    JFrame playerInfoFrame = new JFrame();
    TrackerInfo[] playerPanel;
    JPanel trackerHolder;
    Player[] currentPlayers;
    Player onPlay=new Player();
    int numPlayers=0;
    int rollTotal=0;
    int newIndex, oldIndex;

    //Activation of KeyEvents
    boolean activate=false;

    //Mortgage Frame
    JFrame mortgageFrame = new JFrame();
    JPanel northMortPanel = new JPanel(new GridLayout(1,2));
    JPanel boxMortPanel = new JPanel(new GridLayout(1,2));
    JPanel southMortPanel = new JPanel(new GridLayout(1,2));
    JPanel mortgagePanel = new JPanel(new BorderLayout());
    JLabel mortgageNameLabel = new JLabel("Player", SwingConstants.CENTER);
    JLabel mortgageMoneyLabel = new JLabel("Money",SwingConstants.CENTER);
    JComboBox<String> mortgagableProps = new JComboBox<String>();
    JComboBox<String> unmortgagableProps = new JComboBox<String>();
    JButton mortgageButton =new JButton("Mortgage Selected Property");
    JButton unmortgageButton = new JButton("Unmortgage Selected Property");
    DefaultListCellRenderer comboRender = new DefaultListCellRenderer();
    ArrayList<Property> mortProps;
    ArrayList<Property> unMortProps;

    //Utilities Frame
    JFrame utilityFrame = new JFrame();
    JPanel holdingPanel = new JPanel(new GridLayout(2,1));
    JLabel imageLabel = new JLabel("",SwingConstants.CENTER);
    JLabel titleLabel = new JLabel("",SwingConstants.CENTER);
    JPanel utilityInfoPanel = new JPanel(new BorderLayout());
    JTextArea costArea = new JTextArea("If one 'Utility' is owned, rent is 4 times\nthe amount shown on both dice.\n\nIf both 'Utilities' are owned, rent is 10\ntimes the amount shown on both\ndice.");
    JLabel mortgageUtilityLabel = new JLabel("Mortgage Value: $75", SwingConstants.CENTER);
    JLabel ownerUtilityLabel = new JLabel("Owned by: ", SwingConstants.CENTER);
    JPanel utilitySouthPanel = new JPanel(new GridLayout(2,1));

    //Railroad Frame
    JFrame railRoadFrame = new JFrame();
    JPanel holdingRailRoadPanel = new JPanel(new GridLayout(2,1));
    JLabel imageRailRoadLabel = new JLabel("",SwingConstants.CENTER);
    JLabel titleRailRoadLabel = new JLabel("",SwingConstants.CENTER);
    JPanel railRoadInfoPanel = new JPanel(new BorderLayout());
    JTextArea costRailRoadArea = new JTextArea("Rent w/ 1 Railroad Owned              $25\nRent w/ 2 Railroads Owned            $50\nRent w/ 3 Railroads Owned          $100\nRent w/ 4 Railroads Owned          $200");
    JLabel mortgageRailRoadLabel = new JLabel("Mortgage Value: $100", SwingConstants.CENTER);
    JLabel ownerRailRoadLabel = new JLabel("Owned by: ", SwingConstants.CENTER);
    JPanel railRoadSouthPanel = new JPanel(new GridLayout(2,1));

    //Special Non-Property Spaces Display Objects
    JFrame extraFrame = new JFrame();
    JLabel extraImageLabel = new JLabel("",SwingConstants.CENTER);
    JPanel holderWHITE = new JPanel();
    int freeParking = 0;
    boolean playingWithFreeParking=false;

    //Trading Frame & Upgrade Frames
    TradingFrame[] tradingFrames;
    UpgradeFrame upgradeFrames;
    boolean duplication=false;

    //Forced Mortgage Frame
    JFrame forceFrame = new JFrame();
    JLabel playerLabelForce = new JLabel("", SwingConstants.CENTER);
    JLabel moneyLabelForce = new JLabel("",SwingConstants.CENTER);
    JComboBox<String> forceBox = new JComboBox<String>();
    JButton mortgageForceButton = new JButton("Mortgage Selected Property");
    JPanel forcePanel = new JPanel(new BorderLayout());
    JPanel forceNorth = new JPanel(new GridLayout(1,2));
    Player appropriatePlayer = new Player();

    //Frame for Property Close Preview
    JFrame imageFrame = new JFrame();
    JPanel hotelPanel = new JPanel();
    JPanel housePanel = new JPanel(new GridLayout(1,4));
    JPanel[] houseImagePanel = new JPanel[4];
    JPanel northImagePanel = new JPanel(new GridLayout(2,1));
    JPanel centerImagePanel = new JPanel(new GridLayout(2,2));
    JPanel imageHolderPanel = new JPanel(new BorderLayout());
    JLabel[] imagePanel = new JLabel[4];

    //Banner Frame
    JFrame banner = new JFrame();
    JLabel bannerHolder = new JLabel("",SwingConstants.CENTER);

    //Animations
    ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
    AtomicBoolean sesRunBool = new AtomicBoolean(false);

    //Token Frame for Non-Property Spaces
    JFrame tokenFrame = new JFrame();
    JPanel tokenPanel = new JPanel(new GridLayout(2,2));
    JLabel[] tokenLabel = new JLabel[4];

    //Testing Variables
    final int DELAY=100;

    //Testing Constructor
    public MonopolyBoard(){}

    public MonopolyBoard(int numPlayers, Player[] players, int freeParking, boolean useFreeParking){
        //Aesthetics and KeyEvent Operations
        this.setUndecorated(true);
        this.addKeyListener(this);

        //Testing Delay Schedule
        //delay = Integer.parseInt(JOptionPane.showInputDialog(banner,"Delay value?"));

        //Prevents NullPointerExceptions
        currentPlayers=new Player[numPlayers];
        this.freeParking=freeParking;
        this.numPlayers=numPlayers;
        this.currentPlayers=players;
        this.setResizable(false);
        tradingFrames=new TradingFrame[numPlayers];

        //FreeParking check (obviously...)
        if(useFreeParking)
            playingWithFreeParking=true;

        //Sets up default properties to be modified later, and then creates the board
        for(int x=0; x<spaces.length; x++){
            spaces[x] = new Property(Color.WHITE, "",0,0,0,0,0,0,0,0,false,false, new Player(),0,0,0,0);
            board.add(spaces[x]);
        }

        //Setting up an index system to add properties appropriately to tracker frames (so you can more easily manage monopolies)
        for(int x=0; x<propertyIndexes.length; x++)
            spaces[propertyIndexes[x]].setManageIndex(x+1);

        //Sets up the special spaces with actual information
        for(int x=0; x<importantIndexes.length; x++){
            spaces[importantIndexes[x]].setBackgroundColor(importantColor[x]);
            spaces[importantIndexes[x]].setName(names[x]);
            spaces[importantIndexes[x]].setBorders(compound);
            spaces[importantIndexes[x]].setPricePerHouse(pricePerHouse[x]);
            spaces[importantIndexes[x]].setStandardRent(standardRentPrice[x]);
            spaces[importantIndexes[x]].setOneHouseRent(rentAfterOneHouse[x]);
            spaces[importantIndexes[x]].setTwoHouseRent(rentAfterTwoHouses[x]);
            spaces[importantIndexes[x]].setThreeHouseRent(rentAfterThreeHouses[x]);
            spaces[importantIndexes[x]].setFourHouseRent(rentAfterFourHouses[x]);
            spaces[importantIndexes[x]].setHotelRent(rentAfterHotel[x]);
            spaces[importantIndexes[x]].setMortgageValue(mortgageValue[x]);
            spaces[importantIndexes[x]].addMouseMotionListener(this);
            spaces[importantIndexes[x]].setCostToBuy(buyPrice[x]);

            //Gets rid of the property header area for spaces without color identity
            if(spaces[importantIndexes[x]].propertyColor == Color.WHITE){
                spaces[importantIndexes[x]].remove(spaces[importantIndexes[x]].northField);
            }
        }

        for(int x=0; x<nonPropertyIndex.length; x++)
            nonPropertyIndexes.add(nonPropertyIndex[x]);

        this.add(board);
        this.addMouseMotionListener(this);

        //Perform Special Operations For Later-Game Functionality
        initializePropertyPreview();
        attachNewIndex();
        createPlayerInfoGUI(numPlayers, players);
        diceInitialization();
        utilityViewerInitialization();
        railRoadViewerInitialization();
        specialSpacesInitialization();
        initializeMortgageFrame();
        initializeTradingFrames();
        initializeForceFrame();
        initializeImageViewer();
        createBannerFrame();
        recolorize();
        createNonPropTokenFrame();
    }

    //Creates Banner Logo For Board
    public void createBannerFrame(){
        banner.add(bannerHolder,BorderLayout.CENTER);
        banner.setAlwaysOnTop(true);
        banner.setBounds(215,455,590,110);
        bannerHolder.setIcon(new ImageIcon(new File("Images\\banner.png").getAbsolutePath()));
        banner.setUndecorated(true);
        banner.setVisible(true);
    }

    //Creates Non-Property Token Frame
    public void createNonPropTokenFrame() {
        tokenFrame.add(tokenPanel);
        tokenFrame.setBounds(1035,663,450,350);

        for (int x = 0; x < 4; x++) {
            tokenLabel[x] = new JLabel("", SwingConstants.CENTER);
            tokenPanel.add(tokenLabel[x]);
            tokenLabel[x].setBorder(compound);
        }
        tokenPanel.setBorder(compound);
    }

    //Recolors JOptionPanes/Panels
    public void recolorize(){
        new UIManager().put("OptionPane.background", Color.WHITE);
        new UIManager().put("Panel.background", Color.WHITE);
    }

    //Creates the frame for special non-property spaces' display
    public void specialSpacesInitialization(){
        extraFrame.setBounds(1510,520,250,285);
        extraFrame.setTitle("Preview");
        extraFrame.add(holderWHITE);
            holderWHITE.setBackground(Color.WHITE);
            holderWHITE.add(extraImageLabel);
            holderWHITE.setBorder(compound);
                extraImageLabel.setBackground(Color.WHITE);
                extraFrame.setBackground(Color.WHITE);
    }

    //Creates a dice frame for where the dice can be interacted with
    public void diceInitialization(){
        diceFrame.setBounds(1510,335,250,160);
        diceFrame.setTitle("Roll");
        diceFrame.setResizable(false);
        diceFrame.setVisible(true);
        diceFrame.add(dicePanel);
            dicePanel.setLayout(new BorderLayout());
            dicePanel.add(centerDice, BorderLayout.CENTER);
                dicePanel.setBorder(compound);

        for(int i=0; i<dice.length; i++){
            dice[i] = new Dice();
            dice[i].setBackgroundImage(new Random().nextInt(5)+1);
            centerDice.add(dice[i]);
        }
        dicePanel.add(endRollPanel, BorderLayout.SOUTH);
        endRollPanel.setBorder(compound);
            endRollPanel.add(rollButton);
                rollButton.setMnemonic('R');
            endRollPanel.add(endTurnButton);
                endTurnButton.addActionListener(this);
                endTurnButton.setMnemonic('E');
                endTurnButton.setBorder(compound);
                    endTurnButton.setEnabled(false);
            rollButton.setBorder(compound);
            rollButton.addActionListener(this);
    }

    //Creates a frame for each player to monitor their stats (money, properties, jail_status, etc.)
    public void createPlayerInfoGUI(int numPlayers, Player[] players){
        playerPanel = new TrackerInfo[numPlayers];
        trackerHolder = new JPanel(new GridLayout(numPlayers, 1));
        playerInfoFrame = new JFrame();
        playerInfoFrame.setResizable(false);
        playerInfoFrame.add(trackerHolder);
        for(int x = 0; x < players.length; x++){
            playerPanel[x] = new TrackerInfo(players[x]);
            trackerHolder.add(playerPanel[x]);
        }
        playerInfoFrame.setBounds(1035,10,450,numPlayers*160);
        playerInfoFrame.setTitle("Player Information Monitor");
        playerInfoFrame.setVisible(true);
    }

    //Aesthetic Fix to Removing Panels from Inner Frame
    public void attachNewIndex(){
        for(int i=0; i<importantIndexes.length; i++)
            others.add(importantIndexes[i]);

        for(int x=0; x<spaces.length; x++){
            if(!others.contains(x)){
                spaces[x].remove(spaces[x].colorMonitor);
                spaces[x].workAround(Color.WHITE);
            }
        }
    }

    //Rolls Dice
    public int rollTotal(Dice[] dice){
        int returnInt = 0;
        for(int x=0; x<dice.length; x++)
            returnInt+=dice[x].currentVal;
        return returnInt;
    }

    //Creates the Property Preview Frame
    public void initializePropertyPreview(){
        propertyPreview.add(entirePanel);
        propertyPreview.setResizable(false);
        propertyPreview.setBounds(1510,10,250,300);
        propertyPreview.setTitle("Preview");
        propertyPreview.setVisible(true);
            entirePanel.add(titlePanel, BorderLayout.NORTH);
                titlePanel.setBorder(compound);
                titlePanel.add(titleDeed);
                titlePanel.add(propertyTitle);
            entirePanel.add(centerPreviewPanel, BorderLayout.CENTER);
                centerPreviewPanel.setBorder(compound);
                centerPreviewPanel.add(rentLabel, BorderLayout.NORTH);
                centerPreviewPanel.add(rentContainer, BorderLayout.CENTER);
                    rentContainer.add(smooshRentContainer,BorderLayout.CENTER);
                        smooshRentContainer.add(leftRentContainer);
                        smooshRentContainer.add(rightRentContainer);
                    rentContainer.add(miscelanneousContainer);
                        miscelanneousContainer.add(hotelRentLabel,BorderLayout.NORTH);
                        miscelanneousContainer.add(excessInfoPanel, BorderLayout.CENTER);
                            excessInfoPanel.add(mortgageLabel);
                            excessInfoPanel.add(housesCostLabel);
                            excessInfoPanel.add(hotelsCostLabel);
                        miscelanneousContainer.add(ownerLabel,BorderLayout.SOUTH);

            for(int i=0; i<houseCostLabel.length; i++){
                houseCostLabel[i] = new JLabel("Rent w/ "+(i+1)+" House(s):",SwingConstants.LEFT);
                houseCostLabelActualNumber[i] = new JLabel("",SwingConstants.RIGHT);
                leftRentContainer.add(houseCostLabel[i]);
                rightRentContainer.add(houseCostLabelActualNumber[i]);
            }
    }

    //Creates the Mortgage Frame for Special Menu Functions
    public void initializeMortgageFrame(){
        comboRender.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        mortgageFrame.setTitle("Mortgage Frame");
        mortgageFrame.setBounds(100,100,470,130);
        mortgageFrame.add(mortgagePanel);
        mortgageFrame.setAlwaysOnTop(true);
            mortgagePanel.setBorder(compound);
            mortgagePanel.add(northMortPanel, BorderLayout.NORTH);
                northMortPanel.add(mortgageNameLabel);
                    mortgageNameLabel.setBorder(compound);
                northMortPanel.add(mortgageMoneyLabel);
                    mortgageMoneyLabel.setBorder(compound);
            mortgagePanel.add(boxMortPanel, BorderLayout.CENTER);
                boxMortPanel.add(mortgagableProps);
                    mortgagableProps.setRenderer(comboRender);
                    mortgagableProps.setBorder(compound);
                    unmortgagableProps.setRenderer(comboRender);
                boxMortPanel.add(unmortgagableProps);
                    unmortgagableProps.setBorder(compound);
            mortgagePanel.add(southMortPanel, BorderLayout.SOUTH);
                southMortPanel.add(mortgageButton);
                    mortgageButton.setBorder(compound);
                    mortgageButton.addActionListener(this);
                southMortPanel.add(unmortgageButton);
                    unmortgageButton.setBorder(compound);
                    unmortgageButton.addActionListener(this);
    }

    //Rolls Dice and Returns Total (for Utility rent calc)
    public int rollDiceAndReturnTotal(Dice[] dice){
        for(int x=0; x<dice.length; x++)
            dice[x].roll();

        return rollTotal(dice);
    }

    public boolean getDoubles(Dice[] dice){
        if(dice[0].getCurrentVal() == dice[1].getCurrentVal())
            return true;

        return false;
    }

    public void actionPerformed(ActionEvent e){
        //Gives Functionality to a player to move them
        if(e.getSource() == rollButton){
            boolean boolCheck = false;
            //Gives options for player if in jail, and performs cleanup for the movement if they are statically placed
            if(getCurrentPlayer().isInJail()){
                if(getCurrentPlayer().isHasGetOutOfJailFreeCard()){
                    int checkCard = JOptionPane.showConfirmDialog(banner, "Would you like to use your 'Get out of jail free' card?", "Get out of jail free", JOptionPane.YES_NO_OPTION);
                    if(checkCard == JOptionPane.YES_OPTION){
                        rollTotal = rollDiceAndReturnTotal(dice);
                        JOptionPane.showMessageDialog(banner, "Okay. You're free to go, but you lose your 'Get out of jail free' card.", "Out of Jail", 1);
                        getCurrentPlayer().setHasGetOutOfJailFreeCard(false);
                        getCurrentPlayer().setInJail(false);
                        getCurrentPlayer().setJailRolls(0);
                        boolCheck = true;
                        movePlayer(rollTotal);
                    } else if(checkCard == JOptionPane.NO_OPTION){
                        int payCheck = JOptionPane.showConfirmDialog(banner, "Would you like to pay $50 to get out?", "Pay to leave", JOptionPane.YES_NO_OPTION);
                        if(payCheck == JOptionPane.YES_OPTION && getCurrentPlayer().getTotalMoney()>=50){
                            getCurrentPlayer().setInJail(false);
                            getCurrentPlayer().subtractMoney(50);
                            getCurrentPlayer().setJailRolls(0);
                            rollTotal = rollDiceAndReturnTotal(dice);
                            boolCheck = true;
                            movePlayer(rollTotal);
                        }
                        else if(payCheck==JOptionPane.YES_OPTION && getCurrentPlayer().getTotalMoney()<50)
                            JOptionPane.showMessageDialog(banner,"You can't afford to pay that. So, you must roll.","Too Poor",3);

                        else
                            JOptionPane.showMessageDialog(banner, "Okay. Then you must roll to get out.", "Out of Jail", 3);
                    }
                } else if(!getCurrentPlayer().isHasGetOutOfJailFreeCard()){
                    int checkPay = JOptionPane.showConfirmDialog(banner, "Would you like to pay $50 to get out of jail?", "Pay to leave", JOptionPane.YES_NO_OPTION);
                    if(checkPay == JOptionPane.YES_OPTION){
                        JOptionPane.showMessageDialog(banner, "Okay. You're free to go; but first pay $50.", "Out of Jail", 1);
                        getCurrentPlayer().subtractMoney(50);
                        getCurrentPlayer().setInJail(false);
                        getCurrentPlayer().setJailRolls(0);
                        rollTotal = rollDiceAndReturnTotal(dice);
                        boolCheck = true;
                        movePlayer(rollTotal);
                    } else
                        JOptionPane.showMessageDialog(banner, "Okay. Then you must roll to get out.", "Out of Jail", 3);
                }
                if(getCurrentPlayer().isInJail()){
                    rollTotal = rollDiceAndReturnTotal(dice);
                    if(dice[0].getCurrentVal() == dice[1].getCurrentVal()){
                        getCurrentPlayer().setJailRolls(0);
                        getCurrentPlayer().setInJail(false);
                        JOptionPane.showMessageDialog(banner, "You rolled doubles, and you are free to leave jail, like in real life.", "Freedom", 1);
                        movePlayer(rollTotal);
                        boolCheck = true;
                    } else{
                        getCurrentPlayer().addJailRolls();
                        JOptionPane.showMessageDialog(banner, (3-getCurrentPlayer().getJailRolls()!=0)?"You're stuck in jail. In " + (3 - getCurrentPlayer().getJailRolls()) + " turn(s), you'll be forced to pay bail.":"This is your third attempt to roll doubles, and you have failed. Please pay $50 and leave.", "Freedom", 1);
                    }

                    if(getCurrentPlayer().getJailRolls() == 3){
                        if(getCurrentPlayer().isHasGetOutOfJailFreeCard()){
                            int checkCardUse = JOptionPane.showConfirmDialog(banner, "This is your third unsuccessful roll in jail: you need to pay now. Or, you may use your 'Get out of jail free' card. Would you like to use it?", "Get out of jail free", JOptionPane.YES_NO_OPTION);
                            if(checkCardUse == JOptionPane.YES_OPTION){
                                JOptionPane.showMessageDialog(banner, "Okay. You're free to go, but you lose your 'Get out of jail free' card.", "Out of Jail", 1);
                                getCurrentPlayer().setHasGetOutOfJailFreeCard(false);
                                getCurrentPlayer().setInJail(false);
                                boolCheck = true;
                                movePlayer(rollTotal);
                            }
                            else if(checkCardUse == JOptionPane.NO_OPTION){
                                JOptionPane.showMessageDialog(banner, "Okay. You're free to go, but you must pay $50 to leave.", "Out of Jail", 3);
                                getCurrentPlayer().subtractMoney(50);
                                getCurrentPlayer().setInJail(false);
                                movePlayer(rollTotal);
                                boolCheck = true;
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(banner,"This was your third unsuccessful roll in jail: now, pay your $50 fine.","Get out of Jail",3);
                            getCurrentPlayer().subtractMoney(50);
                            getCurrentPlayer().setInJail(false);
                            movePlayer(rollTotal);
                            boolCheck=true;
                        }
                        getCurrentPlayer().setJailRolls(0);
                    }
                }
            }

            //Remainder code to move player and check for doubles/triple doubles rolled
            if(!getCurrentPlayer().isInJail() && !boolCheck){
                rollTotal = rollDiceAndReturnTotal(dice);
                if(getDoubles(dice) && !getCurrentPlayer().isInJail()){
                    getCurrentPlayer().setDoublesCount(getCurrentPlayer().getDoublesCount() + 1);

                    if(getCurrentPlayer().getDoublesCount() == 3){
                        JOptionPane.showMessageDialog(banner,"You rolled doubles three times in succession. Go to jail.","Speeding",3);
                        getCurrentPlayer().setInJail(true);
                        int checkVal = getCurrentPlayer().getPositionIndex();
                        getCurrentPlayer().setPositionIndex(10);
                        getCurrentPlayer().setDoublesCount(0);
                        repaintSpaces(checkVal,10,getCurrentPlayer());
                        rollButton.setEnabled(false);
                        endTurnButton.setEnabled(true);
                        activate=true;
                    }
                    else{
                        movePlayer(rollTotal);
                        activate=true;

                        if(!getCurrentPlayer().isInJail() && sesRunBool.get())
                            JOptionPane.showOptionDialog(banner, "You've rolled doubles. Roll again.", "Doubles", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Continue"}, null);
                    }
                }
                else if(!getDoubles(dice)){
                    getCurrentPlayer().setDoublesCount(0);
                    movePlayer(rollTotal);
                }
            }

            if(!getDoubles(dice)){
                rollButton.setEnabled(false);
                endTurnButton.setEnabled(true);
                activate=true;
            }
        }

        //Runs the passturn cleanup
        else if(e.getSource()==endTurnButton)
            if(JOptionPane.showConfirmDialog(banner,"Are you sure you are done with your turn?","End Turn?",JOptionPane.YES_NO_OPTION)==0)
                passTurn();

        else if(e.getSource()==mortgageButton){
            if(mortgagableProps.getSelectedIndex()==0)
                JOptionPane.showMessageDialog(banner,"You cannot mortgage that. It isn't a property.","Not a Valid Selection",3);

            else{
                for(int x=0; x<mortProps.size(); x++){
                    if(mortgagableProps.getSelectedItem().equals(mortProps.get(x).getName())){
                        Property mortgagedProperty = mortProps.get(x);
                        JOptionPane.showMessageDialog(banner,"You have successfully mortgaged "+mortProps.get(x).getName()+" for $"+mortProps.get(x).getMortgageValue()+".","Mortgage Successful",1);
                        getCurrentPlayer().addMoney(mortgagedProperty.getMortgageValue());
                        mortgagedProperty.setMortgageState(true);
                        unMortProps.add(mortgagedProperty);

                        mortgagableProps.removeItem(mortgagedProperty.getName());
                        unmortgagableProps.addItem(mortgagedProperty.getName());
                        mortProps.remove(mortgagedProperty);
                        unMortProps.add(mortgagedProperty);
                    }
                }
            }
            mortgageMoneyLabel.setText("Money: $"+getCurrentPlayer().getTotalMoney());
        }
        else if(e.getSource()==unmortgageButton){
            boolean update=false;
            if(unmortgagableProps.getSelectedIndex()==0)
                JOptionPane.showMessageDialog(banner,"You cannot unmortgage that. It isn't a property.","Invalid Choice",3);

            else{
                for(int x=0; x<unMortProps.size(); x++){
                    if(unmortgagableProps.getSelectedItem().equals(unMortProps.get(x).getName()) && !update){
                        Property unmortgagedProperty = unMortProps.get(x);
                        if(getCurrentPlayer().getTotalMoney()<unmortgagedProperty.getMortgageValue())
                            JOptionPane.showMessageDialog(banner,"You do not have sufficient funds to unmortgage "+unmortgagedProperty.getName()+".","Not Enough Money",3);
                        else{
                            JOptionPane.showMessageDialog(banner, "You pay the unmortgage fee of $"+unMortProps.get(x).getMortgageValue()+". Now, "+unMortProps.get(x).getName()+" is back for rent again.","Succesfully Unmortgaged Property",1);
                            getCurrentPlayer().subtractMoney(unmortgagedProperty.getMortgageValue());
                            unmortgagedProperty.setMortgageState(true);
                            mortProps.add(unmortgagedProperty);

                            mortgagableProps.addItem(unmortgagedProperty.getName());
                            unmortgagableProps.removeItem(unmortgagedProperty.getName());
                            mortProps.add(unmortgagedProperty);
                            unMortProps.remove(unmortgagedProperty);
                            update = true;
                        }
                    }
                }
                mortgageMoneyLabel.setText("Money: $"+getCurrentPlayer().getTotalMoney());
            }
        }

        //When you are being forced to mortgage properties if you are in the negative, this is the button that mortgages the appropriately indexed property of the combobox
        else if(e.getSource()==mortgageForceButton){
            if(forceBox.getSelectedIndex()==0)
                JOptionPane.showMessageDialog(banner,"You cannot mortgage that. It isn't a property.","Invalid Choice",3);

            else{
                for(int x=0; x<mortProps.size(); x++){
                    if(forceBox.getSelectedItem().equals(mortProps.get(x).getName())){
                        Property mortgagedProperty = mortProps.get(x);
                        JOptionPane.showMessageDialog(banner,"You have successfully mortgaged "+mortProps.get(x).getName()+" for $"+mortProps.get(x).getMortgageValue()+".","Mortgage Successful",1);
                        appropriatePlayer.addMoney(mortgagedProperty.getMortgageValue());
                        mortgagedProperty.setMortgageState(true);
                        forceBox.removeItem(mortgagedProperty.getName());
                        mortProps.remove(mortgagedProperty);
                    }
                }
            }
            moneyLabelForce.setText("Money: $"+appropriatePlayer.getTotalMoney());

            if(appropriatePlayer.getTotalMoney()<=0)
                JOptionPane.showMessageDialog(banner,"You are still in the red. You must continue mortgaging properties.","Negative Balance",3);

            else if(appropriatePlayer.getTotalMoney()>0){
                forceFrame.setVisible(false);
                JOptionPane.showMessageDialog(banner,"You are successfully out of the hole. Continue your game from less.","Positive Balance",1);
                activate=true;
                endTurnButton.setEnabled(true);
            }
        }

        for(int a=0; a<currentPlayers.length; a++)
            resetJComboBox(currentPlayers[a]);
        refreshTrackerInfo();
    }

    //Recursive player movement method
    public void movePlayer(int move){
        sesRunBool.set(false);
        oldIndex = getCurrentPlayer().getPositionIndex();
        onPlay = getCurrentPlayer();
        if(getCurrentPlayer().positionIndex + 1 >= 40){
            JOptionPane.showMessageDialog(banner, "You have passed go. Collect $200.");
            getCurrentPlayer().addMoney(200);
        }

        newIndex = (getCurrentPlayer().positionIndex + 1 >= 40) ? 0 : getCurrentPlayer().positionIndex + 1;

        if(move != 1){
            //Delay = time between movements
            Runnable repeatTask = () -> movePlayer(move-1);
            ses.schedule(repeatTask, DELAY, TimeUnit.MILLISECONDS);
        }
        if(move==1)
            sesRunBool.set(true);

        getCurrentPlayer().positionIndex = newIndex;
        repaintSpaces(oldIndex, newIndex, getCurrentPlayer());

        if(sesRunBool.get())
            performBuyOperation(getCurrentPlayer());
    }

    //Creates the trading frame for each player
    public void initializeTradingFrames(){
        for(int x=0; x<currentPlayers.length; x++)
            tradingFrames[x] = new TradingFrame(currentPlayers[x],false,false,this);
    }

    //Gets the player whose turn it currently is
    public Player getCurrentPlayer(){
        for(int x=0; x<currentPlayers.length; x++)
            if(currentPlayers[x].isHasCurrentTurn())
                return currentPlayers[x];
        return null;
    }

    //The cleanup for when a player ends their turn
    public void passTurn(){
        Player checkThem= new Player();
        if(getCurrentPlayer().getPlayerNumber()==(numPlayers-1)){
            returnPlayerWithNum(numPlayers-1).setHasCurrentTurn(false);
            returnPlayerWithNum(0).setHasCurrentTurn(true);
        }

        else{
            checkThem = returnPlayerWithNum(getCurrentPlayer().getPlayerNumber()+1);
            returnPlayerWithNum(getCurrentPlayer().getPlayerNumber()+1).setHasCurrentTurn(true);
            returnPlayerWithNum(getCurrentPlayer().getPlayerNumber()).setHasCurrentTurn(false);
        }
        if(checkThem.isBankrupt())
            passTurn();

        JOptionPane.showMessageDialog(banner,getCurrentPlayer().getName()+", it's your turn.","A New Turn",1);
        rollButton.setEnabled(true);
        endTurnButton.setEnabled(false);
        activate=false;
        duplication=false;
        refreshTrackerInfo();
    }

    //Gets all other players who are not currently taking their turn
    public ArrayList<Player> getOtherPlayers(){
        Player current =  getCurrentPlayer();
        ArrayList<Player> others = new ArrayList<Player>(numPlayers-1);
        for(int x=0; x<currentPlayers.length; x++){
            if(!currentPlayers[x].getName().equals(current.getName()))
                others.add(currentPlayers[x]);
        }
        return others;
    }

    //Gets the player with the appropriately given player number (used for turn order)
    public Player returnPlayerWithNum(int num){
        for(int x=0; x<currentPlayers.length; x++)
            if(currentPlayers[x].getPlayerNumber()==num)
                return currentPlayers[x];

        return null;
    }

    //Just repaints and refreshes the info on the tracker panel (where player information is displayed)
    public void refreshTrackerInfo(){
        for(int x=0; x<playerPanel.length; x++)
            playerPanel[x].refreshLabels();
    }

    //Finds the trading frame for the player when asked by other players
    public TradingFrame getTradingFrame(Player player){
        for(int x=0; x<tradingFrames.length; x++)
            if(tradingFrames[x].getPlayer()==player)
                return tradingFrames[x];
        return null;
    }

    //Performs the various operations associated with buying each property (normal props, railroads, utilities)
    public void performBuyOperation(Player currentPlayer){
        for(int x=0; x<importantIndexes.length; x++){
            //Are you on a space to buy a property
            if(getCurrentPlayer().getPositionIndex() == x && !nonPropertyIndexes.contains(x)){
                //Is the property owned? If not, you can buy it. Otherwise, you pay rent.
                if(!spaces[importantIndexes[x]].isOwned() && spaces[importantIndexes[x]].getOwner() !=currentPlayer){
                    int buyInput = JOptionPane.showConfirmDialog(banner, "Would you like to buy " + spaces[importantIndexes[x]].getName() + " for $" + spaces[importantIndexes[x]].getCostToBuy() + "?", "Buy a Property", JOptionPane.YES_NO_OPTION);
                    if(buyInput == 0 && getCurrentPlayer().getTotalMoney() >= spaces[importantIndexes[x]].getCostToBuy()){
                        JOptionPane.showMessageDialog(banner, "You are the proud owner of " + spaces[importantIndexes[x]].getName() + ".", "Purchase Success", 1);
                        getCurrentPlayer().addProperty(spaces[importantIndexes[x]]);
                        spaces[importantIndexes[x]].setOwned(true);
                        getCurrentPlayer().setTotalMoney(getCurrentPlayer().getTotalMoney() - spaces[importantIndexes[x]].getCostToBuy());
                        spaces[importantIndexes[x]].setOwner(getCurrentPlayer());

                        for(int a = 0; a < playerPanel.length; a++)
                            if(playerPanel[a].player.getName().equals(getCurrentPlayer().getName())){
                                playerPanel[a].moneyLabel.setText("$" + getCurrentPlayer().getTotalMoney());
                                playerPanel[a].addProperty(spaces[importantIndexes[x]]);
                            }
                    } else if(buyInput == 0 && getCurrentPlayer().getTotalMoney() < spaces[importantIndexes[x]].getCostToBuy()){
                        JOptionPane.showMessageDialog(banner, "No can do. You cannot afford this property.", "Purchase Unsuccessful", 1);
                    }
                }
                //Paying Rent For General Properties
                else if(spaces[importantIndexes[x]].isOwned() && !spaces[importantIndexes[x]].getName().equals("Reading Railroad")&& !spaces[importantIndexes[x]].getName().equals("Pennsylvania Railroad")&& !spaces[importantIndexes[x]].getName().equals("B&O Railroad")&& !spaces[importantIndexes[x]].getName().equals("Short Line Railroad")&& !spaces[importantIndexes[x]].getName().equals("Electric Company")&& !spaces[importantIndexes[x]].getName().equals("Water Works")){
                    if(!spaces[importantIndexes[x]].isMortgaged()){
                        Player owner = spaces[importantIndexes[x]].getOwner();
                        int rent = spaces[importantIndexes[x]].getRent(spaces[importantIndexes[x]].getNumHouses(), spaces[importantIndexes[x]].getNumHotels());

                        if(checkForMonopoly(spaces[importantIndexes[x]].getBackgroundColor(), owner) && spaces[importantIndexes[x]].getNumHouses() == 0 && spaces[importantIndexes[x]].getNumHotels() == 0)
                            rent*=2;

                        JOptionPane.showMessageDialog(banner, "This space is owned by " + owner.getName() + ", and you owe them $" + rent + ".", "Paying Rent", 1);
                        getCurrentPlayer().subtractMoney(rent);
                        if(!getCurrentPlayer().isBankrupt() && getCurrentPlayer().getTotalMoney()<=0)
                            forceMortgageProcedure(getCurrentPlayer());

                        else if(getCurrentPlayer().isBankrupt() && getCurrentPlayer().getIdentifier()!=Color.WHITE){
                            initiateBankruptcy(getCurrentPlayer());
                            rent+=(getCurrentPlayer().getTotalMoney()+getCurrentPlayer().returnTotalPropertyWorth());
                        }
                        owner.addMoney(rent);
                    }
                    else
                        JOptionPane.showMessageDialog(banner,"This space is owned by " + spaces[importantIndexes[x]].getOwner()+". However, "+spaces[importantIndexes[x]].getName()+" is currently mortgaged, so you pay nothing.","Mortgaged Property",2);
                }
                //Paying Rent for Railroads
                else if(spaces[importantIndexes[x]].isOwned() && spaces[importantIndexes[x]].getName().equals("Reading Railroad")||spaces[importantIndexes[x]].getName().equals("Pennsylvania Railroad")|| spaces[importantIndexes[x]].getName().equals("B&O Railroad")|| spaces[importantIndexes[x]].getName().equals("Short Line Railroad")){
                    Player owner = spaces[importantIndexes[x]].getOwner();
                    int rent = railRoadRent(owner);

                    JOptionPane.showMessageDialog(banner,"This space is owned by "+owner.getName() + ", and they own "+numRailroads(owner)+ " un-mortgaged Railroad(s). You owe them $"+rent+".","Paying Rent",2);
                    getCurrentPlayer().subtractMoney(rent);
                    if(!getCurrentPlayer().isBankrupt() && getCurrentPlayer().getTotalMoney()<=0)
                        forceMortgageProcedure(getCurrentPlayer());

                    else if(getCurrentPlayer().isBankrupt() && getCurrentPlayer().getIdentifier()!=Color.WHITE){
                        initiateBankruptcy(getCurrentPlayer());
                        rent+=(getCurrentPlayer().getTotalMoney()+getCurrentPlayer().returnTotalPropertyWorth());
                    }
                    owner.addMoney(rent);
                }
                //Paying Rent for utilities
                else if(spaces[importantIndexes[x]].isOwned() && spaces[importantIndexes[x]].getName().equals("Water Works") || spaces[importantIndexes[x]].getName().equals("Electric Company")){
                    Player owner = spaces[importantIndexes[x]].getOwner();
                    int rent = utilitiesRent(owner,rollTotal);
                    JOptionPane.showMessageDialog(banner,"This space is owned by "+owner.getName() + ", and they own "+numUtilities(owner)+ " un-mortgaged Utility(s). You owe them $"+rent+".","Paying Rent",2);
                    getCurrentPlayer().subtractMoney(rent);

                    if(!getCurrentPlayer().isBankrupt() && getCurrentPlayer().getTotalMoney()<=0)
                        forceMortgageProcedure(getCurrentPlayer());

                    else if(getCurrentPlayer().isBankrupt() && getCurrentPlayer().getIdentifier()!=Color.WHITE){
                        initiateBankruptcy(getCurrentPlayer());
                        rent+=(getCurrentPlayer().getTotalMoney()+getCurrentPlayer().returnTotalPropertyWorth());
                    }

                    owner.addMoney(rent);
                }
            }
            refreshTrackerInfo();
        }

        //Community Chest Card Checks
        if(getCurrentPlayer().getPositionIndex()==2 || getCurrentPlayer().getPositionIndex()==17 || getCurrentPlayer().getPositionIndex()==33){
            int currentIndex=getCurrentPlayer().getPositionIndex();
            ArrayList<Player> otherPlayers=new ArrayList<Player>();
            int index = new Random().nextInt(14);

            for(int a=0; a<currentPlayers.length; a++)
                if(!currentPlayers[a].getName().equals(getCurrentPlayer().getName()))
                    otherPlayers.add(currentPlayers[a]);

            CommunityChestCard chestCard = new CommunityChestCard(chestMessages[index],index,getCurrentPlayer(),otherPlayers,this);
            chestCard.generateCommunityCard(chestMessages[index]);
            recolorize();

            for(int x=0; x<currentPlayers.length; x++){
                if(!currentPlayers[x].isBankrupt() && currentPlayers[x].getTotalMoney() <= 0)
                    forceMortgageProcedure(currentPlayers[x]);

                else if(currentPlayers[x].isBankrupt() && currentPlayers[x].getIdentifier()!=Color.WHITE)
                    initiateBankruptcy(currentPlayers[x]);
            }

            repaintSpaces(currentIndex,getCurrentPlayer().getPositionIndex(),currentPlayer);
            refreshTrackerInfo();
            recolorize();
        }

        //Chance Card Checks
        else if(getCurrentPlayer().getPositionIndex()==7 || getCurrentPlayer().getPositionIndex()==22 || getCurrentPlayer().getPositionIndex()==36){
            int currentIndex=getCurrentPlayer().getPositionIndex();
            ArrayList<Player> otherPlayers = new ArrayList<Player>();
            int index = new Random().nextInt(15);

            for(int a=0; a<currentPlayers.length; a++)
                if(!currentPlayers[a].getName().equals(getCurrentPlayer().getName()))
                    otherPlayers.add(currentPlayers[a]);

            ChanceCard newChance = new ChanceCard(chanceMessages[index],index,getCurrentPlayer(),otherPlayers,this);
            boolean chanceCheck = newChance.generateChanceCard(chanceMessages[index]);
            if(index==3){
                rollTotal = rollDiceAndReturnTotal(dice);
                JOptionPane.showMessageDialog(banner, "You have been moved to a utility. If it is unowned, you have rolled a total of " + rollTotal + " for which to pay them.", "Roll Total", 3);
            }
            recolorize();

            if(chanceCheck)
                performBuyOperation(currentPlayer);

            for(int x=0; x<currentPlayers.length; x++){
                if(!currentPlayers[x].isBankrupt() && currentPlayers[x].getTotalMoney() <= 0)
                    forceMortgageProcedure(currentPlayers[x]);

                else if(currentPlayers[x].isBankrupt() && currentPlayers[x].getIdentifier()!=Color.WHITE)
                    initiateBankruptcy(currentPlayers[x]);
            }

            repaintSpaces(currentIndex,getCurrentPlayer().getPositionIndex(),currentPlayer);
            refreshTrackerInfo();
        }

        //Go Directly To Jail
        else if(getCurrentPlayer().getPositionIndex()==30){
            getCurrentPlayer().setPositionIndex(10);
            getCurrentPlayer().setInJail(true);
            repaintSpaces(30,10,currentPlayer);

            //If they rolled doubles but went to jail, they lose their extra roll & their doubles count goes back to 0
            if(dice[0].getCurrentVal()==dice[1].getCurrentVal()){
                getCurrentPlayer().setDoublesCount(0);
                rollButton.setEnabled(false);
                endTurnButton.setEnabled(true);
                activate=true;
            }
            JOptionPane.showMessageDialog(banner,"Go directly to jail.","Jail Time",3);
            refreshTrackerInfo();
        }

        //Income Tax Space
        else if(getCurrentPlayer().getPositionIndex()==4){
            String[] options = {"$200","10%"};
            int choiceOption = JOptionPane.showOptionDialog(banner,"You must pay an income tax. Either pay $200, or pay 10% of your total wealth.","Income Tax Choice", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            switch (choiceOption){
                case JOptionPane.YES_OPTION:
                    getCurrentPlayer().subtractMoney(200);
                    if(playingWithFreeParking)
                        freeParking+=200;
                    break;
                case JOptionPane.NO_OPTION:
                    getCurrentPlayer().subtractMoney((int)Math.round(getCurrentPlayer().getTotalMoney()*.1));
                    if(playingWithFreeParking)
                        freeParking+=(int)Math.round(getCurrentPlayer().getTotalMoney()*.1);
                    break;
            }
            if(playingWithFreeParking)
                JOptionPane.showMessageDialog(banner,"Thank you. Your payment has been added to free parking. If you're lucky, you could recoup your loss by landing there.","Income Tax",1);
            if(!getCurrentPlayer().isBankrupt() && getCurrentPlayer().getTotalMoney()<=0)
                forceMortgageProcedure(getCurrentPlayer());

            else if(getCurrentPlayer().isBankrupt() && getCurrentPlayer().getIdentifier()!=Color.WHITE)
                initiateBankruptcy(getCurrentPlayer());

            refreshTrackerInfo();
        }

        //Luxury Tax code
        else if(getCurrentPlayer().getPositionIndex()==38){
            getCurrentPlayer().subtractMoney(75);
            JOptionPane.showMessageDialog(banner,"You must pay a luxury tax of $75."+((playingWithFreeParking)?" It will be added to free parking.":""),"Luxury Tax",3);
            freeParking+=75;

            if(!getCurrentPlayer().isBankrupt() && getCurrentPlayer().getTotalMoney()<=0)
                forceMortgageProcedure(getCurrentPlayer());

            else if(getCurrentPlayer().isBankrupt() && getCurrentPlayer().getIdentifier()!=Color.WHITE)
                initiateBankruptcy(getCurrentPlayer());

            refreshTrackerInfo();
        }

        //Free Parking Code
        else if(getCurrentPlayer().getPositionIndex()==20){
            if(playingWithFreeParking){
                getCurrentPlayer().addMoney(freeParking);
                JOptionPane.showMessageDialog(banner, (freeParking != 0) ? "You've landed on free parking; there is currently $" + freeParking + " in the fund. You may take it." : "You've landed on free parking. It is unfortunately empty now.", "Free Parking", 1);
                freeParking = 0;
            }
        }

        for(int a=0; a<currentPlayers.length; a++)
            resetJComboBox(currentPlayers[a]);
        refreshTrackerInfo();
    }

    //Recolors property tiles when players move
    public void repaintSpaces(int currentIndex, int newIndex, Player player){
        spaces[importantIndexes[currentIndex]].colors[player.getPlayerNumber()].setBackground(Color.WHITE);
        spaces[importantIndexes[newIndex]].colors[player.getPlayerNumber()].setBackground(player.getIdentifier());
    }

    //Finds the number of railroads a player owns for rent purposes
    public int numRailroads(Player player){
        ArrayList<Property> props =player.getProperties();
        int railroadCounter=0;

        for(int x=0; x<props.size(); x++)
            if(props.get(x).getName().contains("Railroad") && !props.get(x).isMortgaged())
                railroadCounter++;

        return railroadCounter;
    }

    //calculates rent for when a player lands on a railroad
    public int railRoadRent(Player player){
        int railRoads=numRailroads(player);
        if(railRoads==0)
            return 0;
        else if(railRoads==1)
            return 25;
        else if(railRoads==2)
            return 50;
        else if(railRoads==3)
            return 100;
        else
            return 200;
    }

    //Performs the cleanup steps for when a player loses
    public void initiateBankruptcy(Player player){
        ArrayList<Property> ownedProps = player.getProperties();
        JOptionPane.showMessageDialog(banner,"You have bankrupted. All your properties were mortgaged (or were done for you in the background), and you still cannot produce the necessary amount.\nAll your properties have been returned to the bank, and you are out of the game.","Bankruptcy",3);
        for(int x=0; x<ownedProps.size(); x++){
            ownedProps.get(x).setOwned(false);
            ownedProps.get(x).setOwner(null);
            ownedProps.get(x).setNumHouses(0);
            ownedProps.get(x).setNumHotels(0);
        }
        player.setBankrupt(true);
        player.setPositionIndex(69);
        player.setIdentifier(Color.WHITE);
        player.setHasGetOutOfJailFreeCard(false);
        player.setTotalMoney(0);
        player.setPiece("None");
        player.setName("");
        trackerHolder.remove(getPlayerTracker(player));
    }

    //Calculates rent for when a player lands on a utility
    public int utilitiesRent(Player player, int roll){
        int numUtility = numUtilities(player);
        if(numUtility==0)
            return 0;
        else if(numUtility==1)
            return roll*4;
        else
            return roll*10;
    }

    //Finds the number of utilities a player owns for rent calculation
    public int numUtilities(Player player){
        ArrayList<Property> props = player.getProperties();
        //props = player.getProperties();
        int utilitiesCounter=0;

        for(int x=0; x<props.size(); x++)
            if(props.get(x).getName().equals("Electric Company") || props.get(x).getName().equals("Water Works"))
                if(!props.get(x).isMortgaged())
                    utilitiesCounter++;

        return utilitiesCounter;
    }

    //Code for checking monopoly for doubled rent calculation, as well as condition to buy houses/hotel
    public boolean checkForMonopoly(Color monopolyColor, Player player){
        ArrayList<Property> playerProperties = player.getProperties();
        int counter = 0;
        for(int x = 0; x < playerProperties.size(); x++)
        {
            if(monopolyColor == darkPurple)
                if(!playerProperties.get(x).isMortgaged() && (playerProperties.get(x).getName().equals("Mediterranean Avenue") ||playerProperties.get(x).getName().equals("Baltic Avenue")))
                    counter++;

            if(monopolyColor == Color.LIGHT_GRAY)
                if(!playerProperties.get(x).isMortgaged() && (playerProperties.get(x).getName().equals("Oriental Avenue") ||playerProperties.get(x).getName().equals("Vermont Avenue")) || playerProperties.get(x).getName().equals("Connecticut Avenue"))
                    counter++;

            if(monopolyColor == Color.MAGENTA)
                if(!playerProperties.get(x).isMortgaged() && (playerProperties.get(x).getName().equals("St. Charles Place") ||playerProperties.get(x).getName().equals("States Avenue")) || playerProperties.get(x).getName().equals("Virginia Avenue"))
                    counter++;

            if(monopolyColor == Color.ORANGE)
                if(!playerProperties.get(x).isMortgaged() && (playerProperties.get(x).getName().equals("St. James Place") ||playerProperties.get(x).getName().equals("Tennessee Avenue")) || playerProperties.get(x).getName().equals("New York Avenue"))
                    counter++;

            if(monopolyColor == Color.RED)
                if(!playerProperties.get(x).isMortgaged() && (playerProperties.get(x).getName().equals("Kentucky Avenue") ||playerProperties.get(x).getName().equals("Indiana Avenue")) || playerProperties.get(x).getName().equals("Illinois Avenue"))
                    counter++;

            if(monopolyColor == Color.YELLOW)
                if(!playerProperties.get(x).isMortgaged() && (playerProperties.get(x).getName().equals("Atlantic Avenue") ||playerProperties.get(x).getName().equals("Ventnor Avenue")) || playerProperties.get(x).getName().equals("Marvin Gardens"))
                    counter++;

            if(monopolyColor == Color.GREEN)
                if(!playerProperties.get(x).isMortgaged() && (playerProperties.get(x).getName().equals("Pacific Avenue") ||playerProperties.get(x).getName().equals("North Carolina Avenue")) || playerProperties.get(x).getName().equals("Pennsylvania"))
                    counter++;

            if(monopolyColor == Color.BLUE)
                if(!playerProperties.get(x).isMortgaged() && (playerProperties.get(x).getName().equals("Park Place") ||playerProperties.get(x).getName().equals("Boardwalk")))
                    counter++;
        }
        if(monopolyColor==darkPurple || monopolyColor==Color.BLUE)
            if(counter==2)
                return true;

        if(monopolyColor == Color.LIGHT_GRAY ||monopolyColor == Color.MAGENTA ||monopolyColor == Color.ORANGE ||monopolyColor == Color.RED ||monopolyColor == Color.YELLOW ||monopolyColor == Color.GREEN)
            if(counter==3)
                return true;

        return false;
    }

    //Creates custom frame for viewing railroads
    public void railRoadViewerInitialization(){
        railRoadFrame.setBounds(1510,520,250,370);
        railRoadFrame.setTitle("Preview");
        railRoadFrame.add(holdingRailRoadPanel);
            holdingRailRoadPanel.setBackground(Color.WHITE);
            railRoadFrame.setBackground(Color.WHITE);
            holdingRailRoadPanel.setBorder(compound);
            holdingRailRoadPanel.add(imageRailRoadLabel);
                imageRailRoadLabel.setBackground(Color.WHITE);
                imageRailRoadLabel.setBorder(compound);
            holdingRailRoadPanel.add(railRoadInfoPanel);
                railRoadInfoPanel.setBorder(compound);
                railRoadInfoPanel.setBackground(Color.WHITE);
                railRoadInfoPanel.add(titleRailRoadLabel, BorderLayout.NORTH);
                    titleRailRoadLabel.setBackground(Color.WHITE);
                    titleRailRoadLabel.setBorder(compound);
                        railRoadInfoPanel.add(costRailRoadArea,BorderLayout.CENTER);
                            costRailRoadArea.setBorder(compound);
                            costRailRoadArea.setEditable(false);
                        railRoadInfoPanel.add(railRoadSouthPanel, BorderLayout.SOUTH);
                            railRoadSouthPanel.setBorder(compound);
                            railRoadSouthPanel.add(mortgageRailRoadLabel);
                            railRoadSouthPanel.setBackground(Color.WHITE);
                            railRoadSouthPanel.add(ownerRailRoadLabel);
                                ownerRailRoadLabel.setBackground(Color.WHITE);
                                railRoadSouthPanel.setBackground(Color.WHITE);
    }

    //Creates custom frame for viewing utilities
    public void utilityViewerInitialization(){
        utilityFrame.setBounds(1510,520,250,370);
        utilityFrame.setTitle("Preview");
            utilityFrame.add(holdingPanel);
            holdingPanel.setBackground(Color.WHITE);
            utilityFrame.setBackground(Color.WHITE);
                holdingPanel.setBorder(compound);
                holdingPanel.add(imageLabel);
                    imageLabel.setBackground(Color.WHITE);
                    imageLabel.setBorder(compound);
                holdingPanel.add(utilityInfoPanel);
                    utilityInfoPanel.setBorder(compound);
                    utilityInfoPanel.setBackground(Color.WHITE);
                        utilityInfoPanel.add(titleLabel, BorderLayout.NORTH);
                            titleLabel.setBackground(Color.WHITE);
                            titleLabel.setBorder(compound);
                        utilityInfoPanel.add(costArea,BorderLayout.CENTER);
                            costArea.setBorder(compound);
                            costArea.setEditable(false);
                        utilityInfoPanel.add(utilitySouthPanel, BorderLayout.SOUTH);
                            utilitySouthPanel.setBorder(compound);
                            utilitySouthPanel.add(mortgageUtilityLabel);
                                utilitySouthPanel.setBackground(Color.WHITE);
                            utilitySouthPanel.add(ownerUtilityLabel);
                                ownerUtilityLabel.setBackground(Color.WHITE);
                            utilitySouthPanel.setBackground(Color.WHITE);
    }

    //Creates custom frame for viewing actual player tokens, as well as houses/hotel if a property has them
    public void initializeImageViewer(){
        imageFrame.setTitle("");
        imageFrame.setBounds(1035,663,450,350);
        imageFrame.add(imageHolderPanel);
            imageHolderPanel.setBorder(compound);

            imageHolderPanel.add(northImagePanel, BorderLayout.NORTH);
                northImagePanel.setBackground(Color.WHITE);
                northImagePanel.add(hotelPanel);
                    hotelPanel.setBackground(Color.WHITE);
                    hotelPanel.setBorder(compound);
                northImagePanel.add(housePanel);
                    housePanel.setBorder(compound);
                    housePanel.setBackground(Color.WHITE);

                for(int x=0; x<4; x++){
                    houseImagePanel[x] = new JPanel();
                    housePanel.add(houseImagePanel[x]);
                        houseImagePanel[x].setBorder(compound);
                        houseImagePanel[x].setBackground(Color.WHITE);
                }
                imageHolderPanel.add(centerImagePanel, BorderLayout.CENTER);
                    centerImagePanel.setBackground(Color.WHITE);
                    imageHolderPanel.setBackground(Color.WHITE);

                for(int x=0; x<4; x++){
                    imagePanel[x] = new JLabel("",SwingConstants.CENTER);
                    centerImagePanel.add(imagePanel[x]);
                        imagePanel[x].setBackground(Color.WHITE);
                        imagePanel[x].setBorder(compound);
                }
    }

    //Finds the display for a given player so that it can be appropriately updated
    public TrackerInfo getPlayerTracker(Player player){
        for(int x=0; x<playerPanel.length; x++)
            if(playerPanel[x].getPlayer()==player)
                return playerPanel[x];

        return null;
    }

    //Code to make all combo-boxes add items in order so the players can more easily keep track of monopolies
    public void resetJComboBox(Player player){
        TrackerInfo appropPanel = getPlayerTracker(player);
        appropPanel.refreshLabels();
        appropPanel.properties.removeAllItems();
        appropPanel.properties.addItem("Property List");

        for(int x=0; x<importantIndexes.length; x++){
            if(spaces[importantIndexes[x]].getOwner()==player)
                appropPanel.properties.addItem(spaces[importantIndexes[x]].getName());
        }
    }

    public void mouseDragged(MouseEvent e){}

    public void mouseMoved(MouseEvent e){
        for(int i=0; i<importantIndexes.length; i++){
            if(e.getSource() == spaces[importantIndexes[i]] && spaces[importantIndexes[i]].getStandardRent() != 0){
                //Make Information Viewer Populated
                propertyPreview.setVisible(true);
                propertyTitle.setText(spaces[importantIndexes[i]].getName().toUpperCase() + (spaces[importantIndexes[i]].isMortgaged()?  " (Mortgaged)":""));
                titlePanel.setBackground(spaces[importantIndexes[i]].getBackgroundColor());
                rentLabel.setText("Rent: $" + spaces[importantIndexes[i]].getStandardRent() + ".");
                houseCostLabelActualNumber[0].setText("$" + spaces[importantIndexes[i]].getOneHouseRent() + ".");
                houseCostLabelActualNumber[1].setText("$" + spaces[importantIndexes[i]].getTwoHouseRent() + ".");
                houseCostLabelActualNumber[2].setText("$" + spaces[importantIndexes[i]].getThreeHouseRent() + ".");
                houseCostLabelActualNumber[3].setText("$" + spaces[importantIndexes[i]].getFourHouseRent() + ".");
                hotelRentLabel.setText("With HOTEL: $" + spaces[importantIndexes[i]].getHotelRent() + ".");
                mortgageLabel.setText("            Mortgage Value $" + spaces[importantIndexes[i]].getMortgageValue() + ".");
                housesCostLabel.setText("            Houses cost $" + spaces[importantIndexes[i]].getPricePerHouse() + ". each");
                hotelsCostLabel.setText("            Hotels, $" + spaces[importantIndexes[i]].getPricePerHouse() + ". plus 4 houses");
                ownerLabel.setText("Owner: " + (spaces[importantIndexes[i]].isOwned() ? spaces[importantIndexes[i]].getOwner().getName() : "Purchasable"));

                //Make Image Viewer Populated
                //Reset House/Hotel Info Between Switches
                for(int x=0; x<4; x++)
                    houseImagePanel[x].setBackground(Color.WHITE);
                hotelPanel.setBackground(Color.WHITE);

                imageFrame.setTitle("'"+spaces[importantIndexes[i]].getName()+"' Upgrade/Token Viewer");

                for(int x=0; x<spaces[importantIndexes[i]].getNumHouses(); x++)
                    houseImagePanel[x].setBackground(Color.GREEN);

                if(spaces[importantIndexes[i]].getNumHotels()==1){
                    for(int x=0; x<4; x++)
                        houseImagePanel[x].setBackground(Color.WHITE);
                    hotelPanel.setBackground(Color.RED);
                }

                for(int x=0; x<numPlayers; x++){
                    if(returnPlayerWithNum(x).getPositionIndex()==i)
                        imagePanel[x].setIcon(new ImageIcon(new File("Images\\"+returnPlayerWithNum(x).getPiece()+".jpg").getAbsolutePath()));

                    else if(returnPlayerWithNum(x).getPositionIndex()!=i)
                        imagePanel[x].setIcon(null);
                }
                tokenFrame.setVisible(false);
                imageFrame.setVisible(true);
            }
            //Code for when players land on non-property spaces for token visibility
            else if(e.getSource()==spaces[importantIndexes[i]] && spaces[importantIndexes[i]].getStandardRent()==0){
                tokenFrame.setTitle("'"+spaces[importantIndexes[i]].getName()+"' Standard Token Viewer");
                for(int x=0; x<numPlayers; x++){
                    if(returnPlayerWithNum(x).getPositionIndex()==i)
                        tokenLabel[x].setIcon(new ImageIcon(new File("Images\\"+returnPlayerWithNum(x).getPiece()+".jpg").getAbsolutePath()));

                    else if(returnPlayerWithNum(x).getPositionIndex()!=i)
                        tokenLabel[x].setIcon(null);
                }
                tokenFrame.setVisible(true);
                imageFrame.setVisible(false);
            }
        }

        //Utilities Spaces
        if(e.getSource() == spaces[importantIndexes[12]] || e.getSource()==spaces[importantIndexes[28]]){
            utilityFrame.setVisible(true);
            extraFrame.setVisible(false);
            railRoadFrame.setVisible(false);
            titleLabel.setText((e.getSource() == spaces[importantIndexes[12]])?"ELECTRIC COMPANY":"WATER WORKS");

            ownerUtilityLabel.setText("Owned by: "+ ((e.getSource() == spaces[importantIndexes[12]])?
                                                    (spaces[importantIndexes[12]].isOwned()?
                                                    spaces[importantIndexes[12]].getOwner():"Purchasable"):(spaces[importantIndexes[28]].isOwned()?
                                                    spaces[importantIndexes[28]].getOwner():"Purchasable")));

            imageLabel.setIcon(new ImageIcon((e.getSource() == spaces[importantIndexes[12]])?new File("Images\\Electric Company.png").getAbsolutePath():new File("Images\\Water Works.png").getAbsolutePath()));
        }

        //Railroad Spaces
        else if(e.getSource() == spaces[importantIndexes[5]] ||e.getSource() == spaces[importantIndexes[15]] || e.getSource()==spaces[importantIndexes[25]] || e.getSource()==spaces[importantIndexes[35]]){
            railRoadFrame.setVisible(true);
            extraFrame.setVisible(false);
            utilityFrame.setVisible(false);
            imageRailRoadLabel.setIcon(new ImageIcon(new File("Images\\Railroad.png").getAbsolutePath()));

            if(e.getSource()==spaces[importantIndexes[5]]){
                titleRailRoadLabel.setText("READING RAILROAD");
                ownerRailRoadLabel.setText("Owned by: "+((spaces[importantIndexes[5]].isOwned()?spaces[importantIndexes[5]].getOwner().getName():"Purchasable")));
            }
            if(e.getSource()==spaces[importantIndexes[15]]){
                titleRailRoadLabel.setText("PENNSYLVANIA RAILROAD");
                ownerRailRoadLabel.setText("Owned by: "+((spaces[importantIndexes[15]].isOwned()?spaces[importantIndexes[15]].getOwner().getName():"Purchasable")));
            }
            if(e.getSource()==spaces[importantIndexes[25]]){
                titleRailRoadLabel.setText("B&O RAILROAD");
                ownerRailRoadLabel.setText("Owned by: "+((spaces[importantIndexes[25]].isOwned()?spaces[importantIndexes[25]].getOwner().getName():"Purchasable")));
            }
            if(e.getSource()==spaces[importantIndexes[35]]){
                titleRailRoadLabel.setText("SHORT LINE RAILROAD");
                ownerRailRoadLabel.setText("Owned by: "+((spaces[importantIndexes[35]].isOwned()?spaces[importantIndexes[35]].getOwner().getName():"Purchasable")));
            }
        }
        //Mouse Hovering Over Pass Go
        else if(e.getSource() == spaces[importantIndexes[0]]){
            extraFrame.setVisible(true);
            utilityFrame.setVisible(false);
            railRoadFrame.setVisible(false);
            extraImageLabel.setIcon(new ImageIcon(new File("Images\\Pass Go.png").getAbsolutePath()));
        }
        //Mouse Hovering Over Income Tax
        else if(e.getSource()==spaces[importantIndexes[4]]){
            extraFrame.setVisible(true);
            utilityFrame.setVisible(false);
            railRoadFrame.setVisible(false);
            extraImageLabel.setIcon(new ImageIcon(new File("Images\\Income Tax.png").getAbsolutePath()));
        }
        //Mouse Hovering Over Chance
        else if(e.getSource() == spaces[importantIndexes[7]] ||e.getSource()==spaces[importantIndexes[22]] || e.getSource() == spaces[importantIndexes[36]]){
            extraFrame.setVisible(true);
            utilityFrame.setVisible(false);
            railRoadFrame.setVisible(false);
            extraImageLabel.setIcon(new ImageIcon(new File("Images\\Chance.png").getAbsolutePath()));
        }
        //Mouse Hovering Over Community Chest
        else if(e.getSource() == spaces[importantIndexes[2]] ||e.getSource()==spaces[importantIndexes[17]] || e.getSource() == spaces[importantIndexes[33]]){
            extraFrame.setVisible(true);
            utilityFrame.setVisible(false);
            railRoadFrame.setVisible(false);
            extraImageLabel.setIcon(new ImageIcon(new File("Images\\Community Chest.png").getAbsolutePath()));
        }
        //Mouse Hovering Over Luxury Tax
        else if(e.getSource()==spaces[importantIndexes[38]]){
            extraFrame.setVisible(true);
            utilityFrame.setVisible(false);
            railRoadFrame.setVisible(false);
            extraImageLabel.setIcon(new ImageIcon(new File("Images\\Luxury Tax.png").getAbsolutePath()));
        }
        //Mouse Hovering Over Jail Visiting Space
        else if(e.getSource()==spaces[importantIndexes[10]]){
            extraFrame.setVisible(true);
            utilityFrame.setVisible(false);
            railRoadFrame.setVisible(false);
            extraImageLabel.setIcon(new ImageIcon(new File("Images\\In Jail.png").getAbsolutePath()));

            for(int x=0; x<currentPlayers.length; x++)
                spaces[10].colors[x].setToolTipText((checkJailStatus()[x])?"You're in Jail!":"Just Visiting.");

            for(int x=0; x<currentPlayers.length; x++)
                if(currentPlayers[x].getPositionIndex()!=importantIndexes[10])
                    spaces[10].colors[x].setToolTipText(null);

        }
        //Mouse Hovering Over Go to Jail Space
        else if(e.getSource()==spaces[importantIndexes[30]]){
            extraFrame.setVisible(true);
            utilityFrame.setVisible(false);
            railRoadFrame.setVisible(false);
            extraImageLabel.setIcon(new ImageIcon(new File("Images\\Go To Jail.png").getAbsolutePath()));
        }
        //Mouse Hovering Over Free Parking
        else if(e.getSource()==spaces[importantIndexes[20]]){
            extraFrame.setVisible(true);
            utilityFrame.setVisible(false);
            railRoadFrame.setVisible(false);
            extraImageLabel.setIcon(new ImageIcon(new File("Images\\Free Parking.png").getAbsolutePath()));
            if(playingWithFreeParking)
                for(int x=0; x<4; x++)
                    spaces[importantIndexes[20]].colors[x].setToolTipText((freeParking!=0)?"There is currently $"+freeParking+" in free parking":"Free parking is currently empty.");
        }
    }

    //Method to easily find a property with a given name
    public Property getAssociatedProperty(String name){
        for(int x=0; x<spaces.length; x++)
            if(spaces[x].getName()==name)
                return spaces[x];
        return null;
    }

    public boolean[] checkJailStatus(){
        boolean[] checker = new boolean[currentPlayers.length];
        for(int x=0; x<currentPlayers.length; x++){
            if(currentPlayers[x].isInJail())
                checker[x] = true;
            else
                checker[x] = false;
        }
        return checker;
    }

    //Creates the frame for when a player must mortgage their properties to get out of debt
    public void initializeForceFrame(){
        forceFrame.add(forcePanel);
        forceFrame.setTitle("Forced Mortgage");
        forceFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        forceFrame.setBounds(100,100,300,135);
            forcePanel.setBorder(compound);
            forcePanel.add(forceNorth, BorderLayout.NORTH);
                forceNorth.add(playerLabelForce);
                forceNorth.add(moneyLabelForce);
                    playerLabelForce.setBorder(compound);
                    moneyLabelForce.setBorder(compound);
                    forceNorth.setBorder(compound);
            forcePanel.add(forceBox, BorderLayout.CENTER);
                forceBox.addItem("Player Properties");
                forceBox.setBorder(compound);
            forcePanel.add(mortgageForceButton, BorderLayout.SOUTH);
                mortgageForceButton.addActionListener(this);
                mortgageForceButton.setBorder(compound);
    }

    //Procedure to open up forced mortgage frame so a player must mortgage their properties if managing potential bankruptcy
    public void forceMortgageProcedure(Player player){
        JOptionPane.showMessageDialog(banner,"You are in debt and must mortgage some of your properties.","Forced Mortgage",3);
        activate=false;
        endTurnButton.setEnabled(false);

        //Manage new items here
        mortProps = player.getProperties();
        ArrayList<Property> ownedProps = player.getProperties();

        for(int x=0; x<ownedProps.size(); x++)
            forceBox.addItem(ownedProps.get(x).getName());

        //Populate appropriate arraylists
        for(int x=0; x<ownedProps.size(); x++)
            if(ownedProps.get(x).isMortgaged()){
                forceBox.removeItem(mortProps.get(x).getName());
                mortProps.remove(ownedProps.get(x));
            }

        playerLabelForce.setText(player.getName());
        moneyLabelForce.setText("Money: $"+player.getTotalMoney());
        forceFrame.setVisible(true);
        appropriatePlayer=player;
    }

    public void workAroundMaybe(){
        //Dark Purple
        for(int x=0; x<2; x++){
            if(checkForMonopoly(darkPurple,getCurrentPlayer()))
                upgradeFrames.properties[x].setEnabled(true);
        }

        //Light Gray
        for(int x = 2; x < 5; x++){
            if(checkForMonopoly(Color.LIGHT_GRAY,getCurrentPlayer()))
                upgradeFrames.properties[x].setEnabled(true);
        }

        //MAGENTA
        for(int x = 5; x < 8; x++){
            if(checkForMonopoly(Color.MAGENTA,getCurrentPlayer()))
                upgradeFrames.properties[x].setEnabled(true);
        }

        //ORANGE
        for(int x = 8; x < 11; x++){
            if(checkForMonopoly(Color.ORANGE,getCurrentPlayer()))
                upgradeFrames.properties[x].setEnabled(true);
        }

        //RED
        for(int x = 11; x < 14; x++){
            if(checkForMonopoly(Color.RED,getCurrentPlayer()))
                upgradeFrames.properties[x].setEnabled(true);
        }

        //YELLOW
        for(int x = 14; x < 17; x++){
            if(checkForMonopoly(Color.YELLOW,getCurrentPlayer()))
                upgradeFrames.properties[x].setEnabled(true);
        }

        //GREEN
        for(int x = 17; x < 20; x++){
            if(checkForMonopoly(Color.GREEN,getCurrentPlayer()))
                upgradeFrames.properties[x].setEnabled(true);
        }

        //BLUE
        for(int x = 20; x < 22; x++){
            if(checkForMonopoly(Color.BLUE,getCurrentPlayer()))
                upgradeFrames.properties[x].setEnabled(true);
        }
    }

    public void windowClosing(WindowEvent e){
        if(e.getSource()==upgradeFrames)
            duplication=false;
    }

    //Unnecessary Overridden Methods :(
    public void windowOpened(WindowEvent e){}
    public void windowClosed(WindowEvent e){}
    public void windowIconified(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowActivated(WindowEvent e){}
    public void windowDeactivated(WindowEvent e){}

    public void keyPressed(KeyEvent e){
        //Checks to see if a player can use menu at a certain time
        if(activate){
            //Code to run trades if a certain key is pressed
            if(e.getKeyCode() == KeyEvent.VK_T){
                int playerNum = getCurrentPlayer().getPlayerNumber();
                tradingFrames[playerNum].setDefaultValues();
                tradingFrames[playerNum].moneyLabel.setText("Your Money: $" + getCurrentPlayer().getTotalMoney());
                tradingFrames[playerNum].setPlayer(getCurrentPlayer());
                tradingFrames[playerNum].setBounds(100, 100, 370, 500);
                tradingFrames[playerNum].setAlwaysOnTop(true);
                tradingFrames[playerNum].setVisible(true);
                tradingFrames[playerNum].setInitiator(true);
                tradingFrames[playerNum].setAsked(false);
            }

            //Code to run upgrades if a certain key is pressed
            else if(e.getKeyCode() == KeyEvent.VK_U){
                if(duplication)
                    JOptionPane.showMessageDialog(banner, "You already have one open.", "Duplication", 3);

                else {
                    duplication = true;
                    upgradeFrames = new UpgradeFrame(getCurrentPlayer(), this);
                    workAroundMaybe();
                    upgradeFrames.setBounds(100, 100, 380, 600);
                    upgradeFrames.setAlwaysOnTop(true);
                    upgradeFrames.addWindowListener(this);
                    upgradeFrames.setTitle("Upgrade Properties");
                    upgradeFrames.setVisible(true);
                }
            }

            //Code to run unmortgage/mortgage procedure if a certain key is pressed
            else if(e.getKeyCode() == KeyEvent.VK_M){
                //Manage new items here
                mortProps = new ArrayList<Property>();
                unMortProps = new ArrayList<Property>();

                //Clean up for multiple use assurance
                mortProps.clear();
                unMortProps.clear();
                mortgagableProps.removeAllItems();
                unmortgagableProps.removeAllItems();
                mortgagableProps.addItem("Unmortgaged Properties");
                unmortgagableProps.addItem("Mortgaged Properties");

                //Populate appropriate arraylists
                for(int x = 0; x < getCurrentPlayer().getProperties().size(); x++){
                    if(!getCurrentPlayer().getProperties().get(x).isMortgaged()){
                        mortProps.add(getCurrentPlayer().getProperties().get(x));
                        mortgagableProps.addItem(getCurrentPlayer().getProperties().get(x).getName());
                    } else if(getCurrentPlayer().getProperties().get(x).isMortgaged()){
                        unMortProps.add(getCurrentPlayer().getProperties().get(x));
                        unmortgagableProps.addItem(getCurrentPlayer().getProperties().get(x).getName());
                    }
                }
                mortgageNameLabel.setText("Player: " + getCurrentPlayer().getName());
                mortgageMoneyLabel.setText("Money: $" + getCurrentPlayer().getTotalMoney());
                mortgageFrame.setVisible(true);
            }
        }

        else if(!activate){
            if(e.getKeyCode()==KeyEvent.VK_T || e.getKeyCode()==KeyEvent.VK_U || e.getKeyCode()==KeyEvent.VK_M)
                JOptionPane.showMessageDialog(banner,"You cannot use this command now. Complete your first roll; then this feature will be accessible.","Roll First",3);
        }

        //Code to exit game if a certain key is pressed
        if(e.getKeyCode()==KeyEvent.VK_X){
            int quitOpt = JOptionPane.showConfirmDialog(banner,"Would you like to quit your game?","Exit Game",JOptionPane.YES_NO_OPTION);
            if(quitOpt==0){
                JOptionPane.showMessageDialog(banner, "Thanks for playing. See you next time.", "Thanks", 1);
                System.exit(0);
            }
        }

        //Code to show players the commands to perform between-turn actions
        else if(e.getKeyCode()==KeyEvent.VK_H){
            JOptionPane.showMessageDialog(banner,"How To Perform Extra Actions:\n" +
                                                          "CTRL_M  →  Mortgage Properties\n" +
                                                          "CTRL_T  →  Trade w/ Other Players\n" +
                                                          "CTRL_U  →  Upgrade Properties\n"+
                                                          "CTRL_X  →  Exit Game","Shortcut Keys",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}
}