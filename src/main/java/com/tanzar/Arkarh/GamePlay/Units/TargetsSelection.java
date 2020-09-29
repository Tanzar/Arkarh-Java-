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
public enum TargetsSelection {
    single,
    piercing;
    
    public Units getTargets(int position, Side side, int range, int areaSize){
        Units units = TargetsSelection.getTargets(this, position, side, range, areaSize);
        return units;
    }
    
    public static Units getTargets(TargetsSelection attackStyle, int position, Side side, int range, int areaSize){
        int firstTargetPosition = getFirstTargetPosition(position, range, side);
        if(firstTargetPosition > -1){
            switch(attackStyle){
            case single:
                return getTargetsSingle(side, firstTargetPosition, areaSize);
            case piercing:
                return getTargetsPiercing(side, firstTargetPosition, areaSize);
            default:
                return new Units();
            }
        }
        else{
            return new Units();
        }
    }
    
    private static int getFirstTargetPosition(int position, int range, Side side){
        int firstTargetPosition = -1;
        int x = 0;
        int numberOfTargetsInRange = 2 * range + 1;
        for(int i = 0; i < numberOfTargetsInRange; i++){
            int index = nextPosition(position, i, x);
            if(side.isAnyOnPosition(index)){
                firstTargetPosition = index;
                break;
            }
        }
        return firstTargetPosition;
    }
    
    private static Units getTargetsSingle(Side side, int position, int areaSize){
        Units units = new Units();
        int x = 0;
        int numberOfTargetsInRange = 2 * areaSize + 1;
        for(int i = 0; i < numberOfTargetsInRange; i++){
            int index = nextPosition(position, i, x);
            Unit unit = side.getUnit(index);
            units.add(unit);
        }
        return units;
    }
    
    private static Units getTargetsPiercing(Side side, int position, int areaSize){
        Units units = new Units();
        int x = 0;
        int numberOfTargetsInRange = 2 * areaSize + 1;
        for(int i = 0; i < numberOfTargetsInRange; i++){
            int index = nextPosition(position, i, x);
            Unit unit = side.getUnit(new Position(index, true));
            units.add(unit);
            unit = side.getUnit(new Position(index, false));
            units.add(unit);
        }
        return units;
    }
    
    private static int nextPosition(int startPosition, int i, int x){
        int index = 0;
        if(i % 2 == 1){
            index = (int) startPosition - x;
        }
        else{
            index = (int) startPosition + x;
            x++;
        }
        return index;
    }
    
}
