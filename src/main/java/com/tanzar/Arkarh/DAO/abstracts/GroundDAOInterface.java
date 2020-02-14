/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.DAO.abstracts;

import com.tanzar.Arkarh.Entities.Terrain;

/**
 *
 * @author Tanzar
 */
public interface GroundDAOInterface {
    
    public Terrain getById(Integer id);
}
