/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.setimig;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.litus.util.IObserver;
import com.mygdx.litus.util.IRetard;
import com.mygdx.litus.util.Observable;
import com.mygdx.litus.util.Retard;
import static java.lang.Thread.yield;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carles
 */
public class Croupier implements IObserver,IRetard {
    private Setimig game;
    private Players players;
    private Player banca,torn;
    private Cards deck;
    private int x,y;
    float delta;
    private Card ultimaCarta=null;
    public Croupier(Setimig g){
        
    game = g;
    players = g.getPlayers();
    players.putCroupier(this);
    x = Gdx.graphics.getWidth() / 2;
    y = Gdx.graphics.getHeight()/2;
    delta = .1f;
    deck = new Cards(40,x,2*y/3,delta);
    banca = null;
    game.estat = Estats.Inici;
    new Retard(this,1);
    }
    
    public Card getHiddenCard(){
        return deck.getHiddenCard();
    }
    public Card getCard(){
        return deck.getCard();
    }
    public Cards getDeck(){
        return deck;
    }
    
    Array <Player> auxPlayers = new Array<Player>();
    Array <Player> auxPlayers2 = new Array<Player>();
    int valor = 0,maxim = 0;
    int frequencia[]={0,0,0,0,0,0,0,0,0,0,0,0,0};

    public void preSorteigBanca(){
        for (Player player : players.getPlayers()) auxPlayers.add(player);
        sortejaBanca();
    }
    
    private void sortejaBanca(){
            //deck.reset();
            //deck.getCards().shuffle();
            Card auxCard;
            float delay = 0;
            for ( int f = 0 ; f < 13 ; f++)frequencia[f] = 0;
            for (Player player : auxPlayers) {
                Timer.schedule(new Task(){
                    @Override
                    public void run(){
                      player.jugaSorteig();//(Jugada.CartaDestapada);  
                    }
                },delay);
                delay+=0.5f;
            }
            game.estat = Estats.SorteigBanca;
            new Retard(this,1);
    }
    private void postSortejaBanca(){
        Gdx.app.log("sim","passa per postsorteig");
            ultimaCarta = players.getPlayers().peek().viewCard();
            
            if (ultimaCarta == null) new Retard(this,0.5f);
            else{
                ultimaCarta.obs.addObserver(this);//croupier.preGeneraLListaFinalistes();
                game.estat = Estats.PostSorteigBanca;
            }
            //new Retard(this,3);
            //preGeneraLListaFinalistes();
    }
    
    
        /*public void preGeneraLListaFinalistes(){
            Card c;
            
            c = auxPlayers.peek().getCard();
            if (c != null) {
                c.obs.addObserver(this);
                return;
            }
            else
                return;
        }*/
          public void generaLListaFinalistes(){   
            for (Player player : auxPlayers) 
                frequencia[player.viewCard().getNumero()]++;
            maxim = 0;
            for (int f = 12; f > 0 ; f--){
                if (frequencia[f] > 0){ 
                    maxim = f;
                    break;
                }
            }
            for (Player player : auxPlayers)
                if (player.viewCard().getNumero() == maxim)
                    auxPlayers2.add(player);
            float delay=0.5f;
            Card card=null;
            for (Player player : auxPlayers) {
                card = player.getCard();
                card.tapa(true);
                deck.putCard(card, x, 2*y/3, 90, delay);
                delay+=0.5f;
            }
            card.obs.addObserver(this);
            game.estat = Estats.LListaFinalistes;
    }
    
    public void evaluaBanca(){
        if (auxPlayers2.size > 1){ // hi ha empat, sha de tornar a fer
            auxPlayers.clear();
            auxPlayers.addAll(auxPlayers2);
            sortejaBanca();
        }
        else 
        {
            banca = auxPlayers2.get(0);
            auxPlayers2.clear();
            auxPlayers.clear();
            game.estat = Estats.BancaAssignada;
            deck.mou(banca.posiciox, banca.posicioy + (int)Card.getAlt()/2+(int)Card.getAmple()/2, 3);
            new Retard(this,1);
        }
    }
    
