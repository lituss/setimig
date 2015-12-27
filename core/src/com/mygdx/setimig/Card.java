/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.setimig;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.litus.util.Observable;

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
    static float specialX,specialY;
    static Texture marca;
    public Observable obs;
    protected long timer;
 
    
    public static void posaGame(Setimig g){game = g;}
    
    public Card(Pal pal,int numero,boolean tapada,int posx,int posy){
        super(cards);
        float aux,x,y;
        obs = new Observable();
        /*if (nCards == 0){
            cards = new Texture(Gdx.files.internal("BarajaE.jpg"));
        }*/
        id = nCards++;
        this.pal = pal;
        this.numero = numero;
        this.tapada = tapada;
        imatge();
        setPosition(posx,posy);
        setRotation(90);
        Drawables.push(this);
        if (id == 1){
            Pixmap pixmap = new Pixmap((int)offsetX,(int)offsetY,Format.RGBA8888);
            pixmap.setColor(0.39f,0.39f,0.39f,0.5f);
            pixmap.fillRectangle(0, 0, (int)offsetX,(int)offsetY);
            marca = new Texture (pixmap);
            pixmap.dispose();
            }
    }
    public void tapa(boolean tapada){
        if (this.tapada != tapada){
            this.tapada = tapada;
            imatge();
        }
    }
    private  void imatge(){
        float aux,x,y;
        aux = (tapada ? 69 : getPal().valor()*12+(getNumero() - 1));
        x = (aux % 10)*offsetX;
        y = ((int)(aux / 10)) * offsetY;
        //Gdx.app.log("x,y : ", String.valueOf(x)+ " "+String.valueOf(y));
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
    public boolean esViu(){return timer < TimeUtils.nanoTime() ? true : false; }
    private boolean dinamica = false;
    private float xf,yf,anglef,vx,vy,w,dt; // x final y final velocidad x velocidad y velocitat de rotacio i temps per arribar al desti dt
    static private final float precisio = 3.f;

    public  synchronized boolean novaPosicio(float delta){
		float x = getX(),y=getY();
		if (dinamica && esViu()){
			if ((Math.abs(x - xf) < precisio) && (Math.abs(y - yf) < precisio)){
				dinamica = false;
				setRotation(anglef);
				setPosition(xf, yf);
                                if (this.numero == 10)
                                    Gdx.app.log("","activa debug");
                                obs.setChanged();
                                obs.notifyObservers();
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
                //echo();
		return dinamica;	
	}
	public void marcaNovaPosicio(float x, float y , float anglef, float temps, float timer, boolean drawable){
		float angle = getRotation();
		//temps*=10;
		xf = x;
		yf = y;
		vx = (xf - getX())/temps;
		vy = (yf - getY())/temps;
		this.anglef = anglef;
		w = (anglef - angle)/temps;
        this.timer = TimeUtils.nanoTime()+((long)(timer*1000000000));
		
		dinamica = true;
        game.dynamicCards.putCard(this);
        if (drawable )Drawables.pushDemorat(this);
                //game.dinamica = true;
		Gdx.app.log("litus", "x,y,xf,yf,vx,vy :: temps : "+getNumero()+" "+getPal()+" -> "+getX()+","+getY()+","+xf+","+yf+","+vx+","+vy+" :: "+temps);
	}
        public void echo(){
            System.out.print("id : x, y -> "+id+" : "+getX()+" , "+getY()+"\n");
        }
        
        
        
        public void specialMark(){
           
        float aux;
        aux = getPal().valor()*12+(getNumero() - 1);
        specialX = (aux % 10)*offsetX;
        specialY = ((int)(aux / 10)) * offsetY;
        Drawables.setSpecial(this);
        
        }
        //Gdx.app.log("x,y : ", String.valueOf(x)+ " "+String.valueOf(y));
        public void specialDraw(SpriteBatch batch){
        setRegion(Math.round(specialX),Math.round(specialY),(int)offsetX,(int)offsetY);
        draw(batch);
        if (tapada && getX() == xf && getY() == yf) batch.draw(marca, this.getX(), this.getY());
      
        //setSize((int)offsetX,(int)offsetY);
        //setOriginCenter();
        }

    /**
     * @return the pal
     */
    public Pal getPal() {
        return pal;
    }
}
