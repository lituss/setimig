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
    private boolean miraCanviEstat = false;
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
    
    private void sortejaBanca(){ // reparteix una carta a cada jugador , la mes alta sera banca
            //deck.reset();
            //deck.getCards().shuffle();
            Card auxCard;
            float delay = 0;
            for ( int f = 0 ; f < 13 ; f++)frequencia[f] = 0;
            for (Player player : auxPlayers) {
                player.jugaSorteig(delay);//(Jugada.CartaDestapada);
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
            for (Player player : auxPlayers) {//torna les cartes al munt
                card = player.getCard();
                card.tapa(true);
                deck.putCard(card, x, 2*y/3, 90, delay);
                delay+=0.5f;
            }
            ultimaCarta = card;
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
            banca = auxPlayers2.get(0); //mou les cartes al jugador que es banca
            auxPlayers2.clear();
            auxPlayers.clear();
            game.estat = Estats.BancaAssignada;
            x = banca.getPosicioX();
            y =  0;//(int)Card.getAmple()/2;
            //y = banca.getPosicioY()+ (int)Card.getAlt()/2+(int)Card.getAmple()/2;
            //deck.mou(banca.posiciox, banca.posicioy + (int)Card.getAlt()/2+(int)Card.getAmple()/2, 3);
            deck.mou(x,y, 3);
            new Retard(this,1);
        }
    }
    
    public void reparteix(){ // recull cartes i prepara per seguent pertida, el primer cop no recull perque
        float lapse = 0.5f;  // ja estan recullides de abans
        ultimaCarta=null;
        Array<Player> aPlayer = players.getPlayers();
        Player player;
        for (int f = 0 ; f < aPlayer.size ; f++) {
            player = aPlayer.get(f);
            Array<Card> aCard = player.returnCards();
            Array<Card> aCard2 = new Array<Card>();
            for (Card card : aCard)aCard2.add(card);
            lapse = 0.5f*(aCard2.size + 1);
            for (Card card: aCard2) {
                player.cards.popCard(card);
                player.setPosicioY(player.getPosicioY() + Player.deltay);
                deck.putCard(card, x, y, 90, 3f, lapse);
                lapse -= 0.5f;
                //ultimaCarta = card;
            }
        }
        torn = banca;
        game.estat = Estats.Reparteix15;
        //if (ultimaCarta != null) ultimaCarta.obs.addObserver(this);
        //else
        new Retard(this,3);
        //reparteix2();
    }
    private void reparteix15(){
        if (!game.isDinamica())game.estat = Estats.Reparteix2;
        new Retard(this,1);
    }
    private void reparteix2(){
        deck.tapa(true);
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
              //torn.aposta();
              game.estat = Estats.Aposta;
        }
        new Retard(this,0.5f);
    }
    public void juga2(){
        torn.aposta();
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
                //torn.perdAposta();
            case Planta :
                game.estat = Estats.Juga;
                break;
            } 
        new Retard(this,1);
    }
    
    public void bancaJuga(){
        Jugada jugada;
        
        jugada = banca.juga();
        if (jugada == Jugada.CartaDestapada| jugada == Jugada.CartaTapada) {
            banca.juga(Jugada.CartaDestapada, 1);
            game.estat = Estats.BancaJuga;
        }
        else {
            torn = players.next(banca);
            game.estat = Estats.BancaLiquida;
        }
        new Retard(this,1);
        }

    public void bancaLiquida(){
        if (torn == banca){
            Player novaBanca = null,auxPlayer = banca;
            while(true){
                if (auxPlayer.getValueCards() == 7.5f) break;
                auxPlayer = players.next(auxPlayer);
                if (auxPlayer == banca) break;
            }
            if ((auxPlayer.getValueCards() == 7.5f) && auxPlayer != banca){
                auxPlayers2.add(auxPlayer);
                game.estat = Estats.EvaluaBanca;
            }
            else
                // recullir cartes
                game.estat = Estats.BancaAssignada;
        }
        else{
            banca.liquida(torn);
            torn = players.next(torn);
        }
        new Retard(this,1);
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
                    game.estat = Estats.EvaluaBanca;
                    new Retard (this,0.1f);
                    break;
                case Reparteix2 :
                    new Retard (this,1f);
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
            case EvaluaBanca :
                evaluaBanca();
                break;
            case BancaAssignada :
                Gdx.app.log("SIM","callback per repartir");
                if (!game.isDinamica()) reparteix();
                else new Retard(this,3);
                break;
            case Reparteix15:
                reparteix15();
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
                bancaJuga();
                break;
            case BancaJuga :
                bancaJuga();
                break;
            case BancaLiquida :
                bancaLiquida();

        }
        //new Retard(this,1);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
