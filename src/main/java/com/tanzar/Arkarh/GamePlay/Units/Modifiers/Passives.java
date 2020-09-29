/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Modifiers;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author spako
 */
public class Passives {
    
    private List<Passive> passives;
    
    public Passives(){
        this.passives = new ArrayList<Passive>();
    }
    
    public void add(Passive passive){
        if(passive != null){
            boolean found = false;
            for(Passive item: this.passives){
                if(item.equals(passive)){
                    if(!item.isAtStacksLimit()){
                        item.addStacks(passive.getStacks());
                    }
                    found = true;
                    break;
                }
            }
            if(!found){
                this.passives.add(passive);
            }
        }
    }
    
    public Passive get(int index){
        return this.passives.get(index);
    }
    
    public void remove(int index){
        this.passives.remove(index);
    }
    
    public void remove(Passive item){
        this.passives.remove(item);
    }
    
    public int size(){
        return this.passives.size();
    }
    
    public Passives getByEffect(PassiveEffect effect){
        Passives results = new Passives();
        for(Passive passive: this.passives){
            if(passive.getEffect().equals(effect)){
                results.add(passive);
            }
        }
        return results;
    }
    
    public int summarizeValues(PassiveEffect effect){
        int value = 0;
        for(Passive passive: this.passives){
            if(passive.getEffect().equals(effect)){
                value += passive.getTotalValue();
            }
        }
        return value;
    }
    
    public Passive[] toArray(){
        Passive[] array = new Passive[this.size()];
        this.passives.toArray(array);
        return array;
    }
}
