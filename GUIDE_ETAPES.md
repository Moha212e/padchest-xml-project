# 📋 Guide d'étapes pédagogiques - Projet PadChest XML

## 🎯 Vue d'ensemble
Ce guide vous accompagne étape par étape dans la réalisation du projet PadChest XML, de la conversion CSV vers XML jusqu'à la présentation finale.

---

## 📊 Phase 1 : Préparation et organisation (Semaine 1)

### Étape 1.1 : Analyse du fichier CSV
**Objectif** : Comprendre la structure des données
- [ ] Télécharger le fichier `PADCHEST_chest_x_ray_images_labels_160K_01.02.19.csv`
- [ ] Placer le fichier dans `data/raw/PADCHEST_chest_x_ray_images_labels_160K_01.02.19/`
- [ ] Ouvrir le fichier avec Excel ou un éditeur de texte
- [ ] Identifier les 36 champs disponibles
- [ ] Analyser les exemples de données (ligne 2 du fichier)

**Champs à identifier** :
- ImageID, ImageDir, StudyDate_DICOM, StudyID, PatientID
- PatientBirth, PatientSex_DICOM, ViewPosition_DICOM, Projection
- MethodProjection, Pediatric, Modality_DICOM, Manufacturer_DICOM
- Et 24 autres champs...

### Étape 1.2 : Choix des champs à conserver
**Objectif** : Sélectionner les champs pertinents pour le XML
- [ ] Lister les champs essentiels (minimum 10-15 champs)
- [ ] Justifier le choix de chaque champ
- [ ] Documenter les décisions dans `docs/field_selection.md`

**Champs recommandés** :
- ImageID (obligatoire)
- PatientID, PatientSex_DICOM, PatientBirth
- StudyDate_DICOM, ViewPosition_DICOM
- Labels, Localizations
- Report (texte du rapport)

### Étape 1.3 : Définition de la structure XML
**Objectif** : Concevoir l'architecture du document XML
- [ ] Créer un diagramme de la hiérarchie XML
- [ ] Définir les éléments racines et enfants
- [ ] Choisir les attributs vs éléments
- [ ] Prévoir au moins 1 attribut obligatoire

**Structure suggérée** :
```xml
<images>
  <image id="[ImageID]">
    <patient>
      <id>[PatientID]</id>
      <sex>[PatientSex_DICOM]</sex>
      <birth>[PatientBirth]</birth>
    </patient>
    <study>
      <date>[StudyDate_DICOM]</date>
      <position>[ViewPosition_DICOM]</position>
    </study>
    <labels>[Labels]</labels>
    <localizations>[Localizations]</localizations>
    <report>[Report]</report>
  </image>
</images>
```

---

## 🔄 Phase 2 : Conversion CSV → XML (Semaine 2)

### Étape 2.1 : Choix du langage de programmation
**Objectif** : Sélectionner l'outil de conversion
- [ ] Évaluer les options : Java, C, C++, PL/SQL
- [ ] Choisir selon vos compétences
- [ ] Installer l'environnement de développement

**Recommandation** : Java (plus simple pour débuter)

### Étape 2.2 : Développement du programme de conversion
**Objectif** : Créer le convertisseur CSV → XML

**Fonctions à implémenter** :
- `readCSVFile(String filename)` : Lecture du fichier CSV
- `parseCSVLine(String line)` : Parsing d'une ligne CSV
- `extractFields(String[] fields)` : Extraction des champs sélectionnés
- `createXMLElement(String[] data)` : Création d'un élément XML
- `writeXMLFile(String filename, String xmlContent)` : Écriture du fichier XML
- `main(String[] args)` : Fonction principale

