/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Equipment;

import com.tanzar.Arkarh.GamePlay.Leader.Leader;
import com.tanzar.Arkarh.GamePlay.Units.Unit;

/**
 *
 * @author spako
 */
public class Equipment {
    
    private Artifacts[] equipped;
    private Artifacts bag;
    
    public Equipment(){
        Slot[] slots = Slot.values();
        this.equipped = new Artifacts[slots.length];
        for(int i = 0; i < slots.length; i++){
            switch(slots[i]){
                case ring:
                    this.equipped[i] = new Artifacts(2);
                    break;
                case bonus:
                    this.equipped[i] = new Artifacts(4);
                    break;
                default:
                    this.equipped[i] = new Artifacts(1);
                    break;
            }
        }
        this.bag = new Artifacts(40);
    }
    
    public Artifacts getEquipped(){
        Artifacts result = new Artifacts();
        for(Artifacts equippedInSlot: this.equipped){
            for(Artifact item: equippedInSlot.toArray()){
                result.add(item);
            }
        }
        return result;
    }
    
    public void addArtifact(Artifact artifact){
        this.bag.add(artifact);
    }
    
    public void equip(int index){
        Artifact artifact = this.bag.get(index);
        if(artifact != null){
            Slot slot = artifact.getSlot();
            Artifacts itemsInSlot = this.equipped[slot.getIndex()];
            if(itemsInSlot.isFull()){
                if(!this.bag.isFull()){
                    Artifact item = itemsInSlot.get(0);
                    itemsInSlot.remove(0);
                    this.bag.add(item);
                    itemsInSlot.add(artifact);
                }
            }
            else{
                itemsInSlot.add(artifact);
            }
        }
    }
    
    public boolean equip(Artifact artifact){
        if(artifact != null){
            Slot slot = artifact.getSlot();
            Artifacts itemsInSlot = this.equipped[slot.getIndex()];
            if(itemsInSlot.isFull()){
                if(!this.bag.isFull()){
                    Artifact item = itemsInSlot.get(0);
                    itemsInSlot.remove(0);
                    this.bag.add(item);
                    itemsInSlot.add(artifact);
                }
            }
            else{
                itemsInSlot.add(artifact);
            }
        }
        return false;//TMP
    }
    
    public void applyBonuses(Leader leader){
        Artifacts equipped = this.getEquipped();
        for(Artifact artifact: equipped.toArray()){
            artifact.applyBonus(leader);
        }
    }
    
    public void applyBonuses(Unit unit){
        Artifacts equipped = this.getEquipped();
        for(Artifact artifact: equipped.toArray()){
            artifact.applyBonus(unit);
        }
    }
}
