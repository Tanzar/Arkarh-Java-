/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Map;

import com.tanzar.Arkarh.Map.Biomes.Biome;
import com.tanzar.Arkarh.Map.Biomes.BiomeGenerator;
import com.tanzar.Arkarh.Map.Biomes.BiomeList;
import com.tanzar.Arkarh.Map.Exceptions.FieldLocationException;
import com.tanzar.Arkarh.Map.Exceptions.FieldsVerificationException;
import com.tanzar.Arkarh.Map.Exceptions.GenerationException;
import com.tanzar.Arkarh.Map.Exceptions.MapSizeException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tanzar
 */
public class MapGenerator {
    private Integer width;
    private Integer height;
    private Randomizer randomizer;
    private BiomeGenerator biomeGenerator;
    private Splitter splitter;
    private Map map;
    
    public MapGenerator(Integer width, Integer height){
        this.width = width;
        this.height = height;
        this.randomizer = new Randomizer();
        this.biomeGenerator = new BiomeGenerator();
        this.splitter = new Splitter(this.randomizer);
    }
    
    /**
     * Generae map based on randomized seed
     * 
     * @return
     * @throws GenerationException 
     */
    public Map generate() throws GenerationException{
        Map newMap = this.generate(this.randomizer.getSeed());
        return newMap;
    }
    
    /**
     * Generate map based on seed
     * 
     * @param seed number by which map is created
     * @return 
     * @throws GenerationException 
     */
    public Map generate(Integer seed) throws GenerationException{
        try {
            this.randomizer.setSeed(seed);
            Map newMap = this.newMap();
            newMap.setSeed(seed);
            return newMap;
        }
        catch (MapSizeException | FieldLocationException | FieldsVerificationException ex) {
            throw new GenerationException(ex.getMessage());
        }
    }
    
    /**
     * Creates new map with 3 phase generation
     * 1) Map split - splits map for smaller parts(biomes)
     * 2) Biomes generation - generate each biome based on this biome "rules"
     * 3) Fill empty - for each biome fills remaining empty spaces
     * 
     * @return
     * @throws MapSizeException
     * @throws FieldLocationException
     * @throws FieldsVerificationException 
     */
    private Map newMap() throws MapSizeException, FieldLocationException, FieldsVerificationException{
        this.map = new Map(this.width, this.height);
        BiomeList biomes = this.mapSplitPhase();
        this.biomesGenerationPhase(biomes);
        this.fillEmptyPhase(biomes);
        return map;
    }
    
    /**
     * First phase of map generation
     * Sets positions and spaces of biomes
     * For each vertical position finds free horizontal position
     * Then calculates space and splits it for smaller parts
     * For each part selects biome and calculates it size
     * 
     * @return List of biomes on map
     * @throws FieldLocationException
     * @throws FieldsVerificationException 
     */
    private BiomeList mapSplitPhase() throws FieldLocationException, FieldsVerificationException{
        BiomesMap biomesMap = new BiomesMap(this.width, this.height);
        BiomeList biomes = new BiomeList();
        int minWidth = this.biomeGenerator.minBiomeWidth();
        int maxWidth = this.biomeGenerator.maxBiomeWidth();
        for(int y = 0; y < this.height; y++){
            boolean endLine = false;
            while(!endLine){
                int x = biomesMap.firstFreeXIndex(y);
                if(x > -1){
                    int space = biomesMap.countSpaceX(x, y);
                    int freeHeight = biomesMap.countSpaceY(x, y);
                    int[] widths = this.splitter.split(space, minWidth, maxWidth);
                    for(int i = 0; i < widths.length; i++){
                        Biome biome = this.generateBiome(x, y, widths[i], freeHeight);
                        biomes.add(biome);
                        biomesMap.markZone(x, y, widths[i], biome.getHeight(), biomes.size());
                        x += widths[i];
                    }
                }
                else{
                    endLine = true;
                }
            }
        }
        return biomes;
    }
    
