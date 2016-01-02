package com.mygdx.setimig;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.screens.InitialScreen;

/*public class setimig extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
*/
    public class Setimig extends Game {
        SpriteBatch batch;
        Texture img;
            TextureRegion reg;
        private Players players;
            // globals
            public int numPlayers;
            public int maxAmount;
            public int amount;
            public Cards dynamicCards = new Cards();
            private boolean dinamica = false;
            public Estats estat;
            public Skin skin;
            private Screen currentScreen,nextScreen;
            public Preferences prefs;

            // end globals

            public void addPlayer(Player player){
                players.add(player);
            }
            public void calculaPos(){players.calculaPos();}
            public Players getPlayers(){return players;}
            public int maxAmount(){return maxAmount;}
            public void canviaPantalla(Screen newScreen){nextScreen = newScreen;}
            public void iniciaJoc(int numPlayers,int credit,int apostaMaxima){
                players = new Players(this);
                Card.posaGame(this);
                for ( int f = 0 ; f < numPlayers ; f++) players.add(new BotPlayer(this,credit,apostaMaxima));
                Player player = new PlayerScreen(this,credit,apostaMaxima);
                canviaPantalla((PlayerScreen)player);
            }

            @Override
        public void create () {
                    Player player;
                    numPlayers = 8;
                    maxAmount = 10;
                    amount = 100;
                    estat = Estats.Inici;
                    prefs = Gdx.app.getPreferences("Setimig");
                    Player.stage = new Stage();
                    this.skin = new Skin(Gdx.files.internal("uiskin.json"));
                    currentScreen = new InitialScreen(this);
                    nextScreen = currentScreen;
                    setScreen(new InitialScreen(this));
                    // aixo es fara desde altres finestres
                /*
                    players = new Players(this);
                    Card.posaGame(this);
                    for ( int f = 0 ; f < numPlayers - 1; f++) players.add(new BotPlayer(this,amount));
                    player = new PlayerScreen(this,amount);
                    this.setScreen((PlayerScreen)player);
                    */
                    //players.add(player);

            //batch = new SpriteBatch();
            //img = new Texture("badlogic.jpg");
                    //this.setScreen(new LoginScreen(this));

                    /*
                    img = new Texture(Gdx.files.internal("BarajaE5.png"));
                    int dx,dy,x,y;
                    dx = (int)(750f/10f);//img.getWidth()/10f;
                    dy = (int)(808f/7f);//img.getHeight()/6f;
                    reg = new TextureRegion(img,0,0,dx,dy);
    */
        }

        @Override
        public void render () {
                /*
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            batch.draw(reg, 0, 0);
            batch.end();
                */
                if (currentScreen != nextScreen) {
                    currentScreen = nextScreen;
                    setScreen(nextScreen);
                }
                super.render();
        }

        /**
         * @return the dinamica
         */
        public boolean isDinamica() {
            return dinamica;
        }

        /**
         * @param dinamica the dinamica to set
         */
        public void setDinamica(boolean dinamica) {
            if (this.dinamica != dinamica) Gdx.app.log("", "canvi dinamica : "+dinamica);
            this.dinamica = dinamica;
        }
    }

