package gui;

import javax.sound.sampled.Clip;

public final class Sounds {

    private Sounds() {
    }

    private static Clip currentClip;
    private static long clipPosition;

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
        playSound("/assets/stress.wav", false, true);
    }

    public static void invalidSound() {
        playSound("/assets/disable.wav", false, true);
    }

    public static void endSound() {
        playSound("/assets/end.wav", true, false);
    }

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

    public static void playSound(String audioPath, boolean loop, boolean overlap) {
        try {
            Clip clip = GUIUtility.loadAudioClip(audioPath);

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
