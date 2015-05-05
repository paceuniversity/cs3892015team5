package com.williamokano.apps.kidsworld.models;

/**
 * Created by William on 4/18/2015.
 */
public class Category {
    private Integer IdCategoria;
    private String Name;

    public Category(Integer idCategoria, String name) {
        IdCategoria = idCategoria;
        Name = name;
    }

    public Integer getIdCategoria() {
        return IdCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        IdCategoria = idCategoria;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return this.Name;
    }
}
