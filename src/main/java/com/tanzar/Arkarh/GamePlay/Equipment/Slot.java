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
    head(0),
    neck(1),
    chest(2),
    hands(3),
    legs(4),
    feet(5),
    mainhand(6),
    offhand(7),
    ring(8),
    bonus(9),
    ;
    
    private int index;
    
    Slot(int index){
        this.index = index;
    }
    
    public int getIndex(){
        return this.index;
    }
}
