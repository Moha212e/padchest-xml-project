import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.validation.*;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Comparateur de validation DTD vs XSD - Niveau EXPERT
 * 
 * Fonctionnalités :
 * 1. Validation avec DTD
 * 2. Validation avec XSD  
 * 3. Comparaison des performances de validation
 * 4. Rapport détaillé des différences
 * 
 * @author Projet PadChest XML - Niveau Expert Final
 */
public class ValidationComparator {
    
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
     * Forcer le garbage collector
     */
    private static void forceGC() {
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Tester la validation DTD
     */
    public static ValidationResult testDTDValidation(String xmlFile, String dtdFile, int iterations) {
        System.out.println("\n" + repeat("=", 60));
        System.out.println("TEST DE VALIDATION DTD");
        System.out.println(repeat("=", 60));
        
        long totalTime = 0;
        long maxMemory = 0;
        boolean isValid = true;
        String errorMessage = "";
        
        for (int i = 0; i < iterations; i++) {
            forceGC();
            long memoryBefore = getUsedMemory();
            
            try {
                long startTime = System.currentTimeMillis();
                
                // Créer parser SAX avec validation DTD
                SAXParserFactory factory = SAXParserFactory.newInstance();
                factory.setValidating(true);  // Activer la validation DTD
                
                SAXParser parser = factory.newSAXParser();
                
                // Handler pour capturer les erreurs
                DefaultHandler handler = new DefaultHandler() {
                    @Override
                    public void error(SAXParseException e) throws SAXException {
                        throw e;
                    }
                    
                    @Override
                    public void fatalError(SAXParseException e) throws SAXException {
                        throw e;
                    }
                };
                
                parser.parse(new File(xmlFile), handler);
                
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                totalTime += executionTime;
                
                long memoryAfter = getUsedMemory();
                long memoryUsed = memoryAfter - memoryBefore;
                if (memoryUsed > maxMemory) {
                    maxMemory = memoryUsed;
                }
                
                System.out.println("Iteration " + (i + 1) + " DTD : " + executionTime + " ms, Memoire: " + 
                                 (memoryUsed / 1024) + " KB - VALIDE");
                
            } catch (Exception e) {
                isValid = false;
                errorMessage = e.getMessage();
                System.out.println("Iteration " + (i + 1) + " DTD : ERREUR - " + e.getMessage());
            }
        }
        
        ValidationResult result = new ValidationResult();
        result.validationType = "DTD";
        result.iterations = iterations;
        result.totalTime = totalTime;
        result.averageTime = (iterations > 0) ? totalTime / iterations : 0;
        result.maxMemory = maxMemory;
        result.isValid = isValid;
        result.errorMessage = errorMessage;
        
        return result;
    }
    
    /**
     * Tester la validation XSD
     */
    public static ValidationResult testXSDValidation(String xmlFile, String xsdFile, int iterations) {
        System.out.println("\n" + repeat("=", 60));
        System.out.println("TEST DE VALIDATION XSD");
        System.out.println(repeat("=", 60));
        
        long totalTime = 0;
        long maxMemory = 0;
        boolean isValid = true;
        String errorMessage = "";
        
        for (int i = 0; i < iterations; i++) {
            forceGC();
            long memoryBefore = getUsedMemory();
            
            try {
                long startTime = System.currentTimeMillis();
                
                // Créer le schéma XSD
                SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
                Schema schema = schemaFactory.newSchema(new File(xsdFile));
                
                // Créer le validateur
                Validator validator = schema.newValidator();
                
                // Valider le fichier XML
                validator.validate(new StreamSource(new File(xmlFile)));
                
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                totalTime += executionTime;
                
                long memoryAfter = getUsedMemory();
                long memoryUsed = memoryAfter - memoryBefore;
                if (memoryUsed > maxMemory) {
                    maxMemory = memoryUsed;
                }
                
                System.out.println("Iteration " + (i + 1) + " XSD : " + executionTime + " ms, Memoire: " + 
                                 (memoryUsed / 1024) + " KB - VALIDE");
                
            } catch (Exception e) {
                isValid = false;
                errorMessage = e.getMessage();
                System.out.println("Iteration " + (i + 1) + " XSD : ERREUR - " + e.getMessage());
            }
        }
        
        ValidationResult result = new ValidationResult();
        result.validationType = "XSD";
        result.iterations = iterations;
        result.totalTime = totalTime;
        result.averageTime = (iterations > 0) ? totalTime / iterations : 0;
        result.maxMemory = maxMemory;
        result.isValid = isValid;
        result.errorMessage = errorMessage;
        
        return result;
    }
    
    /**
     * Tester validation sans schéma (well-formed seulement)
     */
    public static ValidationResult testWellFormedValidation(String xmlFile, int iterations) {
        System.out.println("\n" + repeat("=", 60));
        System.out.println("TEST WELL-FORMED (SANS VALIDATION)");
        System.out.println(repeat("=", 60));
        
        long totalTime = 0;
        long maxMemory = 0;
        boolean isValid = true;
        String errorMessage = "";
        
        for (int i = 0; i < iterations; i++) {
            forceGC();
            long memoryBefore = getUsedMemory();
            
            try {
                long startTime = System.currentTimeMillis();
                
                // Parser simple sans validation
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                builder.parse(new File(xmlFile));
                
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                totalTime += executionTime;
                
                long memoryAfter = getUsedMemory();
                long memoryUsed = memoryAfter - memoryBefore;
                if (memoryUsed > maxMemory) {
                    maxMemory = memoryUsed;
                }
                
                System.out.println("Iteration " + (i + 1) + " WELL-FORMED : " + executionTime + " ms, Memoire: " + 
                                 (memoryUsed / 1024) + " KB - VALIDE");
                
            } catch (Exception e) {
                isValid = false;
                errorMessage = e.getMessage();
                System.out.println("Iteration " + (i + 1) + " WELL-FORMED : ERREUR - " + e.getMessage());
            }
        }
        
        ValidationResult result = new ValidationResult();
        result.validationType = "WELL-FORMED";
        result.iterations = iterations;
        result.totalTime = totalTime;
        result.averageTime = (iterations > 0) ? totalTime / iterations : 0;
        result.maxMemory = maxMemory;
        result.isValid = isValid;
        result.errorMessage = errorMessage;
        
        return result;
    }
    
    /**
     * Générer le rapport de validation expert
     */
    public static void generateValidationReport(ValidationResult wellFormed, ValidationResult dtd, ValidationResult xsd, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            
            writer.println(repeat("=", 80));
            writer.println("RAPPORT DE VALIDATION EXPERT - DTD vs XSD vs WELL-FORMED");
            writer.println(repeat("=", 80));
            writer.println("Rapport genere le : " + new Date());
            writer.println();
            
            // Tableau de comparaison
            writer.println("COMPARAISON DES VALIDATIONS :");
            writer.println(repeat("-", 80));
            writer.printf("%-15s %-10s %-12s %-12s %-15s %-20s%n", 
                         "Type", "Valide", "Temps Moyen", "Memoire Max", "Iterations", "Erreur");
            writer.printf("%-15s %-10s %-12s %-12s %-15s %-20s%n", 
                         repeat("-", 15), repeat("-", 10), repeat("-", 12), repeat("-", 12), repeat("-", 15), repeat("-", 20));
            
            writer.printf("%-15s %-10s %-12d %-12d %-15d %-20s%n", 
                         wellFormed.validationType, 
                         wellFormed.isValid ? "OUI" : "NON",
                         wellFormed.averageTime,
                         wellFormed.maxMemory / 1024,
                         wellFormed.iterations,
                         wellFormed.isValid ? "Aucune" : "Erreur");
            
            writer.printf("%-15s %-10s %-12d %-12d %-15d %-20s%n", 
                         dtd.validationType, 
                         dtd.isValid ? "OUI" : "NON",
                         dtd.averageTime,
                         dtd.maxMemory / 1024,
                         dtd.iterations,
                         dtd.isValid ? "Aucune" : "DTD manquant");
            
            writer.printf("%-15s %-10s %-12d %-12d %-15d %-20s%n", 
                         xsd.validationType, 
                         xsd.isValid ? "OUI" : "NON",
                         xsd.averageTime,
                         xsd.maxMemory / 1024,
                         xsd.iterations,
                         xsd.isValid ? "Aucune" : "XSD manquant");
            
            writer.println();
            
            // Analyse des performances
            writer.println("ANALYSE DES PERFORMANCES :");
            writer.println(repeat("-", 50));
            
            long fastestTime = Math.min(Math.min(wellFormed.averageTime, dtd.averageTime), xsd.averageTime);
            String fastest = "";
            if (wellFormed.averageTime == fastestTime) fastest = "WELL-FORMED";
            else if (dtd.averageTime == fastestTime) fastest = "DTD";
            else fastest = "XSD";
            
            writer.println("Validation la plus rapide : " + fastest + " (" + fastestTime + " ms)");
            
            if (dtd.isValid && xsd.isValid) {
                double dtdVsXsd = (double) xsd.averageTime / dtd.averageTime;
                writer.printf("XSD est %.2fx plus lent que DTD%n", dtdVsXsd);
            }
            
            writer.println();
            
            // Recommandations
            writer.println("RECOMMANDATIONS TECHNIQUES :");
            writer.println(repeat("-", 50));
            writer.println("WELL-FORMED :");
            writer.println("  + Tres rapide (pas de validation)");
            writer.println("  + Faible consommation memoire");
            writer.println("  - Aucune validation de structure");
            writer.println("  - Risque d'erreurs non detectees");
            writer.println();
            
            writer.println("DTD :");
            writer.println("  + Validation rapide et efficace");
            writer.println("  + Syntaxe simple et lisible");
            writer.println("  + Support universel");
            writer.println("  - Types de donnees limites");
            writer.println("  - Pas de namespace");
            writer.println();
            
            writer.println("XSD :");
            writer.println("  + Validation tres stricte");
            writer.println("  + Types de donnees avances");
            writer.println("  + Support des namespaces");
            writer.println("  + Contraintes complexes possibles");
            writer.println("  - Plus lent que DTD");
            writer.println("  - Plus complexe a ecrire");
            writer.println();
            
            // Cas d'usage
            writer.println("CAS D'USAGE RECOMMANDES :");
            writer.println(repeat("-", 50));
            writer.println("Utilisez WELL-FORMED quand :");
            writer.println("  - Performance maximale requise");
            writer.println("  - XML genere automatiquement (fiable)");
            writer.println("  - Validation faite ailleurs");
            writer.println();
            
            writer.println("Utilisez DTD quand :");
            writer.println("  - Validation rapide necessaire");
            writer.println("  - Structure simple a definir");
            writer.println("  - Compatibilite maximale requise");
            writer.println();
            
            writer.println("Utilisez XSD quand :");
            writer.println("  - Validation stricte obligatoire");
            writer.println("  - Types de donnees complexes");
            writer.println("  - Contraintes metier avancees");
            writer.println("  - Namespaces necessaires");
            writer.println();
            
            writer.println(repeat("=", 80));
            writer.println("Fin du rapport de validation expert");
            
            writer.close();
            System.out.println("Rapport de validation sauvegarde dans : " + filename);
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la generation du rapport : " + e.getMessage());
        }
    }
    
