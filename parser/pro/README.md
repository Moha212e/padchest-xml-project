# Mode Pro - Parser DOM + Comparaisons 🟡

## 🎯 Objectif
Implémenter un **niveau professionnel** avec parser DOM et comparaisons avancées.

## 📋 Fonctionnalités
- ✅ **Parser DOM** : Navigation complète de l'arbre XML
- ✅ **Mêmes calculs que SAX** : Pour comparaison des résultats
- ✅ **Navigation flexible** : Accès libre aux éléments
- ✅ **Génération stats_dom.txt** : Résultats DOM séparés
- ✅ **Comparaison implicite** : Validation des deux approches

## 🚀 Utilisation

### Compilation et exécution
```bash
# Compilation
javac PadChestParserDOM.java

# Exécution
java PadChestParserDOM ../../data/processed/test_simple.xml
```

### Comparaison avec SAX
```bash
# Exécuter les deux parsers
java PadChestParserSimple ../../data/processed/test_simple.xml
java PadChestParserDOM ../../data/processed/test_simple.xml

# Comparer les résultats
diff stats.txt stats_dom.txt
```

## 📊 Avantages du DOM

### Navigation
- **Accès libre** : Parcours dans tous les sens
- **Recherche flexible** : XPath-like avec Java
- **Modification** : Possibilité d'éditer l'arbre
- **Structure** : Vision globale du document

### Inconvénients
- **Mémoire** : Charge tout le document
- **Vitesse** : Plus lent pour gros fichiers
- **Complexité** : Plus de code nécessaire

## 🔧 Architecture technique

### Classe principale
- `PadChestParserDOM` : Parser DOM avec navigation

### Méthodes DOM utilisées
- `DocumentBuilder.parse()` : Chargement du document
- `getElementsByTagName()` : Sélection d'éléments
- `getAttribute()` : Lecture d'attributs
- `getTextContent()` : Contenu textuel

### Stratégie de parsing
1. **Chargement** : Document entier en mémoire
2. **Navigation** : Parcours des éléments `<image>`
3. **Extraction** : Données via navigation DOM
4. **Calculs** : Mêmes algorithmes que SAX

## 📊 Comparaison SAX vs DOM

| Critère | SAX | DOM |
|---------|-----|-----|
| **Mémoire** | Très faible | Élevée |
| **Vitesse** | Rapide | Plus lent |
| **Navigation** | Séquentielle | Libre |
| **Modification** | Impossible | Possible |
| **Complexité** | Simple | Moyenne |

## 🎯 Cas d'usage DOM
- **Modification** de documents XML
- **Navigation complexe** entre éléments
- **Validation croisée** de données
- **Génération** de nouveaux XML

## 📝 Exemple de code DOM

```java
// Navigation DOM typique
Document doc = builder.parse(xmlFile);
NodeList images = doc.getElementsByTagName("image");

for (int i = 0; i < images.getLength(); i++) {
    Element image = (Element) images.item(i);
    String imageId = image.getAttribute("id");
    
    // Navigation vers les enfants
    NodeList localizations = image.getElementsByTagName("localizations");
    if (localizations.getLength() > 0) {
        String locText = localizations.item(0).getTextContent();
        // Traitement...
    }
}
```

## 🎯 Niveau de difficulté
**🟡 INTERMÉDIAIRE** - Nécessite compréhension DOM

## 📚 Concepts appris
- Document Object Model (DOM)
- Navigation d'arbre XML
- Comparaison d'approches parsing
- Gestion mémoire vs performance

---

**💡 Conseil** : Comparez toujours vos résultats avec le mode Simple !
