/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Modifiers;

import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 *
 * @author spako
 */
public class Passive {
    
    private String name;
    private int stacks;
    private int stacksLimit;
    private boolean isStackable;
    private int value;
    private PassiveEffect effect;
    private EffectSchool school;

    public Passive(String name, int stacks, int stacksLimit, boolean isStackable, int value, PassiveEffect effect, EffectSchool school) {
        this.name = name;
        this.stacks = stacks;
        this.stacksLimit = stacksLimit;
        this.isStackable = isStackable;
        this.value = value;
        this.effect = effect;
        this.school = school;
        this.adjustStacks();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStacks() {
        return stacks;
    }

    public void setStacks(int stacks) {
        this.stacks = stacks;
        this.adjustStacks();
    }
    
    public void addStacks(int numberOfStacks){
        this.stacks += numberOfStacks;
        this.adjustStacks();
    }

    public int getStacksLimit() {
        return stacksLimit;
    }

    public void setStacksLimit(int stacksLimit) {
        this.stacksLimit = stacksLimit;
        this.adjustStacks();
    }

    public boolean isIsStackable() {
        return isStackable;
    }

    public void setIsStackable(boolean isStackable) {
        this.isStackable = isStackable;
    }

    public int getSingleValue() {
        return value;
    }
    
    public int getTotalValue(){
        return this.value * this.stacks;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public PassiveEffect getEffect() {
        return effect;
    }

    public void setEffect(PassiveEffect effect) {
        this.effect = effect;
    }
    
    public EffectSchool getSchool(){
        return this.school;
    }
    
    public void setSchool(EffectSchool school){
        this.school = school;
    }
    
    public boolean isAtStacksLimit(){
        if(this.stacks == this.stacksLimit){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Passive other = (Passive) obj;
        if (this.isStackable != other.isStackable) {
            return false;
        }
        if (this.value != other.value) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.effect != other.effect) {
            return false;
        }
        return true;
    }

    private void adjustStacks(){
        if(this.stacks > this.stacksLimit){
            this.stacks = this.stacksLimit;
        }
    }
}
