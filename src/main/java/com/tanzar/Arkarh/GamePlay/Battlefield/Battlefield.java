/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Battlefield;

import com.tanzar.Arkarh.GamePlay.Units.Army;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;

/**
 *
 * @author spako
 */
public class Battlefield {
    
    private int fieldWidth;
    
    private Army attacker;
    private Army defender;
    
    private boolean fotrified;
    
    private Side attackingSide;
    private Side defendingSide;
    
    public Battlefield(int fieldWidth, Army attacker, Army defender){
        this.fieldWidth = fieldWidth;
        this.attacker = attacker;
        this.defender = defender;
        this.attackingSide = new Side(attacker, fieldWidth);
        this.defendingSide = new Side(defender, fieldWidth);
        this.fotrified = false;
    }
    
    public void enableFortifications(){
        this.fotrified = true;
    }
    
    public void disableFortifications(){
        this.fotrified = false;
    }
    
    public void tick(){
        Units attackersOrder = this.attackingSide.getFieldedUnitsOrderedBySpeed();
        Units defendersOrder = this.defendingSide.getFieldedUnitsOrderedBySpeed();
        int currentSpeed = this.getHighestSpeed(attackersOrder, defendersOrder);
        while(currentSpeed > 0){
            boolean flag = true;
            int defendersDamageTaken[][] = new int[this.fieldWidth][2];
            int attackersDamageTaken[][] = new int[this.fieldWidth][2];
            while(flag){
                Unit attacker = attackersOrder.getFirst();
                Unit defender = defendersOrder.getFirst();
                if(attacker == null && defender == null){
                    currentSpeed = 0;
                    flag = false;
                }
                else{
                    if(attacker == null){
                        
                    }
                    if(defender == null){
                        
                    }
                    if(attacker != null && defender != null){
                        
                    }
                }
            }
        }
    }
    
    private int getHighestSpeed(Units attackersOrder, Units defendersOrder){
        Unit firstAttacker = attackersOrder.get(0);
        Unit firstDefender = defendersOrder.get(0);
        if(firstAttacker.getSpeed() >= firstDefender.getSpeed()){
            return firstAttacker.getSpeed();
        }
        else{
            return firstDefender.getSpeed();
        }
    }
    
    public Units test(){
        return this.attackingSide.getFieldedUnitsOrderedBySpeed();
    }
    
    @Override
    public String toString(){
        String result = "Field Width = " + this.fieldWidth + System.lineSeparator();
        result += System.lineSeparator();
        result += System.lineSeparator();
        result += "Attackers: " + System.lineSeparator();
        result += this.attackingSide.toString();
        result += System.lineSeparator();
        result += System.lineSeparator();
        result += "Defenders: " + System.lineSeparator();
        result += this.defendingSide.toString();
        return result;
    }
}
