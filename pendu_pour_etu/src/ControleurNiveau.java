import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;

/**
 * Controleur des radio boutons gérant le niveau
 */
public class ControleurNiveau implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;


    /**
     * @param modelePendu modèle du jeu
     */
    public ControleurNiveau(MotMystere modelePendu) {
        this.modelePendu=modelePendu;
    }

    /**
     * gère le changement de niveau
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        RadioButton radiobouton = (RadioButton) actionEvent.getTarget();
        String nomDuRadiobouton = radiobouton.getText();
        if (nomDuRadiobouton.contains("Facile")){this.modelePendu.setNiveau(modelePendu.FACILE);}
        else if (nomDuRadiobouton.contains("Moyen")){this.modelePendu.setNiveau(modelePendu.MOYEN);} 
        else if (nomDuRadiobouton.contains("Difficile")){this.modelePendu.setNiveau(modelePendu.DIFFICILE);}
        else {this.modelePendu.setNiveau(modelePendu.EXPERT);}
        System.out.println(nomDuRadiobouton);
    }
}
