/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Modifiers;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;

/**
 *
 * @author spako
 */
public class Passive {
    
    private int index;
    private int stacks;
    private int stacksLimit;
    private int value;
    private PassiveEffect effect;
    private EffectSchool school;
    private PassiveSource source;

    public Passive() {
        this.index = 0;
        this.stacks = 1;
        this.stacksLimit = 1;
        this.value = 0;
        this.effect = PassiveEffect.attack;
        this.school = EffectSchool.physical;
        this.source = PassiveSource.unit;
    }

    public Passive(int index, int stacks, int stacksLimit, int value, PassiveEffect effect, EffectSchool school, PassiveSource source) {
        this.index = index;
        this.stacks = stacks;
        this.stacksLimit = stacksLimit;
        this.value = value;
        this.effect = effect;
        this.school = school;
        this.source = source;
        this.adjustStacks();
    }
    
    public Passive(Passive original){
        this.index = original.getIndex();
        this.stacks = original.getStacks();
        this.stacksLimit = original.getStacksLimit();
        this.value = original.getSingleValue();
        this.effect = original.getEffect();
        this.school = original.getSchool();
        this.source = original.getSource();
        this.adjustStacks();
    }
    
    public Passive(String jsonString) {
        Json json = new Json(jsonString);
        this.index = json.getInt("index");
        this.stacks = json.getInt("stacks");
        this.stacksLimit = json.getInt("stacksLimit");
        this.value = json.getInt("value");
        this.effect = PassiveEffect.valueOf(json.getString("effect"));
        this.school = EffectSchool.valueOf(json.getString("school"));
        this.source = PassiveSource.valueOf(json.getString("source"));
        this.adjustStacks();
    }
    
    public int getIndex(){
        return this.index;
    }
    
    public void setIndex(int index){
        this.index = index;
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

    public PassiveSource getSource() {
        return source;
    }

    public void setSource(PassiveSource source) {
        this.source = source;
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
        if (this.value != other.value) {
            return false;
        }
        if (this.index != other.index) {
            return false;
        }
        if (this.effect != other.effect) {
            return false;
        }
        if (this.source != other.source) {
            return false;
        }
        if (this.school != other.school) {
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
