/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Database.DAO.Abstracts;

/**
 *
 * @author Tanzar
 */
public interface GetById<EntityType> {
    public EntityType getById(Integer Id);
}
