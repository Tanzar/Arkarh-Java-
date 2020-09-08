/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

import com.tanzar.Arkarh.GamePlay.Combat.Position;
import com.tanzar.Arkarh.GamePlay.Combat.Side;

/**
 *
 * @author spako
 */
public enum AttackStyle {
    single,
    cleave,
    splash;
    /*
    public static Units getTargets(Unit source, Side side){
        AttackStyle attackStyle = source.getAttackType();
        switch(attackStyle){
            case single:
                return this.getFirstTarget(positions);
            case cleave:
                return this.getCleaveTargets(positions);
            case splash:
                return this.getAllTargets(positions);
            default:
                return new Units();
        }
    }
    
    private Units getFirstTarget(Unit source, Side side){
        Units target = new Units();
        int range = source.getRange();
        int position = source.getPosition();
        for(int i = 0; i <= range; i++){
            Unit unit = null;
            if(i == 0){
                unit = side.getUnit(position);
            }
            else{
                
            }
            if(unit != null){
                target.add(unit);
                break;
            }
        }
        return target;
    }
    
    private Units getCleaveTargets(Unit source, Side side){
        Units targets = new Units();
        for(int i = 0; i < positions.length; i++){
            Position position = positions[i];
            if(position.isValid(this.width - 1)){
                int index = position.getPositionInLine();
                Unit target = null;
                if(position.isOnFrontLine()){
                    target = this.front[index];
                }
                else{
                    if(this.front[index] == null){
                        target = this.back[index];
                    }
                }
                if(target != null){
                    targets.add(target);
                }
            }
        }
        return targets;
    }
    
    private Units getAllTargets(Unit source, Side side){
        Units target = new Units();
        for(int i = 0; i < positions.length; i++){
            Position position = positions[i];
            Unit unit = this.getUnit(position);
            if(unit != null){
                target.add(unit);
            }
        }
        return target;
    }
    */
}
