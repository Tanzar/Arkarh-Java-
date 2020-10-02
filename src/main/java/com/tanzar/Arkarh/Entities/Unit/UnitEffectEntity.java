/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Entities.Unit;

import com.tanzar.Arkarh.GamePlay.Units.UnitEffectGroup;
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
@Table(name = "units_effects")
public class UnitEffectEntity implements Serializable{
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer id;
    
    @Column(name = "unit_id")
    private int unitId;
    
    @Column(name = "effect_name")
    private String effectName;
    
    @Column(name = "effect_group")
    private String effectGroup;
    
    @Column(name = "asset_name")
    private String assetName;
    
    @Column(name = "charges")
    private int charges;
    
    @Column(name = "effect_json")
    private String effect;

    public UnitEffectEntity() {
        this.id = 0;
        this.unitId = 0;
        this.effectName = "none";
        this.effectGroup = UnitEffectGroup.attack.toString();
        this.assetName = "none.png";
        this.charges = -1;
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
        return effectName;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    public String getEffectGroup() {
        return effectGroup;
    }

    public void setEffectGroup(String effectGroup) {
        this.effectGroup = effectGroup;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
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

    
}
