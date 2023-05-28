import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


/**
 * Permet de gérer un Text associé à une Timeline pour afficher un temps écoulé
 */
public class Chronometre extends Text{
    /**
     * timeline qui va gérer le temps
     */
    private Timeline timeline;
    /**
     * la fenêtre de temps
     */
    private KeyFrame keyFrame;
    /**
     * le contrôleur associé au chronomètre
     */
    private ControleurChronometre actionTemps;

    /**
     * Constructeur permettant de créer le chronomètre
     * avec un label initialisé à "0:0:0"
     * Ce constructeur créer la Timeline, la KeyFrame et le contrôleur
     */
    public Chronometre() {
        
        setText("00:00");                                       // va nous afficher le texte sous forme minute:seconde
        actionTemps = new ControleurChronometre(this);          //permet d'ajouter le controleur chronometre
        timeline = new Timeline();                             
        keyFrame = new KeyFrame(Duration.seconds(1), actionEvent -> actionTemps.handle(actionEvent));   
        timeline.getKeyFrames().add(keyFrame);          //ajoute la keyFrame à la timeline
        timeline.setCycleCount(Animation.INDEFINITE);   //nous donne une animation infinie ce qui va permettre de changer le texte à chaque seconde
       
    }


    /**
     * Permet au controleur de mettre à jour le text
     * la durée est affichée sous la forme m:s
     * @param tempsMillisec la durée depuis à afficher
     */

    public void setTime(long tempsMillisec) {
        long secondes = (tempsMillisec / 1000) % 60;        // divise la milliseconde par 100 et le met modulo 60
        long minutes = (tempsMillisec / (1000* 60)) % 60;

        setText(String.format("%02d:%02d", minutes, secondes));
    }

    /**
     * Permet de démarrer le chronomètre
     */
    public void start() {
        timeline.play();
    }

    /**
     * Permet d'arrêter le chronomètre
     */

    public void stop() {
        timeline.stop();
    }
    /**
     * Permet de remettre le chronomètre à 0
     */
    public void resetTime() {
        actionTemps.reset();
        setTime(0);
    }
}


