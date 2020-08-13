/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

/**
 *
 * @author spako
 */
public enum EffectType {
    none,
    physical,
    light,
    shadow,
    arcane,
    chaos,
    fire,
    water,
    earth,
    air,
    death,
    life;
    
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
                return "Physical";
            case light:
                return "Light";
            case shadow:
                return "Shadow";
            case arcane:
                return "Arcane";
            case chaos:
                return "Chaos";
            case fire:
                return "Fire";
            case water:
                return "Water";
            case earth:
                return "Earth";
            case air:
                return "Air";
            case death:
                return "Death";
            case life:
                return "Life";
            default:
                return "None";
                
        }
    }
}
