/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Combat.Log;

import com.tanzar.Arkarh.GamePlay.Combat.BattleSide;
import com.tanzar.Arkarh.GamePlay.Combat.Position;
import com.tanzar.Arkarh.GamePlay.Combat.Side;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Stats.State;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;

/**
 *
 * @author spako
 */
public class ReportEntry {
    
    private Entry entryCategory;
    private Position sourcePosition;
    private Position targetPosition;
    private String stringFormat;
    private String[] frontLine;
    private String[] backLine;
    private String[] reserves;
    private int[] reservesState; //0 - alive, 1 - dead, 2 - risen

    public ReportEntry(Entry action, Position sourcePosition, Position targetPosition, String stringFormat) {
        this.entryCategory = action;
        this.sourcePosition = sourcePosition;
        this.targetPosition = targetPosition;
        this.stringFormat = stringFormat;
    }
    
    public ReportEntry(Entry entry, Side side){
        this.entryCategory = entry;
        this.frontLine = new String[side.getWidth()];
        this.backLine = new String[side.getWidth()];
        for(int i = 0; i < side.getWidth(); i++){
            this.frontLine[i] = this.getAssetName(i, true, side);
            this.backLine[i] = this.getAssetName(i, false, side);
        }
        this.setReserves(side);
    }
    
    private String getAssetName(int i, boolean front, Side side){
        Unit unit = side.getUnit(new Position(i, front));
        if(unit != null){
            return unit.getAssetName();
        }
        else{
            return "none";
        }
    }
    
    private void setReserves(Side side){
        Units units = side.getReserves();
        this.reserves = new String[units.size()];
        this.reservesState = new int[units.size()];
        for(int i = 0; i < this.reserves.length; i++){
            Unit unit = units.get(i);
            this.reserves[i] = unit.getAssetName();
            if(unit.isState(State.alive)){
                this.reservesState[i] = 0;
            }
            else{
                if(unit.isState(State.dead)){
                    this.reservesState[i] = 1;
                }
                else{
                    this.reservesState[i] = 2;
                }
            }
            
        }
    }

    @Override
    public String toString(){
        return this.stringFormat;
    }
}
