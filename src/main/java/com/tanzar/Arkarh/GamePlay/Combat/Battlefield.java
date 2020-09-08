/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Combat;

import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.Combat.Log.ReportEntry;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Trigger;
import com.tanzar.Arkarh.GamePlay.Units.Army;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;
import java.util.ArrayList;
import java.util.List;

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
        this.attackingSide = new Side(attacker, fieldWidth, BattleSide.attacker);
        this.defendingSide = new Side(defender, fieldWidth, BattleSide.defender);
        this.fotrified = false;
    }
    
    public void enableFortifications(){
        this.fotrified = true;
    }
    
    public void disableFortifications(){
        this.fotrified = false;
    }
    
    public CombatReport fight(){
        BattleState battleState = BattleState.ongoing;
        CombatReport report = new CombatReport(this.attackingSide, this.defendingSide);
        while(battleState == BattleState.ongoing){
            report.nextTick();
            this.tick(report);
            battleState = this.getState();
        }
        report.combatResult(battleState);
        return report;
    }
    
    public BattleState getState(){
        boolean attackersCappable = this.attackingSide.isSideCappableToFight();
        boolean defendersCappable = this.defendingSide.isSideCappableToFight();
        if(attackersCappable && defendersCappable){
            return BattleState.ongoing;
        }
        else{
            if(!attackersCappable && !defendersCappable){
                return BattleState.draw;
            }
            else{
                if(attackersCappable){
                    return BattleState.attackerWin;
                }
                else{
                    return BattleState.defenderWin;
                }
            }
        }
    }
    
    private CombatReport tick(CombatReport report){
        Units attackersOrder = this.attackingSide.getFieldedUnitsOrderedBySpeed();
        Units defendersOrder = this.defendingSide.getFieldedUnitsOrderedBySpeed();
        int currentSpeed = this.getHighestSpeed(attackersOrder, defendersOrder);
        while(currentSpeed > 0){
            report.nextWeave();
            Units attackersWave = attackersOrder.getBySpeed(currentSpeed);
            Units defendersWave = defendersOrder.getBySpeed(currentSpeed);
            Units wave = this.combineWaves(attackersWave, defendersWave);
            this.unitsActions(wave, report);
            currentSpeed = this.selectNextSpeed(attackersOrder, defendersOrder, currentSpeed);
        }
        int reinforcedCount = this.attackingSide.reorganizeLines(report, 0);
        this.defendingSide.reorganizeLines(report, reinforcedCount);
        return report;
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
    
    private Units combineWaves(Units attackers, Units defenders){
        Units wave = new Units();
        wave.addUnits(attackers);
        wave.addUnits(defenders);
        return wave;
    }
    
    private void unitsActions(Units units, CombatReport report){
        for(int i = 0; i < units.size(); i++){
            Unit source = units.get(i);
            Units targets = this.attackingSide.getTargets(source);
            targets.addUnits(this.defendingSide.getTargets(source));
            for(int j = 0; j < targets.size(); j++){
                Unit target = targets.get(j);
                UnitActions actions = new UnitActions(report);
                actions.action(source, target);
            }
        }
        
    }
    
    private int selectNextSpeed(Units attackersOrder, Units defendersOrder, int currentSpeed){
        int nextSpeed = 0;
        int nextAttackSpeed = attackersOrder.getNextSpeed(currentSpeed);
        int nextDefenseSpeed = defendersOrder.getNextSpeed(currentSpeed);
        if(nextAttackSpeed > nextDefenseSpeed){
            nextSpeed = nextAttackSpeed;
        }
        else{
            nextSpeed = nextDefenseSpeed;
        }
        return nextSpeed;
    }
    
    public void newTick(){
        Unit[] fieldedUnits = this.groupUnits(attackingSide, defendingSide);
        Trigger trigger = Trigger.onAction;
        for(Unit unit: fieldedUnits){
            
        }
    }
    
    private Unit[] groupUnits(Side attackers, Side defenders){
        Units groupedUnits = new Units();
        groupedUnits.addUnits(attackers.getFieldedUnits());
        groupedUnits.addUnits(defenders.getFieldedUnits());
        return groupedUnits.toArray();
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
