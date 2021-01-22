/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Entities.Leader;

import com.tanzar.Arkarh.GamePlay.Equipment.Artifact;
import com.tanzar.Arkarh.GamePlay.Equipment.Slot;
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
@Table(name = "artifacts")
public class ArtifactEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "asset")
    private String asset;
    
    @Column(name = "slot")
    private String slot;
    
    @Column(name = "effect")
    private String effect;
    
    @Column(name = "value")
    private int value;
    
    @Column(name = "school")
    private String school;
    
    public ArtifactEntity() {
        this.id = 0;
        this.name = "noname";
        this.asset = "none.png";
        this.slot = "head";
        this.effect = "attack";
        this.value = 1;
        this.school = "physical";
    }

    public ArtifactEntity(Integer id, String name, String asset, String slot, String effect, int value, String school) {
        this.id = id;
        this.name = name;
        this.asset = asset;
        this.slot = slot;
        this.effect = effect;
        this.value = value;
        this.school = school;
    }
    
    public ArtifactEntity(Artifact artifact) {
        this.id = artifact.getId();
        this.name = artifact.getName();
        this.asset = artifact.getAsset();
        this.slot = artifact.getSlot().toString();
        this.effect = artifact.getEffect().toString();
        this.value = artifact.getValue();
        this.school = artifact.getSchool().toString();
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

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

}
