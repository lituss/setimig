/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.setimig;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Carles
 */
public class PlayerScreen extends Player implements Screen {
   SpriteBatch batch = new SpriteBatch();
    //private Texture cards;
   //Array<Card> carta = new Array<Card>();
 
   
    
    public PlayerScreen(Setimig g, int credit){
        game = g;
        index = g.getPlayers().add(this);
        g.calculaPos();
        croupier = new Croupier(g);
        deltay = (int)Card.getAlt()/4;
        cards = new Cards();
/*    
        Card aux;
        for ( int f = 1 ; f < 10 ; f++){
         aux = new Card(Pal.Copes,f,true);
         aux.setPosition(50, 80*(f -1));
         carta.add(aux);
        }
*/
    }
    void dinamica(float delta){
        for (Card card : game.dynamicCards.getCards()){
            if (!card.novaPosicio(delta)) game.dynamicCards.popCard(card);
            
        }
    }
    @Override
    public void show() {
        
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(float delta) {
        dinamica(delta);
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (Card c : croupier.getDeck().getCards())c.draw(batch);
        for (Player player : game.getPlayers().getPlayers())
            for(Card c : player.returnCards()) c.draw(batch);
        batch.end();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resize(int width, int height) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hide() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
// --------------------------------------- //
    
    public int aposta() {
        aposta = 1;
        apostaTotal=+aposta;
        credit=-aposta;
        return aposta;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Jugada juga() {
        
        if (cards.getValor()<5) estat = juga(Jugada.CartaTapada);
        else estat = Jugada.Planta;
        return estat;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Jugada juga(Jugada jugada) {
    
        switch(jugada){
            case CartaDestapada :
                cards.putCard(croupier.getCard());
            case CartaTapada :
                cards.tapa(false);
                cards.putCard (croupier.getHiddenCard());
          
                    
                
                if (cards.getValor() > 7.5f) estat = Jugada.Passa;
                else
                    if (cards.getValor() < 5 ) estat = Jugada.CartaTapada;
                        else estat = Jugada.Planta;
                
        }
        return estat;
    }
}
