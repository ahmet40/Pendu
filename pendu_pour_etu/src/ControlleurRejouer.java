import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton rejouer ou Lancer une partie
 */
public class ControlleurRejouer implements EventHandler<ActionEvent> {
    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     **/
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param p vue du jeu
     */
    public ControlleurRejouer(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu=modelePendu;
        this.vuePendu=vuePendu;
    }

    /**
     * L'action consiste à recommencer une partie. Il faut vérifier qu'il n'y a pas une partie en cours
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        this.modelePendu.setMotATrouver();
        this.vuePendu.lancePartie();
    }
}
