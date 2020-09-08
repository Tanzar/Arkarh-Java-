/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Containers;

import com.tanzar.Arkarh.Entities.Unit.UnitEffectEntity;
import java.util.HashSet;

/**
 *
 * @author spako
 */
public class UnitEffects  extends HashSetContainer<UnitEffectEntity>{

    @Override
    protected UnitEffectEntity[] toArray(HashSet<UnitEffectEntity> data) {
        UnitEffectEntity[] array = new UnitEffectEntity[data.size()];
        data.toArray(array);
        return array;
    }
    
}
