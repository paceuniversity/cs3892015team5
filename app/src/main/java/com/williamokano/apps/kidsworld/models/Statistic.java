package com.williamokano.apps.kidsworld.models;

/**
 * Created by William on 5/5/2015.
 */
public class Statistic {
    private Integer Id;
    private Game Game;
    private Thing Thing;
    private Integer TimesWrong = 0;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Game getGame() {
        return Game;
    }

    public void setGame(Game game) {
        Game = game;
    }

    public Thing getThing() {
        return Thing;
    }

    public void setThing(Thing thing) {
        Thing = thing;
    }

    public Integer getTimesWrong() {
        return TimesWrong;
    }

    public void setTimesWrong(Integer timesWrong) {
        TimesWrong = timesWrong;
    }
}
