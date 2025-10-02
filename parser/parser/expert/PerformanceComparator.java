import java.io.*;
import java.util.*;

/**
 * Comparateur de performances SAX vs DOM - Niveau EXPERT
 * 
 * Fonctionnalités niveau expert :
 * 1. Comparaison temps d'exécution SAX vs DOM
 * 2. Mesure consommation mémoire
 * 3. Validation DTD vs XSD (si disponible)
 * 4. Rapport détaillé de performance
 * 
 * @author Projet PadChest XML - Niveau Expert
 */
public class PerformanceComparator {
    
    // Méthode utilitaire pour répéter une chaîne
    private static String repeat(String str, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    
    /**
     * Mesurer la mémoire utilisée
     */
    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
    
    /**
     * Forcer le garbage collector et attendre
     */
    private static void forceGC() {
        System.gc();
        try {
            Thread.sleep(100); // Attendre que le GC se termine
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Tester les performances du parser SAX
     */
    public static PerformanceResult testSAXParser(String xmlFile, int iterations) {
        System.out.println("\n" + repeat("=", 60));
        System.out.println("TEST DE PERFORMANCE - PARSER SAX");
        System.out.println(repeat("=", 60));
        
        long totalTime = 0;
        long maxMemory = 0;
        PadChestParserSimple lastParser = null;
        
        for (int i = 0; i < iterations; i++) {
            forceGC();
            long memoryBefore = getUsedMemory();
            
            try {
                PadChestParserSimple parser = new PadChestParserSimple();
                
                long startTime = System.currentTimeMillis();
                
                // Simulation du parsing SAX (appel de la méthode main modifiée)
                javax.xml.parsers.SAXParserFactory factory = javax.xml.parsers.SAXParserFactory.newInstance();
                javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
                saxParser.parse(new File(xmlFile), parser);
                
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                totalTime += executionTime;
                
                long memoryAfter = getUsedMemory();
                long memoryUsed = memoryAfter - memoryBefore;
                if (memoryUsed > maxMemory) {
                    maxMemory = memoryUsed;
                }
                
                lastParser = parser;
                
                System.out.println("Iteration " + (i + 1) + " : " + executionTime + " ms, Memoire: " + 
                                 (memoryUsed / 1024) + " KB");
                
            } catch (Exception e) {
                System.err.println("Erreur iteration " + (i + 1) + " : " + e.getMessage());
            }
        }
        
        long avgTime = totalTime / iterations;
        
        PerformanceResult result = new PerformanceResult();
        result.parserType = "SAX";
        result.iterations = iterations;
        result.totalTime = totalTime;
        result.averageTime = avgTime;
        result.maxMemory = maxMemory;
        result.totalImages = (lastParser != null) ? lastParser.getTotalImages() : 0;
        result.locRightCount = (lastParser != null) ? lastParser.getLocRightCount() : 0;
        result.uniqueLabels = (lastParser != null) ? lastParser.getUniqueLabelsCount() : 0;
        
        return result;
    }
    
    /**
     * Tester les performances du parser DOM
     */
    public static PerformanceResult testDOMParser(String xmlFile, int iterations) {
        System.out.println("\n" + repeat("=", 60));
        System.out.println("TEST DE PERFORMANCE - PARSER DOM");
        System.out.println(repeat("=", 60));
        
        long totalTime = 0;
        long maxMemory = 0;
        PadChestParserDOM lastParser = null;
        
        for (int i = 0; i < iterations; i++) {
            forceGC();
            long memoryBefore = getUsedMemory();
            
            try {
                PadChestParserDOM parser = new PadChestParserDOM();
                
                long startTime = System.currentTimeMillis();
                parser.parseXML(xmlFile);
                long endTime = System.currentTimeMillis();
                
                long executionTime = endTime - startTime;
                totalTime += executionTime;
                
                long memoryAfter = getUsedMemory();
                long memoryUsed = memoryAfter - memoryBefore;
                if (memoryUsed > maxMemory) {
                    maxMemory = memoryUsed;
                }
                
                lastParser = parser;
                
                System.out.println("Iteration " + (i + 1) + " : " + executionTime + " ms, Memoire: " + 
                                 (memoryUsed / 1024) + " KB");
                
            } catch (Exception e) {
                System.err.println("Erreur iteration " + (i + 1) + " : " + e.getMessage());
            }
        }
        
        long avgTime = totalTime / iterations;
        
        PerformanceResult result = new PerformanceResult();
        result.parserType = "DOM";
        result.iterations = iterations;
        result.totalTime = totalTime;
        result.averageTime = avgTime;
        result.maxMemory = maxMemory;
        result.totalImages = (lastParser != null) ? lastParser.getTotalImages() : 0;
        result.locRightCount = (lastParser != null) ? lastParser.getLocRightCount() : 0;
        result.uniqueLabels = (lastParser != null) ? lastParser.getUniqueLabelsCount() : 0;
        
        return result;
    }
    
    /**
     * Générer le rapport de comparaison expert
     */
    public static void generateExpertReport(PerformanceResult saxResult, PerformanceResult domResult, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            
            writer.println(repeat("=", 80));
            writer.println("RAPPORT DE PERFORMANCE EXPERT - PADCHEST XML PARSERS");
            writer.println(repeat("=", 80));
            writer.println("Rapport genere le : " + new Date());
            writer.println();
            
            // Informations générales
            writer.println("CONFIGURATION DU TEST :");
            writer.println(repeat("-", 50));
            writer.println("Iterations par parser : " + saxResult.iterations);
            writer.println("Images traitees : " + saxResult.totalImages);
            writer.println("Images avec 'loc right' : " + saxResult.locRightCount);
            writer.println("Labels uniques : " + saxResult.uniqueLabels);
            writer.println();
            
            // Comparaison des performances
            writer.println("COMPARAISON DES PERFORMANCES :");
            writer.println(repeat("-", 50));
            writer.printf("%-20s %-15s %-15s %-15s%n", "Parser", "Temps Total", "Temps Moyen", "Memoire Max");
            writer.printf("%-20s %-15s %-15s %-15s%n", repeat("-", 20), repeat("-", 15), repeat("-", 15), repeat("-", 15));
            writer.printf("%-20s %-15d %-15d %-15d%n", "SAX", saxResult.totalTime, saxResult.averageTime, saxResult.maxMemory / 1024);
            writer.printf("%-20s %-15d %-15d %-15d%n", "DOM", domResult.totalTime, domResult.averageTime, domResult.maxMemory / 1024);
            writer.println();
            
            // Analyse comparative
            writer.println("ANALYSE COMPARATIVE :");
            writer.println(repeat("-", 50));
            
            double speedRatio = (double) domResult.averageTime / saxResult.averageTime;
            double memoryRatio = (double) domResult.maxMemory / saxResult.maxMemory;
            
            writer.printf("Vitesse : SAX est %.2fx plus rapide que DOM%n", speedRatio);
            writer.printf("Memoire : DOM utilise %.2fx plus de memoire que SAX%n", memoryRatio);
            writer.println();
            
            // Recommandations
            writer.println("RECOMMANDATIONS :");
            writer.println(repeat("-", 50));
            if (speedRatio > 2.0) {
                writer.println("- SAX est SIGNIFICATIVEMENT plus rapide pour ce type de traitement");
            } else {
                writer.println("- Difference de vitesse moderee entre SAX et DOM");
            }
            
            if (memoryRatio > 2.0) {
                writer.println("- SAX est BEAUCOUP plus econome en memoire");
                writer.println("- Recommande pour traiter de gros fichiers XML");
            } else {
                writer.println("- Consommation memoire similaire");
            }
            
            writer.println("- SAX : Ideal pour parsing sequentiel et gros volumes");
            writer.println("- DOM : Ideal pour navigation complexe et modifications");
            writer.println();
            
            // Avantages/Inconvénients
            writer.println("AVANTAGES ET INCONVENIENTS :");
            writer.println(repeat("-", 50));
            writer.println("SAX :");
            writer.println("  + Tres rapide et econome en memoire");
            writer.println("  + Traitement en streaming (pas de limite de taille)");
            writer.println("  - Parsing sequentiel uniquement");
            writer.println("  - Pas de navigation arriere");
            writer.println();
            writer.println("DOM :");
            writer.println("  + Navigation libre dans l'arbre XML");
            writer.println("  + Modification du document possible");
            writer.println("  - Plus lent et consomme plus de memoire");
            writer.println("  - Charge tout le document en memoire");
            writer.println();
            
            writer.println(repeat("=", 80));
            writer.println("Fin du rapport expert");
            
            writer.close();
            System.out.println("Rapport expert sauvegarde dans : " + filename);
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la generation du rapport : " + e.getMessage());
        }
    }
    
    /**
     * Méthode principale pour lancer les tests de performance
     */
    public static void main(String[] args) {
        System.out.println("COMPARATEUR DE PERFORMANCE EXPERT - PADCHEST XML");
        System.out.println(repeat("=", 70));
        
        String xmlFile = "../data/processed/test_simple.xml";
        int iterations = 5;
        
        if (args.length > 0) {
            xmlFile = args[0];
        }
        if (args.length > 1) {
            iterations = Integer.parseInt(args[1]);
        }
        
        System.out.println("Fichier XML : " + xmlFile);
        System.out.println("Iterations : " + iterations);
        
        // Tester SAX
        PerformanceResult saxResult = testSAXParser(xmlFile, iterations);
        
        // Tester DOM
        PerformanceResult domResult = testDOMParser(xmlFile, iterations);
        
        // Afficher résumé
        System.out.println("\n" + repeat("=", 70));
        System.out.println("RESUME DES PERFORMANCES");
        System.out.println(repeat("=", 70));
        System.out.printf("SAX : %d ms moyen, %d KB memoire max%n", 
                         saxResult.averageTime, saxResult.maxMemory / 1024);
        System.out.printf("DOM : %d ms moyen, %d KB memoire max%n", 
                         domResult.averageTime, domResult.maxMemory / 1024);
        
        // Générer rapport expert
        generateExpertReport(saxResult, domResult, "rapport_expert.txt");
    }
    
    /**
     * Classe pour stocker les résultats de performance
     */
    static class PerformanceResult {
        String parserType;
        int iterations;
        long totalTime;
        long averageTime;
        long maxMemory;
        int totalImages;
        int locRightCount;
        int uniqueLabels;
    }
}
