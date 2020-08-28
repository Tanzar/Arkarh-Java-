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
    private Role[] attackersFront;
    private Role[] attackersBack;
    private Role[] defendersFront;
    private Role[] defendersBack;
    
    private List<ReportEntry> entries;
    private boolean lock;
    private int tickCounter;
    
    public CombatReport(Side attackers, Side defenders){
        this.setupSidesState(attackers, defenders);
        this.entries = new ArrayList<ReportEntry>();
        this.lock = false;
        this.tickCounter = 0;
    }
    
    private void setupSidesState(Side attackers, Side defenders){
        this.width = attackers.getWidth();
        this.attackersFront = new Role[this.width];
        this.attackersBack = new Role[this.width];
        this.defendersFront = new Role[this.width];
        this.defendersBack = new Role[this.width];
        for(int i = 0; i < this.width; i++){
            this.attackersFront[i] = this.setupRole(attackers.getUnit(new Position(i, true)));
            this.attackersBack[i] = this.setupRole(attackers.getUnit(new Position(i, false)));
            this.defendersFront[i] = this.setupRole(defenders.getUnit(new Position(i, true)));
            this.defendersBack[i] = this.setupRole(defenders.getUnit(new Position(i, false)));
        }
    }
    
    private Role setupRole(Unit unit){
        if(unit == null){
            return Role.none;
        }
        else{
            return unit.getRole();
        }
    }
    
    public void newEntry(ReportEntry entry){
        if(!lock && entry != null){
            this.entries.add(entry);
        }
    }
    
    public void nextTick(){
        this.tickCounter++;
        ReportEntry entry = new ReportEntry(Actions.tick, null, null, String.valueOf(this.tickCounter));
        this.newEntry(entry);
    }
    
    public void nextWeave(){
        ReportEntry entry = new ReportEntry(Actions.wave, null, null, "");
        this.newEntry(entry);
    }
    
    public void attacking(Unit source, Unit target, int damage){
        ReportEntry entry = new ReportEntry(Actions.attack, source, target, String.valueOf(damage));
        this.newEntry(entry);
    }
    
    public void healing(Unit source, Unit target, int heal){
        ReportEntry entry = new ReportEntry(Actions.heal, source, target, String.valueOf(heal));
        this.newEntry(entry);
    }
    
    public void reinforcing(Unit unit, int position){
        ReportEntry entry = new ReportEntry(Actions.reinforce, unit, null, String.valueOf(position));
        this.newEntry(entry);
    }
    
    public void unitRemovalFromBattlefield(Unit unit){
        ReportEntry entry = null;
        if(unit.isAlive()){
            entry = new ReportEntry(Actions.retreat, unit, null, "");
        }
        else{
            entry = new ReportEntry(Actions.death, unit, null, "");
        }
        this.newEntry(entry);
    }
    
    /**
     * !WARNING!
     * This method locks report makeing it impossible to add new entries
     * @param state 
     */
    public void combatResult(BattleState state){
        ReportEntry entry = new ReportEntry(Actions.none, null, null, state.toString());
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
