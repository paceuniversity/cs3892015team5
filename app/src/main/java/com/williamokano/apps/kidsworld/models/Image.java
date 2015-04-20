package com.williamokano.apps.kidsworld.models;

/**
 * Class responsible for holding a image asset //
 */
public class Image {
    private int imageAsset;
    private Sound sound;
    private String description;
    private String category;

    public Image(int imageAsset, int sound, String description, String category){

        this.imageAsset = imageAsset;
        this.sound = new Sound(sound);
        this.description = description;
        this.category = category;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
