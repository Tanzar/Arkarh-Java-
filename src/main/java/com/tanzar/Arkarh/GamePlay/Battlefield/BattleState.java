/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Battlefield;

/**
 *
 * @author spako
 */
public enum BattleState {
    ongoing,
    draw,
    defenderWin,
    attackerWin;
    
    @Override
    public String toString(){
        switch(this){
            case ongoing:
                return "Ongoing";
            case draw:
                return "Both lost";
            case defenderWin:
                return "Defenders won.";
            case attackerWin:
                return "Attackers won.";
            default:
                return "Undefined State";
        }
    }
}
