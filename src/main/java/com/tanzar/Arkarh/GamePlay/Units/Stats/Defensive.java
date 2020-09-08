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
public class Defensive {
    private int defense;
    private int armor;
    private int ward;
    private int baseHealth;
    private int health;
    private final int maxArmor = 75;
    private final int maxWard = 75;
    
    public void setDefense(int value){
        this.defense = value;
    }
    
    public int getDefense(){
        if(this.defense < 0){
            return 0;
        }
        else{
            return this.defense;
        }
    }
    
    public void setArmor(int value){
        this.armor = value;
    }
    
    public int getArmor(){
        if(this.armor < 0){
            return 0;
        }
        else{
            if(this.armor > this.maxArmor){
                return this.maxArmor;
            }
            else{
                return this.armor;
            }
        }
    }
    
    public void setWard(int value){
        this.ward += value;
    }
    
    public int getWard(){
        if(this.ward < 0){
            return 0;
        }
        else{
            if(this.ward > this.maxWard){
                return this.maxWard;
            }
            else{
                return this.ward;
            }
        }
    }
    
    public void setBaseHealth(int value){
        this.baseHealth = value;
    }
    
    public int getBaseHealth(){
        if(this.baseHealth < 1){
            return 1;
        }
        else{
            return this.baseHealth;
        }
    }

    public int setHealth(int value){
        this.health += value;
        return this.health;
    }
    
    public int getHealth(){
        if(this.health < 0){
            return 0;
        }
        else{
            return this.health;
        }
    }
    
}
