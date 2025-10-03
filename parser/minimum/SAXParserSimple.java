package minimum;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Parser SAX très simple pour débutants
 * Ce code lit un fichier XML et compte des éléments
 */
public class SAXParserSimple extends DefaultHandler {
    
    // Variables pour compter
    private int compteurLocDroite = 0;
    private Map<String, Integer> compteurEtiquettes = new HashMap<>();
    
    // Variable pour stocker le texte qu'on lit
    private String texteActuel = "";
    
    /**
     * Cette méthode est appelée quand on trouve une balise ouvrante comme <localisation>
     */
    @Override
    public void startElement(String uri, String nomLocal, String nomBalise, Attributes attributs) {
        // On remet le texte à zéro pour la nouvelle balise
        texteActuel = "";
    }
    
    /**
     * Cette méthode est appelée quand on lit du texte entre les balises
     */
    @Override
    public void characters(char[] caracteres, int debut, int longueur) {
        // On récupère le texte et on l'ajoute à notre variable
        texteActuel = texteActuel + new String(caracteres, debut, longueur);
    }
    
    /**
     * Cette méthode est appelée quand on trouve une balise fermante comme </localisation>
     */
    @Override
    public void endElement(String uri, String nomLocal, String nomBalise) {
        // On nettoie le texte (enlève les espaces au début et à la fin)
        String texte = texteActuel.trim();
        
        // Si c'est une balise localisation et que le texte est "loc right"
        if (nomBalise.equals("localisation") && texte.equals("loc right")) {
            compteurLocDroite++;
        }
        
        // Si c'est une balise label, on compte cette étiquette
        if (nomBalise.equals("label")) {
            // Si l'étiquette existe déjà, on ajoute 1
            if (compteurEtiquettes.containsKey(texte)) {
                int ancienNombre = compteurEtiquettes.get(texte);
                compteurEtiquettes.put(texte, ancienNombre + 1);
            } else {
                // Si c'est la première fois qu'on voit cette étiquette
                compteurEtiquettes.put(texte, 1);
            }
        }
    }
    
    /**
     * Méthode pour lancer l'analyse du fichier XML
     */
    public void analyserFichier(String nomFichierXML) {
        try {
            // On crée le parser SAX
            SAXParserFactory fabrique = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser analyseur = fabrique.newSAXParser();
            
            // On mesure le temps de début
            long tempsDebut = System.currentTimeMillis();
            
            // On lance l'analyse du fichier
            analyseur.parse(new File(nomFichierXML), this);
            
            // On mesure le temps de fin
            long tempsFin = System.currentTimeMillis();
            long tempsTotal = tempsFin - tempsDebut;
            
            // On affiche les résultats
            afficherResultats(tempsTotal);
            
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
    
    /**
     * Méthode pour afficher les résultats
     */
    private void afficherResultats(long temps) {
        System.out.println("=== RESULTATS ===");
        System.out.println("Temps d'analyse : " + temps + " millisecondes");
        System.out.println("Nombre d'images avec 'loc right' : " + compteurLocDroite);
        
        System.out.println("\n=== TOP 10 ETIQUETTES ===");
        
        // On trouve les 10 étiquettes les plus fréquentes
        int position = 1;
        for (int i = 0; i < 10 && position <= compteurEtiquettes.size(); i++) {
            String etiquettePlusFrequente = "";
            int nombreMax = 0;
            
            // On cherche l'étiquette avec le plus d'occurrences
            for (String etiquette : compteurEtiquettes.keySet()) {
                int nombre = compteurEtiquettes.get(etiquette);
                if (nombre > nombreMax) {
                    nombreMax = nombre;
                    etiquettePlusFrequente = etiquette;
                }
            }
            
            // Si on a trouvé une étiquette, on l'affiche et on l'enlève de la liste
            if (!etiquettePlusFrequente.isEmpty()) {
                System.out.println(position + ". " + etiquettePlusFrequente + " : " + nombreMax + " fois");
                compteurEtiquettes.remove(etiquettePlusFrequente);
                position++;
            } else {
                break;
            }
        }
    }
    
    /**
     * Méthode principale pour tester le parser
     */
    public static void main(String[] arguments) {
        // On vérifie qu'on a bien donné le nom du fichier
        if (arguments.length != 1) {
            System.out.println("Comment utiliser : java SAXParserSimple nomfichier.xml");
            return;
        }
        
        // On crée notre parser et on analyse le fichier
        SAXParserSimple analyseur = new SAXParserSimple();
        analyseur.analyserFichier(arguments[0]);
    }
}