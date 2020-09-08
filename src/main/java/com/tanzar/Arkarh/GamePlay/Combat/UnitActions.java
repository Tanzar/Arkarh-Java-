/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Combat;

import com.tanzar.Arkarh.GamePlay.Combat.Log.Actions;
import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.Combat.Log.ReportEntry;
import com.tanzar.Arkarh.GamePlay.Modifiers.ActiveEffect;
import com.tanzar.Arkarh.GamePlay.Modifiers.Passives;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Unit;

/**
 *
 * @author spako
 */
public class UnitActions {
    
    private CombatReport report;
    
    public UnitActions(CombatReport report){
        this.report = report;
    }
    
    public void action(Unit source, Unit target){
        if(source.isFriendly(target)){
            if(source.isHealer()){
                this.heal(source, target);
            }
        }
        else{
            this.attack(source, target);
        }
    }
    /*
    private Mods combineMods(Unit source, Unit target){
        
    }*/
    
    public void heal(Unit source, Unit target){
        int healValue = (int) Math.round(source.getBaseHealingValue() * (1 + source.getSpellPower() * source.getSpellPowerBonus()));
        int trueValue = target.restoreHealth(healValue);
        this.report.healing(source, target, trueValue);
    }
    
    public void attack(Unit source, Unit target){
        int damage = this.calculateDamage(source, target);
        this.report.attacking(source, target, damage);
        target.takeDamage(damage);
        this.secondAttack(source, target, damage);
        this.damageMorale(source, target);
        this.stealLife(source, damage);
    }
    
    private int calculateDamage(Unit source, Unit target){
        int damage = 0;
        if(source.getEffectType() == EffectSchool.physical){
            damage = this.physicalAttack(source, target);
        }
        else{
            damage = this.magicalAttack(source, target);
        }
        return damage;
    }
    
    private int physicalAttack(Unit source, Unit target){
        int damage = 1;
        double defIgnore = 1 - source.getActiveValue(ActiveEffect.ignoreDefense);
        if(defIgnore < 0){
            defIgnore = 0;
        }
        int differenceAtkDef = source.getAttack() - (int) (target.getDefense() * defIgnore);
        double atkDefMultiplier = 1 + differenceAtkDef * source.getAttackDefenseBonus();
        if(atkDefMultiplier < 0.1){
            atkDefMultiplier = 0.1;
        }
        double armorIgnore = 1 - source.getActiveValue(ActiveEffect.ignoreArmor);
        if(armorIgnore < 0){
            armorIgnore = 0;
        }
        double armor = (double) target.getArmor() * armorIgnore;
        double armorReductionMultiplier = (100 - armor) / 100;
        damage = (int) Math.round((source.getDamage() * atkDefMultiplier) * armorReductionMultiplier);
        return damage;
    }
    
    private int magicalAttack(Unit source, Unit target){
        int damage = 1;
        double spellPowerMultiplier = 1 + (source.getSpellPower() * source.getSpellPowerBonus());
        double wardIgnore = 1 - source.getActiveValue(ActiveEffect.ignoreWard);
        if(wardIgnore < 0){
            wardIgnore = 0;
        }
        double ward = (double) target.getWard() * wardIgnore;
        double wardReductionMultiplier = (100 - ward) / 100;
        double weaknessMultiplier = 1 + this.weaknessToAttack(source, target);
        damage = (int) (Math.round(source.getDamage() * spellPowerMultiplier * wardReductionMultiplier) * weaknessMultiplier);
        return damage;
    }
    
    private double weaknessToAttack(Unit source, Unit target){
        EffectSchool damageCategory = source.getEffectType();
        double weakness = target.weaknessValue(damageCategory);
        return weakness;
    }
    
    private void secondAttack(Unit source, Unit target, int damage){
        double secondAttack = source.getActiveValue(ActiveEffect.multipleAttack);
        if(secondAttack == 1){
            this.report.attacking(source, target, damage);
            target.takeDamage(damage);
            
        }
    }
    
    private void damageMorale(Unit source, Unit target){
        double moraleDamage = source.getActiveValue(ActiveEffect.moraleDamage);
        if(moraleDamage == 1){
            target.takeMoraleDamage();
        }
    }
    
    private void stealLife(Unit source, int damage){
        double lifeStealPercentage = source.getActiveValue(ActiveEffect.lifeSteal);
        if(lifeStealPercentage > 0){
            int healingValue = (int) lifeStealPercentage * damage;
            healingValue = source.restoreHealth(healingValue);
            this.report.healing(source, source, healingValue);
        }
    }
}
