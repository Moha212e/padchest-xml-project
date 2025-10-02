# Mode Simple - Parser SAX Basique 🟢

## 🎯 Objectif
Implémenter le **minimum requis** pour la Phase 4 : un parser SAX simple qui effectue les calculs de base.

## 📋 Fonctionnalités
- ✅ **Parser SAX** : Parsing séquentiel efficace
- ✅ **Comptage "loc right"** : Nombre d'images avec localisation droite
- ✅ **Top 10 labels** : Labels les plus fréquents avec leurs occurrences
- ✅ **Génération stats.txt** : Sauvegarde automatique des résultats

## 🚀 Utilisation

### Compilation et exécution
```bash
# Compilation
javac PadChestParserSimple.java

# Exécution
java PadChestParserSimple ../../data/processed/test_simple.xml
```

### Résultats attendus
Le programme génère :
1. **Affichage console** : Statistiques en temps réel
2. **Fichier stats.txt** : Résultats détaillés sauvegardés

## 📊 Exemple de sortie

### Console
```
=== PARSER SAX SIMPLE - ANALYSE XML ===
Fichier analysé : ../../data/processed/test_simple.xml
Images traitées : 100
Images avec 'loc right' : 25
Top 10 des labels calculé
Résultats sauvegardés dans : stats.txt
```

### Fichier stats.txt
```
=== STATISTIQUES PADCHEST XML ===
Nombre total d'images : 100
Nombre d'images avec 'loc right' : 25

=== TOP 10 DES LABELS ===
1. normal - 45 occurrences
2. pneumonia - 23 occurrences
3. infiltration - 18 occurrences
...
```

## 🔧 Architecture technique

### Classe principale
- `PadChestParserSimple` : Parser SAX avec handler intégré

### Méthodes SAX implémentées
- `startElement()` : Détection des balises ouvrantes
- `characters()` : Lecture du contenu textuel
- `endElement()` : Traitement des balises fermantes

### Variables de comptage
- `totalImages` : Compteur total d'images
- `rightLocalizationsCount` : Compteur "loc right"
- `allLabels` : Liste de tous les labels collectés

## 📝 Points clés
- **Simplicité** : Code clair et commenté
- **Efficacité** : Parsing en une seule passe
- **Mémoire** : Faible consommation (streaming)
- **Robustesse** : Gestion d'erreurs basique

## 🎯 Niveau de difficulté
**🟢 DÉBUTANT** - Parfait pour commencer avec SAX

## 📚 Concepts appris
- Parsing SAX (Simple API for XML)
- Gestion d'événements XML
- Traitement de données textuelles
- Génération de statistiques

---

**💡 Conseil** : Maîtrisez ce mode avant de passer au niveau Pro !
