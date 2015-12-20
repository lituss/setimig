/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.setimig;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Carles
 */
public class BotPlayer extends Player{
   
    public BotPlayer(Setimig g,int credit){
    game = g;
    this.credit = credit;
    //index = g.getPlayers().add(this);
    nom = "bot"+String.valueOf(index);
    deltay = (int)Card.getAlt()/4;
    cards = new Cards();
    }
    
    @Override
    public int aposta() {
        aposta = 1;
        apostaTotal+=aposta;
        credit-=aposta;
        return aposta;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Jugada juga() {
      Gdx.app.log("sim","Valor : "+cards.getValor());
        if (cards.getValor()<5) estat = Jugada.CartaTapada;
        else 
            if (cards.getValor() > 7.5) estat = Jugada.Passa;
            else estat = Jugada.Planta;
        return estat;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Jugada juga(Jugada jugada,float lapse) {
    
        switch(jugada){
            case CartaDestapada :
                cards.putCard(croupier.getCard(),posiciox,posicioy,720,lapse);
                posicioy-=deltay;
                break;
            case CartaTapada :
                cards.tapa(false);
                cards.putCard (croupier.getHiddenCard(),posiciox,posicioy,720,lapse);
                posicioy-=deltay;
        }
        return juga();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
