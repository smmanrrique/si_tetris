package Agentes;

import Utilidades.BajarPieza;
import Utilidades.Pieza;
import Vista.frm_Juego;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREInitiator;


public class Juego extends GuiAgent {

    private frm_Juego juego;
    public boolean game = true;
    public  AID sender;
//    public  AID x = new AID

    @Override
    protected void onGuiEvent(GuiEvent ge) {
        System.out.println("Receive event  JUEGOOOO"+ge.toString());
        if (ge.getType() == 0) // Start game
        {
//          Creating Agent description 
            System.out.println("Creating service Juego");
            DFAgentDescription descripcion = new DFAgentDescription();
            descripcion.setName(getAID());
            
//          Creating service offered by the agent  
            ServiceDescription servicio = new ServiceDescription();
            servicio.setType("Tetris");
            servicio.setName("Juego");

//          Add more information to description            
            descripcion.addLanguages("castellano");
            descripcion.addServices(servicio);
            
//          Register the service in the platform
            try {
                DFService.register(this, descripcion);
            } catch (FIPAException e) {
                e.printStackTrace();
            }
            
            BajarPieza pieza = (BajarPieza) ge.getParameter(0);
            pieza.start();
        }else if (ge.getType() == 1) // End game
        {
            game = false;
            System.out.println("GAME IS OVER, SENT MESSAGE TO BLOCK CONTROLS");
            
//            Send message to inform that the game is over  
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(sender);
            send(msg);          
        }
    }

    @Override
    protected void setup() {
        juego = new frm_Juego(this);
        juego.setVisible(true);
        this.addBehaviour(new EscucharControles());
    }

    class EscucharControles extends SimpleBehaviour {

        @Override
        public void action() {
            
            ACLMessage resp = this.myAgent.receive();
            if (resp != null) // Check if message if no null
            {
                System.out.println(" READY TO RECEIVE MESSAGE"  );
                if (resp.getPerformative() == ACLMessage.INFORM ) // Check if ACLMessage is inform type 
                {
                    try {
//                      Geting action in the message  
                        String action = resp.getContent();
                        sender = resp.getSender();
                        System.out.println("Action to execute: "+ action );
                        
//                      Execute action 
                        if(game){
                            this.realizarAccion(action);
                        }                        
                        
                    } catch (Exception ce) {
                        System.out.println("error " + ce.toString());
                    }
                }
            }
        }

        @Override
        public boolean done() {
            return false;
        }

        private void realizarAccion(String accion) {
            if (accion.equals("LEFT")) // Check left action
            {
                if (juego.estaLibreIzquierda()) {
                    Pieza.getInstancia().moverAIzquierda();
                    juego.mostrarPieza();
                    juego.borrarRastroHaciaIzquierda();
                }
            } else if(accion.equals("RIGHT")) // Check rignt action
            {
                if (juego.estaLibreDerecha()) {
                    Pieza.getInstancia().moverADerecha();
                    juego.mostrarPieza();
                    juego.borrarRastroHaciaDerecha();
                }
            } else if(accion.equals("TURN")) // Check turn action
            {
                int oldPosition = Pieza.getInstancia().getEstado();
                if ( oldPosition == 1 && juego.estaLibreDerecha() ) {
                     System.out.println(" Pieza 1"  );
                    juego.borrarPieza();
                    Pieza.getInstancia().setEstado(4);
                    juego.mostrarPieza();
                }else if ( oldPosition == 2) {
                    juego.borrarPieza();
                    Pieza.getInstancia().setEstado(1);
                    juego.mostrarPieza();
                }else if ( oldPosition == 3 && juego.estaLibreIzquierda()) {
                    juego.borrarPieza();
                    Pieza.getInstancia().setEstado(2);
                    juego.mostrarPieza();
                }else if ( oldPosition == 4 && juego.estaLibreBajando()) {
                    juego.borrarPieza();
                    Pieza.getInstancia().setEstado(3);
                    juego.mostrarPieza();
                }
            }
        }
    }
}
