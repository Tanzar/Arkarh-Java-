/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

import com.tanzar.Arkarh.GamePlay.Modifiers.ActiveEffect;

/**
 *
 * @author spako
 */
public enum EffectSchool {
    none(null),
    physical(null),
    light(ActiveEffect.weaknessToLight),
    shadow(ActiveEffect.weaknessToShadow),
    arcane(ActiveEffect.weaknessToArcane),
    chaos(ActiveEffect.weaknessToChaos),
    fire(ActiveEffect.weaknessToFire),
    water(ActiveEffect.weaknessToWater),
    earth(ActiveEffect.weaknessToEarth),
    air(ActiveEffect.weaknessToAir),
    death(ActiveEffect.weaknessToDeath),
    life(ActiveEffect.weaknessToLife);
    
    private ActiveEffect weakness;
    
    EffectSchool(ActiveEffect weakness){
        this.weakness = weakness;
    }
    
    public ActiveEffect getWeakness(){
        return this.weakness;
    }
    
    public boolean isOpposite(EffectSchool compared){
        EffectSchool opposite = this.opposite();
        if(this != physical && this != none && opposite == compared){
            return true;
        }
        else{
            return false;
        }
    }
    
    public EffectSchool opposite(){
        switch(this){
            case light:
                return EffectSchool.shadow;
            case shadow:
                return EffectSchool.light;
            case arcane:
                return EffectSchool.chaos;
            case chaos:
                return EffectSchool.arcane;
            case fire:
                return EffectSchool.water;
            case water:
                return EffectSchool.fire;
            case earth:
                return EffectSchool.air;
            case air:
                return EffectSchool.earth;
            case death:
                return EffectSchool.life;
            case life:
                return EffectSchool.death;
            default:
                return EffectSchool.none;
                
        }
    }
    
    @Override
    public String toString(){
        switch(this){
            case physical:
                return "physical";
            case light:
                return "light";
            case shadow:
                return "shadow";
            case arcane:
                return "arcane";
            case chaos:
                return "chaos";
            case fire:
                return "fire";
            case water:
                return "water";
            case earth:
                return "earth";
            case air:
                return "air";
            case death:
                return "death";
            case life:
                return "life";
            default:
                return "none";
                
        }
    }
}
