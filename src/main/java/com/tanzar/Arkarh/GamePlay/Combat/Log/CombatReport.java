/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Combat.Log;

import com.tanzar.Arkarh.GamePlay.Combat.BattleState;
import com.tanzar.Arkarh.GamePlay.Combat.Side;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author spako
 */
public class CombatReport {
    private int width;
    
    private List<Entry> entries;
    private boolean lock;
    private int tickCounter;
    
    public CombatReport(int combatWidth){
        this.entries = new ArrayList<Entry>();
        this.lock = false;
        this.tickCounter = 0;
        this.width = combatWidth;
    }
    
    public void newEntry(Entry entry){
        if(!lock && entry != null){
            this.entries.add(entry);
        }
    }
    
    public int getTickCount(){
        return this.tickCounter;
    }
    
    public void nextTick(){
        this.tickCounter++;
        Entry entry = new TickEntry(this.tickCounter, true);
        this.newEntry(entry);
    }
    
    public void endTick(){
        Entry entry = new TickEntry(this.tickCounter, false);
        this.newEntry(entry);
    }
    
    public void nextReinforcementPhase(){
        Entry entry = new ReinforceEntry(true);
        this.newEntry(entry);
    }
    
    public void endReinforcementPhase(){
        Entry entry = new ReinforceEntry(false);
        this.newEntry(entry);
    }
    
    public void abilityUse(UnitAbility ability, Unit source, Unit target, String text){
        Entry entry = new AbilityEntry(ability, source, target, text);
        this.newEntry(entry);
    }
    
    public void battlefieldState(Side attackers, Side defenders){
        Entry entry = new SideStateEntry(attackers);
        this.newEntry(entry);
        entry = new SideStateEntry(defenders);
        this.newEntry(entry);
    }
    
    /**
     * !WARNING!
     * This method locks report makeing it impossible to add new entries
     * @param state 
     */
    public void combatResult(BattleState state){
        String text = state.toString();
        Entry entry = new EndEntry(text);
        this.newEntry(entry);
        entry = new EndEntry("Combat Ends.");
        this.newEntry(entry);
        this.lock = true;
    }
    
    public Entry getEntry(int index){
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
            Entry entry = report.getEntry(i);
            this.entries.add(entry);
        }
    }
    
    @Override
    public String toString(){
        String result = "";
        for(Entry entry: this.entries){
            result += entry;
            result += System.lineSeparator();
        }
        return result;
    }
}
