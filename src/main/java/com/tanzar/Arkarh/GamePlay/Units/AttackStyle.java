/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

import com.tanzar.Arkarh.GamePlay.Combat.Position;
import com.tanzar.Arkarh.GamePlay.Combat.Side;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Offensive;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Special;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Status;

/**
 *
 * @author spako
 */
public enum AttackStyle {
    single,
    cleave,
    splash;
    
    public static Units getTargets(Unit source, Side side){
        Offensive stats = source.getOffensive();
        AttackStyle attackStyle = stats.getAttackType();
        switch(attackStyle){
            case single:
                return getFirstTarget(source, side);
            case cleave:
                return getCleaveTargets(source, side);
            case splash:
                return getAllTargets(source, side);
            default:
                return new Units();
        }
    }
    
    private static Units getFirstTarget(Unit source, Side side){
        Units target = new Units();
        Special specialStats = source.getSpecial();
        int range = specialStats.getRange();
        Status unitStatus = source.getStatus();
        int position = unitStatus.getPosition();
        for(int i = 0; i <= range; i++){
            Unit unit = getUnitFrontOrBack(position, i, side);
            if(unit != null){
                target.add(unit);
                break;
            }
        }
        return target;
    }
    
    
    private static Units getCleaveTargets(Unit source, Side side){
        Units targets = new Units();
        Special specialStats = source.getSpecial();
        int range = specialStats.getRange();
        Status unitStatus = source.getStatus();
        int position = unitStatus.getPosition();
        for(int i = 0; i <= range; i++){
            Unit unit = getUnitFrontOrBack(position, i, side);
            if(unit != null){
                targets.add(unit);
            }
        }
        return targets;
    }
    
    private static Units getAllTargets(Unit source, Side side){
        Units targets = new Units();
        Special specialStats = source.getSpecial();
        int range = specialStats.getRange();
        Status unitStatus = source.getStatus();
        int position = unitStatus.getPosition();
        for(int i = 0; i <= range; i++){
            addUnitFrontAndBack(position, i, side, targets);
        }
        return targets;
    }
    
    private static void addUnitFrontAndBack(int position, int i, Side side, Units units){
        Unit unit = null;
        if(i == 0){
            unit = side.getUnit(new Position(position, true));
            units.add(unit);
            unit = side.getUnit(new Position(position, false));
            units.add(unit);
        }
        else{
            unit = side.getUnit(new Position(position + i, true));
            units.add(unit);
            unit = side.getUnit(new Position(position - i, true));
            units.add(unit);
            unit = side.getUnit(new Position(position + i, false));
            units.add(unit);
            unit = side.getUnit(new Position(position - i, false));
            units.add(unit);
        }
    }
    
    private static Unit getUnitFrontOrBack(int position, int i, Side side){
        Unit unit = null;
        if(i == 0){
            unit = side.getUnit(new Position(position, true));
            if(unit == null){
                unit = side.getUnit(new Position(position, false));
            }
        }
        else{
            unit = side.getUnit(new Position(position + i, true));
            if(unit == null){
                unit = side.getUnit(new Position(position - i, true));
            }
            unit = side.getUnit(new Position(position + i, false));
            if(unit == null){
                unit = side.getUnit(new Position(position - i, false));
            }
        }
        return unit;
    }
    
}
