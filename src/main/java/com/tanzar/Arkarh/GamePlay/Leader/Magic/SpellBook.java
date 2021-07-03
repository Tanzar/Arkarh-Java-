/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Leader.Magic;

import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;

/**
 *
 * @author Tanzar
 */
public class SpellBook {
    
    private Spell[] spellSlots;
    
    public SpellBook(int slots){
        this.spellSlots = new Spell[slots];
    }
    
    public Spell getSlot(int slot) throws SpellBookException{
        if(slot >= 0 || slot < this.spellSlots.length){
            return this.spellSlots[slot];
        }
        else{
            throw new SpellBookException("Wrong slot index, must be : 0 - " + (this.spellSlots.length - 1) + ".");
        }
    }
    
    public void setSpell(int slot, Spell spell) throws SpellBookException{
        if(slot >= 0 || slot < this.spellSlots.length){
            for(Spell spellInSlot: this.spellSlots){
                if(spellInSlot.isSameAs(spell)){
                    throw new SpellBookException("Spells in spellbook cannot repeat, you cannot have 2 same spells in slots.");
                }
            }
            this.spellSlots[slot] = spell;
        }
        else{
            throw new SpellBookException("Wrong slot index, must be : 0 - " + (this.spellSlots.length - 1) + ".");
        }
    }
    
    public void applySpells(Battlefield battlefield, CombatReport log){
        for(Spell spell : this.spellSlots){
            spell.applySpell(battlefield, log);
        }
    }
}
