/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities;

import com.tanzar.Arkarh.Entities.Unit.UnitEffectEntity;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Side;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.Trigger;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.UnitEffectGroup;
import com.tanzar.Arkarh.GamePlay.Units.Units;

/**
 *
 * @author Tanzar
 */
public class Heal extends UnitAbility{

    public Heal(UnitEffectEntity entity) {
        super(entity, Trigger.onAction);
    }

    @Override
    protected boolean additionalConditions(Unit source) {
        return source.isAlive();
    }

    @Override
    protected Units setupTargets(Unit source, Battlefield battlefield) {
        Side side = battlefield.getSide(source);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void onUse(Unit source, Units targets) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String formJson() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
