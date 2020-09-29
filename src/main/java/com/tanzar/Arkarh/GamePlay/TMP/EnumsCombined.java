/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.TMP;

import com.tanzar.Arkarh.GamePlay.Units.TargetsSelection;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Role;

/**
 *
 * @author Tanzar
 */
public class EnumsCombined {
    private Fraction fractions[];
    private Role roles[];
    private Tier tiers[];
    private Category categories[];
    private EffectSchool schools[];
    private TargetsSelection attacks[];
    
    public EnumsCombined(){
        this.fractions = Fraction.values();
        this.roles = Role.values();
        this.tiers = Tier.values();
        this.categories = Category.values();
        this.schools = EffectSchool.values();
        this.attacks = TargetsSelection.values();
    }
}
