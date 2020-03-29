/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import Vista.frm_Juego;

/**
 *
 */
public class BajarPieza extends Thread {

    private boolean salir = false;
    private frm_Juego juego;

    public BajarPieza(frm_Juego juego) {
        this.juego = juego;
    }

    public BajarPieza() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        Pieza.getInstancia().posicionInicial();
        juego.mostrarPieza();
        if (juego.estaLibreBajando()) {
            juego.mostrarPieza();
        } else {
            juego.setFinDeJuego();
            salir = true;
        }
        while (!salir) {
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("error bajar pieza: " + ex.getMessage());
            }
            if (juego.estaLibreBajando()) {
                Pieza.getInstancia().moverAAbajo();
                juego.mostrarPieza();
                juego.borrarRastroBajando();
            } else {
                salir = true;
                new BajarPieza(juego).start();
            }

        }
    }
}
