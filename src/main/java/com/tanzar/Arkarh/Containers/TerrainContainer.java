/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Containers;

import com.tanzar.Arkarh.Entities.Terrain;
import java.util.HashSet;

/**
 *
 * @author Tanzar
 */
public class TerrainContainer extends HashSetContainer<Terrain>{

    @Override
    public Terrain[] toArray(HashSet<Terrain> hs) {
        Terrain[] result = new Terrain[hs.size()];
        hs.toArray(result);
        return result;
    }
    
    public Terrain getById(Integer id){
        boolean found = false;
        int i = 0;
        Terrain[] terrains = toArray();
        while(!found && i < terrains.length){
            Terrain terrain = terrains[i];
            if(id.equals(terrain.getId())){
                return terrain;
            }
            i++;
        }
        return new Terrain(-1, "none");
    }
}
