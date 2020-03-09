/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Elements;

/**
 *
 * @author Tanzar
 */
public class Assets {
    private String terrains[];
    private String items[];
    private String enviroments[];
    private String enemies[];
    private String locations[];
    private String players[];
    
    public Assets(){
        
    }
    
    private void prepareTerrain(){
        this.terrains = new String[7];
        this.terrains[0] = "/img/terrain/ash.jpg";
        this.terrains[1] = "/img/terrain/grass.jpg";
        this.terrains[2] = "/img/terrain/ice.jpg";
        this.terrains[3] = "/img/terrain/lava.jpg";
        this.terrains[4] = "/img/terrain/sand.jpg";
        this.terrains[5] = "/img/terrain/snow.jpg";
        this.terrains[6] = "/img/terrain/water.jpg";
    }
    
    private void prepareEnviroments(){
        this.enviroments = new String[2];
        this.enviroments[0] = "";
        this.enviroments[1] = "";
    }

    public String[] getTerrains() {
        return terrains;
    }
    
    public String getTerrain(int id){
        if(id >= 0 && id < this.terrains.length){
            return this.terrains[id];
        }
        else{
            return "";
        }
    }

    public String[] getItems() {
        return items;
    }
    
    public String getItem(int id){
        if(id >= 0 && id < this.items.length){
            return this.items[id];
        }
        else{
            return "";
        }
    }

    public String[] getEnviroments() {
        return enviroments;
    }
    
    public String getEnviroment(int id){
        if(id >= 0 && id < this.enviroments.length){
            return this.enviroments[id];
        }
        else{
            return "";
        }
    }
    
    public String[] getEnemies() {
        return enemies;
    }
    
    public String getEnemy(int id){
        if(id >= 0 && id < this.enemies.length){
            return this.enemies[id];
        }
        else{
            return "";
        }
    }

    public String[] getLocations() {
        return locations;
    }
    
    public String getLocation(int id){
        if(id >= 0 && id < this.locations.length){
            return this.locations[id];
        }
        else{
            return "";
        }
    }

    public String[] getPlayers() {
        return players;
    }
    
    public String getPlayer(int id){
        if(id >= 0 && id < this.players.length){
            return this.players[id];
        }
        else{
            return "";
        }
    }

}
