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
 * Created by Carles on 1/1/2016.
 */
public class Single implements Screen {
    Setimig game;
    Stage stage;
    Table table;
    Skin skin;
    float escala = 2f;

    public Single(Setimig g){
        game = g;
        skin = g.skin;
    }

    @Override
    public void show() {
        table = new Table();
        Label titol,subtitol,nom,num,credit,aposta;
        final TextField iNom,iCredit,iAposta,iNum;
        TextButton inici;
        Slider oNum;

        titol = new Label("Set i mig",skin);
        titol.setFontScale(escala);
        subtitol = new Label("Jugador contra la maquina",skin);
        subtitol.setFontScale(escala);
        nom = new Label("Nom Jugador",skin);
        nom.setFontScale(escala);
        num = new Label("Numero de jugadors mecanics",skin);
        num.setFontScale(escala);
        credit = new Label("Credit de joc",skin);
        credit.setFontScale(escala);
        aposta = new Label("Aposta maxima",skin);
        aposta.setFontScale(escala);
        iNom = new TextField("Pakita",skin);
        iNom.setScale(escala);
        iCredit = new TextField("100",skin);
        iCredit.setScale(escala);
        iAposta = new TextField("10",skin);
        iAposta.setScale(escala);
        oNum = new Slider(1,7,1,false,skin);
        oNum.setScale(escala);
        inici = new TextButton("començar",skin);
        inici.getLabel().setScale(escala);

        iNum = new TextField("1",skin);


        table.setFillParent(true);
        table.add(titol).top();
        table.row();
        table.add(subtitol).expand().top();
        table.row();
        table.add(nom).right().padRight(10);
        table.add(iNom);
        table.row();

       table.add(num).right().padRight(10);
        //table.add(oNum).center();
        table.add(iNum);


        table.row();
        table.add(credit).right().padRight(10);
        table.add(iCredit);
        table.row();
        table.add(aposta).right().padRight(10);
        table.add(iAposta).expand();
        table.row();
        table.add(inici).center().fill().pad(30);
        table.row();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        inici.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                int numPlayers,credit,maximaAposta;
                numPlayers = Integer.valueOf(iNum.getText());
                credit = Integer.valueOf(iCredit.getText());
                maximaAposta = Integer.valueOf(iAposta.getText());

                game.iniciaJoc(Integer.valueOf(iNum.getText()),Integer.valueOf(iCredit.getText()),Integer.valueOf(iAposta.getText()));
            }
        });
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