**Logique de traitement** :
1. Ouvrir le fichier CSV
2. Lire ligne par ligne (ignorer l'en-tête)
3. Parser chaque ligne avec gestion des virgules
4. Extraire les champs sélectionnés
5. Générer le XML correspondant
6. Sauvegarder dans `data/processed/images.xml`

### Étape 2.3 : Test et validation
**Objectif** : Vérifier la qualité du XML généré
- [ ] Exécuter le programme de conversion
- [ ] Vérifier que le fichier XML est bien formé
- [ ] Contrôler quelques enregistrements manuellement
- [ ] Compter le nombre d'images converties

---

## 📐 Phase 3 : Définition de la structure (Semaine 3)

### Étape 3.1 : Création du DTD
**Objectif** : Définir la structure formelle du XML

**Fichier à créer** : `schema/images.dtd`

**Éléments à définir** :
- `<!ELEMENT images (image+)>`
- `<!ELEMENT image (patient, study, labels, localizations, report)>`
- `<!ELEMENT patient (id, sex, birth)>`
- `<!ELEMENT study (date, position)>`
- `<!ATTLIST image id CDATA #REQUIRED>`

**Validation** :
- [ ] Écrire le DTD à la main (pas d'outil automatique)
- [ ] Tester avec un éditeur XML
- [ ] Vérifier la cohérence avec le XML généré

### Étape 3.2 : Création du XSD (Niveau Pro)
**Objectif** : Schéma XML plus avancé

**Fichier à créer** : `schema/images.xsd`

**Éléments à définir** :
- `<xs:element name="images">`
- `<xs:complexType name="imageType">`
- `<xs:attribute name="id" type="xs:string" use="required"/>`
- Types de données : `xs:string`, `xs:date`, `xs:integer`

**Validation** :
- [ ] Définir les types de données appropriés
- [ ] Ajouter des contraintes (minLength, maxLength)
- [ ] Tester la validation

### Étape 3.3 : Validation du XML
**Objectif** : Vérifier la conformité du XML

**Outils de validation** :
- Éditeur XML (XMLSpy, Oxygen, etc.)
- Ligne de commande : `xmllint --dtdvalid images.dtd images.xml`
- En ligne : W3C Validator

**Tests à effectuer** :
- [ ] Validation avec DTD
- [ ] Validation avec XSD (si créé)
- [ ] Vérification de la structure
- [ ] Contrôle des attributs obligatoires

---

## ☕ Phase 4 : Parser Java (Semaine 4)

### Étape 4.1 : Parser SAX (Minimum requis)
**Objectif** : Implémenter un parser SAX pour validation et calculs

**Classes à créer** :
- `SAXParserHandler extends DefaultHandler`
- `XMLParser` (classe principale)

**Méthodes SAX à implémenter** :
- `startElement(String uri, String localName, String qName, Attributes attributes)`
- `characters(char[] ch, int start, int length)`
- `endElement(String uri, String localName, String qName)`

**Fonctions de calcul** :
- `countRightLocalizations()` : Compter les "loc right"
- `extractLabels()` : Extraire tous les labels
- `calculateLabelFrequencies()` : Calculer les fréquences
- `getTop10Labels()` : Top 10 des labels
- `writeStatsToFile(String filename)` : Sauvegarder les résultats

### Étape 4.2 : Parser DOM (Niveau Pro)
**Objectif** : Implémenter un parser DOM alternatif

**Classes à créer** :
- `DOMParser`
- `XMLAnalyzer`

**Méthodes DOM à implémenter** :
- `parseXML(String filename)` : Chargement du document
- `getElementsByTagName(String tagName)` : Sélection d'éléments
- `getAttribute(String attributeName)` : Récupération d'attributs
- `getTextContent()` : Contenu textuel

**Fonctions de calcul** :
- `analyzeWithDOM()` : Analyse complète avec DOM
- `compareResults()` : Comparaison avec SAX

### Étape 4.3 : Analyse de performance (Niveau Expert)
**Objectif** : Comparer les performances SAX vs DOM

**Métriques à mesurer** :
- Temps d'exécution (`System.currentTimeMillis()`)
- Consommation mémoire (`Runtime.getRuntime().totalMemory()`)
- Temps de validation DTD vs XSD

**Fonctions de mesure** :
- `measureExecutionTime(Runnable task)` : Mesure du temps
- `measureMemoryUsage()` : Mesure de la mémoire
- `generatePerformanceReport()` : Rapport de performance

**Fichier de sortie** : `results/stats/stats.txt`

---

## 🎨 Phase 5 : Transformation XSLT → HTML (Semaine 5)

### Étape 5.1 : Feuille de style basique (Minimum)
**Objectif** : Créer une transformation XSLT fonctionnelle

**Fichier à créer** : `xslt/basic/view.xsl`

**Templates XSLT à implémenter** :
- `<xsl:template match="/">` : Template racine
- `<xsl:template match="images">` : Template pour la liste
- `<xsl:template match="image">` : Template pour chaque image
- `<xsl:template match="patient">` : Template patient
- `<xsl:template match="study">` : Template étude

**Éléments HTML à générer** :
- `<table>` : Tableau principal
- `<tr>`, `<td>` : Lignes et cellules
- `<h1>`, `<h2>` : Titres
- `<p>` : Paragraphes

### Étape 5.2 : Mise en forme avancée (Niveau Pro)
**Objectif** : Améliorer la présentation

**Fichier à créer** : `xslt/advanced/view.xsl`

**Améliorations à ajouter** :
- CSS intégré dans le XSLT
- Classes CSS pour le style
- Structure HTML sémantique
- Navigation entre les images

**Fonctions XSLT avancées** :
- `<xsl:sort>` : Tri des données
- `<xsl:if>` : Conditions
- `<xsl:choose>` : Choix multiples
- `<xsl:for-each-group>` : Groupement

### Étape 5.3 : Fonctionnalités expertes (Niveau Expert)
**Objectif** : Ajouter des fonctionnalités interactives

**Fichier à créer** : `xslt/advanced/view.xsl` (version complète)

**Fonctionnalités à implémenter** :
- JavaScript pour la recherche
- Filtres dynamiques
- Pagination
- Export des données
- Graphiques (Chart.js)

**Fichiers à créer** :
- `web/xampp/index.html` : Page principale
- `web/xampp/styles.css` : Feuille de style
- `web/xampp/script.js` : JavaScript

### Étape 5.4 : Déploiement web
**Objectif** : Mettre en ligne le site

**Configuration XAMPP** :
- [ ] Installer XAMPP
- [ ] Copier les fichiers dans `htdocs/`
- [ ] Configurer Apache
- [ ] Tester l'accès via navigateur

---

## 🔍 Phase 6 : BaseX et XQuery (Semaine 6)

### Étape 6.1 : Installation et configuration BaseX
**Objectif** : Préparer l'environnement BaseX

**Actions à effectuer** :
- [ ] Télécharger et installer BaseX
- [ ] Lancer BaseX (interface graphique ou ligne de commande)
- [ ] Créer une nouvelle base de données
- [ ] Importer le fichier XML

**Commandes BaseX** :
- `CREATE DB padchest images.xml`
- `OPEN padchest`
- `INFO`

### Étape 6.2 : Requêtes XQuery de base
**Objectif** : Implémenter les 2 requêtes obligatoires

**Fichier à créer** : `xquery/basic/queries.xq`

**Requête 1 : Compter les "loc right"**
```xquery
count(//image[contains(localizations, 'loc right')])
```

**Requête 2 : Top 10 des labels**
```xquery
let $labels := //image/labels
let $all-labels := tokenize(string-join($labels, '|'), '\|')
let $frequencies := 
  for $label in distinct-values($all-labels)
  return <label name="{$label}" count="{count($all-labels[. = $label])}"/>
return 
  <top10>
    {for $freq in $frequencies
     order by xs:integer($freq/@count) descending
     return $freq}[position() <= 10]
  </top10>
```

### Étape 6.3 : Webservice BaseX (Niveau Pro)
**Objectif** : Exposer les requêtes via HTTP

**Configuration** :
- [ ] Activer le serveur HTTP de BaseX
- [ ] Configurer les ports (8984 par défaut)
- [ ] Créer des endpoints REST

**URLs à tester** :
- `http://localhost:8984/rest/padchest`
- `http://localhost:8984/rest/padchest?query=...`

### Étape 6.4 : Intégration avec XSLT (Niveau Expert)
**Objectif** : Appeler BaseX depuis la page HTML

**Fonctions JavaScript à implémenter** :
- `callBaseXQuery(query)` : Appel des requêtes
- `updateStatistics()` : Mise à jour des stats
- `refreshData()` : Actualisation des données

**Fichier à créer** : `web/xampp/basex-integration.js`

---

## 📦 Phase 7 : Finalisation et livraison (Semaine 7)

### Étape 7.1 : Rassemblement des livrables
**Objectif** : Préparer le paquet final

**Fichiers à rassembler dans `deliverables/`** :
- [ ] `images.xml` (conversion réussie)
- [ ] `images.dtd` (structure validée)
- [ ] `images.xsd` (si niveau pro)
- [ ] `Parser.java` (avec stats.txt)
- [ ] `view.xsl` (transformation fonctionnelle)
- [ ] `index.html` (page web opérationnelle)
- [ ] `queries.xq` (requêtes BaseX)
- [ ] Captures d'écran des résultats

### Étape 7.2 : Documentation finale
**Objectif** : Compléter la documentation

**Fichiers à créer** :
- `README.md` complet avec instructions
- `docs/technical_report.md` : Rapport technique
- `docs/user_manual.md` : Manuel utilisateur
- `docs/installation_guide.md` : Guide d'installation

### Étape 7.3 : Tests et validation
**Objectif** : Vérifier le bon fonctionnement

**Tests à effectuer** :
- [ ] Conversion CSV → XML
- [ ] Validation DTD/XSD
- [ ] Parser Java (calculs corrects)
- [ ] Transformation XSLT → HTML
- [ ] Requêtes BaseX
- [ ] Site web fonctionnel

### Étape 7.4 : Préparation de la présentation
**Objectif** : Se préparer pour la démonstration

**Points à préparer** :
- [ ] Démonstration du processus complet
- [ ] Explication des choix techniques
- [ ] Présentation des résultats
- [ ] Réponses aux questions potentielles

**Captures d'écran à prendre** :
- [ ] Résultats du parser (stats.txt)
- [ ] Interface BaseX avec requêtes
- [ ] Page HTML générée
- [ ] Validation XML réussie

### Étape 7.5 : Envoi final
**Objectif** : Livrer le projet

**Actions finales** :
- [ ] Compression du dossier `deliverables/`
- [ ] Vérification de tous les fichiers
- [ ] Envoi par email à `samuel.hiard@hepl.be`
- [ ] Confirmation de réception

---

## 🎯 Checklist de progression

### Phase 1 - Préparation
- [ ] Fichier CSV analysé
- [ ] Champs sélectionnés
- [ ] Structure XML définie

### Phase 2 - Conversion
- [ ] Programme de conversion développé
- [ ] Fichier XML généré
- [ ] Conversion testée

### Phase 3 - Structure
- [ ] DTD créé et validé
- [ ] XSD créé (niveau pro)
- [ ] XML validé

### Phase 4 - Parser
- [ ] Parser SAX implémenté
- [ ] Parser DOM implémenté (niveau pro)
- [ ] Performance mesurée (niveau expert)
- [ ] Stats calculées et sauvegardées

### Phase 5 - XSLT
- [ ] Transformation basique fonctionnelle
- [ ] Mise en forme avancée (niveau pro)
- [ ] Fonctionnalités expertes (niveau expert)
- [ ] Site web déployé

### Phase 6 - XQuery
- [ ] BaseX configuré
- [ ] Requêtes implémentées
- [ ] Webservice configuré (niveau pro)
- [ ] Intégration HTML (niveau expert)

### Phase 7 - Livraison
- [ ] Tous les livrables rassemblés
- [ ] Documentation complète
- [ ] Tests effectués
- [ ] Projet envoyé

---

## 📚 Ressources utiles

### Documentation
- [W3C XML Tutorial](https://www.w3schools.com/xml/)
- [XSLT Reference](https://www.w3schools.com/xml/xsl_intro.asp)
- [XQuery Tutorial](https://www.w3schools.com/xml/xquery_intro.asp)
- [BaseX Documentation](https://docs.basex.org/)

### Outils recommandés
- **Éditeurs XML** : XMLSpy, Oxygen XML Editor, Visual Studio Code
- **Validation** : W3C Validator, xmllint
- **Serveur web** : XAMPP, WAMP, MAMP
- **BaseX** : Interface graphique + ligne de commande

### Conseils pratiques
- Commencez simple, complexifiez progressivement
- Testez chaque étape avant de passer à la suivante
- Sauvegardez régulièrement vos fichiers
- Documentez vos choix techniques
- Préparez-vous aux questions sur vos décisions

---

**🎉 Bon courage et amusez-vous bien !**
