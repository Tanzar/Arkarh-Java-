/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Database.DAO.Abstracts;

import com.tanzar.Arkarh.Containers.MapContainer;
import com.tanzar.Arkarh.Entities.Map;

/**
 *
 * @author Tanzar
 */
public interface MapDAOInterface {
    
    public Map getById(Integer id);
    public MapContainer getByName(String name);
}
