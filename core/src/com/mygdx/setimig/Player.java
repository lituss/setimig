/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.setimig;

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
    protected int aposta;
    protected int apostaTotal;
    protected Jugada estat=Jugada.CartaTapada;
    protected Croupier croupier;
    protected boolean eliminat = false;
    
    public abstract int aposta();
    
    public abstract Jugada juga(); 

    public abstract Jugada juga(Jugada jugada);

    public Card getCard() {return cards.getCard();}
    
    public Card viewCard() {return cards.viewCard();}

    public Array<Card> returnCards() {return cards.getCards();}        

    public int perdAposta() {
        int aux = apostaTotal;
        aposta = 0;apostaTotal=0;
        return aux;
        }
    public int apostaTotal() {return apostaTotal;}

    public int saldo() {return credit;}

    public int ultimaAposta() {return aposta;}
    
    public void guanyaAposta(int value){
        credit +=value;
        aposta = 0;
        apostaTotal = 0;
    };
    public String nom() {return nom;}
    public float getValueCards(){return cards.getValor();}
    
    public void setApostaTotal(int value){
        apostaTotal = value;
    };
    
    public void liquida() {
        float valor,valorAdversari;
        boolean guanyo;
        int valorPagament=0;
        
        valor = cards.getValor();
        Player player = game.getPlayers().next(this);
        valorAdversari = player.getValueCards();
        if (valorAdversari > 7.5) guanyo = true;
        else
            if (valorAdversari > valor) guanyo = false;
            else guanyo = true;
        
        if (guanyo) credit +=player.perdAposta();
        else
        if (valorAdversari == 7.5)
            valorPagament = 2*player.apostaTotal();
        else
            valorPagament = player.apostaTotal();
      
        if (credit >= valorPagament){
            credit -=valorPagament;
            player.guanyaAposta(valorPagament);
        }
        else
            player.guanyaAposta(credit);
            credit = 0;      
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
