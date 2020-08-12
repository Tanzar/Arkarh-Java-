/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.CombatLog;

/**
 *
 * @author spako
 */
public enum Actions {
    none,
    attack,
    retreat,
    death,
    reinforce,
    tick,
    wave;
    
    @Override
    public String toString(){
        switch(this){
            case none:
                return "";
            case attack:
                return " attacks ";
            case retreat:
                return " retreats from battlefield.";
            case death:
                return " dies";
            case reinforce:
                return " reinforces ";
            case tick:
                return "Tick nr. ";
            case wave:
                return "Wave starts.";
            default:
                return "No action defined";
        }
    }
}
