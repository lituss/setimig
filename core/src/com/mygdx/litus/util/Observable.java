/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.litus.util;

//import java.util.Observer;

import com.badlogic.gdx.utils.Array;
import com.mygdx.litus.util.IObserver;


/**
 *
 * @author Carles
 */
public class Observable {
    private Array<IObserver> observers;
    private boolean changed = false;
    private Object arg;
    
    public Observable(){
        observers = new Array<IObserver>();
    }
    
    public synchronized void addObserver(IObserver o){
        if (o == null)
             throw new NullPointerException();
        if (!observers.contains(o,true)) 
          observers.add(o);  
    }
    public synchronized void clearChanged(){
        changed = false;
    }

    public synchronized long	countObservers(){
        return observers.size;
    }
    
    public synchronized void deleteObserver(IObserver o){
        observers.removeValue(o, true);
    }
    
    public synchronized void deleteObservers(){
        observers.clear();
    }
    
//Clears the observer list so that this object no longer has any observers.
    public synchronized boolean hasChanged(){
        return changed;
    }

    public void notifyObservers(){
        notifyObservers(null);
    }
//If this object has changed, as indicated by the hasChanged method, then notify all of its observers and then call the clearChanged method to indicate that this object has no longer changed.
    public void notifyObservers(Object arg){
        Array<IObserver> localObs = new Array<IObserver>();
        synchronized(this) {
            if (!changed) return;
            localObs.addAll(observers);
            clearChanged();
            for (IObserver o : localObs) o.update(this, arg);
        }
    }
//If this object has changed, as indicated by the hasChanged method, then notify all of its observers and then call the clearChanged method to indicate that this object has no longer changed.
    public synchronized void setChanged(){
        changed = true;
    }
}