    /**
     * Generate new biome based on given parameters
     * 
     * @param x horisontal position on map, top left corner
     * @param y vertical position on map, top left corner
     * @param width biome width
     * @param freeHeight available vertical space on map
     * @return 
     */
    private Biome generateBiome(int x, int y, int width, int freeHeight){
        int[] matchingBiomes = this.biomeGenerator.getMatchingBiomes(width, freeHeight);
        int biomeNumber;
        if(matchingBiomes.length == 1 && matchingBiomes[0] == -1){
            biomeNumber = -1;
        }
        else{
            biomeNumber = this.randomizer.random(0, matchingBiomes.length - 1);
        }
        Biome biome = this.biomeGenerator.newBiome(biomeNumber);
        int height = this.calculateHeight(freeHeight, biome.getMinHeight(), biome.getMaxHeight());
        biome.setX(x);
        biome.setY(y);
        biome.setWidth(width);
        biome.setHeight(height);
        biome.setRandomizer(this.randomizer);
        return biome;
    }
    
    /**
     * Calculates height of biome
     * 
     * @param freeHeight current free vertical space
     * @param min minimal height
     * @param max maximal height
     * @return biome height
     */
    private int calculateHeight(int freeHeight, int min, int max){
        if(freeHeight > max){
            return this.randomizer.random(min, max);
        }
        else{
            return freeHeight;
        }
    }
    
    /**
     * Initial Biomes generation
     * 
     * @param biomes list of biomes to generate
     * @throws FieldsVerificationException 
     */
    private void biomesGenerationPhase(BiomeList biomes) throws FieldsVerificationException{
        for(int i = 0; i < biomes.size(); i++){
            Biome biome = biomes.get(i);
            Field[][] fields = biome.generate();
            map.setFields(fields);
        }
    }
    
    /**
     * Filling remaining empty space
     * Starts with borders then by layers fills biome
     * 
     * @param biomes list of biomes on map
     * @throws FieldLocationException 
     */
    private void fillEmptyPhase(BiomeList biomes) throws FieldLocationException{
        for(int i = 0; i < biomes.size(); i++){
            Biome biome = biomes.get(i);
            this.makeBiomeBorder(biome);
            this.fillInnerBiome(biome);
        }
        
    }
    
    /**
     * Selecting terrains for empty fields on border
     * 
     * @param biome list of biomes on map
     * @throws FieldLocationException 
     */
    private void makeBiomeBorder(Biome biome) throws FieldLocationException{
        for(int x = 0; x < biome.getWidth(); x++){
            this.selectTerrain(biome, x, 0);
        }
        for(int y = 0; y < biome.getHeight(); y++){
            this.selectTerrain(biome, biome.getWidth() - 1, y);
        }
        for(int x = (biome.getWidth() - 1); x > 0; x--){
            this.selectTerrain(biome, x, biome.getHeight() - 1);
        }
        for(int y = (biome.getHeight() - 1); y > 0; y--){
            this.selectTerrain(biome, 0, y);
        }
        
    }
    
    /**
     * Calculating number of layers for biome, then for each layer setting terrain
     * Number of layers is width/height (whichever is smaller) divided by 2
     * 
     * @param biome biome which empty fields will be set
     * @throws FieldLocationException 
     */
    private void fillInnerBiome(Biome biome) throws FieldLocationException{
        int numberOfLayers = 0;
        if(biome.getWidth() > biome.getHeight()){
            numberOfLayers = (int) Math.floor(biome.getHeight() / 2);
        }
        else{
            numberOfLayers = (int) Math.floor(biome.getWidth() / 2);
        }
        for(int i = 1; i <= numberOfLayers; i++){
            this.setLayerTerrains(biome, i);
        }
    }
    
