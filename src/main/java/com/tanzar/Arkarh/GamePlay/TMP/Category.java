/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.TMP;

/**
 *
 * @author spako
 */
public enum Category {
    living(true, true),
    undead(false, false),
    demon(false, true),
    elemental(false, false),
    construct(false, false),
    drake(true, false),
    spirit(false, false);
    
    private boolean ressurectable;
    private boolean takesMoraleDamage;
    
    
    Category(boolean ressurectable, boolean moraleDamage){
        this.ressurectable = ressurectable;
        this.takesMoraleDamage = moraleDamage;
    }
    
    public boolean isRessurectable(){
        return this.ressurectable;
    }
    
    public boolean takesMoraleDamage(){
        return this.takesMoraleDamage;
    }
}
