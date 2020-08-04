/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

import java.util.List;

/**
 *
 * @author spako
 */
public class Army {
    
    private Leader leader;
    
    private Units units[];
    
    public Army(){
        this.units = new Units[Role.countRoles()];
        for(int i = 0; i < this.units.length; i++){
            this.units[i] = new Units();
        }
    }
    
    public Army copy(){
        Army copy = new Army();
        for(int i = 0; i < this.units.length; i++){
            Units unitsCopy = this.units[i].copy();
            copy.addUnits(unitsCopy);
        }
        return copy;
    }
    
    public void addUnits(Units units){
        for(int i = 0; i < units.size(); i++){
            Unit unit = units.get(i);
            this.addUnit(unit);
        }
    }
    
    public void addUnit(Unit unit){
        Role unitRole = unit.getRole();
        int roleIndex = unitRole.getIndex();
        this.units[roleIndex].addUnit(unit);
    }
    
    public Units getUnits(Role role){
        int index = role.getIndex();
        return this.units[index];
    }
    
    public int size(){
        Role roles[] = Role.values();
        int size = 0;
        for(int i = 0; i < roles.length; i++){
            size += this.units[i].size();
        }
        return size;
    }
}
