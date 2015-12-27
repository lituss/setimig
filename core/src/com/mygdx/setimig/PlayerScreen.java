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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.litus.util.Retard;

/**
 *
 * @author Carles
 */
public class PlayerScreen extends Player implements Screen {
   SpriteBatch batch = new SpriteBatch();
    //private Texture cards;
   //Array<Card> carta = new Array<Card>();
  //boolean timerUp = false;
  private Card  specialCard;
   
    public String nom(){return nom;}
    public PlayerScreen(Setimig g, int credit){
        game = g;
        nom = "Carles";
        cards = new Cards();
        index = g.getPlayers().add(this);
        g.calculaPos();
        croupier = new Croupier(g);
        deltay = (int)Card.getAlt()/4;

/*    
        Card aux;
        for ( int f = 1 ; f < 10 ; f++){
         aux = new Card(Pal.Copes,f,true);
         aux.setPosition(50, 80*(f -1));
         carta.add(aux);
        }
*/
    }
    synchronized void dinamica(float delta){
        boolean dinamica = false;
        for (Card card : game.dynamicCards.getCards()){
            if (!card.novaPosicio(delta)) game.dynamicCards.popCard(card);
            else{ 
                dinamica = true;
                if (game.estat == Estats.BancaAssignada) Gdx.app.log("sim","Dinamica true : "+card.getNumero()+" - "+card.getPal()+ "( "+card.getX()+" , "+card.getY());
            }
        }
        game.setDinamica(dinamica);
        /*if (!dinamica && !timerUp){
            timerUp = true;
            Timer.schedule(new Task(){
                @Override
                public void run() {
                canviEstat();
                }
            }, 3);
        }*/
    }
        private void canviEstat(){
            Gdx.app.log("sim", game.estat.toString());
            //timerUp = false;
            switch (game.estat){
                case Inici : 
                    game.estat = Estats.PreSorteigBanca;
                    croupier.preSorteigBanca();
                break;
                /*case SorteigBanca : 
                    //game.estat = Estats.FiSorteigBanca;
                    croupier.preGeneraLListaFinalistes();
                    break;*/
                case GeneraLListaFinalistes :
                    croupier.generaLListaFinalistes();
                    break;
                case LListaFinalistes:
                    game.estat = Estats.FiLListaFinalistes;
                    croupier.evaluaBanca();
                    break;
                case BancaAssignada:
                    croupier.reparteix();
                    break;
                case CartesRepartides:
                    croupier.juga();
                    break;
                case Aposta :
                    croupier.juga2();
                    break;
                case Juga :
                    croupier.juga();
                    break;
                case Juga2 :
                    croupier.juga2();
                    break;
                case BancaDestapa :
                case BancaJuga :
                    croupier.bancaJuga();
                    break;
                case BancaLiquida :
                    croupier.bancaLiquida();
                    
                    
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
        stage.act(delta);
        batch.begin();
       /* for (Card c : croupier.getDeck().getCards())c.draw(batch);
        for (Player player : game.getPlayers().getPlayers())
            for(Card c : player.returnCards()) {
                c.draw(batch);
                //if (c == specialCard) c.specialDraw(batch);
                //else
                  //  c.draw(batch);
                //c.echo();
            }*/
        Drawables.draw(batch);
       //if (specialCard != null)specialCard.specialDraw(batch);
        batch.end();
        stage.draw();
        Retard.evalua();
        Drawables.evalua();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resize(int width, int height) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pause() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return(marcador.aposta(1));
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Jugada juga() {
        if (cards.getValor()<5) estat = Jugada.CartaTapada;
        else 
            if (cards.getValor() > 7.5) estat = Jugada.Planta;
            else estat = Jugada.Planta;
        return estat;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Jugada juga(Jugada jugada,float lapse) {
    Card auxCard;
    
        switch(jugada){
            case CartaDestapada :
                cards.putCard(croupier.getCard(),posiciox,posicioy,720,lapse);
                posicioy-=deltay;
                break;
            case CartaTapada :
                cards.tapa(false);
                specialCard = croupier.getHiddenCard();
                specialCard.specialMark();
                cards.putCard (specialCard,posiciox,posicioy,720,lapse);
                posicioy-=deltay;
                    
                if (cards.getValor() > 7.5f) estat = Jugada.Passa;
                else
                    if (cards.getValor() < 5 ) estat = Jugada.CartaTapada;
                        else estat = Jugada.Planta;
                break;
        }
        return estat;
    }
}
