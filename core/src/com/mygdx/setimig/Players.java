/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.setimig;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Carles
 */
public class Players {
    private Array <Player> players = new Array<Player>();
    private Setimig g;
    
    public Players(Setimig g){this.g = g;}
    
    public Player next(Player player){
        int index = players.indexOf(player, true)+1;
        if ( index >= players.size) index = 0;
        return players.get(index);
    }
    
    public int add(Player player){ 
        players.add(player);
        return players.indexOf(player, true);
    }

    //public int getSize(){return players.size;}
    public int getActiveSize(){
        int contador = 0;
        for (Player auxPlayer : players) if (auxPlayer.marcador.getSaldo()> 0) contador++;
            //if (auxPlayer.getEstat() == Jugada.CartaDestapada || auxPlayer.getEstat() == Jugada.CartaTapada)
              //  contador ++;
        return contador;
    }
    public Array<Player> getPlayers(){return players;}
    
    public void putCroupier(Croupier croupier){
        for (Player  player : players) player.putCroupier(croupier);
    }
    
    public void calculaPos(){
        Player auxPlayer;
        int offx = (int)Card.getAmple();
        int offy = (int)Card.getAlt();
        int auxX = Gdx.graphics.getWidth()/(players.size + 1);
        for (int f = 0 ; f < players.size ; f++){
            auxPlayer = players.get(f);
            auxPlayer.setPosicioX(auxX*(f )+ auxX /2);
            auxPlayer.setPosicioY((int)(Gdx.graphics.getHeight() - 1.1f*offx - offy));
            auxPlayer.index = f;
            auxPlayer.marcador = new Marcador(auxX*f+auxX/2,Gdx.graphics.getHeight()-0.1f*offx -offy/2,auxX,offy/2,auxPlayer.nom(),g.amount,Player.stage,auxPlayer.game.skin);
        }
            
        
        
    }
}
