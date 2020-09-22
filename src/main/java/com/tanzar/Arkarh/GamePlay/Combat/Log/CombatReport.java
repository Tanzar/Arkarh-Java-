/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Combat.Log;

import com.tanzar.Arkarh.GamePlay.Combat.BattleState;
import com.tanzar.Arkarh.GamePlay.Combat.Position;
import com.tanzar.Arkarh.GamePlay.Combat.Side;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author spako
 */
public class CombatReport {
    private int width;
    
    private List<ReportEntry> entries;
    private boolean lock;
    private int tickCounter;
    
    public CombatReport(int combatWidth){
        this.entries = new ArrayList<ReportEntry>();
        this.lock = false;
        this.tickCounter = 0;
        this.width = combatWidth;
    }
    
    public void newEntry(ReportEntry entry){
        if(!lock && entry != null){
            this.entries.add(entry);
        }
    }
    
    public void nextTick(){
        this.tickCounter++;
        ReportEntry entry = new ReportEntry(Entry.tick, null, null, String.valueOf(this.tickCounter));
        this.newEntry(entry);
    }
    
    public void nextReinforcementPhase(){
        ReportEntry entry = new ReportEntry(Entry.reinforcePhase, null, null, "Reinforcing");
        this.newEntry(entry);
    }
    
    public void abilityUse(Unit source, Unit target, String stringFormat){
        int position = source.getPosition();
        boolean front = source.isFront();
        Position sourcePosition = new Position(position, front);
        position = target.getPosition();
        front = target.isFront();
        Position targetPosition = new Position(position, front);
        ReportEntry entry = new ReportEntry(Entry.ability, sourcePosition, targetPosition, stringFormat);
        this.newEntry(entry);
    }
    
    public void battlefieldState(Side attackers, Side defenders){
        ReportEntry entry = new ReportEntry(Entry.attackersState, attackers);
        this.newEntry(entry);
        entry = new ReportEntry(Entry.defendersState, defenders);
        this.newEntry(entry);
    }
    
    /**
     * !WARNING!
     * This method locks report makeing it impossible to add new entries
     * @param state 
     */
    public void combatResult(BattleState state){
        ReportEntry entry = new ReportEntry(Entry.none, null, null, state.toString());
        this.newEntry(entry);
        this.lock = true;
    }
    
    public ReportEntry getEntry(int index){
        if(index >= 0 && index < this.entries.size()){
            return this.entries.get(index);
        }
        else{
            return null;
        }
    }
    
    public int size(){
        return this.entries.size();
    }
    
    public void addReport(CombatReport report){
        for(int i = 0; i < report.size(); i++){
            ReportEntry entry = report.getEntry(i);
            this.entries.add(entry);
        }
    }
    
    @Override
    public String toString(){
        String result = "";
        for(ReportEntry entry: this.entries){
            result += entry;
            result += System.lineSeparator();
        }
        return result;
    }
}
