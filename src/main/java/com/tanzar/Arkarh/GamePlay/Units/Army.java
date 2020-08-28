/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

import com.tanzar.Arkarh.GamePlay.Combat.BattleSide;
import java.util.List;

/**
 *
 * @author spako
 */
public class Army {
    
    private Leader leader;
    
    private Units unitsSplitPerRole[];
    
    public Army(){
        this.unitsSplitPerRole = new Units[Role.countRoles()];
        for(int i = 0; i < this.unitsSplitPerRole.length; i++){
            this.unitsSplitPerRole[i] = new Units();
        }
    }
    
    public void setSide(BattleSide side){
        for(int i = 0; i < this.unitsSplitPerRole.length; i++){
            for(int j = 0; j < this.unitsSplitPerRole[i].size(); j++){
                Unit unit = this.unitsSplitPerRole[i].get(j);
                unit.setSide(side);
            }
        }
    }
    /*
    public Army copy(){
        Army copy = new Army();
        for(int i = 0; i < this.unitsSplitPerRole.length; i++){
            Units unitsCopy = this.unitsSplitPerRole[i].copy();
            copy.addUnits(unitsCopy);
        }
        return copy;
    }*/
    
    public void addUnits(Units units){
        for(int i = 0; i < units.size(); i++){
            Unit unit = units.get(i);
            this.addUnit(unit);
        }
    }
    
    public void addUnit(Unit unit){
        Role unitRole = unit.getRole();
        int roleIndex = unitRole.getIndex();
        this.unitsSplitPerRole[roleIndex].add(unit);
    }
    
    public Units getUnits(Role role){
        int index = role.getIndex();
        return this.unitsSplitPerRole[index];
    }
    
    public int size(){
        Role roles[] = Role.values();
        int size = 0;
        for(int i = 0; i < roles.length; i++){
            size += this.unitsSplitPerRole[i].size();
        }
        return size;
    }
}
