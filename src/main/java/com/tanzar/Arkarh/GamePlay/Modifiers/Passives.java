/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Modifiers;

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
            this.passives.add(passive);
        }
    }
    
    public void addCopy(Passive passive){
        if(passive != null){
            Passive copy = passive.copy();
            this.passives.add(copy);
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
    
    public void stack(Passive passive){
        for(Passive item : passives){
            if(item.isSimillar(passive)){
                double value = passive.getValue();
                item.changeValue(value);
                break;
            }
        }
        //if not found
        this.addCopy(passive);
    }
    
    public static Passives merge(Passives first, Passives second){
        Passives all = Passives.combine(first, second);
        Passives unique = new Passives();
        for(int i = 0; i < all.size(); i++){
            Passive mod = all.get(i);
            unique.stack(mod);
        }
        return unique;
    }
    
    public static Passives combine(Passives first, Passives second){
        Passives mods = new Passives();
        for(int i = 0; i < first.size(); i++){
            Passive passive = first.get(i);
            mods.add(passive);
        }
        for(int i = 0; i < second.size(); i ++){
            Passive passive = second.get(i);
            mods.add(passive);
        }
        return mods;
    }
}
