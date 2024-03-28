package GUI;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public final class SoundUtility {

    private SoundUtility() {}

    private static Clip currentClip;

    /*
     * SOUND ASSET METHODS
     */

    public static void cardSound() {
        playSound("/assets/cardplacesound.wav", false, true);
    }

    public static void bgmSound() {
        playSound("/assets/bgm.wav", true, false);
    }

    public static void menuSound() {
        playSound("/assets/menu.wav", true, false);
    }

    public static void stressSound() {
        playSound("/assets/redstress.wav", false, true);
    }

    public static void invalidSound() {
        playSound("/assets/disable.wav", false, true);
    }

    /*
     * SOUND PLAYING METHODS
     */

    private static Clip loadAudioClip(String audioPath) throws Exception {
        URL audioUrl = SoundUtility.class.getResource(audioPath);
        if (audioUrl == null) {
            throw new RuntimeException("Audio file not found: " + audioPath);
        }
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioUrl);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }

    public static void playSound(String audioPath, boolean loop, boolean overlap) {
        try {
            Clip clip = loadAudioClip(audioPath);

            if (!overlap && currentClip != null && !clip.equals(currentClip)) {
                currentClip.stop();
            }
            currentClip = clip;

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the clip continuously
            } else {
                clip.start(); // Play the clip once
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
