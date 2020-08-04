/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Equipment;

/**
 *
 * @author spako
 */
public enum Slot {
    helmet(0),
    armor(1),
    boots(2),
    weapon(3),
    shield(4),
    necklace(5),
    ring(6),
    equipment(7);
    
    private int index;
    
    Slot(int index){
        this.index = index;
    }
    
    public int toInt(){
        return this.index;
    }
}
