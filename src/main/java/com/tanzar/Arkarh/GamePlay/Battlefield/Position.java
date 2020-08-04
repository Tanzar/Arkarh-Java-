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
    
    public Position(int positionInLine, boolean isFront){
        this.positionInLine = positionInLine;
        this.front = isFront;
    }
    
    public int getPositionInLine(){
        return this.positionInLine;
    }
    
    public boolean isOnFrontLine(){
        return this.front;
    }
}
