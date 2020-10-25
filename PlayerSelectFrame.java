import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;

public class PlayerSelectFrame extends JFrame implements FocusListener, ActionListener, ChangeListener {
    //Border Initialization
    Border compound = BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder());

    //Creating GUI
    JPanel playerPanel = new JPanel(new GridLayout(2, 1));
    JPanel customizationPanel = new JPanel(new GridLayout(1, 2));
    JPanel containedPanel = new JPanel(new GridLayout(1, 2));
    JPanel colorPanel = new JPanel(new GridLayout(1,2));
    JTextField nameField = new JTextField("Name Your Player");
    JColorChooser jcc = new JColorChooser();
    JComboBox pieceBox = new JComboBox();
    JButton openJCC = new JButton("Color Identifier");
    String[] tokens = {"Thimble","Wheel-Barrow","Shoe","Dog","Car","Iron","Top-Hat","Ship"};
    JButton lockIn = new JButton("Lock In");

    //Initializing Variables
    String name, token;
    boolean lockedIn;
    StartGame reference;
    Color identifier;

    //Creation of ColorChooser Items
    JFrame colorFrame = new JFrame();
    Color color = new Color(100, 100, 100);
    JTextArea colorView = new JTextArea();
    int x,y,width,height, playerNumber;
    Color colorChoice = Color.white;

    public PlayerSelectFrame(String name, String token, Color identifier, boolean lockedIn, StartGame reference, int playerNumber, int x, int y, int width, int height){
        this.setResizable(false);
        this.x=x;
        this.y=y;
        this.height=height;
        this.width=width;
        this.identifier = identifier;
        this.playerNumber=playerNumber;
        this.reference = reference;
        this.name = name;
        this.token = token;
        this.lockedIn = lockedIn;
        this.add(playerPanel);

        //Add items to panel
        playerPanel.setBorder(compound);
        playerPanel.add(customizationPanel);
            customizationPanel.setBorder(compound);
            customizationPanel.add(nameField);
            customizationPanel.add(colorPanel);
            colorPanel.add(openJCC);
                nameField.setBorder(compound);
                nameField.addFocusListener(this);
                nameField.setHorizontalAlignment(JTextField.CENTER);
                nameField.setForeground(color);
                openJCC.addActionListener(this);
                openJCC.setBackground(Color.white);
                openJCC.setToolTipText("Click to choose a color identifier.");
                openJCC.setBorder(compound);
            colorPanel.add(colorView);
                colorView.setBorder(compound);
                colorView.setEnabled(false);
                    colorView.setBackground(Color.white);
        playerPanel.add(containedPanel);
            containedPanel.add(pieceBox);
                pieceBox.addItem("Choose Token");
                for (int i = 0; i < tokens.length; i++)
                    pieceBox.addItem(tokens[i]);
                pieceBox.setBorder(compound);
                pieceBox.setBackground(Color.white);
            containedPanel.add(lockIn);
                lockIn.addActionListener(this);
                lockIn.setBorder(compound);
                lockIn.setBackground(Color.white);
                lockIn.setToolTipText("Click to lock in player characteristics.");
            containedPanel.setBorder(compound);

        //ColorChanger getting choice color
        jcc.getSelectionModel().addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent arg0){
                colorChoice = jcc.getColor();
                colorView.setBackground(colorChoice);
            }
        });

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public void colorChooserOpen(){
        colorFrame.setBounds(x,y,width,height);
        colorFrame.setTitle("Player "+this.getPlayerNumber()+"'s Color");
        colorFrame.setVisible(true);
        colorFrame.add(jcc);

    }

    public void focusGained(FocusEvent e){
        if (e.getSource() == nameField && nameField.getText().equals("Name Your Player")){
            nameField.setText("");
            nameField.setForeground(Color.black);
        }
    }

    @Override
    public void focusLost(FocusEvent e){
        if (e.getSource() == nameField && nameField.getText().equals("")){
            nameField.setText("Name Your Player");
            nameField.setForeground(color);
        }
    }

    public String getName(){
        return name;
    }

    public Color getIdentifier(){
        return identifier;
    }

    public void setIdentifier(Color identifier){
        this.identifier = identifier;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public boolean isLockedIn(){
        return lockedIn;
    }

    public void setLockedIn(boolean lockedIn){
        this.lockedIn = lockedIn;
    }

    public int getPlayerNumber(){
        return playerNumber;
    }

    public void voidInfo(){
        lockIn.setEnabled(true);
        nameField.setText("");
        pieceBox.setSelectedIndex(0);
        pieceBox.setEnabled(true);
        nameField.setEditable(true);
        nameField.setForeground(Color.black);
        openJCC.setEnabled(true);
        colorView.setBackground(Color.white);
        colorFrame.setVisible(false);
    }

    public boolean checkLockIn(){
        return lockedIn;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == lockIn && !pieceBox.getSelectedItem().toString().equals("Choose Token") && !nameField.getText().equals("Name Your Player") && colorChoice!=Color.white){
            if(reference.orderList.contains(playerNumber-1)?false:true){
                JOptionPane.showMessageDialog(reference.m.banner,"You must register players in ascending numerical order.","Registration Error",JOptionPane.ERROR_MESSAGE);
            }
            else if (reference.getTokenList().contains(pieceBox.getSelectedItem().toString())){
                JOptionPane.showMessageDialog(reference.m.banner, "Another player has already chosen that token. Pick another.", "Duplication Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (reference.getNameList().contains(nameField.getText())){
                JOptionPane.showMessageDialog(reference.m.banner, "Another player has already chosen that name. Pick another.", "Duplication Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(reference.getColorList().contains(colorChoice)){
                JOptionPane.showMessageDialog(reference.m.banner, "Another player has already chosen that color. Pick another.", "Duplication Error", JOptionPane.ERROR_MESSAGE);

            }
            else{
                reference.addToTokenList(pieceBox.getSelectedItem().toString());
                reference.addNameToList(nameField.getText());
                reference.addColorToList(colorChoice);
                reference.addToOrderList(playerNumber);
                this.setName(nameField.getText());
                this.setToken(pieceBox.getSelectedItem().toString());
                this.setLockedIn(true);
                lockIn.setEnabled(false);
                pieceBox.setEnabled(false);
                nameField.setEditable(false);
                this.setTitle("Player: "+nameField.getText());
                nameField.setText("Your name is: " + nameField.getText() + ".");
                colorFrame.setVisible(false);
                openJCC.setEnabled(false);

                if(reference.getTokenList().size()>=2)
                    reference.confirmButton.setEnabled(true);
            }
        }
        else if (e.getSource() == lockIn && pieceBox.getSelectedItem().toString().equals("Choose Token")){
            JOptionPane.showMessageDialog(reference.m.banner, "You cannot choose that as your token. It is a filler item to direct you.");
        }
        else if (e.getSource() == lockIn && !pieceBox.getSelectedItem().toString().equals("Choose Token") && nameField.getText().equals("Name Your Player"))
            JOptionPane.showMessageDialog(reference.m.banner, "You aren't the main character of Ralph Ellison's \"Invisible Man\". You have a name, I presume.", "Choose a Name", 2);

        else if(e.getSource() == lockIn && !pieceBox.getSelectedItem().toString().equals("Choose Token") && !nameField.getText().equals("Name Your Player") && colorChoice == Color.white){
            JOptionPane.showMessageDialog(reference.m.banner, "You cannot be that color. It is not a clear identifier.", "Pick a Color Identifier", 2);

        }

        else if(e.getSource()==openJCC){
            colorChooserOpen();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e){}
}