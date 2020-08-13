/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.CombatLog;

import com.tanzar.Arkarh.GamePlay.Units.EffectType;
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
    private EffectType effectType;
    private String stringFormat;

    public ReportEntry(Actions action, Unit source, Unit target, String value) {
        this.action = action;
        this.stringFormat = this.parseFormat(source, target, value);
    }
    
    private String parseFormat(Unit source, Unit target, String value){
        switch(this.action){
            case tick:
                return this.tickString(value);
            case wave:
                return Actions.wave.toString();
            case attack:
                return this.attackString(source, target, value);
            case heal:
                return this.healString(source, target, value);
            case reinforce:
                return this.reinforceString(source, value);
            case death:
                return this.deathString(source);
            case retreat:
                return this.retreatString(source);
            case none:
                return this.noneString(value);
            default:
                return "";
        }
    }
    
    private String tickString(String value){
        this.value = value;
        return "" + Actions.tick + this.value;
    }
    
    private String attackString(Unit source, Unit target, String value){
        this.sourceName = source.getName();
        this.sourcePosition = source.getPosition();
        this.sourceSide = source.getSide();
        this.isFront = !source.getRole().isRanged();
        this.targetPosition = target.getPosition();
        this.value = value;
        this.effectType = source.getEffectType();
        return source.getSide() + source.toString() + Actions.attack + target.toString() + "for " + this.value + " " + source.getEffectType() + " damage.";
    }
    
    private String reinforceString(Unit source, String value){
        this.sourceName = source.getName();
        this.sourcePosition = source.getPosition();
        this.sourceSide = source.getSide();
        this.isFront = !source.getRole().isRanged();
        this.value = value;
        return source.getSide() + source.toString() + Actions.reinforce + " position " + this.value;
    }
    
    private String deathString(Unit source){
        this.sourceName = source.getName();
        this.sourcePosition = source.getPosition();
        this.sourceSide = source.getSide();
        this.isFront = !source.getRole().isRanged();
        return source.getSide() + source.toString() + Actions.death;
    }
    
    private String retreatString(Unit source){
        this.sourceName = source.getName();
        this.sourcePosition = source.getPosition();
        this.sourceSide = source.getSide();
        this.isFront = !source.getRole().isRanged();
        return source.getSide() + source.toString() + Actions.retreat;
    }
    
    private String healString(Unit source, Unit target, String value){
        this.sourceName = source.getName();
        this.sourcePosition = source.getPosition();
        this.sourceSide = source.getSide();
        this.isFront = !source.getRole().isRanged();
        this.targetPosition = target.getPosition();
        this.value = value;
        this.effectType = source.getEffectType();
        return source.getSide() + source.toString() + Actions.heal + target.toString() + "for " + this.value + " " + source.getEffectType() + ".";
    }
    
    private String noneString(String value){
        this.value = value;
        return this.value;
    }
    
    @Override
    public String toString(){
        return this.stringFormat;
    }
}