    /**
     * Sets terrains for biome layer
     * Biomes are two dimensional arrays (rectamgles) layer is rectangle inside this biome
     * Layer 0 is border
     * Layer 1 are field "under" boder by 1 field
     * Layer 2 is same as 1 but is 2 under border etc.
     * 
     * Visual example:
     * 
     * 0 0 0 0 0 0 0
     * 0 1 1 1 1 1 0
     * 0 1 2 2 2 1 0
     * 0 1 2 3 2 1 0
     * 0 1 2 2 2 1 0
     * 0 1 1 1 1 1 0
     * 0 0 0 0 0 0 0
     * 
     * @param biome biome which layer is used
     * @param layerLevel level of layer in biome - see visual example
     * @throws FieldLocationException 
     */
    private void setLayerTerrains(Biome biome, int layerLevel) throws FieldLocationException{
        if(layerLevel < 1)
            layerLevel = 1;
        for(int x = layerLevel; x < (biome.getWidth() - layerLevel); x++){
            this.selectTerrain(biome, x, layerLevel);
        }
        for(int y = layerLevel; y < (biome.getHeight() - layerLevel); y++){
            this.selectTerrain(biome, (biome.getWidth() - layerLevel - 1), y);
        }
        for(int x = (biome.getWidth() - layerLevel - 1); x > layerLevel; x--){
            this.selectTerrain(biome, x, (biome.getHeight() - layerLevel - 1));
        }
        for(int y = (biome.getHeight() - layerLevel - 1); y > layerLevel; y--){
            this.selectTerrain(biome, layerLevel, y);
        }
    }
    
    /**
     * Selects terrain based on position, only if terrain is not set
     * for map border sets default terrain
     * for biome border checks ouuter neighbours an sets terrain based on them
     * otherwise sets terrain based on field neighbours inside biome
     * 
     * @param biome biome for which terrain will be selected
     * @param x horizontal position in biome
     * @param y vertical position in biome
     * @throws FieldLocationException 
     */
    private void selectTerrain(Biome biome, int x, int y) throws FieldLocationException{
        if(!map.isTerrainSet(x + biome.getX(), y + biome.getY())){
            if(map.isOnBorder(x + biome.getX(), y + biome.getY())){
                Field field = map.getField(x + biome.getX(), y + biome.getY());
                field.setDefaultTerrain();
            }
            else{
                if(biome.isOnBorder(x, y)){
                    this.checkOuterNetghbours(biome, x, y);
                }
                else{
                    this.setInnerTerrain(biome, x, y);
                }
            }
        }
    }
    
    /**
     * Method checks if position is in corner or on border between corners - for corners there are 2 neighbours, otherwise there is 1
     * 
     * @param biome biome for which terrain will be selected
     * @param x horizontal position in biome
     * @param y vertical position in biome
     * @throws FieldLocationException 
     */
    private void checkOuterNetghbours(Biome biome, int x, int y) throws FieldLocationException{
        if(biome.isCorner(x, y)){
            this.checkCornerNeighbours(biome, x, y);
        }
        else{
            this.checkLineNeighbour(biome, x, y);
        }
    }
    
    /**
     * Checks which corner is on this position
     * 
     * @param biome biome for which terrain will be selected
     * @param x horizontal position in biome
     * @param y vertical position in biome
     * @throws FieldLocationException 
     */
    private void checkCornerNeighbours(Biome biome, int x, int y) throws FieldLocationException{
        Field up = map.getField(biome.getX() + x, biome.getY() + y - 1);
        Field down = map.getField(biome.getX() + x, biome.getY() + y + 1);
        Field right = map.getField(biome.getX() + x + 1, biome.getY() + y);
        Field left = map.getField(biome.getX() + x - 1, biome.getY() + y);
        Field current = map.getField(biome.getX() + x, biome.getY() + y);
        if(x == 0 && y == 0){
            this.setCorner(current, up, left);
        }
        if(x == 0 && y == biome.getHeight() - 1){
            this.setCorner(current, down, left);
        }
        if(x == biome.getWidth() - 1 && y == 0){
            this.setCorner(current, up, right);
        }
        if(x == biome.getWidth() - 1 && y == biome.getHeight() - 1){
            this.setCorner(current, down, right);
        }
    }
    
