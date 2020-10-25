import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.util.*;

public class Dice extends JPanel{
    JLabel imageLabel = new JLabel();
    int currentVal;
    Border compound = BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder());

    public Dice(){
        this.currentVal=currentVal;
        this.add(imageLabel, BorderLayout.CENTER);
        this.setBorder(compound);
        this.setBackground(Color.white);
    }

    public int roll(){
        int randRoll = new Random().nextInt(5)+1;
        setBackgroundImage(randRoll);
        currentVal=randRoll;
        return randRoll;
    }

    public int getCurrentVal(){
        return currentVal;
    }

    public void setBackgroundImage(int diceVal){
        imageLabel.setIcon(new ImageIcon(new File("Images\\"+Integer.toString(diceVal)+".jpg").getAbsolutePath()));
    }
}