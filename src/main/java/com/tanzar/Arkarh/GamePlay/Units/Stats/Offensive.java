/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Stats;

import com.tanzar.Arkarh.GamePlay.Units.TargetsSelection;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;

/**
 *
 * @author spako
 */
public class Offensive {
    private int attack;
    private int spellPower;
    private int damage;
    private int baseHealingValue;

    public Offensive() {
    }

    public Offensive(int attack, int spellPower, int damage, int healing) {
        this.attack = attack;
        this.spellPower = spellPower;
        this.damage = damage;
        this.baseHealingValue = healing;
    }
    
    public void setAttack(int value){
        this.attack = value;
    }
    
    public int getAttack(){
        if(this.attack < 0){
            return 0;
        }
        else{
            return this.attack;
        }
    }
    
    public void setSpellPower(int value){
        this.spellPower = value;
    }
    
    public int getSpellPower(){
        if(this.spellPower < 0){
            return 0;
        }
        else{
            return this.spellPower;
        }
    }
    
    public void setDamage(int value){
        this.damage = value;
    }
    
    public int getDamage(){
        if(this.damage < 1){
            return 1;
        }
        else{
            return this.damage;
        }
    }
    
    public void setBaseHealing(int value){
        this.baseHealingValue = value;
    }
    
    public int getBaseHealingValue(){
        if(this.baseHealingValue < 0){
            return 0;
        }
        else{
            return this.baseHealingValue;
        }
    }
    
}
