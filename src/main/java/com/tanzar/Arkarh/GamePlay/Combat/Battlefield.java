/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Combat;

import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.Combat.Log.ReportEntry;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Trigger;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.UnitAbilities;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.UnitAbility;
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
        Unit[] fieldedUnits = this.groupUnits(attackingSide, defendingSide);
        Trigger trigger = Trigger.onAction;
        for(Unit unit: fieldedUnits){
            UnitAbilities abilities = unit.getAbilities(trigger);
            for(UnitAbility ability: abilities.toArray()){
                ability.use(trigger, this, report);
            }
        }
        this.reinforcementPhase(report);
    }
    
    private Unit[] groupUnits(Side attackers, Side defenders){
        Units groupedUnits = new Units();
        groupedUnits.addUnits(attackers.getFieldedUnits());
        groupedUnits.addUnits(defenders.getFieldedUnits());
        return groupedUnits.toArray();
    }
    
    private void reinforcementPhase(CombatReport report){
        report.nextReinforcementPhase();
        this.attackingSide.reorganizeLinesNew();
        this.defendingSide.reorganizeLinesNew();
        report.battlefieldState(attackingSide, defendingSide);
    }
    
    public Side getOppositeSide(Unit unit){
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
