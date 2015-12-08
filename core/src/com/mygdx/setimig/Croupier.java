/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.setimig;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import static java.lang.Thread.yield;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carles
 */
public class Croupier {
    private Setimig game;
    private Players players;
    private Player banca,torn;
    private Cards deck;
    private int x,y;
    float delta;
    
    public Croupier(Setimig g){
        
    game = g;
    players = g.getPlayers();
    players.putCroupier(this);
    x = Gdx.graphics.getWidth() / 2;
    y = Gdx.graphics.getHeight()/2;
    delta = .1f;
    deck = new Cards(40,x,y,delta);
    banca = null;
    game.estat = Estats.Inici;
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
            for ( int f = 0 ; f < 13 ; f++)frequencia[f] = 0;
            for (Player player : auxPlayers) player.juga(Jugada.CartaDestapada);
            game.estat = Estats.SorteigBanca;
    }
    
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
            for (Player player : auxPlayers) {
                Card card = player.getCard();
                deck.putCard(card, x, y, 0, 3);
                card.tapa(true);  
            }
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
        }
    }
    
    public void reparteix(){
        if (players.getActiveSize()==1){
            System.out.println("Guanya : "+players.getPlayers().get(0).nom());
            game.estat = Estats.FinalTimba;
        }
        else{
            torn = banca;
            //deck.reset();
            // repartim  
            do {
                torn = players.next(torn);
                torn.juga(Jugada.CartaTapada);
            }
            while(torn != banca);
            game.estat = Estats.CartesRepartides;
        }
    }
    
    public void juga(){
    
        torn = players.next(torn);
        if (torn == banca){
            // banca destapa
            game.estat = Estats.BancaDestapa;
        }
        else {
              torn.aposta();
              game.estat = Estats.Aposta;
        }
    }
    public void juga2(){
        Jugada jugada;
        
        jugada = torn.juga();
        if (jugada == Jugada.CartaDestapada || jugada == Jugada.CartaTapada)
            game.estat = Estats.Juga2;
        else
            if (jugada == Jugada.Passa){
                torn.perdAposta();
                game.estat = Estats.Juga;
            }   
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
}
