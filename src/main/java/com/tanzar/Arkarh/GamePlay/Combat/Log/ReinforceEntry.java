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
public class ReinforceEntry extends Entry{
    
    public ReinforceEntry(boolean isStart) {
        super(EntryGroup.reinforceStart);
        if(isStart){
            String text = "Reinforcing.";
            this.setText(text);
        }
        else{
            this.setEntryGroup(EntryGroup.reinforceEnd);
            String text = "Reinforcing ends.";
            this.setText(text);
        }
    }
    
}
