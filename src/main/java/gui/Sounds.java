package gui;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public final class Sounds {

    private Sounds() {
    }

    private static Clip currentClip;
    private static long clipPosition;

    private static URL getResourceUrl(String path) {
        System.out.println("\n[DEBUG] Audio Resource Loading Diagnostic:");
        System.out.println("----------------------------------------");
        System.out.println("Attempting to load audio: " + path);
        
        // Try different relative paths
        String[] pathsToTry = {
            path,                                           // Original path
            "../../resources/" + path,                      // Relative to class location
            "../../../resources/" + path,                   // One level up
            path.replaceFirst("^/", ""),                   // Remove leading slash
            "assets/" + path.replaceFirst("^/?assets/", "") // Direct to assets
        };

        URL url = null;
        for (String tryPath : pathsToTry) {
            System.out.println("Trying audio path: " + tryPath);
            url = Sounds.class.getClassLoader().getResource(tryPath);
            if (url != null) {
                System.out.println("Successfully found audio at: " + url);
                break;
            }
        }

        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        System.out.println("Class location: " + Sounds.class.getProtectionDomain().getCodeSource().getLocation());
        System.out.println("Final audio URL result: " + url);
        System.out.println("----------------------------------------\n");
        
        return url;
    }

    /**
     * Method to load the Audio Clip
     */
    private static Clip loadAudioClip(String audioPath) throws Exception {
        URL audioUrl = getResourceUrl(audioPath);
        if (audioUrl == null) {
            System.err.println("[ERROR] Audio file not found: " + audioPath);
            return null;
        }

        System.out.println("[DEBUG] Opening audio stream from: " + audioUrl);
        try {
            // Set system properties required for audio
            System.setProperty("javax.sound.sampled.Clip", "com.sun.media.sound.DirectAudioDevice");
            System.setProperty("java.home", System.getProperty("user.dir"));
            
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load audio clip: " + e.getMessage());
            e.printStackTrace();
            return null;
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
        System.out.println("[DEBUG] Attempting to play sound: " + audioPath);
        try {
            Clip clip = loadAudioClip(audioPath);
            if (clip == null) {
                System.err.println("[ERROR] Failed to create clip for: " + audioPath);
                return;
            }

            if (!overlap && currentClip != null && !clip.equals(currentClip)) {
                System.out.println("[DEBUG] Stopping current clip for non-overlapping sound");
                currentClip.stop();
            }

            if (loop) {
                System.out.println("[DEBUG] Starting looped playback");
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                currentClip = clip;
            } else {
                System.out.println("[DEBUG] Starting single playback");
                clip.start();
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Playback failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