    public void reparteix(){
        float lapse = 0.5f;
        for (Player player : players.getPlayers())
            for (Card card : player.returnCards()){
                player.cards.popCard(card);
                deck.putCard(card,x,y,0,3f);
            }
        torn = banca;
        game.estat = Estats.Reparteix2;
        reparteix2();
    }
    
    private void reparteix2(){
        if (players.getActiveSize()==1){
            System.out.println("Guanya : "+players.getPlayers().get(0).nom());
            game.estat = Estats.FinalTimba;
        }
        else{
            torn = players.next(torn);
            torn.juga(Jugada.CartaTapada,3);
            if (torn == banca)
                game.estat = Estats.CartesRepartides;
            new Retard(this,0.5f);   
            }
        }
    
    public void juga(){
    
        if (game.isDinamica()) {
            new Retard(this,1);
            return;
        }
        torn = players.next(torn);
        if (torn == banca){
            // banca destapa
            banca.destapa();
            //banca.juga(Jugada.CartaDestapada);
            game.estat = Estats.BancaDestapa;
        }
        else {
              torn.aposta();
              game.estat = Estats.Aposta;
        }
        new Retard(this,0.5f);
    }
    public void juga2(){
        torn.juga(torn.juga(),3);
        game.estat = Estats.Juga2;
        new Retard(this,0.5f);
    }
    public void juga3(){
        Jugada jugada;
        
        if (game.isDinamica()) {
            new Retard(this,1);
            return;
        }
        jugada = torn.juga();
        switch (jugada){
            case CartaDestapada :
            case CartaTapada :
                game.estat = Estats.Aposta;
                break;
            case Passa :
                torn.destapa();
                torn.perdAposta();
            case Planta :
                game.estat = Estats.Juga;
                break;
            } 
        new Retard(this,1);
    }
    
    public void bancaJuga(){
        Jugada jugada;
        
        
        jugada = banca.juga();
        if (jugada == Jugada.CartaDestapada)
            game.estat = Estats.BancaJuga;
        else {
            torn = players.next(banca);
            game.estat = Estats.BancaLiquida;
        }
        }

    public void bancaLiquida(){
        if (torn == banca){
            // recullir cartes
            game.estat = Estats.BancaAssignada;
        }
        else{
            banca.liquida(torn);
            torn = players.next(torn);
        }
    
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == ultimaCarta.obs){
            Gdx.app.log("sim", "callback carta quieta : "+game.estat);
            ultimaCarta.obs.deleteObserver(this);
            switch (game.estat){
                case PostSorteigBanca :
                    game.estat = Estats.PreGeneraLListaFinalistes;
                    new Retard(this,3);
                    break;
                case LListaFinalistes :
                    evaluaBanca();
                    break;
            }
            //game.estat = Estats.GeneraLListaFinalistes;
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void canviEstat() {
        switch (game.estat){
            case Inici :
            //    game.estat = Estats.PreSorteigBanca;
                preSorteigBanca();
                break;
            case SorteigBanca : 
                postSortejaBanca();
                break;
            case PreGeneraLListaFinalistes :
                generaLListaFinalistes();
                break;
            case BancaAssignada :
                Gdx.app.log("SIM","callback per repartir");
                if (!game.isDinamica()) reparteix();
                else new Retard(this,3);
                break;
            case Reparteix2 :
                reparteix2();
                break;
            case CartesRepartides:
                juga();
                break;
            case Aposta :
                juga2();
                break;
            case Juga2 :
                juga3();
                break;
            case Juga :
                juga();
                break;
            case BancaDestapa :
            case BancaJuga :
                bancaJuga();
                break;
            case BancaLiquida :
                bancaLiquida();
                
        }   
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
