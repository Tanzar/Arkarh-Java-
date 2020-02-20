/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Elements;

import com.tanzar.Arkarh.Entities.Obstacle;
import com.tanzar.Arkarh.Entities.Terrain;

/**
 *
 * @author Tanzar
 */
public class BoardSpace {
    private int x;
    private int y;
    private boolean locked = false;
    private Terrain terrain;
    private Obstacle obstacle;

    public BoardSpace(int x, int y) {
        this.x = x;
        this.y = y;
        this.locked = false;
        this.terrain = new Terrain(-1, "none");
        this.obstacle = new Obstacle();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isLocked() {
        return locked;
    }

    public void lock() {
        this.locked = true;
    }
    
    public void unlock(){
        this.locked = true;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }
}
