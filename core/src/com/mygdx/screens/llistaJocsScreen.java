package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.setimig.Setimig;

import java.util.Random;

/**
 * Created by Carles on 1/3/2016.
 */
public class llistaJocsScreen implements Screen {

    Setimig game;
    Stage stage;
    Table table;
    Skin skin;
    float escala = 2f;
    Array<String> aJocs;
    public llistaJocsScreen (Setimig g){
        game = g;
        skin = g.skin;
    }

    @Override
    public void show() {
        table = new Table();
        Label titol,subtitol,columnesNom,columnesJugadors,columnesCredit,columnesMaximaAposta;

        TextButton crear,unirse;


        titol = new Label("Set i mig",skin);
        titol.setFontScale(escala);
        subtitol = new Label("Entrada al Lobby",skin);
        subtitol.setFontScale(escala);
        columnesNom = new Label("Nom joc",skin);
        columnesNom.setScale(escala);
        columnesJugadors = new Label("Jugadors",skin);
        columnesJugadors.setScale(escala);
        columnesCredit = new Label("Credit",skin);
        columnesCredit.setScale(escala);
        columnesMaximaAposta = new Label("max. Aposta",skin);
        columnesMaximaAposta.setScale(escala);

        List jocs = new List(skin);
        jocs.

        crear = new TextButton("Crear joc",skin);
        crear.getLabel().setScale(escala);
        unirse = new TextButton("entrar",skin);
        unirse.getLabel().setScale(escala);

        Label status = new Label ("     ",skin);
        status.setScale(escala);
        Label blanc = new Label ("     ",skin);
        blanc.setScale(escala);




        table.setFillParent(true);
        table.add(titol).top();
        table.row();
        table.add(subtitol).expand().top();
        table.row();
        HorizontalGroup hg = new HorizontalGroup();
        hg.addActor(columnesNom);
        hg.addActor(columnesJugadors);
        hg.addActor(columnesCredit);
        hg.addActor(columnesMaximaAposta);

        table.add(nom).right().padRight(10);
        table.add(iNom);
        table.row();

        table.add(pass).right().padRight(10);
        //table.add(oNum).center();
        table.add(iPass);

        table.row();
        table.add(conecta).center().fill().pad(30);
        table.row();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        conecta.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                int numPlayers,credit,maximaAposta;

                game.prefs.putString("nomMulti",iNom.getText());
                game.prefs.putString("passMulti",iPass.getText());

                // game.iniciaJoc(Integer.valueOf(iNum.getText()),Integer.valueOf(iCredit.getText()),Integer.valueOf(iAposta.getText()));
            }
        });
        table.row();
        table.add(status).expandX();
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    void omplaArrayProves(int numRows){
        String text="";
        int nom = 10;
        int numPlayers = 1;
        int credit = 5;
        int maxAposta = 5;
        Array<String> noms = new Array<String>();
        for ( int f = 0 ; f < (numRows/10 + 1); f++) noms.addAll("Mateo","Sebastian","Alejandro","Matias","Diego","Samuel","Nicolas","Daniel","Gabriel","Thiago");
        String numeros ="123456789";

        for ( int f = 0 ; f < numRows ; f++){
            text+=noms.get(f);
            int g = nom - noms.get(f).length();
            if (g > 0) for (int h = g ; g < nom ; g++)text+=' ';
            // players
            Random random = new Random();
            text+=" "+String.valueOf(random.nextInt(9))+' ';
            //
        }
}
}
