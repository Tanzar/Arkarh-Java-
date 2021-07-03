/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Database.Entities.Leader;

import com.tanzar.Arkarh.GamePlay.Leader.Magic.Spell;
import com.tanzar.Arkarh.GamePlay.TMP.Fraction;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Tanzar
 */
@Entity
@Table(name = "spells")
public class SpellEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "asset")
    private String asset;
    
    @Column(name = "school")
    private String school;
    
    @Column(name = "fraction")
    private String fraction;
    
    @Column(name = "allianceAccess")
    private Boolean allianceAccess;
    
    @Column(name = "scaling")
    private Double scaling;
    
    @Column(name = "effect_json")
    private String effectJson;

    public SpellEntity() {
        this.id = 0;
        this.name = "noname";
        this.asset = "none.png";
        this.school = EffectSchool.arcane.toString();
        this.fraction = Fraction.none.toString();
        this.allianceAccess = true;
        this.scaling = 1.0;
        this.effectJson = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }

    public Boolean getAllianceAccess() {
        return allianceAccess;
    }

    public void setAllianceAccess(Boolean allianceAccess) {
        this.allianceAccess = allianceAccess;
    }

    public String getEffectJson() {
        return effectJson;
    }

    public void setEffectJson(String effectJson) {
        this.effectJson = effectJson;
    }

    public Double getScaling() {
        return scaling;
    }

    public void setScaling(Double scaling) {
        this.scaling = scaling;
    }
     
    
}
