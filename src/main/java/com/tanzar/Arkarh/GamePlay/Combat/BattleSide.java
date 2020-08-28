/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Combat;

/**
 *
 * @author spako
 */
public enum BattleSide {
    attacker,
    defender,
    none;
    
    public BattleSide opposite(){
        if(this == attacker){
            return BattleSide.defender;
        }
        if(this == defender){
            return BattleSide.attacker;
        }
        else{
            return BattleSide.none;
        }
    }
    
    @Override
    public String toString(){
        if(this == attacker){
            return "Attacker ";
        }
        if(this == defender){
            return "Defender ";
        }
        else{
            return "No side ";
        }
    }
}
