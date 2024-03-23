import javax.swing.*;
import java.awt.*;

public class PilePanel extends JPanel {
    private CardPanel pile1;
    private CardPanel pile2;
    private IndicatorPanel indicator1; // Using IndicatorPanel for Player 1
    private IndicatorPanel indicator2; // Using IndicatorPanel for Player 2

    public PilePanel() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(300, 240));

        GridBagConstraints gbc = new GridBagConstraints();

        indicator2 = new IndicatorPanel(Color.BLUE); // Initialize with BLUE for player 2

        gbc.insets = new Insets(5, 5, 5, 5);

        /*
         * INDICATOR 2
         */
        gbc.gridwidth = 2; // Span across two columns
        add(indicator2, gbc);
        indicator2.setPositionToRight();

        // move down by one row
        gbc.gridy = 1;
        // change back to single column
        gbc.gridwidth = 1;

        /*
         * PILE 1
         */
        pile1 = new CardPanel("Pile 1");
        add(pile1, gbc);

        // move to next column in row
        gbc.gridx = 1;

        /*
         * PILE 2
         */
        pile2 = new CardPanel("Pile 2");
        add(pile2, gbc); // Add pile 2

        // move back to first column
        gbc.gridx = 0;
        // move down one more row
        gbc.gridy = 2; // Third row, below the piles
        // change back to two columns
        gbc.gridwidth = 2;

        /*
         * INDICATOR 1
         */
        indicator1 = new IndicatorPanel(Color.RED);
        add(indicator1, gbc); // Add indicator 1 below the piles

    }
}
