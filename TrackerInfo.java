import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TrackerInfo extends JPanel implements ActionListener {
    JLabel nameLabel = new JLabel("",SwingConstants.CENTER);
    JLabel tokenLabel = new JLabel("",SwingConstants.CENTER);
    JLabel identityColorLabel = new JLabel("",SwingConstants.CENTER);
    JComboBox<String> properties = new JComboBox<String>();
    JLabel moneyLabel = new JLabel("",SwingConstants.CENTER);
    JPanel boxPanel = new JPanel(new GridLayout(1,3));
    JPanel infoPanel = new JPanel(new GridLayout(1,3));
    JPanel propsAndColor = new JPanel(new GridLayout(1,2));
    JPanel orderGrid = new JPanel(new BorderLayout());
    JCheckBox yourTurn = new JCheckBox("Your Turn");
    JCheckBox jailFreeCard = new JCheckBox("Jail Free Card");
    JCheckBox inJail = new JCheckBox("In Jail");
    ArrayList<Property> exportedProps = new ArrayList<Property>();
    Border compound = BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder());

    DefaultListCellRenderer comboRender;
    Player player;

    public TrackerInfo(Player player){
        this.player=player;
        comboRender = new DefaultListCellRenderer();
        comboRender.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        properties.setRenderer(comboRender);
        exportedProps = player.getProperties();
        nameLabel.setText(player.getName());
        tokenLabel.setText(player.getPiece());
        identityColorLabel.setBackground(player.getIdentifier());
            identityColorLabel.setText(player.getName()+"'s Color Identity");
        moneyLabel.setText("$"+player.getTotalMoney());
        yourTurn.setSelected(player.isHasCurrentTurn());
        jailFreeCard.setSelected(player.isHasGetOutOfJailFreeCard());
        properties.addItem("Property List");

        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), player.getName()+ "'s Information");
        title.setTitlePosition(TitledBorder.ABOVE_TOP);
        this.setBorder(title);
        this.add(orderGrid);
        orderGrid.setPreferredSize(new Dimension(420,102));
            orderGrid.add(infoPanel, BorderLayout.NORTH);
            orderGrid.setBorder(compound);
                infoPanel.setBorder(compound);
                infoPanel.add(nameLabel);
                    nameLabel.setBorder(compound);
                infoPanel.add(tokenLabel);
                    tokenLabel.setBorder(compound);
                infoPanel.add(moneyLabel);
                    moneyLabel.setBorder(compound);
            orderGrid.add(propsAndColor, BorderLayout.CENTER);
                propsAndColor.setBorder(compound);
                propsAndColor.add(properties);
                    properties.setBorder(compound);
                    properties.addActionListener(this);
                propsAndColor.add(identityColorLabel);
                    identityColorLabel.setBorder(compound);
            orderGrid.add(boxPanel, BorderLayout.SOUTH);
                boxPanel.add(yourTurn);
                    yourTurn.setBorderPainted(true);
                    yourTurn.setBorder(compound);
                    yourTurn.setHorizontalAlignment(JCheckBox.CENTER);
                    yourTurn.setEnabled(false);
                boxPanel.add(jailFreeCard);
                    jailFreeCard.setBorderPainted(true);
                    jailFreeCard.setBorder(compound);
                    jailFreeCard.setHorizontalAlignment(JCheckBox.CENTER);
                    jailFreeCard.setEnabled(false);
                boxPanel.add(inJail);
                    inJail.setBorderPainted(true);
                    inJail.setBorder(compound);
                    inJail.setHorizontalAlignment(JCheckBox.CENTER);
                    inJail.setEnabled(false);
    }

    public void addProperty(Property property){
        properties.addItem(property.getName());
    }

    public Player getPlayer(){
        return this.player;
    }

    public void refreshLabels(){
        exportedProps = player.getProperties();
        nameLabel.setText(player.getName());
        tokenLabel.setText(player.getPiece());
        identityColorLabel.setBackground(player.getIdentifier());
        identityColorLabel.setText(player.getName()+"'s Color Identity");
        moneyLabel.setText("$"+player.getTotalMoney());
        yourTurn.setSelected(player.hasCurrentTurn);
        jailFreeCard.setSelected(player.hasGetOutOfJailFreeCard);
        inJail.setSelected(player.inJail);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==properties){
            properties.setSelectedItem("Property List");
        }
    }
}