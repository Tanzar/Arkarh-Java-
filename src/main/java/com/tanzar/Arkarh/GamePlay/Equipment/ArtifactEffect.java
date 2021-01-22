/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Equipment;

/**
 *
 * @author Tanzar
 */
public enum ArtifactEffect {
    attack("leader"),
    defense("leader"),
    spellPower("leader"),
    attackAndDefense("leader"),
    attackAndSpellPower("leader"),
    defenseAndSpellPower("leader"),
    allStats("leader"),
    armor("unit"),
    ward("unit"),
    armorAndWard("unit"),
    morale("unit"),
    moraleLossReduction("unit"),
    speed("unit"),
    bonusDamage("unit"),
    damage("unit"),
    health("unit"),
    upkeep("leader"),
    ;
    
    private String group;
    
    ArtifactEffect(String group){
        this.group = group;
    }
    
    public boolean isLeaderBonus(){
        if(this.group.equals("leader")){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isUnitBonus(){
        if(this.group.equals("unit")){
            return true;
        }
        else{
            return false;
        }
    }
}
