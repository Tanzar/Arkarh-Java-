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
public class Equipment {
    
    //private Item 
    private Item rings[];
    private Item equipment[];
    private Item bag[];
    
    
    public Equipment(){
        Slot slots[] = Slot.values();
        this.equipment = new Item[slots.length];
        this.bag = new Item[20];
    }
    
    public void equip(int bagIndex){
        Item itemToEquip = this.getItemFromBag(bagIndex);
        if(itemToEquip != null){
            //Item itemToUnequip = this.getItem(itemToEquip.getSlot());
            //this.equipment[]
        }
    }
    
    private Item getItemFromBag(int bagIndex){
        if(bagIndex >= 0 && bagIndex < this.bag.length){
            return this.bag[bagIndex];
        }
        else{
            return null;
        }
    }
    
    public void unequip(Slot slot){
        
    }
    
    public Item getItem(Slot slot){
        int index = slot.toInt();
        return this.equipment[index];
    }
}
