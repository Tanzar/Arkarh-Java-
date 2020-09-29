/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities.Base;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tanzar.Arkarh.Entities.Unit.UnitEffectEntity;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.UnitEffectGroup;
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
    private Trigger trigger;
    private UnitEffectGroup group;
    
    public UnitAbility(UnitEffectEntity entity, Trigger trigger){
        this.id = entity.getId();
        String groupStr = entity.getEffectGroup();
        this.group = UnitEffectGroup.valueOf(groupStr);
        this.name = entity.getEffectName();
        this.asset = entity.getAssetName();
        this.trigger = trigger;
        this.charges = entity.getCharges();
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
    
    public String toJson(){
        JsonObject object = new JsonObject();
        object.addProperty("charges", this.charges);
        object.addProperty("trigger", this.trigger.toString());
        String details = formJson();
        object.addProperty("details", details);
        Gson gson = new Gson();
        return gson.toJson(object);
    }
    
    protected abstract String formJson();
    
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

    public UnitEffectGroup getGroup() {
        return group;
    }

    public void setGroup(UnitEffectGroup group) {
        this.group = group;
    }
    
    public UnitEffectEntity convert(Unit source){
        UnitEffectEntity entity = new UnitEffectEntity();
        entity.setId(this.id);
        int unitId = source.getId();
        entity.setUnitId(unitId);
        entity.setEffectName(this.name);
        entity.setAssetName(this.asset);
        String groupStr = this.group.toString();
        entity.setEffectGroup(groupStr);
        entity.setCharges(this.charges);
        String json = this.formJson();
        entity.setEffect(json);
        return entity;
    }
}
