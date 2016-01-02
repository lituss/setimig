package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.setimig.Setimig;

/**
 * Created by Carles on 1/2/2016.
 */
public class ConnectScreen implements Screen {
    Setimig game;
    Stage stage;
    Table table;
    Skin skin;
    float escala = 2f;

    public ConnectScreen(Setimig g){
        game = g;
        skin = g.skin;
    }

    @Override
    public void show() {
        table = new Table();
        Label titol,subtitol,nom,pass;
        final TextField iNom,iPass;
        TextButton conecta;


        titol = new Label("Set i mig",skin);
        titol.setFontScale(escala);
        subtitol = new Label("Entrada al Lobby",skin);
        subtitol.setFontScale(escala);
        nom = new Label("Nom Jugador",skin);
        nom.setFontScale(escala);
        pass = new Label("contrasenya",skin);
        pass.setFontScale(escala);
        iNom = new TextField(game.prefs.getString("nomMulti","Pakita"),skin);
        iNom.scaleBy(escala);
        iPass = new TextField(game.prefs.getString("passMulti"),skin);
        iPass.setPasswordMode(true);
        iPass.setPasswordCharacter('*');

        conecta = new TextButton("conectar",skin);
        conecta.getLabel().setScale(escala);

        Label status = new Label ("     ",skin);
        status.setScale(escala);




        table.setFillParent(true);
        table.add(titol).top();
        table.row();
        table.add(subtitol).expand().top();
        table.row();
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

}
