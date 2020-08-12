/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Battlefield;

/**
 *
 * @author spako
 */
public class DamagePattern {
    private int width;
    private int[][] pattern;
    
    public DamagePattern(int width){
        this.width = width;
        this.pattern = new int[this.width][2];
        for(int i = 0; i < this.width; i++){
            this.pattern[i][0] = 0;
            this.pattern[i][1] = 0;
        }
    }
    
    public int getDamage(int index, boolean isFront){
        if(index < 0 || index >= this.width){
            return 0;
        }
        else{
            if(isFront){
                return this.pattern[index][0];
            }
            else{
                return this.pattern[index][1];
            }
        }
    }
    
    public void setDamage(int index, boolean isFront, int damage){
        if(index >= 0 && index < this.width){
            if(isFront){
                this.pattern[index][0] = damage;
            }
            else{
                this.pattern[index][1] = damage;
            }
        }
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public void applyDamagePattern(DamagePattern damagePattern){
        if(this.width == damagePattern.getWidth()){
            for(int i = 0; i < this.width; i++){
                int damage = damagePattern.getDamage(i, true);
                this.pattern[i][0] += damage;
                damage = damagePattern.getDamage(i, false);
                this.pattern[i][1] += damage;
            }
        }
    }
    
    public void applyDamage(Position[] positions){
        for(int i = 0; i < positions.length; i++){
            if(positions[i].isValid(this.width - 1)){
                int index = positions[i].getPositionInLine();
                if(positions[i].isOnFrontLine()){
                    this.pattern[index][0] = positions[i].getDamage();
                }
                else{
                    this.pattern[index][1] = positions[i].getDamage();
                }
            }
        }
    }
    
}
