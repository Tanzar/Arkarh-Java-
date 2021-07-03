/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Entities.Unit;

import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbilityGroup;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author spako
 */
@Entity
@Table(name = "units_abilities")
public class UnitAbilityEntity implements Serializable{
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer id;
    
    @Column(name = "unit_id")
    private int unitId;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "ability_group")
    private String group;
    
    @Column(name = "asset")
    private String asset;
    
    @Column(name = "charges")
    private int charges;
    
    @Column(name = "cooldown")
    private int cooldown;
    
    @Column(name = "initial_cooldown")
    private int initialCooldown;
    
    @Column(name = "effect_json")
    private String effect;

    public UnitAbilityEntity() {
        this.id = 0;
        this.unitId = 0;
        this.name = "none";
        this.group = UnitAbilityGroup.attack.toString();
        this.asset = "none.png";
        this.charges = -1;
        this.cooldown = 0;
        this.initialCooldown = 0;
        this.effect = "";
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getEffectName() {
        return name;
    }

    public void setEffectName(String effectName) {
        this.name = effectName;
    }

    public String getEffectGroup() {
        return group;
    }

    public void setEffectGroup(String effectGroup) {
        this.group = effectGroup;
    }

    public String getAssetName() {
        return asset;
    }

    public void setAssetName(String assetName) {
        this.asset = assetName;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public int getCharges() {
        return charges;
    }

    public void setCharges(int charges) {
        this.charges = charges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getInitialCooldown() {
        return initialCooldown;
    }

    public void setInitialCooldown(int initialCooldown) {
        this.initialCooldown = initialCooldown;
    }

    
}
