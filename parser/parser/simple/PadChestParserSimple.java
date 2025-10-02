import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parser SAX SIMPLE pour le projet PadChest XML - Phase 4
 * Compatible avec toutes les versions de Java (Java 8+)
 * 
 * @author Projet PadChest XML
 */
public class PadChestParserSimple extends DefaultHandler {
    
    // Variables de comptage
    private int locRightCount = 0;
    private Map<String, Integer> labelFrequencies = new HashMap<String, Integer>();
    private int totalImages = 0;
    
    // Variables de parsing
    private String currentElement = "";
    private StringBuilder currentText = new StringBuilder();
    private boolean inLocalizations = false;
    private boolean inLabels = false;
    
    // Méthode utilitaire pour répéter un caractère
    private static String repeat(String str, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    
    // ========== MÉTHODES SAX ==========
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentElement = qName;
        currentText.setLength(0);
        
        if ("image".equals(qName)) {
            totalImages++;
            String imageId = attributes.getValue("id");
            System.out.println("Traitement image " + totalImages + " : " + imageId);
        }
        
        if ("localizations".equals(qName)) {
            inLocalizations = true;
        }
        
        if ("labels".equals(qName)) {
            inLabels = true;
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) {
        currentText.append(ch, start, length);
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) {
        String content = currentText.toString().trim();
        
        // Traiter les localisations
        if ("location".equals(qName) && inLocalizations) {
            if (content.contains("loc right")) {
                locRightCount++;
                System.out.println("  -> TROUVE 'loc right' : " + content);
            }
        }
        
        // Traiter les labels
        if ("label".equals(qName) && inLabels) {
            if (!content.isEmpty()) {
                Integer count = labelFrequencies.get(content);
                if (count == null) {
                    labelFrequencies.put(content, 1);
                } else {
                    labelFrequencies.put(content, count + 1);
                }
                System.out.println("  -> Label : " + content);
            }
        }
        
        // Fin des sections
        if ("localizations".equals(qName)) {
            inLocalizations = false;
        }
        
        if ("labels".equals(qName)) {
            inLabels = false;
        }
        
        currentElement = "";
    }
    
    // ========== MÉTHODES UTILITAIRES ==========
    
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
    
    public void printStatistics() {
        System.out.println("\n" + repeat("=", 50));
        System.out.println("STATISTIQUES PADCHEST XML");
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
    
    public void saveStatistics(String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            
            writer.println(repeat("=", 60));
            writer.println("STATISTIQUES PADCHEST XML - PARSER SAX");
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
            writer.println("Fin du rapport");
            
            writer.close();
            System.out.println("Statistiques sauvegardees dans : " + filename);
            
        } catch (IOException e) {
            System.err.println("ERREUR lors de la sauvegarde : " + e.getMessage());
        }
    }
    
    // ========== GETTERS POUR COMPARAISON ==========
    
    public int getTotalImages() { return totalImages; }
    public int getLocRightCount() { return locRightCount; }
    public int getUniqueLabelsCount() { return labelFrequencies.size(); }
    
    // ========== MÉTHODE PRINCIPALE ==========
    
    public static void main(String[] args) {
        System.out.println("PARSER PADCHEST XML - VERSION SIMPLE");
        System.out.println(repeat("=", 50));
        
        String xmlFile = "../data/processed/test_images.xml";
        
        if (args.length > 0) {
            xmlFile = args[0];
        }
        
        System.out.println("Fichier XML : " + xmlFile);
        
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            PadChestParserSimple handler = new PadChestParserSimple();
            
            System.out.println("Debut du parsing...");
            long startTime = System.currentTimeMillis();
            
            saxParser.parse(new File(xmlFile), handler);
            
            long endTime = System.currentTimeMillis();
            System.out.println("Parsing termine en " + (endTime - startTime) + " ms");
            
            handler.printStatistics();
            handler.saveStatistics("stats.txt");
            
        } catch (Exception e) {
            System.err.println("ERREUR : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
