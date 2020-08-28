/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Modifiers;

/**
 *
 * @author spako
 */
public enum ActiveEffect {
    ignoreDefense(0),
    ignoreArmor(1),
    ignoreWard(2),
    moraleDamage(3),
    lifeSteal(4),
    multipleAttack(5),
    weaknessToLight(6),
    weaknessToShadow(7),
    weaknessToArcane(8),
    weaknessToChaos(9),
    weaknessToFire(10),
    weaknessToWater(11),
    weaknessToEarth(12),
    weaknessToAir(13),
    weaknessToDeath(14),
    weaknessToLife(15);
    
    private int index;
    
    ActiveEffect(int index){
        this.index = index;
    }
    
    public int getIndex(){
        return this.index;
    }
}
