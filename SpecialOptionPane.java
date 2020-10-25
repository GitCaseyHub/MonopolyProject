import javax.swing.*;
import java.awt.*;

public class SpecialOptionPane extends JOptionPane{
    public SpecialOptionPane(Component component, String message, String title, Color color, int messageType){
        UIManager.put("OptionPane.background", color);
        UIManager.put("Panel.background", color);
        JOptionPane.showMessageDialog(component,message,title,messageType);
    }
}
