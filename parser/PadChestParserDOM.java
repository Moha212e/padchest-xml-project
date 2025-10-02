import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * Parser DOM pour le projet PadChest XML - Niveau PRO
 * 
 * Fonctionnalités :
 * 1. Parser DOM (alternative au SAX)
 * 2. Même calculs que SAX pour comparaison
 * 3. Interface similaire pour tests de performance
 * 
 * @author Projet PadChest XML - Niveau Pro
 */
public class PadChestParserDOM {
    
    // Variables de comptage (identiques au SAX)
    private int locRightCount = 0;
    private Map<String, Integer> labelFrequencies = new HashMap<String, Integer>();
    private int totalImages = 0;
    
    // Méthode utilitaire pour répéter une chaîne
    private static String repeat(String str, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    
    /**
     * Parser le fichier XML avec DOM
     */
    public void parseXML(String xmlFile) throws Exception {
        System.out.println("Debut du parsing DOM...");
        
        // Créer le parser DOM
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        // Charger le document XML en mémoire
        Document document = builder.parse(new File(xmlFile));
        document.getDocumentElement().normalize();
        
        System.out.println("Document XML charge en memoire");
        
        // Obtenir toutes les images
        NodeList imageNodes = document.getElementsByTagName("image");
        totalImages = imageNodes.getLength();
        
        System.out.println("Nombre d'images trouvees : " + totalImages);
        
        // Traiter chaque image
        for (int i = 0; i < imageNodes.getLength(); i++) {
            Element imageElement = (Element) imageNodes.item(i);
            processImage(imageElement, i + 1);
        }
    }
    
    /**
     * Traiter une image individuelle
     */
    private void processImage(Element imageElement, int imageNumber) {
        // Obtenir l'ID de l'image
        String imageId = imageElement.getAttribute("id");
        System.out.println("Traitement image " + imageNumber + " : " + imageId);
        
        // Traiter les labels
        processLabels(imageElement);
        
        // Traiter les localisations
        processLocalizations(imageElement);
    }
    
    /**
     * Traiter les labels d'une image
     */
    private void processLabels(Element imageElement) {
        // Naviguer vers diagnosis > labels > label
        NodeList diagnosisNodes = imageElement.getElementsByTagName("diagnosis");
        if (diagnosisNodes.getLength() > 0) {
            Element diagnosisElement = (Element) diagnosisNodes.item(0);
            
            NodeList labelsNodes = diagnosisElement.getElementsByTagName("labels");
            if (labelsNodes.getLength() > 0) {
                Element labelsElement = (Element) labelsNodes.item(0);
                
                NodeList labelNodes = labelsElement.getElementsByTagName("label");
                for (int i = 0; i < labelNodes.getLength(); i++) {
                    Element labelElement = (Element) labelNodes.item(i);
                    String labelText = labelElement.getTextContent().trim();
                    
                    if (!labelText.isEmpty()) {
                        // Compter ce label
                        Integer count = labelFrequencies.get(labelText);
                        if (count == null) {
                            labelFrequencies.put(labelText, 1);
                        } else {
                            labelFrequencies.put(labelText, count + 1);
                        }
                        System.out.println("  -> Label : " + labelText);
                    }
                }
            }
        }
    }
    
    /**
     * Traiter les localisations d'une image
     */
    private void processLocalizations(Element imageElement) {
        // Naviguer vers diagnosis > localizations > location
        NodeList diagnosisNodes = imageElement.getElementsByTagName("diagnosis");
        if (diagnosisNodes.getLength() > 0) {
            Element diagnosisElement = (Element) diagnosisNodes.item(0);
            
            NodeList localizationsNodes = diagnosisElement.getElementsByTagName("localizations");
            if (localizationsNodes.getLength() > 0) {
                Element localizationsElement = (Element) localizationsNodes.item(0);
                
                NodeList locationNodes = localizationsElement.getElementsByTagName("location");
                for (int i = 0; i < locationNodes.getLength(); i++) {
                    Element locationElement = (Element) locationNodes.item(i);
                    String locationText = locationElement.getTextContent().trim();
                    
                    if (locationText.contains("loc right")) {
                        locRightCount++;
                        System.out.println("  -> TROUVE 'loc right' : " + locationText);
                    }
                }
            }
        }
    }
    
    /**
     * Obtenir le top 10 des labels les plus fréquents
     */
    public List<Map.Entry<String, Integer>> getTop10Labels() {
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(labelFrequencies.entrySet());
        
        // Tri par fréquence décroissante
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                return b.getValue().compareTo(a.getValue());
            }
        });
        
        // Retourner seulement les 10 premiers
        if (list.size() > 10) {
            return list.subList(0, 10);
        }
        return list;
    }
    
    /**
     * Afficher les statistiques dans la console
     */
    public void printStatistics() {
        System.out.println("\n" + repeat("=", 50));
        System.out.println("STATISTIQUES PADCHEST XML - PARSER DOM");
        System.out.println(repeat("=", 50));
        
        System.out.println("Images totales : " + totalImages);
        System.out.println("Images avec 'loc right' : " + locRightCount);
        System.out.println("Labels uniques : " + labelFrequencies.size());
        
        System.out.println("\nTOP 10 DES LABELS :");
        System.out.println(repeat("-", 50));
        
        List<Map.Entry<String, Integer>> top10 = getTop10Labels();
        for (int i = 0; i < top10.size(); i++) {
            Map.Entry<String, Integer> entry = top10.get(i);
            System.out.println((i + 1) + ". " + entry.getKey() + " : " + entry.getValue() + " fois");
        }
    }
    
    /**
     * Sauvegarder les statistiques dans un fichier
     */
    public void saveStatistics(String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            
            writer.println(repeat("=", 60));
            writer.println("STATISTIQUES PADCHEST XML - PARSER DOM");
            writer.println(repeat("=", 60));
            writer.println("Fichier genere le : " + new Date());
            writer.println();
            
            writer.println("RESUME GENERAL :");
            writer.println(repeat("-", 40));
            writer.println("Images totales : " + totalImages);
            writer.println("Images avec 'loc right' : " + locRightCount);
            writer.println("Labels uniques : " + labelFrequencies.size());
            writer.println();
            
            writer.println("TOP 10 DES LABELS LES PLUS FREQUENTS :");
            writer.println(repeat("-", 40));
            
            List<Map.Entry<String, Integer>> top10 = getTop10Labels();
            for (int i = 0; i < top10.size(); i++) {
                Map.Entry<String, Integer> entry = top10.get(i);
                writer.println((i + 1) + ". " + entry.getKey() + " : " + entry.getValue() + " occurrences");
            }
            
            writer.println();
            writer.println(repeat("=", 60));
            writer.println("Fin du rapport DOM");
            
            writer.close();
            System.out.println("Statistiques DOM sauvegardees dans : " + filename);
            
        } catch (IOException e) {
            System.err.println("ERREUR lors de la sauvegarde DOM : " + e.getMessage());
        }
    }
    
    // Getters pour les comparaisons de performance
    public int getTotalImages() { return totalImages; }
    public int getLocRightCount() { return locRightCount; }
    public int getUniqueLabelsCount() { return labelFrequencies.size(); }
    
    /**
     * Méthode principale pour tester le parser DOM
     */
    public static void main(String[] args) {
        System.out.println("PARSER PADCHEST XML - VERSION DOM (PRO)");
        System.out.println(repeat("=", 50));
        
        String xmlFile = "../data/processed/test_simple.xml";
        
        if (args.length > 0) {
            xmlFile = args[0];
        }
        
        System.out.println("Fichier XML : " + xmlFile);
        
        try {
            PadChestParserDOM parser = new PadChestParserDOM();
            
            long startTime = System.currentTimeMillis();
            parser.parseXML(xmlFile);
            long endTime = System.currentTimeMillis();
            
            System.out.println("Parsing DOM termine en " + (endTime - startTime) + " ms");
            
            parser.printStatistics();
            parser.saveStatistics("stats_dom.txt");
            
        } catch (Exception e) {
            System.err.println("ERREUR DOM : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
