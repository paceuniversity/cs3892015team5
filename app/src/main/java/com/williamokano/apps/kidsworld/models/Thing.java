package com.williamokano.apps.kidsworld.models;

/**
 * Created by William on 4/18/2015.
 */
public class Thing {

    private Integer IdThing;
    private Integer IdCategory;
    private Category Category;
    private Image Image;
    private Sound Sound;
    private String Description;

    public Thing(Integer idThing, Integer idCategory, Category category, Image image, Sound sound, String description) {
        IdThing = idThing;
        IdCategory = idCategory;
        Category = category;
        Image = image;
        Sound = sound;
        Description = description;
    }

    public Integer getIdThing() {
        return IdThing;
    }

    public void setIdThing(Integer idThing) {
        IdThing = idThing;
    }

    public Category getCategory() {
        return Category;
    }

    public void setCategory(Category category) {
        Category = category;
        IdCategory = category.getIdCategoria();
    }

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }

    public Sound getSound() {
        return Sound;
    }

    public void setSound(Sound sound) {
        Sound = sound;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
