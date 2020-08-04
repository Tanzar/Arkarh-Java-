/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

/**
 *
 * @author spako
 */
public enum Role {
    warrior(0, false),
    flanker(1, false),
    mage(2, true),
    shooter(3, true);
    
    private int index;
    private boolean ranged;
    
    
    Role(int index, boolean ranged){
        this.index = index;
        this.ranged = ranged;
    }
    
    public int getIndex(){
        return this.index;
    }
    
    public boolean isRanged(){
        return this.ranged;
    }
    
    public static int countRoles(){
        return Role.values().length;
    }
    
    public Role nextRoleInLine(){
        Role roles[] = Role.values();
        int index = this.index;
        Role result = null;
        while(index != this.index && result == null){
            Role role = roles[index];
            if(role.isRanged() == this.ranged){
                result = role;
            }
            index++;
            if(index == roles.length){
                index = 0;
            }
        }
        return result;
    }
}
