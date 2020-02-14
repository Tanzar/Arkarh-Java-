/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.DAO.abstracts;

import com.tanzar.Arkarh.Containers.FieldContainer;
import com.tanzar.Arkarh.Entities.Field;

/**
 *
 * @author Tanzar
 */
public interface FieldDAOInterface {
    
    public Field getById(Integer id);
    public FieldContainer getByMapId(Integer id);
}
