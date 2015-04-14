package com.williamokano.apps.kidsworld.models;

/**
 * Class responsible for holding a image asset //
 */
public class Image {
    private int imageAsset;
    private Sound sound;
    private String description;

    public Image(int imageAsset, int sound, String description){

        this.imageAsset = imageAsset;
        this.sound = new Sound(sound);
        this.description = description;

    }

    public Image(){};

    public int getImageAsset() {
        return imageAsset;
    }

    public void setImageAsset(int imageAsset) {
        this.imageAsset = imageAsset;
    }

    public Sound getSoundAsset() {
        return sound;
    }

    public void setSoundAsset(Sound soundAsset) {
        sound = soundAsset;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
