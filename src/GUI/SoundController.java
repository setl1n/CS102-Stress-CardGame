/*
 * @OWNER zane
 */

package src.GUI;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.Timer;

public class SoundController {
    public void cardSound() {
        try {
            String audioPath = "/assets/place.wav";
            URL audioUrl = getClass().getResource(audioPath);
            if (audioUrl == null) {
                throw new RuntimeException("Audio file not found: " + audioPath);
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioUrl);
        
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            
            // Set the start position (if needed)
            double microseconds = 24.55 * 1000000;
            clip.setMicrosecondPosition((long)microseconds);

            // Check if volume control is supported
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                // Calculate the new volume level
                float newGain = Math.min(Math.max(gainControl.getMinimum(), gainControl.getValue() + 100), gainControl.getMaximum());
                gainControl.setValue(newGain); // Apply the volume change
            } else {
                System.err.println("Volume control not supported");
            }

            clip.start(); // Start playback

            // Set a timer to stop the clip after a certain duration
            int playTime = 250; // Adjust the playtime as needed
            new Timer(playTime, e -> clip.stop()).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
