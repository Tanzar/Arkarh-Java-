/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities.Base;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Entities.Unit.UnitAbilityEntity;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.UnitAbilityGroup;
import com.tanzar.Arkarh.GamePlay.Units.Units;

/**
 *
 * @author spako
 */
public abstract class UnitAbility {
    protected int id;
    protected String asset;
    protected String name;
    protected CombatReport report;
    private int charges;
    private int unitId;
    private Trigger trigger;
    private UnitAbilityGroup group;
    
    public UnitAbility(){
        this.id = 0;
        this.unitId = 0;
        this.group = UnitAbilityGroup.none;
        this.name = "Noname";
        this.asset = "none.png";
        this.trigger = Trigger.onAction;
        this.charges = 0;
    }
    
    public UnitAbility(Trigger trigger){
        this.id = 0;
        this.unitId = 0;
        this.group = UnitAbilityGroup.none;
        this.name = "Noname";
        this.asset = "none.png";
        this.trigger = trigger;
        this.charges = 0;
    }
    
    public UnitAbility(Trigger trigger, UnitAbilityGroup group){
        this.id = 0;
        this.unitId = 0;
        this.group = group;
        this.name = "Noname";
        this.asset = "none.png";
        this.trigger = trigger;
        this.charges = 0;
    }
    
    
    public UnitAbility(UnitAbilityEntity entity, Trigger trigger){
        this.id = entity.getId();
        this.unitId = entity.getUnitId();
        String groupStr = entity.getEffectGroup();
        this.group = UnitAbilityGroup.valueOf(groupStr);
        this.name = entity.getEffectName();
        this.asset = entity.getAssetName();
        this.trigger = trigger;
        this.charges = entity.getCharges();
    }
    
    public UnitAbility(Json json){
        this.id = json.getInt("id");
        this.unitId = json.getInt("unitId");
        this.group = UnitAbilityGroup.valueOf(json.getString("group"));
        this.name = json.getString("name");
        this.asset = json.getString("asset");
        this.trigger = Trigger.valueOf(json.getString("trigger"));
        this.charges = json.getInt("charges");
    }

    public void use(Unit source, Trigger mainTrigger, Battlefield battlefield, CombatReport report){
        if(this.charges != 0){
            if(this.trigger.equals(mainTrigger) && this.additionalConditions(source)){
                this.report = report;
                Units targets = this.setupTargets(source, battlefield);
                this.onUse(source, targets);
                this.reduceCharges();
            }
        }
    }
    
    protected abstract boolean additionalConditions(Unit source);
    
    protected abstract Units setupTargets(Unit source, Battlefield battlefield);
    
    protected abstract void onUse(Unit source, Units targets);
    
    public Json toJson(){
        Json json = new Json();
        json.add("id", this.id);
        json.add("unitId", this.unitId);
        json.add("group", this.group.toString());
        json.add("name", this.name);
        json.add("asset", this.asset);
        json.add("charges", this.charges);
        json.add("trigger", this.trigger.toString());
        this.formJson(json);
        return json;
    }
    
    protected abstract void formJson(Json json);
    
    private void reduceCharges(){
        if(this.charges > 0){
            this.charges--;
        }
    }
    
    public int getCharges(){
        return this.charges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public UnitAbilityGroup getGroup() {
        return group;
    }

    public void setGroup(UnitAbilityGroup group) {
        this.group = group;
    }
    
    public UnitAbilityEntity convert(Unit source){
        UnitAbilityEntity entity = new UnitAbilityEntity();
        entity.setId(this.id);
        int unitId = source.getId();
        entity.setUnitId(unitId);
        entity.setEffectName(this.name);
        entity.setAssetName(this.asset);
        String groupStr = this.group.toString();
        entity.setEffectGroup(groupStr);
        entity.setCharges(this.charges);
        Json json = new Json();
        this.formJson(json);
        entity.setEffect(json.formJson());
        return entity;
    }
    
    public UnitAbilityEntity convert(int unitId){
        UnitAbilityEntity entity = new UnitAbilityEntity();
        entity.setId(this.id);
        entity.setUnitId(unitId);
        entity.setEffectName(this.name);
        entity.setAssetName(this.asset);
        String groupStr = this.group.toString();
        entity.setEffectGroup(groupStr);
        entity.setCharges(this.charges);
        Json json = new Json();
        this.formJson(json);
        entity.setEffect(json.formJson());
        return entity;
    }
}
