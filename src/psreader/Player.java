/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psreader;

/**
 *
 * @author zolih
 */
public class Player {

    private String playerName;
    private String notes;
    private String color;

    public Player(String playerName, String notes, String color) {
        this.playerName = playerName;
        this.notes = notes;
        this.color = color;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getNotes() {
        return notes;
    }

    public String getColor() {
        return color;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
