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
@Table(name = "fields")
public class Field {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer id;
    
    @Column(name = "x")
    private Integer x;
    
    @Column(name = "y")
    private Integer y;
    
    @Column(name = "id_map")
    private Integer idMap;
    
    @Column(name = "terrain_Index")
    private Integer terrainIndex;
    
    @Column(name = "asset_Index")
    private Integer assetIndex;
    
    @Column(name = "event_type")
    private String eventType;
    
    @Column(name = "event")
    private String event;

    public Field() {
        this.id = 0;
        this.x = 0;
        this.y = 0;
        this.idMap = 0;
        this.terrainIndex = 0;
        this.assetIndex = 0;
        this.eventType = "none";
        this.event = "none";
    }

    public Field(Integer id, Integer x, Integer y, Integer idMap, Integer terrainIndex, Integer assetIndex, String eventType, String event) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.idMap = idMap;
        this.terrainIndex = terrainIndex;
        this.assetIndex = assetIndex;
        this.eventType = eventType;
        this.event = event;
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

    public Integer getTerrainIndex() {
        return terrainIndex;
    }

    public void setTerrainIndex(Integer terrainIndex) {
        this.terrainIndex = terrainIndex;
    }

    public Integer getAssetIndex() {
        return assetIndex;
    }

    public void setAssetIndex(Integer assetIndex) {
        this.assetIndex = assetIndex;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    
}
