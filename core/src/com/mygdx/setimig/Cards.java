/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.setimig;

import com.badlogic.gdx.utils.Array;
import java.util.EnumSet;

/**
 *
 * @author Carles
 */
public class Cards {
    private Array<Card> cards = new Array<Card>();
    private int tipus;
    private float valor = 0;
    private int posx;
    float posy;
    float delta;
    
    public Cards(){}
    
    public Cards(int ncards,int posx,int posy,float delta){
        tipus = ncards;
        this.posx = posx;
        this.posy = posy;
        this.delta = delta;
        genCards();
    }
    
    //public void reset(){
    //    genCards();
    //}
    
    public Card getCard(){return get(false);}
    public Card getHiddenCard(){return get(true);}
    public Array <Card> getCards(){return cards;}
    public void putCard(Card card){
        setValor(card.getNumero()< 10? card.getNumero() : 0.5f); 
        cards.add(card);
    }
    public void putCard(Card card,int posx,int posy, float angle,float temps){
        card.marcaNovaPosicio(posx, posy, angle, temps);
        putCard(card);
    }
    public void popCard(Card card){
        cards.removeIndex(cards.indexOf(card, true));
    }
    public Card viewCard(){return cards.get(0);}
    
    private void genCards(){
        cards.clear();
        switch (tipus){
        case 40:
            for ( Pal pal : EnumSet.allOf(Pal.class))
                for (int f = 1 ; f < 13 ; f++)
                    if (f != 8 && f!= 9){ 
                        cards.add(new Card(pal,f,true,posx,(int)posy));
                        posy+=delta;
                    }
            break;
        }
    }
    
    private Card get(boolean hidden){
        
        if (cards.size == 0) return null;
        
        Card auxCard = cards.first();
        cards.removeValue(auxCard, true);
        if (!hidden) auxCard.tapa(false);
        return auxCard;
    }

    /**
     * @return the valor
     */
    public float getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(float valor) {
        this.valor = valor;
    }
    
    public void tapa(boolean tapada){
        for (Card card : cards) card.tapa(tapada);
    }
}
