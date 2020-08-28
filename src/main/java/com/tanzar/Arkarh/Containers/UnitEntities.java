/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Containers;

import com.tanzar.Arkarh.Entities.Unit.UnitEntity;
import java.util.HashSet;

/**
 *
 * @author spako
 */
public class UnitEntities extends HashSetContainer<UnitEntity>{

    @Override
    protected UnitEntity[] toArray(HashSet<UnitEntity> data) {
        UnitEntity[] units = new UnitEntity[data.size()];
        data.toArray(units);
        return units;
    }
    
}
