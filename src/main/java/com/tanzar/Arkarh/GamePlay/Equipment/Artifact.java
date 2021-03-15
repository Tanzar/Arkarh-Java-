/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Equipment;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Entities.Leader.ArtifactEntity;
import com.tanzar.Arkarh.GamePlay.Leader.Leader;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passive;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.PassiveEffect;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.PassiveSource;
import com.tanzar.Arkarh.GamePlay.Units.Unit;

/**
 *
 * @author spako
 */
public class Artifact {
    private int id;
    private String name;
    private String asset;
    private Slot slot;
    private ArtifactEffect effect;
    private int value;
    private EffectSchool school;
    
    
    public Artifact(){
        this.id = 0;
        this.name = "noname";
        this.asset = "none.png";
        this.slot = Slot.bonus;
        this.effect = ArtifactEffect.attack;
        this.value = 1;
        this.school = EffectSchool.physical;
    }
    
    public Artifact(Json json){
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.asset = json.getString("asset");
        String slot = json.getString("slot");
        this.slot = Slot.valueOf(slot);
        String effect = json.getString("effect");
        this.effect = ArtifactEffect.valueOf(effect);
        this.value = json.getInt("value");
        String school = json.getString("school");
        this.school = EffectSchool.valueOf(school);
    }
    
    public Artifact(ArtifactEntity entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.asset = entity.getAsset();
        String slot = entity.getSlot();
        this.slot = Slot.valueOf(slot);
        String effect = entity.getEffect();
        this.effect = ArtifactEffect.valueOf(effect);
        this.value = entity.getValue();
        String school = entity.getSchool();
        this.school = EffectSchool.valueOf(school);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public ArtifactEffect getEffect() {
        return effect;
    }

    public void setEffect(ArtifactEffect effect) {
        this.effect = effect;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public EffectSchool getSchool() {
        return school;
    }

    public void setSchool(EffectSchool school) {
        this.school = school;
    }
    
    public void applyBonus(Leader leader, Unit unit){
        if(this.effect.isLeaderBonus()){
            this.applyBonus(leader);
        }
        if(this.effect.isUnitBonus()){
            this.applyBonus(unit);
        }
    }

    public void applyBonus(Leader leader){
        switch(this.effect){
            case attack:
                leader.changeAttack(value);
                break;
            case defense:
                leader.changeDefense(value);
                break;
            case spellPower:
                leader.changeSpellPower(value);
                break;
            case attackAndDefense:
                leader.changeAttack(value);
                leader.changeDefense(value);
                break;
            case attackAndSpellPower:
                leader.changeAttack(value);
                leader.changeSpellPower(value);
                break;
            case defenseAndSpellPower:
                leader.changeDefense(value);
                leader.changeSpellPower(value);
                break;
            case allStats:
                leader.changeAttack(value);
                leader.changeDefense(value);
                leader.changeSpellPower(value);
                break;
            case upkeep:
                leader.changeUpkeep(value);
                break;
        }
    }
    
    public void applyBonus(Unit unit){
        switch(this.effect){
            case armor:
                this.applyPassive(unit, PassiveEffect.armor);
                break;
            case ward:
                this.applyPassive(unit, PassiveEffect.ward);
                break;
            case armorAndWard:
                this.applyPassive(unit, PassiveEffect.armor, PassiveEffect.ward);
                break;
            case morale:
                this.applyPassive(unit, PassiveEffect.baseMorale);
                break;
            case moraleLossReduction:
                this.applyPassive(unit, PassiveEffect.moraleLoss);
                break;
            case speed:
                this.applyPassive(unit, PassiveEffect.speed);
                break;
            case bonusDamage:
                this.applyPassive(unit, PassiveEffect.bonusDamage);
                break;
            case damage:
                this.applyPassive(unit, PassiveEffect.damage);
                break;
            case health:
                this.applyPassive(unit, PassiveEffect.bonusHealth);
                break;
        }
    }
    
    private void applyPassive(Unit unit, PassiveEffect ... effects){
        for(PassiveEffect passiveEffect: effects){
            Passive passive = new Passive(this.id, 1, 1, this.value, passiveEffect, this.school, PassiveSource.artifact);
            unit.addPassive(passive);
        }
    }
    
    public String formJson(){
        Json json = new Json();
        json.add("id", this.id);
        json.add("name", this.name);
        json.add("asset", this.asset);
        json.add("slot", this.slot.toString());
        json.add("effect", this.effect.toString());
        json.add("value", this.value);
        json.add("school", this.school.toString());
        return json.formJson();
    }
    
}
