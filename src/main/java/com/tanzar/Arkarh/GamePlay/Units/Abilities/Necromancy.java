/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities;

import com.tanzar.Arkarh.Database.Containers.UnitAbilityEntities;
import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Database.Entities.Units.UnitAbilityEntity;
import com.tanzar.Arkarh.Database.Entities.Units.UnitEntity;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Side;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.Trigger;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbilityGroup;
import com.tanzar.Arkarh.GamePlay.Units.Units;
import com.tanzar.Arkarh.Services.BeanUtil;
import com.tanzar.Arkarh.Services.UnitsService;

/**
 *
 * @author Tanzar
 */
public class Necromancy extends UnitAbility{

    private int summonedId;
    private int count;
    
    public Necromancy(){
        super(Trigger.onAction, UnitAbilityGroup.necromancy);
        this.summonedId = 0;
        this.count = 1;
    }
    
    public Necromancy(UnitAbilityEntity entity) {
        super(entity, Trigger.onAction);
        Json json = new Json(entity.getEffect());
        this.summonedId = json.getInt("summonedId");
        this.count = json.getInt("count");
    }
    
    public Necromancy(Json json){
        super(json);
        this.summonedId = json.getInt("summonedId");
        this.count = json.getInt("count");
    }
    
    @Override
    protected boolean additionalConditions(Unit source) {
        return true;
    }

    @Override
    protected Units setupTargets(Unit source, Battlefield battlefield) {
        int risenCount = this.setupRisenUnits(source, battlefield);
        Units summonedUnits = this.getSummonedUnits(risenCount);
        if(summonedUnits.isEmpty()){
            report.abilityUse(this, source, source, "None can answer call of " + source.toString());
        }
        else{
            Side side = battlefield.getSide(source);
            this.addSummonedUnits(side, summonedUnits);
        }
        return summonedUnits;
    }
    
    private int setupRisenUnits(Unit source, Battlefield battlefield){
        Side opponents = battlefield.getOppositeSide(source);
        Units deadReserves = opponents.getDeadReserves();
        int risenCount = 0;
        for(Unit dead: deadReserves.toArray()){
            if(risenCount < this.count){
                if(dead.rise()){
                    risenCount++;
                }
            }
        }
        if(risenCount == 0 && this.charges != -1){
            this.recoverCharge();
        }
        return risenCount;
    }
    
    private Units getSummonedUnits(int risenCount){
        UnitsService bean = BeanUtil.getBean(UnitsService.class);
        UnitEntity template = bean.getByIdAsEntity(this.summonedId);
        Units units = new Units();
        for(int i = 0; i < risenCount; i++){
            UnitAbilityEntities abilities = bean.getUnitAbilitiesAsEntities(this.summonedId);
            Unit unit = new Unit(template, abilities.toArray());
            units.add(unit);
        }
        return units;
    }
    
    private void addSummonedUnits(Side side, Units summonedUnits){
        for(Unit unit: summonedUnits.toArray()){
            side.addUnit(unit);
        }
    }

    @Override
    protected void onUse(Unit source, Units targets) {
        for(Unit summoned: targets.toArray()){
            this.report.abilityUse(this, source, summoned, source.toString() + " rises " + summoned.toString());
        }
    }

    @Override
    protected void formJson(Json json) {
        json.add("summonedId", this.summonedId);
        json.add("count", this.count);
    }
    
}
