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
 * Created by Carles on 12/31/2015.
 */
public class InitialScreen implements Screen {
    private Setimig game;
    private Stage stage;
    private Table table;
    private Skin skin;
    private Screen nextWindow;
   // private Label titol;
    float escala = 3f;
    private TextButton crearSingle,crearMulti;

    public InitialScreen(Setimig g){
        game = g;
        skin = g.skin;
        nextWindow = this;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Label titol = new Label("Set i mig",skin);
        titol.setFontScale(escala);
        System.out.println(titol.getFontScaleX());
        System.out.println(titol.getFontScaleY());
        //titol.setFontScale();
        //titol.setFillParent(true);


        crearSingle = new TextButton("Crear joc contra el movil",skin);
        crearSingle.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                game.canviaPantalla(new Single(game));
            }
        });
        crearSingle.getLabel().setFontScale(escala);

        crearMulti = new TextButton("Jugar en xarxa",skin);
        crearMulti.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                //crearMulti.setText("Ups, encara no rula! ");
                game.canviaPantalla(new ConnectScreen(game));
            }
        });
        crearMulti.getLabel().setFontScale(escala);

        table = new Table();
        table.debugAll();
        table.setFillParent(true);
        table.add(titol).expandY().top();
        table.row();
        table.add(crearSingle).expandY();
        table.row();
        table.add(crearMulti).expandY();
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
    stage.dispose();
    }
}
