/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Modifiers;

/**
 *
 * @author spako
 */
public enum PassiveEffect {
    attack(false),
    spellPower(false),
    damage(false),
    healing(false),
    attackStyle(false),
    defense(false),
    armor(false),
    ward(false),
    baseHealth(false),
    upkeep(false),
    range(false),
    speed(false),
    baseMorale(false),
    bonusDamage(false),
    ignoreArmor(true),
    ignoreWard(true),
    ignoreDefense(true),
    moraleDamage(false)
    ;
    
    private boolean isPercentage;
    
    PassiveEffect(boolean isPercentage){
        this.isPercentage = isPercentage;
    }
    
    public boolean isPercentage(){
        return this.isPercentage;
    }
}
