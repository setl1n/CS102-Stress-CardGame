package gui;

import java.net.URL;
import javax.sound.sampled.*;

public final class Sounds {

    private Sounds() {
    }

    private static Clip currentClip;
    private static long clipPosition;

    /**
     * Method to load the Audio Clip
     */
    private static Clip loadAudioClip(String audioPath) throws Exception {
        URL audioUrl = Sounds.class.getResource(audioPath);
        if (audioUrl == null) {
            throw new RuntimeException("Audio file not found: " + audioPath);
        }
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioUrl)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        }
    }

    /**
     * Methods to play various sounds
     * @param loop : if this sound will play again when over
     * @param overlap : if this sound interrupts the music
     */

    /*
     * Methods to play sound effects
     */

    public static void cardSound() {
        playSound("/assets/cardplacesound.wav", false, true);
    }

    public static void stressSound() {
        playSound("/assets/stress.wav", false, true);
    }

    public static void invalidSound() {
        playSound("/assets/disable.wav", false, true);
    }

    /*
     * Methods to play music files
     */

    public static void bgmSound() {
        playSound("/assets/bgm.wav", true, false);
    }

    public static void menuSound() {
        playSound("/assets/menu.wav", true, false);
    }

    public static void endSound() {
        playSound("/assets/end.wav", true, false);
    }

    /*
     * Methods to pause and resume sounds
     */

    public static void pauseClip() {
        if (currentClip != null && currentClip.isRunning()) {
            clipPosition = currentClip.getMicrosecondPosition(); // Save the current position
            currentClip.stop(); // Pause the clip
        }
    }

    public static void resumeBgm() {
        if (currentClip != null && !currentClip.isRunning()) {
            currentClip.setMicrosecondPosition(clipPosition);
            currentClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /*
     * Generalised method to play sounds (.wav files)
     */

    private static void playSound(String audioPath, boolean loop, boolean overlap) {
        try {
            Clip clip = loadAudioClip(audioPath);

            if (!overlap && currentClip != null && !clip.equals(currentClip)) {
                currentClip.stop();
            }

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the clip continuously
                currentClip = clip;
            } else {
                clip.start(); // Play the clip once
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
