/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Modifiers;

import com.tanzar.Arkarh.GamePlay.Units.Unit;
import java.lang.reflect.Field;

/**
 *
 * @author spako
 */
public class Passive {
    
    private String stat;
    private boolean isPercentage;
    private int value;
    
    public Passive(String stat,boolean isPercentage , int value){
        this.stat = stat;
        this.isPercentage = isPercentage;
        this.value = value;
    }
    
    public String getStatName(){
        return this.stat;
    }
    
    public double getValue(){
        return this.value;
    }
    
    public void changeValue(double value){
        this.value += value;
    }
    
    public boolean isPercentage(){
        return this.isPercentage;
    }
    
    public Passive copy(){
        Passive copy = new Passive(this.stat, this.isPercentage, this.value);
        return copy;
    }
    
    public boolean isSimillar(Passive passive){
        String modStat = passive.getStatName();
        if(this.stat.equals(modStat) && this.isPercentage == passive.isPercentage()){
            return true;
        }
        else{
            return false;
        }
    }
}
