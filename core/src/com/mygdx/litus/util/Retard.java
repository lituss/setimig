/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.litus.util;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

/**
 *
 * @author Carles
 */
public class Retard {

    public class Duples {
        public IRetard objecte;
        public long temps;
        public void trigger(){objecte.canviEstat();}
        public Duples (Object objecte,float temps){
            this.objecte = (IRetard)objecte;
            this.temps = ((long)(1000000000*temps)) + TimeUtils.nanoTime();
        }
    }

    static protected Array<Duples> objectes = new Array <Duples>();
    static long temps;

    public Retard (Object objecte,float temps){
       objectes.add(new Duples(objecte,temps));
    }
    public static void evalua(){
        temps = TimeUtils.nanoTime();
        for (Duples duples : objectes)
            if (duples.temps < temps){
                duples.trigger();
                objectes.removeIndex(objectes.indexOf(duples,true));
            }
    }
}
