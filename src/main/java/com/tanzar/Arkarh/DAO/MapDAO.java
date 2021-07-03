/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.DAO;

import com.tanzar.Arkarh.DAO.abstracts.DAO;
import com.tanzar.Arkarh.Containers.MapContainer;
import com.tanzar.Arkarh.Entities.Map;
import com.tanzar.Arkarh.Exceptions.QueryException;
import java.util.List;
import com.tanzar.Arkarh.DAO.abstracts.MapDAOInterface;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tanzar
 */
@Repository
public class MapDAO extends DAO<Map, MapContainer> implements MapDAOInterface{

    public MapDAO() {
        super("MapEntity");
    }

    @Override
    protected MapContainer convertResults(List<Map> list) {
        MapContainer result = new MapContainer();
        for(Map map: list){
            result.add(map);
        }
        return result;
    }

    @Override
    public Map getById(Integer id) {
        try {
            MapContainer maps = this.get("where id = " + id);
            if(maps.size() != 1){
                throw new QueryException("");
            }
            else{
                return maps.get(0);
            }
        }
        catch (QueryException ex) {
            return new Map(-1, "", "", -1, -1);
        }
    }

    @Override
    public MapContainer getByName(String name) {
        try {
            return this.get("where name = " + name);
        }
        catch (QueryException ex) {
            return new MapContainer();
        }
    }
    
}
