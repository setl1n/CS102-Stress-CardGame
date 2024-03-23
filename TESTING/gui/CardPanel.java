
import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {

    static final int width = 138;
    static final int height = 186;

    public CardPanel(String label) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Placeholder for the card/deck image
        ImageIcon icon = new ImageIcon("./images/empty.png");
        Image image = icon.getImage().getScaledInstance(138, 186, Image.SCALE_SMOOTH);
        JLabel labelComponent = new JLabel(new ImageIcon(image), JLabel.CENTER);
        add(labelComponent, BorderLayout.CENTER);
    }
}

