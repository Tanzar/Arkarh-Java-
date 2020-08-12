/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.CombatLog;

import com.tanzar.Arkarh.GamePlay.Units.DamageType;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Unit;

/**
 *
 * @author spako
 */
public class ReportEntry {
    
    private Actions action;
    private String sourceName;
    private int sourcePosition;
    private BattleSide sourceSide;
    private boolean isFront;
    private int targetPosition;
    private String value;
    private DamageType effectType;
    private String stringFormat;

    public ReportEntry(Actions action, Unit source, Unit target, String value) {
        this.action = action;
        this.stringFormat = this.parseFormat(source, target, value);
    }
    
    private String parseFormat(Unit source, Unit target, String value){
        switch(this.action){
            case tick:
                this.value = value;
                return "" + Actions.tick + this.value;
            case wave:
                return Actions.wave.toString();
            case attack:
                this.sourceName = source.getName();
                this.sourcePosition = source.getPosition();
                this.sourceSide = source.getSide();
                this.isFront = !source.getRole().isRanged();
                this.targetPosition = target.getPosition();
                this.value = value;
                this.effectType = source.getDamageType();
                return source.getSide() + source.toString() + Actions.attack + target.toString() + "for " + this.value + " " + source.getDamageType() + " damage.";
            case reinforce:
                this.sourceName = source.getName();
                this.sourcePosition = source.getPosition();
                this.sourceSide = source.getSide();
                this.isFront = !source.getRole().isRanged();
                this.value = value;
                return source.getSide() + source.toString() + Actions.reinforce + " position " + this.value;
            case death:
                this.sourceName = source.getName();
                this.sourcePosition = source.getPosition();
                this.sourceSide = source.getSide();
                this.isFront = !source.getRole().isRanged();
                return source.getSide() + source.toString() + Actions.death;
            case retreat:
                this.sourceName = source.getName();
                this.sourcePosition = source.getPosition();
                this.sourceSide = source.getSide();
                this.isFront = !source.getRole().isRanged();
                return source.getSide() + source.toString() + Actions.retreat;
            case none:
                this.value = value;
                return this.value;
            default:
                return "";
        }
    }
    
    @Override
    public String toString(){
        return this.stringFormat;
    }
}
