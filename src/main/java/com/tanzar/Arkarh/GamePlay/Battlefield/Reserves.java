/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Battlefield;

import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Role;
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
    
    public Unit getUnit(Role role){
        Unit result = null;
        for(Unit unit: this.reserves){
            if(unit.isRole(role) && unit.isCapableToFight()){
                if(result != null){
                    if(unit.isBetterToFightThan(result)){
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
    
    public void addUnit(Unit unit){
        this.reserves.add(unit);
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
            if(unit.isCapableToFight()){
                count++;
            }
        }
        return count;
    }
}