    /**
     * Sets terrain for corner
     * If any neighbour have water as terrain sets water otherwise randomizes it
     * 
     * @param current current field, its terrain will be set
     * @param first first od 2 neighbours
     * @param second  second of 2 neighbours
     */
    private void setCorner(Field current, Field first, Field second){
        if(first.getTerrain() == "water" || second.getTerrain() == "water"){
            current.setTerrain("water");
        }
        else{
            Biome biome;
            int number = this.randomizer.random(0, 59);
            if(number < 20){
                biome = first.getBiome();
            }
            else{
                if(number < 40){
                    biome = second.getBiome();
                }
                else{
                    biome = current.getBiome();
                }
            }
            String terrain = biome.getDefaultTerrain();
            current.setTerrain(terrain);
        }
    }
    
    /**
     * Checks which side is on this position
     * 
     * @param biome biome for which terrain will be selected
     * @param x horizontal position in biome
     * @param y vertical position in biome
     * @throws FieldLocationException 
     */
    private void checkLineNeighbour(Biome biome, int x, int y) throws FieldLocationException{
        Field current = map.getField(biome.getX() + x, biome.getY() + y);
        if(x == 0){
            Field left = map.getField(biome.getX() + x - 1, biome.getY() + y);
            this.setBorder(current, left);
        }
        if(y == biome.getHeight() - 1){
            Field down = map.getField(biome.getX() + x, biome.getY() + y + 1);
            this.setBorder(current, down);
        }
        if(y == 0){
            Field up = map.getField(biome.getX() + x, biome.getY() + y - 1);
            this.setBorder(current, up);
        }
        if(x == biome.getWidth() - 1){
            Field right = map.getField(biome.getX() + x + 1, biome.getY() + y);
            this.setBorder(current, right);
        }
    }
    
    /**
     * Sets terrain of current based on neighbour
     * if neighbour have terrain set then it chooses neighbours terrain otherwise it sets default terrain
     * 
     * @param current current field its terrain will be set
     * @param neighbour neighbouring field based on its terrain current will be set
     */
    private void setBorder(Field current, Field neighbour){
        if(neighbour.isTerrainSet()){
            String terrain = neighbour.getDefaultTerrain();
            current.setTerrain(terrain);
        }
        else{
            current.setDefaultTerrain();
        }
    }
    
    /**
     * Sets terrain based on neighbours
     * Checks four neighbours if any is diffrent add to list
     * 
     * @param biome biome for which terrain will be selected
     * @param x horizontal position in biome
     * @param y vertical position in biome
     * @throws FieldLocationException 
     */
    private void setInnerTerrain(Biome biome, int x, int y) throws FieldLocationException{
        Field up = map.getField(biome.getX() + x, biome.getY() + y - 1);
        Field down = map.getField(biome.getX() + x, biome.getY() + y + 1);
        Field right = map.getField(biome.getX() + x + 1, biome.getY() + y);
        Field left = map.getField(biome.getX() + x - 1, biome.getY() + y);
        Field current = map.getField(biome.getX() + x, biome.getY() + y);
        List<Field> list = new ArrayList<Field>();
        if(up.isTerrainSet() && up.isDefaultTerrainNotSet()){
            list.add(up);
        }
        if(down.isTerrainSet() && down.isDefaultTerrainNotSet()){
            list.add(down);
        }
        if(right.isTerrainSet() && right.isDefaultTerrainNotSet()){
            list.add(right);
        }
        if(left.isTerrainSet() && left.isDefaultTerrainNotSet()){
            list.add(left);
        }
        this.setTerrainFromList(current, list);
    }
    
    /**
     * Sets terrain based on fields on list
     * If list is not empty randomly pick one and set its terrain otherwise set default terrain
     * 
     * @param current current field, its terrain will be set
     * @param list list of neighbouring fields with diffrent terrain, can be empty
     */
    private void setTerrainFromList(Field current, List<Field> list){
        if(list.isEmpty()){
            current.setDefaultTerrain();
        }
        else{
            int number = this.randomizer.random(0, (list.size() - 1));
            Field field = list.get(number);
            String terrain = field.getTerrain();
            current.setTerrain(terrain);
        }
    }
    
}
