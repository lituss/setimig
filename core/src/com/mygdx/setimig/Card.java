/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.setimig;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author Carles
 */
public class Card extends Sprite{
    private static Texture cards = new Texture(Gdx.files.internal("BarajaE5.png"));
    private static int nCards = 0;
    private static float offsetY = 808f/7.0f;
    private static float offsetX = 75f;
    private Pal pal;
    private int id = 0;
    private int numero;
    private boolean tapada;
    private static Setimig game;
    
    public static void posaGame(Setimig g){game = g;}
    
    public Card(Pal pal,int numero,boolean tapada,int posx,int posy){
        super(cards);
        
        float aux,x,y;
        
        /*if (nCards == 0){
            cards = new Texture(Gdx.files.internal("BarajaE.jpg"));
        }*/
        id = nCards++;
        this.pal = pal;
        this.numero = numero;
        this.tapada = tapada;
        imatge();
        setPosition(posx,posy);
    }
    public void tapa(boolean tapada){
        if (this.tapada != tapada){
            this.tapada = tapada;
            imatge();
        }
    }
    private  void imatge(){
        float aux,x,y;
        aux = (tapada ? 69 : pal.valor()*12+(getNumero() - 1));
        x = (aux % 10)*offsetX;
        y = ((int)(aux / 10)) * offsetY;
        Gdx.app.log("x,y : ", String.valueOf(x)+ " "+String.valueOf(y));
        setRegion(Math.round(x),Math.round(y),(int)offsetX,(int)offsetY);
        //setRegion(0,300,offsetX,offsetY);
        //setPosition(0,0);
        setSize((int)offsetX,(int)offsetY);
        setOriginCenter();
        //setRotation(90.f);
        
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public static float getAmple(){return offsetX;}
    public static float getAlt(){return offsetY;}
    
    // dinamica

    private boolean dinamica = false;
    private float xf,yf,anglef,vx,vy,w,dt; // x final y final velocidad x velocidad y velocitat de rotacio i temps per arribar al desti dt
    static private final float precisio = 4.f;

    public boolean novaPosicio(float delta){
		float x = getX(),y=getY();
		if (dinamica){
			if ((Math.abs(x - xf) < precisio) && (Math.abs(y - yf) < precisio)){
				dinamica = false;
				setRotation(anglef);
				setPosition(xf, yf);
			}
			else{
				x+=vx*delta;
				y+=vy*delta;
				rotate(w*delta);
				setPosition(x, y);
			}
			//info();
			//return true;
		}
                echo();
		return dinamica;	
	}
	public void marcaNovaPosicio(float x, float y , float anglef, float temps){
		float angle = getRotation();
		//temps*=10;
		xf = x;
		yf = y;
		vx = (xf - getX())/temps;
		vy = (yf - getY())/temps;
		this.anglef = anglef;
		w = (anglef - angle)/temps;
		
		dinamica = true;
                game.dynamicCards.putCard(this);
                game.dinamica = true;
		//Gdx.app.log("litus", "x,y,xf,yf,vx,vy : "+getX()+","+getY()+","+xf+","+yf+","+vx+","+vy);
	}
        public void echo(){
            System.out.print("id : x, y -> "+id+" : "+getX()+" , "+getY()+"\n");
        }
}
