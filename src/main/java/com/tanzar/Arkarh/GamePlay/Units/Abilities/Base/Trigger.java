/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities.Base;

/**
 *
 * WARNING!!!
 * DONT MAKE ABILITIES WITH ON DEATH TRIGGER THAT DEAL DAMAGE IT WILL BE BUGGY
 * @author spako
 */
public enum Trigger {
    onAction,
    onDeath,
    onEntry,
    onDamageTaken;
}
