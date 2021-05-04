/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Combat.Log;

/**
 *
 * @author Tanzar
 */
public class TickEntry extends Entry{
    
    public TickEntry(int tickNumber, boolean isStart) {
        super(EntryGroup.tickStart);
        if(isStart){
            String text = "Tick nr. " + tickNumber + ".";
            this.setText(text);
        }
        else{
            this.setEntryGroup(EntryGroup.tickEnd);
            String text = "Tick nr. " + tickNumber + " ends.";
            this.setText(text);
        }
    }
}
