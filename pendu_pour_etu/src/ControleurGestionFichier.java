import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurGestionFichier implements EventHandler<ActionEvent>{
    private Pendu appliPendu;

    /**
     * @param p vue du jeu
     */
    public ControleurGestionFichier(Pendu appliPendu) {
        this.appliPendu = appliPendu;
        
    }

    /**
     * L'action consiste à ouvrir le gestionnaire de fichier pour que vous puissiez changer de fichier de mots
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        this.appliPendu.ouvrirGestionnaireFichiers();
    }
}
