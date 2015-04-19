package com.williamokano.apps.kidsworld.models;

import java.util.Date;

/**
 * Created by William on 4/19/2015.
 */
public class Game {
    private Integer IdGame;
    private Integer IdCategory;
    private Category Category;
    private Integer IdPlayer;
    private Player Player;
    private Date DtBegin;

    public Integer getIdGame() {
        return IdGame;
    }

    public void setIdGame(Integer idGame) {
        IdGame = idGame;
    }

    public Integer getIdCategory() {
        return IdCategory;
    }

    public void setIdCategory(Integer idCategory) {
        IdCategory = idCategory;
    }

    public Category getCategory() {
        return Category;
    }

    public void setCategory(Category category) {
        Category = category;
    }

    public Integer getIdPlayer() {
        return IdPlayer;
    }

    public void setIdPlayer(Integer idPlayer) {
        IdPlayer = idPlayer;
    }

    public Player getPlayer() {
        return Player;
    }

    public void setPlayer(Player player) {
        Player = player;
    }

    public Date getDtBegin() {
        return DtBegin;
    }

    public void setDtBegin(Date dtBegin) {
        DtBegin = dtBegin;
    }
}
