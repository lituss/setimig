/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.litus.util;

import com.badlogic.gdx.utils.Timer;

/**
 *
 * @author Carles
 */
public class Retard {
    public Retard (Object objecte,float temps){
        Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                ((IRetard)objecte).canviEstat();
                }
            }, temps);
    }
}
