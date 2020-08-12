/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Battlefield;

import com.tanzar.Arkarh.GamePlay.CombatLog.Actions;
import com.tanzar.Arkarh.GamePlay.CombatLog.BattleSide;
import com.tanzar.Arkarh.GamePlay.CombatLog.CombatReport;
import com.tanzar.Arkarh.GamePlay.CombatLog.ReportEntry;
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
    
    public CombatReport tick(CombatReport report){
        Units attackersOrder = this.attackingSide.getFieldedUnitsOrderedBySpeed();
        Units defendersOrder = this.defendingSide.getFieldedUnitsOrderedBySpeed();
        int currentSpeed = this.getHighestSpeed(attackersOrder, defendersOrder);
        while(currentSpeed > 0){
            report.nextWeave();
            Units attackersWave = attackersOrder.getBySpeed(currentSpeed);
            Units defendersWave = defendersOrder.getBySpeed(currentSpeed);
            DamagePattern defendersDamageTaken = this.makeWaveDamagePattern(attackersWave, defendingSide, report, BattleSide.attacker);
            DamagePattern attackersDamageTaken = this.makeWaveDamagePattern(defendersWave, attackingSide, report, BattleSide.defender);
            this.defendingSide.applyDamagePattern(defendersDamageTaken);
            this.attackingSide.applyDamagePattern(attackersDamageTaken);
            currentSpeed = this.selectNextSpeed(attackersOrder, defendersOrder, currentSpeed);
        }
        boolean somethingWasReinforced = this.attackingSide.reorganizeLines(report, false);
        this.defendingSide.reorganizeLines(report, somethingWasReinforced);
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
    
    private DamagePattern makeWaveDamagePattern(Units units, Side targetsSide, CombatReport report, BattleSide battleside){
        DamagePattern damagePattern = new DamagePattern(this.fieldWidth);
        for(int i = 0; i < units.size(); i++){
            Unit unit = units.get(i);
            if(unit.isAlive()){
                DamagePattern pattern = targetsSide.calculateAttackerDamagePattern(unit);
                for(int j = 0; j < pattern.getWidth(); j++){
                    if(pattern.getDamage(j, true) != 0){
                        report.attacking(unit, targetsSide.getUnit(new Position(j, true)), pattern.getDamage(j, true));
                    }
                    if(pattern.getDamage(j, false) != 0){
                        report.attacking(unit, targetsSide.getUnit(new Position(j, false)), pattern.getDamage(j, false));
                    }
                }
                damagePattern.applyDamagePattern(pattern);
            }
        }
        return damagePattern;
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
