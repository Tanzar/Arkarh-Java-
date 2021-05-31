/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities.Base;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Entities.Unit.UnitAbilityEntity;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
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
    protected Trigger trigger;
    protected int cooldown;
    protected int currentCooldown;
    protected int charges;
    private int unitId;
    private UnitAbilityGroup group;
    
    public UnitAbility(){
        this.id = 0;
        this.unitId = 0;
        this.group = UnitAbilityGroup.none;
        this.name = "Noname";
        this.asset = "none.png";
        this.trigger = Trigger.onAction;
        this.charges = 0;
        this.cooldown = 0;
        this.currentCooldown = 0;
    }
    
    public UnitAbility(Trigger trigger){
        this.id = 0;
        this.unitId = 0;
        this.group = UnitAbilityGroup.none;
        this.name = "Noname";
        this.asset = "none.png";
        this.trigger = trigger;
        this.charges = 0;
        this.cooldown = 0;
        this.currentCooldown = 0;
    }
    
    public UnitAbility(Trigger trigger, UnitAbilityGroup group){
        this.id = 0;
        this.unitId = 0;
        this.group = group;
        this.name = "Noname";
        this.asset = "none.png";
        this.trigger = trigger;
        this.charges = 0;
        this.cooldown = 0;
        this.currentCooldown = 0;
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
        this.cooldown = entity.getCooldown();
        this.currentCooldown = entity.getInitialCooldown();
    }
    
    public UnitAbility(Json json){
        this.id = json.getInt("id");
        this.unitId = json.getInt("unitId");
        this.group = UnitAbilityGroup.valueOf(json.getString("group"));
        this.name = json.getString("name");
        this.asset = json.getString("asset");
        this.trigger = Trigger.valueOf(json.getString("trigger"));
        this.charges = json.getInt("charges");
        this.cooldown = json.getInt("cooldown");;
        this.currentCooldown = json.getInt("currentCooldown");;
    }

    public void use(Unit source, Trigger mainTrigger, Battlefield battlefield, CombatReport report){
        this.report = report;
        if(this.trigger.equals(mainTrigger) && this.additionalConditions(source)){
            if(this.isReady()){
                Units targets = this.setupTargets(source, battlefield);
                this.onUse(source, targets);
                this.reduceCharges();
                this.startCooldown();
            }
            else{
                this.reduceCooldown();
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
        json.add("cooldown", this.cooldown);
        json.add("currentCooldown", this.currentCooldown);
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
        entity.setCooldown(this.cooldown);
        entity.setInitialCooldown(this.currentCooldown);
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
        entity.setCooldown(this.cooldown);
        entity.setInitialCooldown(this.currentCooldown);
        Json json = new Json();
        this.formJson(json);
        entity.setEffect(json.formJson());
        return entity;
    }
    
    protected void recoverCharge(){
        this.charges++;
    }
    
    protected boolean isReady(){
        if(this.currentCooldown == 0 && this.charges != 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    protected void reduceCooldown(){
        if(this.currentCooldown > 0){
            this.currentCooldown--;
        }
    }
    
    protected void startCooldown(){
        this.currentCooldown = this.cooldown;
    }
    
    protected void clearCooldown(){
        this.currentCooldown = 0;
    }
}
