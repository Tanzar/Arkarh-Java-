/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Stats;

/**
 *
 * @author spako
 */
public class Special {
    private int upkeep;
    private int speed;
    private int range;
    private int baseMorale;
    private int moraleLoss = 10;

    public Special() {
    }

    public Special(int upkeep, int speed, int range, int baseMorale) {
        this.upkeep = upkeep;
        this.speed = speed;
        this.range = range;
        this.baseMorale = baseMorale;
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

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
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
