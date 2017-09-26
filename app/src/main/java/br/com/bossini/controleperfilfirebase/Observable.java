package br.com.bossini.controleperfilfirebase;

/**
 * Created by rodrigo on 9/26/17.
 */

public interface Observable {

    public void registerObserver (Observer observer);

    public void notifyObservers ();
}
