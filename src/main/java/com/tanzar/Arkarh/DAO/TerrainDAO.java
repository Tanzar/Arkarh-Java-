/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.DAO;

import com.tanzar.Arkarh.Containers.TerrainContainer;
import com.tanzar.Arkarh.DAO.abstracts.DAO;
import com.tanzar.Arkarh.DAO.abstracts.GetById;
import com.tanzar.Arkarh.Entities.Terrain;
import com.tanzar.Arkarh.Exceptions.QueryException;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tanzar
 */
@Repository
public class TerrainDAO extends DAO<Terrain, TerrainContainer> implements GetById<Terrain>{

    public TerrainDAO() {
        super("Terrain");
    }

    @Override
    protected TerrainContainer convertResults(List<Terrain> list) {
        TerrainContainer result = new TerrainContainer();
        for(Terrain terrain: list){
            terrain.fixAsset();
            result.add(terrain);
        }
        return result;
    }

    @Override
    public Terrain getById(Integer id) {
        try {
            TerrainContainer terrain = this.get("where id = " + id);
            if(terrain.size() != 1){
                throw new QueryException("");
            }
            else{
                return terrain.get(0);
            }
        }
        catch (QueryException ex) {
            return new Terrain(-1, "none");
        }
    }
    
    public Terrain getByName(String name){
        try {
            TerrainContainer terrain = this.get("where name = '" + name + "'");
            if(terrain.size() != 1){
                throw new QueryException("");
            }
            else{
                return terrain.get(0);
            }
        }
        catch (QueryException ex) {
            return new Terrain(-1, "none");
        }
    }
    
}
