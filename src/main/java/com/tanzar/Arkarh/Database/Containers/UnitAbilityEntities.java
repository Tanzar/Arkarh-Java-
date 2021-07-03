/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Database.Containers;

import com.tanzar.Arkarh.Containers.HashSetContainer;
import com.tanzar.Arkarh.Database.Entities.Units.UnitAbilityEntity;
import java.util.HashSet;

/**
 *
 * @author spako
 */
public class UnitAbilityEntities  extends HashSetContainer<UnitAbilityEntity>{

    @Override
    protected UnitAbilityEntity[] toArray(HashSet<UnitAbilityEntity> data) {
        UnitAbilityEntity[] array = new UnitAbilityEntity[data.size()];
        data.toArray(array);
        return array;
    }
    
}
