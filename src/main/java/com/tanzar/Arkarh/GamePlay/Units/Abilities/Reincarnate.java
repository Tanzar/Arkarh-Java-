/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Database.Entities.Units.UnitAbilityEntity;
import com.tanzar.Arkarh.GameConfig;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.Trigger;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbilityGroup;
import com.tanzar.Arkarh.GamePlay.Units.Units;
import com.tanzar.Arkarh.Services.BeanUtil;

/**
 *
 * @author Tanzar
 */
public class Reincarnate extends UnitAbility{
    private int healthPercentage;
    
    public Reincarnate(){
        super(Trigger.onDeath, UnitAbilityGroup.reincarnate);
        this.healthPercentage = 20;
    }
    
    public Reincarnate(UnitAbilityEntity entity){
        super(entity, Trigger.onDeath);
        String tmp = entity.getEffect();
        Json json = new Json(tmp);
        this.healthPercentage = json.getInt("healthPercentage");
    }
    
    public Reincarnate(Json json){
        super(json);
        this.healthPercentage = json.getInt("healthPercentage");
    }

    @Override
    protected boolean additionalConditions(Unit source) {
        int tickNumber = this.report.getTickCount();
        GameConfig config = BeanUtil.getBean(GameConfig.class);
        if(tickNumber > config.getAbilityLockTick()){
            return false;
        }
        return true;
    }

    @Override
    protected Units setupTargets(Unit source, Battlefield battlefield) {
        Units targets = new Units();
        targets.add(source);
        return targets;
    }

    @Override
    protected void onUse(Unit source, Units targets) {
        int bonus = source.getTotalSpellPower() * 2;
        int totalPercentage = this.healthPercentage + bonus;
        source.ressurect(totalPercentage);
        int health = source.getStatus().getHealth();
        String stringFormat = source.toString() + " rises with " + health + " (" + this.healthPercentage + "%) health";
        this.report.abilityUse(this, source, source, stringFormat);
    }

    @Override
    protected void formJson(Json json) {
        json.add("healthPercentage", this.healthPercentage);
    }
    
}
