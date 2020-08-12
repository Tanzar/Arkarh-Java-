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
    private Asset terrains[];
    private Asset items[];
    private Asset structures[];
    private Asset enemies[];
    private Asset players[];
    private Asset units[];
    
    public Assets(){
        prepareTerrains();
        prepareStructures();
        prepareUnits();
    }
    
    private void prepareTerrains(){
        this.terrains = new Asset[15];
        this.terrains[0] = new Asset("/img/terrain/ash.jpg", "ash");
        this.terrains[1] = new Asset("/img/terrain/grass.jpg", "grass");
        this.terrains[2] = new Asset("/img/terrain/ice.jpg", "ice");
        this.terrains[3] = new Asset("/img/terrain/lava.jpg", "lava");
        this.terrains[4] = new Asset("/img/terrain/sand.jpg", "sand");
        this.terrains[5] = new Asset("/img/terrain/snow.jpg", "snow");
        this.terrains[6] = new Asset("/img/terrain/water.jpg", "water");
        this.terrains[7] = new Asset("/img/terrain/stone.jpg", "stone");
        this.terrains[8] = new Asset("/img/terrain/arcaneGrass.jpg", "arcaneGrass");
        this.terrains[9] = new Asset("/img/terrain/dryGround.jpg", "dryGround");
        this.terrains[10] = new Asset("/img/terrain/farmlands.jpg", "farmlands");
        this.terrains[11] = new Asset("/img/terrain/jungleGrass.jpg", "jungleGrass");
        this.terrains[12] = new Asset("/img/terrain/steppes.jpg", "steppes");
        this.terrains[13] = new Asset("/img/terrain/obsidian.jpg", "obsidian");
        this.terrains[14] = new Asset("/img/terrain/none.jpg", "none");
    }
    
    private void prepareStructures(){
        this.structures = new Asset[6];
        this.structures[0] = new Asset("", "none");
        this.structures[1] = new Asset("/img/structures/natural/mountains.png", "mountains");
        this.structures[2] = new Asset("/img/structures/natural/trees.png", "trees");
        this.structures[3] = new Asset("/img/structures/natural/arcaneTrees.png", "arcaneTrees");
        this.structures[4] = new Asset("/img/structures/natural/cactus.png", "cactus");
        this.structures[5] = new Asset("/img/structures/natural/deadTree.png", "deadTree");
    }
    
    private void prepareUnits(){
        this.units = new Asset[5];
        this.units[0] = new Asset("/img/units/none.png", "none");
        this.units[1] = new Asset("/img/units/warrior.png", "warrior");
        this.units[2] = new Asset("/img/units/flanker.png", "flanker");
        this.units[3] = new Asset("/img/units/mage.png", "mage");
        this.units[4] = new Asset("/img/units/shooter.png", "shooter");
    }

    public Asset[] getTerrains() {
        return terrains;
    }
    
    public Asset[] getItems() {
        return items;
    }
    
    public Asset[] getStructures() {
        return structures;
    }
    
    public Asset[] getEnemies() {
        return enemies;
    }
    
    public Asset[] getPlayers() {
        return players;
    }
    
    public Asset[] getUnits(){
        return this.units;
    }
}
