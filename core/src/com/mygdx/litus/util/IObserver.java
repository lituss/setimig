/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.litus.util;

import com.mygdx.litus.util.Observable;

/**
 *
 * @author Carles
 */
public interface IObserver {
    void update(Observable o,Object arg);
}
