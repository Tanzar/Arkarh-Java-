/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.DAO;

import com.tanzar.Arkarh.Containers.ObstacleContainer;
import com.tanzar.Arkarh.DAO.abstracts.DAO;
import com.tanzar.Arkarh.DAO.abstracts.GetById;
import com.tanzar.Arkarh.Entities.Obstacle;
import com.tanzar.Arkarh.Exceptions.QueryException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tanzar
 */
@Repository
public class ObstacleDAO extends DAO<Obstacle, ObstacleContainer> implements GetById<Obstacle>{

    public ObstacleDAO() {
        super("Obstacle");
    }

    @Override
    protected ObstacleContainer convertResults(List<Obstacle> list) {
        ObstacleContainer obstacles = new ObstacleContainer();
        for(Obstacle item : list){
            obstacles.add(item);
        }
        return obstacles;
    }

    @Override
    public Obstacle getById(Integer Id) {
        try {
            ObstacleContainer obstacles = this.get("where id = " + Id);
            if(obstacles.size() != 1){
                throw new QueryException("");
            }
            else{
                return obstacles.get(0);
                
            }
        }
        catch (QueryException ex) {
            return new Obstacle();
        }
    }
    
}
