/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.setimig;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Carles
 */
public abstract class Player {
    protected Setimig game;
    protected int index ;
    protected int posiciox,posicioy;
    protected static int deltay;
    protected Cards cards;
    protected String nom;
    protected int credit;
    protected int aposta=100;
    protected int apostaTotal=1000;
    protected Jugada estat=Jugada.CartaTapada;
    protected Croupier croupier;
    protected boolean eliminat = false;
    public static Stage stage;
    public Marcador marcador;

    protected Stage getStage(){return stage;}



    public abstract int aposta();

    public abstract Jugada juga(); 

    public abstract Jugada juga(Jugada jugada,float lapsse);

    public Card getCard() {return cards.getCard();}
    
    public Card viewCard() {return cards.viewCard();}

    public Array<Card> returnCards() {return cards.getCards();}        

    public int perdAposta() {
        return(marcador.perdAposta());
        }
    public int apostaTotal() {return marcador.getAposta();}

    public int saldo() {return marcador.getSaldo();}

    public int ultimaAposta() {return marcador.getUltimaAposta();}
    
    public void guanyaAposta(int value){
        marcador.guanyaAposta(value);
    };
    public abstract String nom();

    public float getValueCards(){return cards.getValor();}
    

    public Card jugaSorteig(float retard){
        Card card = croupier.getCard();
        cards.putCard(card,posiciox,posicioy,720,3,retard);
        return card;
    }
    
    public void liquida(Player player) {//la banca liquida contra cada jugador
        float valor, valorAdversari;
        boolean guanyo;
        int valorPagament = 0;

        valor = cards.getValor();
        valorAdversari = player.getValueCards();
        player.destapa();
        if (valorAdversari > 7.5) guanyo = true;
        else
            if (valorAdversari > valor) guanyo = false;
            else
                if (valor <= 7.5f) guanyo = true;
                else guanyo = false;

        if (guanyo) marcador.guanyaAposta(player.marcador.perdAposta());
        else {
            if (valorAdversari == 7.5) valorPagament = 2 * player.marcador.getApostaTotal();
            else valorPagament = player.marcador.getApostaTotal();
            if (marcador.getSaldo() < valorPagament) valorPagament = marcador.getSaldo();
            marcador.setSaldo(marcador.getSaldo() - valorPagament);
            player.marcador.guanyaAposta(valorPagament);
        }
    }
    void destapa(){
        cards.tapa(false);
    }

    //public void rebCarta(Card card){
    //    card.marcaNovaPosicio(posiciox, posicioy, 720, 3);
    //    posicioy-=deltay;
    //}
    
    public Jugada getEstat() {return estat;}
    public void setEstat(Jugada estat) {this.estat = estat;}
    public void putCroupier(Croupier croupier) {this.croupier = croupier;}
    public int getPosicioX() {return posiciox;}
    public void setPosicioX(int posiciox) {this.posiciox = posiciox;}
    public int getPosicioY() {return posicioy; }
    public void setPosicioY(int posicioy) {this.posicioy = posicioy;}
}
