/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Combat;

import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Status;
import com.tanzar.Arkarh.GamePlay.Units.Units;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author spako
 */
public class Reserves {
    
    private List<Unit> reserves;
    
    public Reserves(){
        this.reserves = new ArrayList<Unit>();
    }
    
    public Unit get(int index){
        return this.reserves.get(index);
    }
    
    public Unit get(boolean front){
        Unit result = null;
        for(Unit unit: this.reserves){
            if(unit.isCappableToFight()){
                if(unit.isFront() == front){
                    result = unit;
                    break;
                }
            }
        }
        if(result != null){
            this.reserves.remove(result);
        }
        return result;
    }
    
    public Unit getUnit(Role role){
        Unit result = null;
        for(Unit unit: this.reserves){
            if(unit.isRole(role) && unit.isCappableToFight()){
                if(result != null){
                    if(unit.isFasterThan(result)){
                        result = unit;
                    }
                }
                else{
                    result = unit;
                }
            }
        }
        if(result != null){
            this.reserves.remove(result);
        }
        return result;
    }
    
    public Units getAll(){
        Units units = new Units();
        for(Unit unit: this.reserves){
            units.add(unit);
        }
        return units;
    }
    
    public void addUnit(Unit unit){
        Status status = unit.getStatus();
        status.setPosition(-1);
        this.reserves.add(unit);
    }
    
    public void remove(Unit unit){
        this.reserves.remove(unit);
    }
    
    public int size(){
        return this.reserves.size();
    }
    
    public int countAlive(){
        int count = 0;
        for(Unit unit: this.reserves){
            if(unit.isAlive()){
                count++;
            }
        }
        return count;
    }
    
    public int countCappableToFight(){
        int count = 0;
        for(Unit unit: this.reserves){
            if(unit.isCappableToFight()){
                count++;
            }
        }
        return count;
    }
    
    public void orderBySpeed(){
        this.reserves.sort(null);
    }
    
}
