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
    none(0, false),
    warrior(1, false),
    flanker(2, false),
    mage(3, true),
    shooter(4, true);
    
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
        if(this != none){
            Role roles[] = Role.values();
            int index = this.index + 1;
            if(index == roles.length){
                index = 1;
            }
            Role result = null;
            while(index != this.index && result == null){
                Role role = roles[index];
                if(role.isRanged() == this.ranged){
                    result = role;
                }
                index++;
                if(index == roles.length){
                    index = 1;
                }
            }
            return result;
        }
        else{
            return none;
        }
    }
}
