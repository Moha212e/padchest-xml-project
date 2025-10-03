package expert;

/**
 * Comparateur très simple pour débutants
 * Compare les performances entre SAX et DOM
 */
public class ComparateurSimple {
    
    /**
     * Compare les deux parsers sur le même fichier
     */
    public void comparer(String nomFichierXML) {
        System.out.println("=== COMPARAISON SAX vs DOM ===\n");
        
        System.out.println("Test avec l'analyseur SAX :");
        System.out.println("----------------------------");
        // On teste SAX
        minimum.SAXParserSimple analyseurSAX = new minimum.SAXParserSimple();
        analyseurSAX.analyserFichier(nomFichierXML);
        
        System.out.println("\n\nTest avec l'analyseur DOM :");
        System.out.println("----------------------------");
        // On teste DOM
        pro.DOMParserSimple analyseurDOM = new pro.DOMParserSimple();
        analyseurDOM.analyserFichier(nomFichierXML);
        
        System.out.println("\n=== CONCLUSION ===");
        System.out.println("- SAX lit le fichier ligne par ligne (plus économe en mémoire)");
        System.out.println("- DOM charge tout en mémoire d'un coup (plus rapide pour les petits fichiers)");
        System.out.println("- Pour de gros fichiers, utilisez SAX");
        System.out.println("- Pour de petits fichiers, DOM peut être plus pratique");
    }
    
    /**
     * Méthode principale pour tester le comparateur
     */
    public static void main(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("Comment utiliser : java ComparateurSimple nomfichier.xml");
            return;
        }
        
        ComparateurSimple comparateur = new ComparateurSimple();
        comparateur.comparer(arguments[0]);
    }
}