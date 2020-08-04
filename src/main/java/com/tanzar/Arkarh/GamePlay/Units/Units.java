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
        if(index < 0 || index >= this.unitList.size()){
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
    
    public void remove(int index){
        if(index >= 0 && index < this.unitList.size()){
            this.unitList.remove(index);
        }
    }
    
    public void remove(Unit unit){
        this.unitList.remove(unit);
    }
    
    public void addUnits(Unit[] units){
        for(int i = 0; i < units.length; i++){
            this.unitList.add(units[i]);
        }
    }
    
    public void addUnit(Unit unit){
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
            copy.addUnit(unitCopy);
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