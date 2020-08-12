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
public enum DamageType {
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
    
    public boolean isOpposite(DamageType compared){
        DamageType opposite = this.opposite();
        if(this != physical && this != none && opposite == compared){
            return true;
        }
        else{
            return false;
        }
    }
    
    public DamageType opposite(){
        switch(this){
            case light:
                return DamageType.shadow;
            case shadow:
                return DamageType.light;
            case arcane:
                return DamageType.chaos;
            case chaos:
                return DamageType.arcane;
            case fire:
                return DamageType.water;
            case water:
                return DamageType.fire;
            case earth:
                return DamageType.air;
            case air:
                return DamageType.earth;
            case death:
                return DamageType.life;
            case life:
                return DamageType.death;
            default:
                return DamageType.none;
                
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
