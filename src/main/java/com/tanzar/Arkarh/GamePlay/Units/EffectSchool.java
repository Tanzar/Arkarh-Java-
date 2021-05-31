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
public enum EffectSchool {
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
    
    public boolean isOpposite(EffectSchool compared){
        EffectSchool opposite = this.opposite();
        if(this != physical && opposite == compared){
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
