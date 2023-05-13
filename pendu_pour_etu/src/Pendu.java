import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.List;

import javax.tools.Tool;

import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private VBox panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;

    private Button boutonInformation;
    /**
     * le bouton qui permet de (lancer ou relancer une partie)
     */ 
    private Button bJouer;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        // A terminer d'implementer
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        modeAccueil();
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        fenetre.setCenter(this.panelCentral);
        System.out.println(fenetre.getCenter());
        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre(){
        // A implementer          
        BorderPane banniere = new BorderPane();
        Text leTexte=new Text("Jeu Du Pendu");
        ImageView imgInfo=new ImageView(new Image(new File("img/info.png").toURI().toString()));    //permet de charger une image
        imgInfo.setFitHeight(50);imgInfo.setFitWidth(50);                                           // pemet de changer la largeur et hauteur de l'image

        ImageView imgParametres=new ImageView(new Image(new File("img/parametres.png").toURI().toString()));
        imgParametres.setFitHeight(50);imgParametres.setFitWidth(50);

        ImageView imgHome=new ImageView(new Image(new File("img/home.png").toURI().toString()));
        imgHome.setFitHeight(50);imgHome.setFitWidth(50);

        this.boutonMaison=new Button();
        this.boutonMaison.setGraphic(imgHome);
     //   this.boutonMaison.setScaleX(1);this.boutonMaison.setScaleY(1);
        this.boutonParametres=new Button("");
        this.boutonParametres.setGraphic(imgParametres);
       // this.boutonParametres.setScaleX(1);this.boutonParametres.setScaleY(1);

        this.boutonInformation=new Button();
        this.boutonInformation.setGraphic(imgInfo);
        //this.boutonInformation.setScaleX(1);this.boutonInformation.setScaleY(1);

        HBox hboxboutons=new HBox();
        hboxboutons.getChildren().addAll(boutonMaison,boutonParametres,boutonInformation);
        hboxboutons.setSpacing(10);
        banniere.setLeft(leTexte);
        banniere.setRight(hboxboutons);
        banniere.setPadding(new Insets(10));
        banniere.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN,null,null)));      //permet de changer la couleur du fond de notre border pane.
        return banniere;
    }

    // /**
     // * @return le panel du chronomètre
     // */
    // private TitledPane leChrono(){
        // A implementer
        // TitledPane res = new TitledPane();
        // return res;
    // }

    // /**
     // * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     // *         de progression et le clavier
     // */
    // private Pane fenetreJeu(){
        // A implementer
        // Pane res = new Pane();
        // return res;
    // }

    // /**
     // * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     // */
    //private BorderPane fenetreAccueil(){
    //    //A implementer    
    //    BorderPane root= new BorderPane();
    //    Pane res = new Pane();
    //    res.getChildren().add(titre());
    //    root.setCenter(res);
    //    
    //    return root;
    //}

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    /**
     *  Pemet de modifier le panel central de la page d'accueil
     */
    public void modeAccueil(){
        this.panelCentral=new VBox();
        this.bJouer=new Button("Lancer une partie");
        //this.bJouer.setOnAction(new ControleurLancerPartie(modelePendu, null));    //à completer
        this.panelCentral.getChildren().add(this.bJouer);
        VBox.setMargin(this.bJouer, new Insets(15));                      // permet de donner une marge à notre bouton
        
        ToggleGroup toogle= new ToggleGroup();    
        RadioButton radio1=new RadioButton("Facile");
        RadioButton radio2=new RadioButton("Moyen");
        RadioButton radio3=new RadioButton("Difficile");
        RadioButton radio4=new RadioButton("Expert");
        // permet d'ajouter les quatre boutons dans le meme toogle ce qui va nous empecher d'appuyer sur les quatres boutons à la fois
        radio1.setToggleGroup(toogle);radio2.setToggleGroup(toogle);radio3.setToggleGroup(toogle);radio4.setToggleGroup(toogle);  
        
        
        VBox v= new VBox();
        v.getChildren().addAll(radio1,radio2,radio3,radio4);
        TitledPane conteneur=new TitledPane("Niveau de jeu",v);
        
        this.panelCentral.getChildren().add(conteneur);
        VBox.setMargin(conteneur, new Insets(15));
        
        

        
    }
    
    public void modeJeu(){
        // A implementer
    }
    
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        // A implementer
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        // A implementer
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        // A implémenter
        return null; // A enlever
    }

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    public Alert popUpReglesDuJeu(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }
    
    public Alert popUpMessageGagne(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);        
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
