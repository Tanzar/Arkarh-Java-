/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Stats;

import com.tanzar.Arkarh.GamePlay.Combat.BattleSide;

/**
 *
 * @author spako
 */
public class Status {
    private int position;
    private BattleSide side = BattleSide.none;
    private int health;
    private int morale;
    private boolean risen;

    public Status() {
    }

    public Status(int position, int health, int morale) {
        this.position = position;
        this.health = health;
        this.morale = morale;
        this.risen = false;
    }
    
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public BattleSide getSide() {
        return side;
    }

    public void setSide(BattleSide side) {
        this.side = side;
    }
    
    public boolean isAlive(){
        if(!risen && health > 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isCappableToFight(){
        if(this.isAlive() && this.morale > 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void rise(){
        this.risen = true;
    }
    
    public void damageMorale(int loss){
        this.morale = this.morale - loss;
    }
    
    public void takeDamage(int damage){
        this.health = this.health - damage;
    }

}
