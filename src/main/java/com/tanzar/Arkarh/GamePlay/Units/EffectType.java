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
public enum EffectType {
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
    
    EffectType(ActiveEffect weakness){
        this.weakness = weakness;
    }
    
    public ActiveEffect getWeakness(){
        return this.weakness;
    }
    
    public boolean isOpposite(EffectType compared){
        EffectType opposite = this.opposite();
        if(this != physical && this != none && opposite == compared){
            return true;
        }
        else{
            return false;
        }
    }
    
    public EffectType opposite(){
        switch(this){
            case light:
                return EffectType.shadow;
            case shadow:
                return EffectType.light;
            case arcane:
                return EffectType.chaos;
            case chaos:
                return EffectType.arcane;
            case fire:
                return EffectType.water;
            case water:
                return EffectType.fire;
            case earth:
                return EffectType.air;
            case air:
                return EffectType.earth;
            case death:
                return EffectType.life;
            case life:
                return EffectType.death;
            default:
                return EffectType.none;
                
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
