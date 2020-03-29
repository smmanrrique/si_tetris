package Agentes;

import Vista.frm_Controles;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;


public class Controles extends GuiAgent {

    private AID agenteJuego;
    frm_Controles ventana;

    // NO SE si esta bien cambiar a public
    @Override
    protected void onGuiEvent(GuiEvent ge) {

        if (ge.getType() == 0) // Botton Left 
        {
            //  System.out.println(" Parameter 0 " + (String)ge.getParameter(0) );    
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(this.agenteJuego);
            msg.setContent((String)ge.getParameter(0));
            send(msg);
        }else if(ge.getType() == 1) // Botton Right 
        {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                        
            msg.addReceiver(this.agenteJuego);
            msg.setContent((String) ge.getParameter(0));
            send(msg);
            
        }else if(ge.getType() == 2)// Turn around
        {            
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                        
            msg.addReceiver(this.agenteJuego);
            msg.setContent((String)ge.getParameter(0));
            send(msg);
        }

    }

    @Override
    protected void setup() {
        ventana = new frm_Controles(this);
        ventana.setVisible(true);
        
//      Sleep before to start game  
//        this.doWait(10000);       
        addBehaviour(new BuscarJuego(this));

    }

    class BuscarJuego extends SimpleBehaviour {

        private boolean encontrado = false;
        private Controles agente;

        private BuscarJuego(Controles agentePadre) {
            agente = agentePadre;
        }

        @Override
        public void action() {
            System.out.println("Searching Game");
                    
//          Search for the service in the yellow pages.
            try {
//              Creating Agent description 
                DFAgentDescription descripcion = new DFAgentDescription();
                
                System.out.println("Creating service request Juego");

//              Creating service that the agent needs to request    
                ServiceDescription servicio = new ServiceDescription();
                servicio.setType("Tetris");
                servicio.setName("Juego");
                
//              Add more information to agent description
                descripcion.addLanguages("castellano");
                descripcion.addServices(servicio);

                System.out.println("Searching service name Juego");
                this.agente.doWait(100);

//              Searching all service registered in yellow pages  
                DFAgentDescription[] resultados = DFService.search(this.agente, descripcion);
                
                
                if (resultados.length > 0) // Found services
                {
                    System.out.println("Tenemos en total de " + resultados.length +" Agentes jugando...");
//                  Set True to encontrado
                    this.encontrado = true;
                    
//                  I need call onGuiEvent, For is not necesary
                    for (DFAgentDescription agente:resultados) {
                        this.agente.agenteJuego = agente.getName();
                    }
                        
                    ventana.setJugando();
                    
                } else // Didn't find services
                {
                    System.out.println("No existen Agentes dispuestos a jugar...");
//                  Set False to encontrado                    
                    this.encontrado = false;
                    ventana.setSinJuego(); 
                }
            } catch (Exception e) {
                System.out.println("error al buscar el juego: " + e.getMessage());
            }
                         
            ACLMessage resp = this.myAgent.receive();
            if (resp != null) // Check if message if no null
            {
                if (resp.getPerformative() == ACLMessage.INFORM ) // Check if ACLMessage is inform type 
                {
                    try {
//                      Geting action in the message  
                        String action = resp.getContent(); 
                        System.out.println("GAME IS OVER " );

//                      Block controls
                        ventana.setSinJuego(); 

                    } catch (Exception ce) {
                        System.out.println("error " + ce.toString());
                    }
                }
            }
        }

        @Override
        public boolean done() {
            return encontrado;
        }
    }
}
