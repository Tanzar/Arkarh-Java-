/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Stats;

import com.tanzar.Arkarh.Converter.Json;

/**
 *
 * @author spako
 */
public class Special {
    private int upkeep;
    private int speed;
    private int baseMorale;
    private int moraleLoss = 10;

    public Special() {
        this.upkeep = 1;
        this.speed = 1;
        this.baseMorale = 1000;
    }

    public Special(int upkeep, int speed, int baseMorale) {
        this.upkeep = upkeep;
        this.speed = speed;
        this.baseMorale = baseMorale;
    }
    
    public Special(String specialJson){
        Json json = new Json(specialJson);
        this.baseMorale = json.getInt("baseMorale");
        this.speed = json.getInt("speed");
        this.upkeep = json.getInt("upkeep");
    }

    public int getUpkeep() {
        return upkeep;
    }

    public void setUpkeep(int upkeep) {
        this.upkeep = upkeep;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBaseMorale() {
        return baseMorale;
    }

    public void setBaseMorale(int baseMorale) {
        this.baseMorale = baseMorale;
    }

    public int getMoraleLoss() {
        return moraleLoss;
    }

    public void setMoraleLoss(int moraleLoss) {
        this.moraleLoss = moraleLoss;
    }
    
    
}
