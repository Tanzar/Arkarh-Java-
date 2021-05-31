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
    bonusHealth(false),
    upkeep(false),
    range(false),
    speed(false),
    baseMorale(false),
    bonusDamage(false),
    ignoreArmorPercentage(true),
    ignoreWardPercentage(true),
    ignoreDefensePercentage(true),
    moraleLoss(false),
    lifeSteal(true),
    weakness(true)
    ;
    
    private boolean isPercentage;
    
    PassiveEffect(boolean isPercentage){
        this.isPercentage = isPercentage;
    }
    
    public boolean isPercentage(){
        return this.isPercentage;
    }
}
