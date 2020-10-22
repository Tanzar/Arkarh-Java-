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
    private State state;
    private BattleSide side = BattleSide.none;
    private int health;
    private int morale;

    public Status() {
    }

    public Status(int position, int health, int morale) {
        this.position = position;
        this.health = health;
        this.morale = morale;
        this.state = State.alive;
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
    
    public State getState(){
        return this.state;
    }
    
    public boolean isAlive(){
        if(this.state.equals(State.alive)){
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
        if(this.state.equals(State.dead)){
            this.state = State.risen;
        }
    }
    
    public void damageMorale(int loss){
        this.morale = this.morale - loss;
    }
    
    public void takeDamage(int damage){
        this.health = this.health - damage;
    }
    
    public void heal(int value){
        this.health = this.health + value;
    }
    
    public void updateState(int maxHealth){
        if(this.health < 1 && this.state.equals(State.alive)){
            this.state = State.dead;
        }
        if(this.health > maxHealth){
            this.health = maxHealth;
        }
    }

}
