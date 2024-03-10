/*
 * @OWNER zane
 */

package src.GUI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CardImageLoader {
    private static final Map<String, Image> cardImages = new HashMap<>();
    private static final String basePath = "/assets/";

    public static Image getCardImage(String cardName) {
        if (cardImages.containsKey(cardName)) {
            return cardImages.get(cardName);
        }

        URL imgUrl = CardImageLoader.class.getResource(basePath + cardName + ".png");
        if (imgUrl == null) {
            imgUrl = CardImageLoader.class.getResource(basePath + "empty.png");
        }
        Image image = new ImageIcon(imgUrl).getImage();
        cardImages.put(cardName, image);
        return image;
    }

    public static Image getIndicatorImage(String indicatorName) {
        return getCardImage(indicatorName);
    }
}

