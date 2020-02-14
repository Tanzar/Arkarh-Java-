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
@Table(name = " fields")
public class Field {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", insertable=false, updatable=false)
    private Integer id;
    
    private Integer x;
    
    private Integer y;
    
    private Integer idMap;
    
    private Integer idTerrain;

    public Field() {
        this.id = 0;
        this.x = 0;
        this.y = 0;
        this.idMap = 0;
        this.idTerrain = 0;
    }

    public Field(Integer id, Integer x, Integer y, Integer idMap, Integer idTerrain) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.idMap = idMap;
        this.idTerrain = idTerrain;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getIdMap() {
        return idMap;
    }

    public void setIdMap(Integer idMap) {
        this.idMap = idMap;
    }

    public Integer getIdTerrain() {
        return idTerrain;
    }

    public void setIdTerrain(Integer id) {
        this.idTerrain = id;
    }
    
    
}
