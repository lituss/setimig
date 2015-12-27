package com.mygdx.setimig;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Carles on 12/25/2015.
 */
public class Drawables implements Disposable {
    protected static Array<Card> drawables;
    protected static Array<Card> demorats;
    protected static Card special;

    static {
        special = null;
        drawables = new Array<Card>();
        demorats = new Array<Card>();
    }
    //private static ArrayMap<Sprite,Integer> indexDrawable = new ArrayMap<Sprite,Integer>();

    public synchronized static void push(Card objecte){
        Integer index = drawables.indexOf(objecte,true);
        if (index != -1) drawables.removeIndex(index);
        drawables.add(objecte);
    }

    public synchronized static void pushDemorat(Card objecte){
        Integer index = demorats.indexOf(objecte,true);
        if (index != -1) demorats.removeIndex(index);
        demorats.add(objecte);
    }
    public static void evalua(){
        for (Card aux : demorats)
            if (aux.esViu()){
                push(aux);
                demorats.removeValue(aux,true);
            }

    }

    public static void draw(SpriteBatch batch){
        for (Card aux : drawables)
            if (aux ==getSpecial())aux.specialDraw(batch);
            else aux.draw(batch);
    }

    public static Card getSpecial() {
        return special;
    }

    public static void setSpecial(Card special) {
        Drawables.special = special;
    }

    @Override
    public  void dispose() {
        drawables.clear();
    }
}
