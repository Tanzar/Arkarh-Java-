/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities;

import com.tanzar.Arkarh.GamePlay.Combat.Side;
import com.tanzar.Arkarh.GamePlay.Units.Unit;

/**
 *
 * @author spako
 */
public class Attack extends UnitAbility{
    private Unit source;
    private Side targetsSide;

    public Attack(Unit source, Side enemiesSide) {
        super(Trigger.onAction);
        this.source = source;
    }

    @Override
    protected boolean additionalConditions() {
        return true;
    }

    @Override
    protected void onUse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
