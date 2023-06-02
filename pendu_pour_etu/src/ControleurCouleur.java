import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton info
 */
public class ControleurCouleur implements EventHandler<ActionEvent> {

    private Pendu appliPendu;

    /**
     * @param p vue du jeu
     */
    public ControleurCouleur(Pendu appliPendu) {
        this.appliPendu = appliPendu;
    }

    /**
     * L'action consiste à afficher une fenêtre popup précisant les règles du jeu.
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        this.appliPendu.modeJeu();
    }
}
