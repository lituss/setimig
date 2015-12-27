package com.mygdx.setimig;

//import android.graphics.Paint;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Carles on 12/22/2015.
 * 3 files
 * fila 1 : nom jufador
 * fila 2 : 2 columnes , esquerra credit , dreta fitxes
 * fila 3 : 4 columnes aposta acumulada , fitxes , ultima aposta, fitxes
 */
public class Marcador {
    String nom;
    private int saldo;
    private int aposta = 0;
    private int ultimaAposta = 0;
    Table table;
    Label labelNom,labelCredit,labelAposta,labelUltimaAposta;
    public Marcador(float origenX,float origenY,float deltaX,float deltaY, String nom, int saldo, Stage stage, Skin skin){
        this.saldo = saldo;
        table = new Table(skin);
        table.setSize(deltaX,deltaY);
        table.setPosition(origenX,origenY);
        //table.debug();
        //table.pad(origenX,origenY,fiX,fiY);
        labelNom = new Label(nom,skin);
        //labelNom.setFillParent(true);
        //labelNom.
        labelCredit = new Label(String.valueOf(getSaldo()),skin);
        labelAposta = new Label(String.valueOf(getAposta()),skin);
        labelUltimaAposta = new Label(String.valueOf(getUltimaAposta()),skin);
        table.add(labelNom).expandX().center();
        table.row();
        table.add(labelCredit);
        table.row();
        Table table2 = new Table();
        table2.debug();

        //table2.setFillParent(true);
        table.add(table2).fillX();
        table2.add(labelAposta).width(deltaX/2).uniform();
        table2.add(labelUltimaAposta);
        //table.add(table2).expandX();
        stage.addActor (table);
    }
    public int perdAposta(){
        int total = getAposta() + getUltimaAposta();
        setAposta(0); setUltimaAposta(0);
        return total;
    }
    public void guanyaAposta(int valor){
        setSaldo(getSaldo()+valor+getAposta()+getUltimaAposta());
        setAposta(0); setUltimaAposta(0);
    }
    public int aposta(int aposta){
        if (getSaldo() - aposta <= 0 ) {
            aposta = getSaldo();
        }
        setSaldo(getSaldo() - aposta);
        setAposta(getAposta()+getUltimaAposta());
        setUltimaAposta(aposta);
        return aposta;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
        labelCredit.setText(String.valueOf(saldo));
    }

    public int getAposta() {
        return aposta;
    }

    public void setAposta(int aposta) {
        this.aposta = aposta;
        labelAposta.setText(String.valueOf(aposta));
    }

    public int getUltimaAposta() {
        return ultimaAposta;
    }

    public void setUltimaAposta(int ultimaAposta) {
        this.ultimaAposta = ultimaAposta;
        labelUltimaAposta.setText(String.valueOf(ultimaAposta));
    }
    public int getApostaTotal(){return aposta + ultimaAposta;}
}
