package com.williamokano.apps.kidsworld.models;

/**
 * Class responsible for holding a sound asset. //
 */
public class Sound {
    private int SoundAsset;
    public Sound ()
    {
    }
    public Sound(int soundAsset)
    {
        SoundAsset = soundAsset;
    }
    public int getSoundAsset() {
        return SoundAsset;
    }

    public void setSoundAsset(int soundAsset) {
        SoundAsset = soundAsset;
    }
}
