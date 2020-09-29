/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Entities.Unit.UnitEffectEntity;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.Trigger;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Side;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passive;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.PassiveEffect;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passives;
import com.tanzar.Arkarh.GamePlay.Units.TargetsSelection;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;

/**
 *
 * @author spako
 */
public class Attack extends UnitAbility{
    private final double attackDefenseBonus = 0.05;
    private final double spellPowerBonus = 0.10;
    private final double minBonus = -0.75;
    private final TargetsSelection attackStyle;
    private final int range;
    private final int areaSize;
    private final EffectSchool school;

    public Attack(UnitEffectEntity entity) {
        super(entity, Trigger.onAction);
        String tmp = entity.getEffect();
        Json json = new Json(tmp);
        tmp = json.getString("attackStyle");
        this.attackStyle = TargetsSelection.valueOf(tmp);
        this.range = json.getInt("range");
        this.areaSize = json.getInt("areaSize");
        tmp = json.getString("school");
        this.school = EffectSchool.valueOf(tmp);
    }
    
    @Override
    protected boolean additionalConditions(Unit source) {
        return true;
    }

    @Override
    protected Units setupTargets(Unit source, Battlefield battlefield) {
        Side side = battlefield.getOppositeSide(source);
        int position = source.getPosition();
        Units targets = TargetsSelection.getTargets(attackStyle, position, side, range, areaSize);
        return targets;
    }
    
    @Override
    protected void onUse(Unit source, Units targets) {
        int damage = source.getTotalBaseDamage();
        int attack = source.getTotalAttack();
        int spellPower = source.getTotalSpellPower();
        Unit[] units = targets.toArray();
        for(Unit target: units){
            int totalDamage = 0;
            if(this.school.equals(EffectSchool.physical)){
                totalDamage = this.physicalAttack(source, target, damage, attack);
            }
            else{
                totalDamage = this.magicAttack(source, target, damage, spellPower);
            }
            this.report(source, target, totalDamage, school);
            this.bonusDamage(source, target);
        }
    }
    
    private int physicalAttack(Unit source, Unit target, int damage, int attack){
        double attackDefenseMultiplier = this.calculateAttackDefenseMultiplier(source, target, attack);
        double armorMultiplier = this.armorMultiplier(source, target);
        damage = (int) Math.round(damage * attackDefenseMultiplier * armorMultiplier);
        target.takeDamage(damage);
        return damage;
    }
    
    private double calculateAttackDefenseMultiplier(Unit source, Unit target, int attack){
        int defense = this.calculateTargetDefense(source, target);
        double multiplier = (attack - defense) * this.attackDefenseBonus;
        if(multiplier < this.minBonus){
            return 1 + this.minBonus;
        }
        else{
            return 1 + multiplier;
        }
    }
    
    private int calculateTargetDefense(Unit source, Unit target){
        double multiplier = this.calculateIgnore(source, PassiveEffect.ignoreDefense);
        return (int) Math.round(target.getTotalDefense() * multiplier);
    }
    
    private int magicAttack(Unit source, Unit target, int damage, int spellPower){
        double spellPowerMultiplier = spellPower * this.spellPowerBonus;
        double wardMultiplier = this.wardMultiplier(source, target);
        damage = (int) Math.round(damage * spellPowerMultiplier * wardMultiplier);
        target.takeDamage(damage);
        return damage;
    }
    
    private void bonusDamage(Unit source, Unit target){
        Passives sourcePassives = source.getPassives();
        Passives bonusDamageEffects = sourcePassives.getByEffect(PassiveEffect.bonusDamage);
        for(Passive passive : bonusDamageEffects.toArray()){
            EffectSchool school = passive.getSchool();
            int damage = passive.getTotalValue();
            if(school.equals(EffectSchool.physical)){
                damage = (int) Math.round(damage * this.armorMultiplier(source, target));
            }
            else{
                damage = (int) Math.round(damage * this.wardMultiplier(source, target));
            }
            target.takeDamage(damage);
            this.report(source, target, damage, school);
        }
    }
    
    private double armorMultiplier(Unit source, Unit target){
        double value = target.getTotalArmor() / 100;
        value = 1 - value;
        double multiplier = this.calculateIgnore(source, PassiveEffect.ignoreArmor);
        return value * multiplier;
    }
    
    private double wardMultiplier(Unit source, Unit target){
        double value = target.getTotalWard() / 100;
        value = 1 - value;
        double multiplier = this.calculateIgnore(source, PassiveEffect.ignoreWard);
        return value * multiplier;
    }
    
    private double calculateIgnore(Unit source, PassiveEffect ignore){
        Passives passives = source.getPassives();
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

    @Override
    protected String formJson() {
        Json json = new Json();
        json.add("attackStyle", this.attackStyle.toString());
        json.add("range", this.range);
        json.add("areaSize", this.areaSize);
        json.add("school", this.school.toString());
        return json.formJson();
    }
    
}
