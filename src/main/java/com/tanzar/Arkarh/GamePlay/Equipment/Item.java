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
public abstract class Item {
    
    private String name;
    private Slot slot;
    
    public Item(Slot slot){
        this.name = "";
        this.slot = slot;
    }
    
    public Slot getSlot(){
        return this.slot;
    }
}
