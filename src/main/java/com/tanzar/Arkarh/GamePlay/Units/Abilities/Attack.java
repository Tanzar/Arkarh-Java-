/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities;

import com.tanzar.Arkarh.GamePlay.Combat.BattleSide;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Side;
import com.tanzar.Arkarh.GamePlay.Modifiers.Passive;
import com.tanzar.Arkarh.GamePlay.Modifiers.PassiveEffect;
import com.tanzar.Arkarh.GamePlay.Modifiers.Passives;
import com.tanzar.Arkarh.GamePlay.Units.AttackStyle;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;

/**
 *
 * @author spako
 */
public class Attack extends UnitAbility{
    private double attackDefenseBonus = 0.05;
    private double spellPowerBonus = 0.10;
    private double minBonus = -0.75;

    public Attack(Unit source) {
        super(source, Trigger.onAction, -1);
    }

    @Override
    protected boolean additionalConditions() {
        return true;
    }

    @Override
    protected Units setupTargets(Battlefield battlefield) {
        Side side = battlefield.getOppositeSide(source);
        Units targets = AttackStyle.getTargets(source, side);
        return targets;
    }
    
    
    @Override
    protected void onUse(Units targets) {
        int damage = this.source.getTotalBaseDamage();
        int attack = this.source.getTotalAttack();
        int spellPower = this.source.getTotalSpellPower();
        Unit[] units = targets.toArray();
        for(Unit target: units){
            int totalDamage = 0;
            if(this.source.isMainAttackPhysical()){
                totalDamage = this.physicalAttack(target, damage, attack);
            }
            else{
                totalDamage = this.magicAttack(target, damage, spellPower);
            }
            EffectSchool school = source.getOffensive().getSchool();
            this.report(source, target, totalDamage, school);
            this.bonusDamage(target);
        }
    }
    
    private int physicalAttack(Unit target, int damage, int attack){
        double attackDefenseMultiplier = this.calculateAttackDefenseMultiplier(target, attack);
        double armorMultiplier = this.armorMultiplier(target);
        damage = (int) Math.round(damage * attackDefenseMultiplier * armorMultiplier);
        target.takeDamage(damage);
        return damage;
    }
    
    private double calculateAttackDefenseMultiplier(Unit target, int attack){
        int defense = this.calculateTargetDefense(target);
        double multiplier = (attack - defense) * this.attackDefenseBonus;
        if(multiplier < this.minBonus){
            return 1 + this.minBonus;
        }
        else{
            return 1 + multiplier;
        }
    }
    
    private int calculateTargetDefense(Unit target){
        double multiplier = this.calculateIgnore(PassiveEffect.ignoreDefense);
        return (int) Math.round(target.getTotalDefense() * multiplier);
    }
    
    private int magicAttack(Unit target, int damage, int spellPower){
        double spellPowerMultiplier = spellPower * this.spellPowerBonus;
        double wardMultiplier = this.wardMultiplier(target);
        damage = (int) Math.round(damage * spellPowerMultiplier * wardMultiplier);
        target.takeDamage(damage);
        return damage;
    }
    
    private void bonusDamage(Unit target){
        Passives sourcePassives = this.source.getPassives();
        Passives bonusDamageEffects = sourcePassives.getByEffect(PassiveEffect.bonusDamage);
        for(Passive passive : bonusDamageEffects.toArray()){
            EffectSchool school = passive.getSchool();
            int damage = passive.getTotalValue();
            if(school.equals(EffectSchool.physical)){
                damage = (int) Math.round(damage * this.armorMultiplier(target));
            }
            else{
                damage = (int) Math.round(damage * this.wardMultiplier(target));
            }
            target.takeDamage(damage);
            this.report(source, target, damage, school);
        }
    }
    
    private double armorMultiplier(Unit target){
        double value = target.getTotalArmor() / 100;
        value = 1 - value;
        double multiplier = this.calculateIgnore(PassiveEffect.ignoreArmor);
        return value * multiplier;
    }
    
    private double wardMultiplier(Unit target){
        double value = target.getTotalWard() / 100;
        value = 1 - value;
        double multiplier = this.calculateIgnore(PassiveEffect.ignoreWard);
        return value * multiplier;
    }
    
    private double calculateIgnore(PassiveEffect ignore){
        Passives passives = this.source.getPassives();
        int value = passives.summarizeValues(ignore);
        if(value > 100){
            value = 100;
        }
        if(value < 0){
            value = 0;
        }
        double multiplier = 1 - (value/100);
        return multiplier;
    }
    
    private void report(Unit source, Unit target, int value, EffectSchool school){
        String stringFormat = source.toString() + " attacks " + target.toString() + " for " + value + " " + school + " damage.";
        this.report.abilityUse(source, target, stringFormat);
    }
    
}
