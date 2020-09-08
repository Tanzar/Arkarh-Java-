/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Modifiers;

import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author spako
 */
public class Actives {
    
    private boolean[] isEffectOnList;
    private List<Active> actives;
    
    public Actives(){
        this.actives = new ArrayList<Active>();
        int effectsCount = ActiveEffect.values().length;
        this.isEffectOnList = new boolean[effectsCount];
        for(int i = 0; i < this.isEffectOnList.length; i++){
            this.isEffectOnList[i] = false;
        }
    }
    
    public void add(Active active){
        if(active != null){
            ActiveEffect effect = active.getEffect();
            if(this.isEffectOnList(effect)){
                this.stack(active);
            }
            else{
                this.addCopy(active);
            }
        }
    }
    
    private boolean isEffectOnList(ActiveEffect effect){
        int index = effect.getIndex();
        return this.isEffectOnList[index];
    }
    
    private void stack(Active active){
        for(Active item: this.actives){
            if(item.isSimillar(active)){
                int value = active.getValue();
                item.changeValue(value);
            }
        }
    }
    
    private void addCopy(Active active){
        if(active != null){
            Active copy = active.copy();
            this.actives.add(copy);
        }
    }
    
    public double getWeaknessValue(EffectSchool effect){
        ActiveEffect weakness = effect.getWeakness();
        if(weakness != null){
            return this.getValueOfEffect(weakness);
        }
        else{
            return 0;
        }
    }
    
    public double getValueOfEffect(ActiveEffect effect){
        if(this.isEffectOnList(effect)){
            return this.getEffectPercentageValue(effect);
        }
        else{
            return 0;
        }
    }
    
    private double getEffectPercentageValue(ActiveEffect effect){
        double value = 0;
        for(Active item: this.actives){
            if(item.isEffect(effect)){
                value = item.getValueAsPercentage();
                break;
            }
        }
        return value;
    }
    
    public void remove(Active item){
        this.actives.remove(item);
    }
    
    public int size(){
        return this.actives.size();
    }
    
}
