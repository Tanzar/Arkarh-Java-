/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author spako
 */
public class Units {
    
    private List<Unit> unitList;
    
    public Units(){
        this.unitList = new ArrayList<Unit>();
    }
    
    public Unit get(int index){
        if(this.size() == 0 || index < 0 || index >= this.unitList.size()){
            return null;
        }
        else{
            return this.unitList.get(index);
        }
    }
    
    public Unit getFirst(){
        return this.get(0);
    }
    
    public Unit getAndRemoveFirst(){
        Unit unit = this.get(0);
        this.unitList.remove(unit);
        return unit;
    }
    
    public Unit getAndRemove(Role role){
        for(Unit unit : this.unitList){
            if(unit.isRole(role)){
                this.unitList.remove(unit);
                return unit;
            }
        }
        return null;
    }
    
    public Units getBySpeed(int speed){
        Units result = new Units();
        for(Unit unit: this.unitList){
            if(unit.getSpeed() == speed){
                result.add(unit);
            }
        }
        return result;
    }
    
    public int getNextSpeed(int speed){
        int result = 0;
        for(Unit unit: this.unitList){
            int unitSpeed = unit.getSpeed();
            if(unitSpeed < speed && unitSpeed > result){
                result = unitSpeed;
            }
        }
        return result;
    }
    
    public void remove(int index){
        if(index >= 0 && index < this.unitList.size()){
            this.unitList.remove(index);
        }
    }
    
    public void remove(Unit unit){
        this.unitList.remove(unit);
    }
    
    public void addUnits(Units units){
        for(int i = 0; i < units.size(); i++){
            Unit unit = units.get(i);
            this.add(unit);
        }
    }
    
    public void add(Unit unit){
        this.unitList.add(unit);
    }
    
    public int size(){
        return this.unitList.size();
    }
    
    public boolean isEmpty(){
        if(this.unitList.size() < 1){
            return true;
        }
        else{
            return false;
        }
    }
    
    public Units copy(){
        Units copy = new Units();
        for(Unit unit : this.unitList){
            Unit unitCopy = unit.copy();
            copy.add(unitCopy);
        }
        return copy;
    }
    
    public void orderBySpeed(){
        this.unitList.sort(null);
    }
    
    public Unit[] toArray(){
        Unit[] array = new Unit[this.unitList.size()];
        this.unitList.toArray(array);
        return array;
    }
}
