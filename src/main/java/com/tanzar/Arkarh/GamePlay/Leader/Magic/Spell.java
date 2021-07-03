/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Leader.Magic;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Database.Entities.Leader.SpellEntity;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.TMP.Alliance;
import com.tanzar.Arkarh.GamePlay.TMP.Fraction;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;

/**
 *
 * @author Tanzar
 */
public abstract class Spell {
    private int id;
    private EffectSchool school;
    private double scaling;
    private String name;
    private String asset;
    private Fraction fraction;
    private boolean allianceAccess;
    
    public Spell(){
        this.id = 0;
        this.school = EffectSchool.arcane;
        this.scaling = 1.0;
        this.name = "noname";
        this.asset = "none.png";
        this.fraction = Fraction.none;
        this.allianceAccess = true;
    }
    
    public Spell(SpellEntity entity){
        this.id = entity.getId();
        this.school = EffectSchool.valueOf(entity.getSchool());
        this.scaling = entity.getScaling();
        this.name = entity.getName();
        this.asset = entity.getAsset();
        this.fraction = Fraction.valueOf(entity.getFraction());
        this.allianceAccess = entity.getAllianceAccess();
    }
    
    public int getId(){
        return this.id;
    }
    
    public EffectSchool getSchool(){
        return this.school;
    }
    
    public double getScaling(){
        return this.scaling;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getAsset(){
        return this.asset;
    }
    
    public Fraction getFraction(){
        return this.fraction;
    }
    
    public boolean haveAllianceAccess(){
        return this.allianceAccess;
    }
    
    public boolean isSameAs(Spell compared){
        if(this.id == compared.getId()){
            return true;
        }
        return false;
    }
    
    public boolean isAvailableFor(Fraction fraction){
        if(this.fraction == Fraction.none){
            return true;
        }
        else{
            if(this.fraction == fraction){
                return true;
            }
            else{
                return false;
            }
        }
    }
    
    public boolean isAvailableFor(Alliance alliance){
        if(this.fraction == Fraction.none){
            return true;
        }
        else{
            if(this.fraction.isPartOfAlliance(alliance) && this.haveAllianceAccess()){
                return true;
            }
            return false;
        }
    }
    
    public abstract void applySpell(Battlefield battlefield, CombatReport log);
    
    public SpellEntity convert(){
        SpellEntity entity = new SpellEntity();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setAsset(this.asset);
        entity.setFraction(this.fraction.toString());
        entity.setAllianceAccess(this.allianceAccess);
        Json json = new Json();
        this.formJson(json);
        entity.setEffectJson(json.formJson());
        return entity;
    }
    
    protected abstract void formJson(Json json);
}
