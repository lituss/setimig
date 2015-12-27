/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.setimig;

/**
 *
 * @author Carles
 */
public enum Pal {
    Espases(0),Bastos(1),Oros(2),Copes(3),Tapada(4);

int valor;
    Pal(int p){
        valor = p;
    }
    int valor(){
        return valor;
    }
}
