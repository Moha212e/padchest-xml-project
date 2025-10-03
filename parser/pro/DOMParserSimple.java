package pro;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Parser DOM très simple pour débutants
 * Ce code charge tout le fichier XML en mémoire puis le lit
 */
public class DOMParserSimple {
    
    // Variables pour compter
    private int compteurLocDroite = 0;
    private Map<String, Integer> compteurEtiquettes = new HashMap<>();
    
    /**
     * Méthode pour analyser le fichier XML
     */
    public void analyserFichier(String nomFichierXML) {
        try {
            // On crée le parser DOM
            DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
            DocumentBuilder constructeur = fabrique.newDocumentBuilder();
            
            // On mesure le temps et la mémoire avant
            Runtime memoire = Runtime.getRuntime();
            long memoireAvant = memoire.totalMemory() - memoire.freeMemory();
            long tempsDebut = System.currentTimeMillis();
            
            // On charge tout le fichier XML en mémoire
            Document document = constructeur.parse(new File(nomFichierXML));
            
            // On analyse le document
            analyserDocument(document);
            
            // On mesure le temps et la mémoire après
            long tempsFin = System.currentTimeMillis();
            long memoireApres = memoire.totalMemory() - memoire.freeMemory();
            
            long tempsTotal = tempsFin - tempsDebut;
            long memoireUtilisee = memoireApres - memoireAvant;
            
            // On affiche les résultats
            afficherResultats(tempsTotal, memoireUtilisee);
            
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
    
    /**
     * Méthode pour analyser le document XML chargé en mémoire
     */
    private void analyserDocument(Document document) {
        // On cherche tous les éléments "localisation"
        NodeList localisations = document.getElementsByTagName("localisation");
        
        // On regarde chaque localisation une par une
        for (int i = 0; i < localisations.getLength(); i++) {
            Node localisation = localisations.item(i);
            String texte = localisation.getTextContent().trim();
            
            // Si c'est "loc right", on compte
            if (texte.equals("loc right")) {
                compteurLocDroite++;
            }
        }
        
        // On cherche tous les éléments "label"
        NodeList etiquettes = document.getElementsByTagName("label");
        
        // On regarde chaque étiquette une par une
        for (int i = 0; i < etiquettes.getLength(); i++) {
            Node etiquette = etiquettes.item(i);
            String texte = etiquette.getTextContent().trim();
            
            // On compte cette étiquette
            if (compteurEtiquettes.containsKey(texte)) {
                // Si l'étiquette existe déjà, on ajoute 1
                int ancienNombre = compteurEtiquettes.get(texte);
                compteurEtiquettes.put(texte, ancienNombre + 1);
            } else {
                // Si c'est la première fois qu'on voit cette étiquette
                compteurEtiquettes.put(texte, 1);
            }
        }
    }
    
    /**
     * Méthode pour afficher les résultats
     */
    private void afficherResultats(long temps, long memoire) {
        System.out.println("=== RESULTATS DOM ===");
        System.out.println("Temps d'analyse : " + temps + " millisecondes");
        System.out.println("Mémoire utilisée : " + (memoire / 1024) + " KB");
        System.out.println("Nombre d'images avec 'loc right' : " + compteurLocDroite);
        
        System.out.println("\n=== TOP 10 ETIQUETTES ===");
        
        // On trouve les 10 étiquettes les plus fréquentes
        int position = 1;
        Map<String, Integer> copieEtiquettes = new HashMap<>(compteurEtiquettes);
        
        for (int i = 0; i < 10 && !copieEtiquettes.isEmpty(); i++) {
            String etiquettePlusFrequente = "";
            int nombreMax = 0;
            
            // On cherche l'étiquette avec le plus d'occurrences
            for (String etiquette : copieEtiquettes.keySet()) {
                int nombre = copieEtiquettes.get(etiquette);
                if (nombre > nombreMax) {
                    nombreMax = nombre;
                    etiquettePlusFrequente = etiquette;
                }
            }
            
            // Si on a trouvé une étiquette, on l'affiche et on l'enlève de la copie
            if (!etiquettePlusFrequente.isEmpty()) {
                System.out.println(position + ". " + etiquettePlusFrequente + " : " + nombreMax + " fois");
                copieEtiquettes.remove(etiquettePlusFrequente);
                position++;
            }
        }
    }
    
    /**
     * Méthode principale pour tester le parser
     */
    public static void main(String[] arguments) {
        // On vérifie qu'on a bien donné le nom du fichier
        if (arguments.length != 1) {
            System.out.println("Comment utiliser : java DOMParserSimple nomfichier.xml");
            return;
        }
        
        // On crée notre parser et on analyse le fichier
        DOMParserSimple analyseur = new DOMParserSimple();
        analyseur.analyserFichier(arguments[0]);
    }
}