/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Combat.Log;

import com.tanzar.Arkarh.GamePlay.Combat.Position;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.Trigger;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.Unit;

/**
 *
 * @author Tanzar
 */
public class AbilityEntry extends Entry{
    private String abilityName;
    private String abilityAsset;
    private Position source;
    private Position target;
    
    public AbilityEntry(UnitAbility ability, Unit source, Unit target, String text) {
        super(EntryGroup.ability);
        this.abilityName = ability.getName();
        this.abilityAsset = ability.getAsset();
        this.setupGroup(ability);
        this.setupPositions(source, target);
        this.setText(text);
    }
    
    private void setupGroup(UnitAbility ability){
        Trigger trigger = ability.getTrigger();
        switch(trigger){
            case onAction:
                this.setGroup(EntryGroup.onAction);
                break;
            case onDeath:
                this.setGroup(EntryGroup.onDeath);
                break;
            case onEntry:
                this.setGroup(EntryGroup.onEntry);
                break;
        }
    }
    
    private void setupPositions(Unit source, Unit target){
        int positionInLine = source.getPosition();
        boolean isFront = source.isFront();
        this.source = new Position(positionInLine, isFront, source.getSide());
        positionInLine = target.getPosition();
        isFront = target.isFront();
        this.target = new Position(positionInLine, isFront, target.getSide());
    }
}