    /**
     * Méthode principale
     */
    public static void main(String[] args) {
        System.out.println("COMPARATEUR DE VALIDATION EXPERT - DTD vs XSD");
        System.out.println(repeat("=", 70));
        
        String xmlFile = "../data/processed/test_simple.xml";
        String dtdFile = "../schema/images.dtd";
        String xsdFile = "../schema/images.xsd";
        int iterations = 3;
        
        if (args.length > 0) xmlFile = args[0];
        if (args.length > 1) dtdFile = args[1];
        if (args.length > 2) xsdFile = args[2];
        if (args.length > 3) iterations = Integer.parseInt(args[3]);
        
        System.out.println("Fichier XML : " + xmlFile);
        System.out.println("Fichier DTD : " + dtdFile);
        System.out.println("Fichier XSD : " + xsdFile);
        System.out.println("Iterations : " + iterations);
        
        // Tests de validation
        ValidationResult wellFormed = testWellFormedValidation(xmlFile, iterations);
        ValidationResult dtd = testDTDValidation(xmlFile, dtdFile, iterations);
        ValidationResult xsd = testXSDValidation(xmlFile, xsdFile, iterations);
        
        // Résumé
        System.out.println("\n" + repeat("=", 70));
        System.out.println("RESUME DES VALIDATIONS");
        System.out.println(repeat("=", 70));
        System.out.printf("WELL-FORMED : %s, %d ms moyen%n", 
                         wellFormed.isValid ? "VALIDE" : "INVALIDE", wellFormed.averageTime);
        System.out.printf("DTD         : %s, %d ms moyen%n", 
                         dtd.isValid ? "VALIDE" : "INVALIDE", dtd.averageTime);
        System.out.printf("XSD         : %s, %d ms moyen%n", 
                         xsd.isValid ? "VALIDE" : "INVALIDE", xsd.averageTime);
        
        // Générer rapport
        generateValidationReport(wellFormed, dtd, xsd, "rapport_validation_expert.txt");
    }
    
    /**
     * Classe pour stocker les résultats de validation
     */
    static class ValidationResult {
        String validationType;
        int iterations;
        long totalTime;
        long averageTime;
        long maxMemory;
        boolean isValid;
        String errorMessage;
    }
}
