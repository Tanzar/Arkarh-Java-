/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Containers;

import com.tanzar.Arkarh.Entities.Obstacle;
import java.util.HashSet;

/**
 *
 * @author Tanzar
 */
public class ObstacleContainer extends HashSetContainer<Obstacle>{

    @Override
    public Obstacle[] toArray(HashSet<Obstacle> data) {
        Obstacle[] obstacles = new Obstacle[data.size()];
        data.toArray(obstacles);
        return obstacles;
    }
    
}
