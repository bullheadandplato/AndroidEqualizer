package com.bullhead.equalizer;

public class Settings {
    static boolean isEqualizerEnabled = true;
    static boolean isEqualizerReloaded = true;
    static int[] seekbarpos = new int[5];
    static int presetPos;
    static short reverbPreset = -1, bassStrength = -1;
    static EqualizerModel equalizerModel;
    public static double ratio = 1.0;
}
