/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.setimig;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 *
 * @author Carles
 */
public class LoginScreen  implements Screen{
    private Game game;
    private Stage stage;
    TextButton btnLogin;
    TextField user,pass;
    Label lblUser,lblPass;
    
    public LoginScreen (Game g){
        game = g;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        btnLogin = new TextButton("Click me",skin);
        btnLogin.setPosition(100, 300);
        btnLogin.setSize(300,60);
        lblUser = new Label("Usuari : ",skin);
        lblUser.setPosition(50, 200);
        lblUser.setSize(300,40);
        lblPass = new Label("Password",skin);
        user = new TextField("Username",skin);
        user.setPosition(100, 200);
        user.setSize(300, 40);
        
        pass = new TextField("Password",skin);
        pass.setPasswordMode(true);
        pass.setPasswordCharacter('*');
        pass.setPosition(100,50);
        pass.setSize(300,40);
        
        btnLogin.addListener(new ClickListener(){
          @Override 
          public void touchUp(InputEvent e, float x , float y, int point, int button){
              btnLogin.setText("Molt be");
          }  
        });
        
        
        
        stage.addActor(btnLogin);
        stage.addActor(lblUser);
        stage.addActor(user);
        stage.addActor(pass);
    }

    @Override
    public void show() {
        
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(delta);
        stage.draw();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resize(int width, int height) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hide() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
