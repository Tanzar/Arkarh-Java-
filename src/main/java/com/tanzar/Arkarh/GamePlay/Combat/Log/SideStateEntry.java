/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Combat.Log;

import com.tanzar.Arkarh.GamePlay.Combat.BattleSide;
import com.tanzar.Arkarh.GamePlay.Combat.Position;
import com.tanzar.Arkarh.GamePlay.Combat.Side;
import com.tanzar.Arkarh.GamePlay.Units.Stats.State;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;

/**
 *
 * @author Tanzar
 */
public class SideStateEntry extends Entry{
    private String[] frontLine;
    private String[] backLine;
    private String[] reserves;
    private State[] reservesState;
    
    public SideStateEntry(Side side) {
        super(EntryGroup.attackersState);
        this.setupGroup(side);
        this.setupArrays(side);
    }
    
    private void setupGroup(Side side){
        BattleSide battleSide = side.getBattleSide();
        if(battleSide.equals(BattleSide.defender)){
            this.setGroup(EntryGroup.defendersState);
        }
    }
    
    private void setupArrays(Side side){
        this.frontLine = new String[side.getWidth()];
        this.backLine = new String[side.getWidth()];
        for(int i = 0; i < side.getWidth(); i++){
            this.frontLine[i] = this.getAssetName(i, true, side);
            this.backLine[i] = this.getAssetName(i, false, side);
        }
        this.setReserves(side);
    }
    
    private String getAssetName(int i, boolean isFront, Side side){
        Unit unit = side.getUnit(new Position(i, isFront, side.getBattleSide()));
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
        this.reservesState = new State[units.size()];
        for(int i = 0; i < this.reserves.length; i++){
            Unit unit = units.get(i);
            this.reserves[i] = unit.getAssetName();
            this.reservesState[i] = unit.getState();
        }
    }
}
