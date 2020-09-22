/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Stats;

import com.tanzar.Arkarh.GamePlay.Units.AttackStyle;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;

/**
 *
 * @author spako
 */
public class Offensive {
    private int attack;
    private int spellPower;
    private EffectSchool school;
    private int damage;
    private int baseHealingValue;
    private AttackStyle attackStyle;

    public Offensive() {
    }

    public Offensive(int attack, int spellPower, EffectSchool school, int damage, int healing, AttackStyle attackStyle) {
        this.attack = attack;
        this.spellPower = spellPower;
        this.school = school;
        this.damage = damage;
        this.baseHealingValue = healing;
        this.attackStyle = attackStyle;
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
    
    public void setSchool(EffectSchool school){
        this.school = school;
    }
    
    public EffectSchool getSchool(){
        return this.school;
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
    
    public void setAttackStyle(AttackStyle style){
        this.attackStyle = style;
    }
    
    public AttackStyle getAttackType(){
        return this.attackStyle;
    }
    
}
