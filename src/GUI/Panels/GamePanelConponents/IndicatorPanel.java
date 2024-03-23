
package GUI.Panels.GamePanelConponents;

import javax.swing.*;
import java.awt.*;

public class IndicatorPanel extends JPanel {

    private JPanel indicator;

    public IndicatorPanel(Color color) {
        // Use FlowLayout to control the position of the indicator
        setLayout(new FlowLayout(FlowLayout.LEFT,0 ,0));

        setPreferredSize(new Dimension(175, 40));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        indicator = new JPanel();
        indicator.setPreferredSize(new Dimension(40, 40)); // Setting size of the inner panel
        indicator.setOpaque(true);
        indicator.setBackground(color);

        add(indicator);

    }

    // Method to update the position of the indicator within this panel
    public void setPositionToLeft() {
        ((FlowLayout) getLayout()).setAlignment(FlowLayout.LEFT);
        revalidate();
        repaint();
    }

    public void setPositionToRight() {
        ((FlowLayout) getLayout()).setAlignment(FlowLayout.RIGHT);
        revalidate();
        repaint();
    }
}
