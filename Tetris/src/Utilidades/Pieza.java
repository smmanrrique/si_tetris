/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.util.Random;

/**
 *
 * @author Dixon
 */
public class Pieza {

    private static Pieza instancia = new Pieza();
    private int estado = 1; // Es para saber en que posición (hacia la izquierda : 1, hacia abajo : 2, hacia la derecha : 3 y hacia arriba : 4) se encuentra la pieza
    private int arriba_X;
    private int arriba_Y;
    private int centro_X;
    private int centro_Y;
    private int izquierdo_X;
    private int izquierdo_Y;
    private int abajo_X;
    private int abajo_Y;

    private Pieza() {
    }

    public static Pieza getInstancia() {
        return instancia;
    }

    public void posicionInicial() {
        setArriba_X(6);
        setArriba_Y(10);
        setCentro_X(6);
        setCentro_Y(9);
        setIzquierdo_X(5);
        setIzquierdo_Y(9);
        setAbajo_X(6);
        setAbajo_Y(8);
//      Get differents values state between 0 and 4 to start   
        int state = posiPieza();
        setEstado(state);
        
    }

    public void moverAAbajo() {
        setArriba_Y(getArriba_Y() - 1);
        setCentro_Y(getCentro_Y() - 1);
        setIzquierdo_Y(getIzquierdo_Y() - 1);
        setAbajo_Y(getAbajo_Y() - 1);
    }

    public void moverAIzquierda() {
        setArriba_X(getArriba_X() - 1);
        setCentro_X(getCentro_X() - 1);
        setIzquierdo_X(getIzquierdo_X() - 1);
        setAbajo_X(getAbajo_X() - 1);
    }

    public void moverADerecha() {
        setArriba_X(getArriba_X() + 1);
        setCentro_X(getCentro_X() + 1);
        setIzquierdo_X(getIzquierdo_X() + 1);
        setAbajo_X(getAbajo_X() + 1);
    }
    
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
        if (estado == 1) {
            setArriba_X(getCentro_X());
            setArriba_Y(getCentro_Y()+1);
            setIzquierdo_X(getCentro_X()-1);
            setIzquierdo_Y(getCentro_Y());
            setAbajo_X(getCentro_X());
            setAbajo_Y(getCentro_Y()-1);
        } else if (estado == 2) {
            setArriba_X(getCentro_X()-1);
            setArriba_Y(getCentro_Y());
            setIzquierdo_X(getCentro_X());
            setIzquierdo_Y(getCentro_Y()-1);
            setAbajo_X(getCentro_X()+1);
            setAbajo_Y(getCentro_Y());
        } else if (estado == 3) {
            setArriba_X(getCentro_X());
            setArriba_Y(getCentro_Y()-1);
            setIzquierdo_X(getCentro_X()+1);
            setIzquierdo_Y(getCentro_Y());
            setAbajo_X(getCentro_X());
            setAbajo_Y(getCentro_Y()+1);
        } else if (estado == 4) {
            setArriba_X(getCentro_X()+1);
            setArriba_Y(getCentro_Y());
            setIzquierdo_X(getCentro_X());
            setIzquierdo_Y(getCentro_Y()+1);
            setAbajo_X(getCentro_X()-1);
            setAbajo_Y(getCentro_Y());
        } else {
            setEstado(1);
        }
    }

    public int getArriba_X() {
        return arriba_X;
    }

    public void setArriba_X(int arriba_X) {
        this.arriba_X = arriba_X;
    }

    public int getArriba_Y() {
        return arriba_Y;
    }

    public void setArriba_Y(int arriba_Y) {
        this.arriba_Y = arriba_Y;
    }

    public int getCentro_X() {
        return centro_X;
    }

    public void setCentro_X(int centro_X) {
        this.centro_X = centro_X;
    }

    public int getCentro_Y() {
        return centro_Y;
    }

    public void setCentro_Y(int centro_Y) {
        this.centro_Y = centro_Y;
    }

    public int getIzquierdo_X() {
        return izquierdo_X;
    }

    public void setIzquierdo_X(int izquierdo_X) {
        this.izquierdo_X = izquierdo_X;
    }

    public int getIzquierdo_Y() {
        return izquierdo_Y;
    }

    public void setIzquierdo_Y(int izquierdo_Y) {
        this.izquierdo_Y = izquierdo_Y;
    }

    public int getAbajo_X() {
        return abajo_X;
    }

    public void setAbajo_X(int abajo_X) {
        this.abajo_X = abajo_X;
    }

    public int getAbajo_Y() {
        return abajo_Y;
    }

    public void setAbajo_Y(int abajo_Y) {
        this.abajo_Y = abajo_Y;
    }
    
    public int posiPieza(){
        int min = 1;
        int max = 4;
        
        Random r = new Random();
	int randomNum =  r.nextInt((max - min) + 1) + min;

        return randomNum;

    }
}
