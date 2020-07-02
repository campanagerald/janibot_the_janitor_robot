package psu.janibot.util;

import psu.janibot.Program;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public enum SoundEffect {

    EXPLODE("/sound/explode.wav"),
    SWEEP("/sound/sweep.wav"),
    FAIL("/sound/fail.wav"),
    VICTORY("/sound/victory.wav"),
    TIMESUP("/sound/buzzer.wav"),
    MOVE("/sound/move.wav");

    private Clip clip;

    SoundEffect(String soundFileName) {
        try {
            URL url = Program.class.getResource(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.loop(0);
    }

    public void play(int n){
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.loop(n);
    }

    public void stop() {
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.stop();
    }

    public static void init() {
        values();
    }
}
