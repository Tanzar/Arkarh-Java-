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
public class Position {
    private int positionInLine;
    private boolean front;
    private int dmg;
    
    public Position(int positionInLine, boolean isFront){
        this.positionInLine = positionInLine;
        this.front = isFront;
        this.dmg = 0;
    }
    
    public int getPositionInLine(){
        return this.positionInLine;
    }
    
    public boolean isOnFrontLine(){
        return this.front;
    }
    
    public int getDamage(){
        return this.dmg;
    }
    
    public void setDamage(int damage){
        this.dmg = damage;
    }
    
    public void addDamage(int value){
        this.dmg += value;
    }
    
    public boolean isValid(int upperLimit){
        if(this.positionInLine < 0 || this.positionInLine > upperLimit){
            return false;
        }
        else{
            return true;
        }
    }
}
