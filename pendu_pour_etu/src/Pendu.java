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

    private double width;

    private RadioButton radio1Niveau; 
    private RadioButton radio2Niveau; 
    private RadioButton radio3Niveau; 
    private RadioButton radio4Niveau; 
    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        //this.chargerImages("./img");
        this.panelCentral=new VBox();
        this.motCrypte= new Text(this.modelePendu.getMotCrypte());
        this.chargerImages("img");
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
        width =800;
        return new Scene(fenetre, width, 800);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre(){        
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
        this.boutonMaison.setOnAction(new RetourAccueil(modelePendu, this));
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

     /**
    / * @return le panel du chronomètre
    / */
     private TitledPane leChrono(){
        this.chrono=new Chronometre();
        TitledPane res = new TitledPane("Chronometre",chrono);
        return res;
    }
     /**
    / * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
    / *         de progression et le clavier
    / */
     private BorderPane fenetreJeu(){
        BorderPane res = new BorderPane();
        VBox left=new VBox();
        left.getChildren().add(motCrypte);
        this.dessin=new ImageView(this.lesImages.get(0));
        left.getChildren().add(this.dessin);
        this.pg=new ProgressBar(0);
        left.getChildren().add(pg);
        this.clavier=new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ-",new ControleurLettres(modelePendu, this));
        left.getChildren().add(clavier);
        left.setPadding(new Insets(30));
        VBox.setMargin(left,new Insets(20));
    
        VBox right=new VBox();
        this.leNiveau=new Text("Niveau "+ getDifText());
        right.getChildren().addAll(leNiveau,leChrono());
        right.setPadding(new Insets(100));
        //VBox.setMargin(right,new Insets(400));
        right.setSpacing(20);
        


        res.setLeft(left);
        res.setRight(right);
        return res;
    } 
    public void changerImage(){
        this.dessin.setImage(lesImages.get(modelePendu.getNbErreursMax()-modelePendu.getNbErreursRestants()));
    }
     /**
    / * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
    / */
    private Pane fenetreAccueil(){  
        Pane res = new Pane();

        VBox maVbox=new VBox();
        this.bJouer=new Button("Lancer une partie");
        this.bJouer.setOnAction(new ControleurLancerPartie(modelePendu, this));    //à completer
        maVbox.getChildren().add(this.bJouer);
        VBox.setMargin(this.bJouer, new Insets(15));                      // permet de donner une marge à notre bouton
        
        ToggleGroup toogle= new ToggleGroup();    
        this.radio1Niveau=new RadioButton("Facile");
        this.radio2Niveau=new RadioButton("Moyen");
        this.radio3Niveau=new RadioButton("Difficile");
        this.radio4Niveau=new RadioButton("Expert");
        // permet d'ajouter les quatre boutons dans le meme toogle ce qui va nous empecher d'appuyer sur les quatres boutons à la fois
        radio1Niveau.setToggleGroup(toogle);radio2Niveau.setToggleGroup(toogle);radio3Niveau.setToggleGroup(toogle);radio4Niveau.setToggleGroup(toogle);
        //radio1Niveau.setOnAction(new ControleurNiveau(modelePendu));
        //radio2Niveau.setOnAction(new ControleurNiveau(modelePendu));
        //radio3Niveau.setOnAction(new ControleurNiveau(modelePendu));
        //radio4Niveau.setOnAction(new ControleurNiveau(modelePendu));
        
        VBox conteneurNiveau= new VBox();
        conteneurNiveau.getChildren().addAll(radio1Niveau,radio2Niveau,radio3Niveau,radio4Niveau);
        TitledPane conteneur=new TitledPane("Niveau de jeu",conteneurNiveau);
        conteneurNiveau.setPrefWidth(width-50);
        maVbox.getChildren().add(conteneur);
        VBox.setMargin(conteneur, new Insets(15));
        res.getChildren().add(maVbox);
        return res;
    }


    private String getDifText(){
        return this.radio1Niveau.isSelected() ? "FACILE" : this.radio2Niveau.isSelected() ? "Moyen" : this.radio3Niveau.isSelected() ? "diffcile" : "Expert" ;
    }

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
        this.panelCentral.getChildren().clear();
        this.panelCentral.getChildren().add(fenetreAccueil());
    }
    
    public void modeJeu(){
        this.panelCentral.getChildren().clear();
        //System.out.println("njn"+this.panelCentral.getChildren());
        this.panelCentral.getChildren().add(fenetreJeu());
    }
    
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        this.modeJeu();
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        this.clavier.desactiveTouches(this.modelePendu.getLettresEssayees());
        //this.modelePendu.maj
        System.out.println(this.modelePendu.getMotATrouve());
        this.motCrypte.setText((this.modelePendu.getMotCrypte()));
        
        if (this.modelePendu.gagne()){this.popUpMessageGagne().showAndWait();}
        else if(this.modelePendu.perdu()){this.popUpMessagePerdu().showAndWait();}
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Vous venez de trouver le mot \n voulez-vous un nouveaux mot ?",ButtonType.YES, ButtonType.NO);        
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Vous venez de perdre le mot était : "+this.modelePendu.getMotATrouve() + " \n voulez-vous un nouveaux mot ?",ButtonType.YES, ButtonType.NO);
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
