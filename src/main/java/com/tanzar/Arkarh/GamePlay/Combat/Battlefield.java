/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Combat;

import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.Combat.Log.ReportEntry;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.Trigger;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbilities;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
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
        CombatReport report = new CombatReport(this.fieldWidth);
        Units fieldedUnits = this.groupUnits(attackingSide, defendingSide);
        for(Unit unit: fieldedUnits.toArray()){
            unit.useAbilities(Trigger.onEntry, this, report);
        }
        while(battleState == BattleState.ongoing){
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
    
    public void tick(CombatReport report){
        report.nextTick();
        report.battlefieldState(attackingSide, defendingSide);
        Units fieldedUnits = this.groupUnits(attackingSide, defendingSide);
        for(Unit unit: fieldedUnits.toArray()){
            unit.useAbilities(Trigger.onAction, this, report);
        }
        this.reinforcementPhase(report);
    }
    
    private Units groupUnits(Side attackers, Side defenders){
        Units groupedUnits = new Units();
        groupedUnits.addUnits(attackers.getFieldedUnits());
        groupedUnits.addUnits(defenders.getFieldedUnits());
        groupedUnits.orderBySpeed();
        return groupedUnits;
    }
    
    private void reinforcementPhase(CombatReport report){
        report.nextReinforcementPhase();
        this.onDeathTrigger(report);
        this.reinforceSides(report);
        report.battlefieldState(attackingSide, defendingSide);
    }
    
    /**
     * WARNING!!!
     * DONT MAKE ABILITIES WITH ON DEATH TRIGGER THAT DEAL DAMAGE IT WILL BE BUGGY
     */
    private void onDeathTrigger(CombatReport report){
        this.attackingSide.updateUnitsStatus();
        this.defendingSide.updateUnitsStatus();
        Units deadUnits = this.attackingSide.getDeadFieldedUnits();
        Units deadDefenders = this.defendingSide.getDeadFieldedUnits();
        deadUnits.addUnits(deadDefenders);
        for(Unit unit: deadUnits.toArray()){
            unit.useAbilities(Trigger.onDeath, this, report);
        }
    }
    
    private void reinforceSides(CombatReport report){
        Units reinforcements = this.attackingSide.reorganizeLinesNew();
        Units defendersReinforcements = this.defendingSide.reorganizeLinesNew();
        reinforcements.addUnits(defendersReinforcements);
        for(Unit unit: reinforcements.toArray()){
            unit.useAbilities(Trigger.onEntry, this, report);
        }
    }
    
    public Side getOppositeSide(Unit unit){
        BattleSide side = unit.getStatus().getSide();
        switch(side){
            case attacker:
                return this.defendingSide;
            case defender:
                return this.attackingSide;
            default:
                return null;
        }
    }
    
    public Side getSide(Unit unit){
        BattleSide side = unit.getStatus().getSide();
        switch(side){
            case attacker:
                return this.attackingSide;
            case defender:
                return this.defendingSide;
            default:
                return null;
        }
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
