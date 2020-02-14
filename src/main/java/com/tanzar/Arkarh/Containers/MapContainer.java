/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Containers;

import com.tanzar.Arkarh.Entities.Map;
import java.util.HashSet;

/**
 *
 * @author Tanzar
 */
public class MapContainer extends HashSetContainer<Map>{

    @Override
    public Map[] toArray(HashSet<Map> hs) {
        Map[] resultArray = new Map[hs.size()];
        hs.toArray(resultArray);
        return resultArray;
    }
    
}
