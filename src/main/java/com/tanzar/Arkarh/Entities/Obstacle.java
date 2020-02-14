/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Entities;

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
@Table(name = "obstacles")
public class Obstacle {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", insertable=false, updatable=false)
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    private String asset;
    
    @Column(name = "lockedX")
    private Integer[] lockedX;
    
    @Column(name = "lockedY")
    private Integer[] lockedY;

    public Obstacle() {
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
        this.asset = setAssetPath(name);
    }

    public String getAsset() {
        return asset;
    }

    public Integer[] getLockedX() {
        return lockedX;
    }

    public void setLockedX(Integer[] lockedX) {
        this.lockedX = lockedX;
    }

    public Integer[] getLockedY() {
        return lockedY;
    }

    public void setLockedY(Integer[] lockedY) {
        this.lockedY = lockedY;
    }
    
    private String setAssetPath(String name){
        return "/img/obstacles/" + name + ".png";
    }
}
