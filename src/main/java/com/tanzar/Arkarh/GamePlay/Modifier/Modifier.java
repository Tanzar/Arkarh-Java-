/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Modifier;

import com.tanzar.Arkarh.GamePlay.Units.Unit;

/**
 *
 * @author spako
 */
public abstract class Modifier {
    
    private String type;
    private int modifier;
    
    
    public void apply(Unit unit){
        
    }
    
    public void revoke(Unit unit){
        
    }
}
