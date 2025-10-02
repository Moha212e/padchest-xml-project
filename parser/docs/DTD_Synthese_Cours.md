# 📚 Cours de Synthèse : DTD (Document Type Definition)

## 🎯 Introduction

Un **DTD (Document Type Definition)** est un document qui définit la structure et les règles d'un document XML. C'est comme un "contrat" qui dit : "Voici comment votre XML doit être organisé pour être valide."

## 📋 Table des matières

1. [Qu'est-ce qu'un DTD ?](#quest-ce-quun-dtd-)
2. [Structure de base d'un DTD](#structure-de-base-dun-dtd)
3. [Déclaration des éléments](#déclaration-des-éléments)
4. [Déclaration des attributs](#déclaration-des-attributs)
5. [Types de contenu](#types-de-contenu)
6. [Exemple pratique : DTD PadChest](#exemple-pratique--dtd-padchest)
7. [Validation XML avec DTD](#validation-xml-avec-dtd)
8. [Bonnes pratiques](#bonnes-pratiques)

---

## 🔍 Qu'est-ce qu'un DTD ?

### Définition
Un DTD est un fichier qui :
- **Définit** la structure d'un document XML
- **Spécifie** quels éléments sont autorisés
- **Détermine** l'ordre et la hiérarchie des éléments
- **Contrôle** les attributs de chaque élément
- **Valide** la conformité d'un XML

### Pourquoi utiliser un DTD ?
- ✅ **Validation** : Vérifier que le XML est correct
- ✅ **Documentation** : Expliquer la structure attendue
- ✅ **Contrôle qualité** : Éviter les erreurs de structure
- ✅ **Interopérabilité** : Standardiser l'échange de données

---

## 🏗️ Structure de base d'un DTD

### Syntaxe générale
```dtd
<!ELEMENT nomElement (contenu)>
<!ATTLIST nomElement 
    attribut1 type #obligatoire
    attribut2 type #optionnel>
```

### Exemple simple
```dtd
<!ELEMENT livre (titre, auteur, prix)>
<!ATTLIST livre 
    isbn CDATA #REQUIRED
    edition CDATA #IMPLIED>
```

---

## 📝 Déclaration des éléments

### 1. Élément avec contenu textuel
```dtd
<!ELEMENT nom (#PCDATA)>
```
**Signification** : L'élément contient du texte simple.

### 2. Élément avec sous-éléments
```dtd
<!ELEMENT personne (nom, age, adresse)>
```
**Signification** : L'élément `personne` contient exactement `nom`, `age`, et `adresse` dans cet ordre.

### 3. Élément vide
```dtd
<!ELEMENT image EMPTY>
```
**Signification** : L'élément n'a pas de contenu.

### 4. Élément avec contenu mixte
```dtd
<!ELEMENT paragraphe (#PCDATA | gras | italique)*>
```
**Signification** : L'élément peut contenir du texte et des éléments `gras` ou `italique`.

### 5. Quantificateurs
```dtd
<!ELEMENT liste (item+)>        <!-- Un ou plusieurs items -->
<!ELEMENT menu (option*)>       <!-- Zéro ou plusieurs options -->
<!ELEMENT choix (a | b | c)>    <!-- Un seul parmi a, b, ou c -->
```

---

## 🏷️ Déclaration des attributs

### Syntaxe de base
```dtd
<!ATTLIST nomElement 
    attribut1 type #obligatoire
    attribut2 type #optionnel
    attribut3 type valeurParDefaut>
```

### Types d'attributs

#### 1. CDATA (texte libre)
```dtd
<!ATTLIST livre 
    titre CDATA #REQUIRED>
```

#### 2. ID (identifiant unique)
```dtd
<!ATTLIST livre 
    id ID #REQUIRED>
```

#### 3. IDREF (référence à un ID)
```dtd
<!ATTLIST auteur 
    livreRef IDREF #IMPLIED>
```

#### 4. Énumération
```dtd
<!ATTLIST livre 
    categorie (roman|essai|poesie) #REQUIRED>
```

#### 5. NMTOKEN (nom de token)
```dtd
<!ATTLIST livre 
    langue NMTOKEN #IMPLIED>
```

### Modificateurs d'attributs

| Modificateur | Signification | Obligatoire | Valeur par défaut | Exemple | Usage |
|--------------|---------------|-------------|-------------------|---------|-------|
| `#REQUIRED` | **Obligatoire** | ✅ Oui | ❌ Non | `id CDATA #REQUIRED` | Identifiants, données critiques |
| `#IMPLIED` | **Optionnel** | ❌ Non | ❌ Non | `imageDir CDATA #IMPLIED` | Métadonnées optionnelles |
| `#FIXED "valeur"` | **Valeur fixe** | ✅ Oui | ✅ Oui (toujours cette valeur) | `version CDATA #FIXED "1.0"` | Versions, constantes |
| `"valeur"` | **Valeur par défaut** | ❌ Non | ✅ Oui (si absent) | `status CDATA "active"` | Valeurs par défaut courantes |

### Exemples pratiques des modificateurs

#### 1. Attribut obligatoire (`#REQUIRED`)
```dtd
<!ATTLIST image 
    id CDATA #REQUIRED>  <!-- Toujours présent -->
```

**XML valide :**
```xml
<image id="12345">  <!-- ✅ id obligatoire -->
  <!-- contenu -->
</image>
```

**XML invalide :**
```xml
<image>  <!-- ❌ id manque - ERREUR -->
  <!-- contenu -->
</image>
```

#### 2. Attribut optionnel (`#IMPLIED`)
```dtd
<!ATTLIST image 
    imageDir CDATA #IMPLIED>  <!-- Peut être absent -->
```

**XML valide :**
```xml
<!-- Avec attribut -->
<image id="12345" imageDir="0">
  <!-- contenu -->
</image>

<!-- Sans attribut -->
<image id="12345">
  <!-- contenu -->
</image>
```

#### 3. Attribut avec valeur fixe (`#FIXED`)
```dtd
<!ATTLIST images 
    version CDATA #FIXED "1.0">  <!-- Toujours "1.0" -->
```

**XML valide :**
```xml
<images version="1.0">  <!-- ✅ version = "1.0" -->
  <!-- contenu -->
</images>

<!-- Ou sans attribut (valeur fixe appliquée) -->
<images>
  <!-- contenu -->
</images>
```

#### 4. Attribut avec valeur par défaut
```dtd
<!ATTLIST patient 
    anonymized (true|false) "true">  <!-- "true" si absent -->
```

**XML valide :**
```xml
<!-- Avec valeur explicite -->
<patient anonymized="false">
  <!-- contenu -->
</patient>

<!-- Sans attribut (valeur par défaut "true") -->
<patient>
  <!-- contenu -->
</patient>
```

---

## 📊 Types de contenu

### 1. Contenu simple
```dtd
<!ELEMENT nom (#PCDATA)>
```

### 2. Contenu complexe
```dtd
<!ELEMENT livre (titre, auteur+, chapitre*)>
```

### 3. Contenu vide
```dtd
<!ELEMENT image EMPTY>
```

### 4. Contenu mixte
```dtd
<!ELEMENT paragraphe (#PCDATA | gras | italique)*>
```

### 5. Contenu ANY
```dtd
<!ELEMENT document ANY>
```
**⚠️ Attention** : `ANY` permet n'importe quel contenu (peu recommandé).

---

## 🏥 Exemple pratique : DTD PadChest

### Contexte
Nous créons un DTD pour des images radiographiques thoraciques avec 16 champs essentiels.

### Structure XML cible
```xml
<images>
  <image id="12345" imageDir="0">
    <patient>
      <id>67890</id>
      <birth>1930.0</birth>
    </patient>
    <study>
      <studyId>11111</studyId>
      <projection>PA</projection>
      <pediatric>No</pediatric>
      <methodProjection>Manual review</methodProjection>
    </study>
    <diagnosis>
      <labels>
        <label cui="C0034069">pulmonary fibrosis</label>
        <label cui="C0742362">chronic changes</label>
      </labels>
      <localizations>
        <location cui="C1282378">loc basal</location>
      </localizations>
      <labelsLocalizationsBySentence>[['pulmonary fibrosis', 'loc basal']]</labelsLocalizationsBySentence>
      <methodLabel>Physician</methodLabel>
      <reportId>4991845</reportId>
    </diagnosis>
    <report>changements pulmonaires chroniques...</report>
  </image>
</images>
```

### DTD correspondant
```dtd
<?xml version="1.0" encoding="UTF-8"?>
<!-- DTD pour le projet PadChest XML -->

<!-- Élément racine -->
<!ELEMENT images (image+)>
<!ATTLIST images 
    version CDATA #IMPLIED
    created CDATA #IMPLIED>

<!-- Élément image principal -->
<!ELEMENT image (patient, study, diagnosis, report)>
<!ATTLIST image 
    id CDATA #REQUIRED        <!-- ATTRIBUT OBLIGATOIRE -->
    imageDir CDATA #IMPLIED>

<!-- Informations patient -->
<!ELEMENT patient (id, birth)>
<!ATTLIST patient 
    anonymized (true|false) "true">

<!ELEMENT id (#PCDATA)>
<!ELEMENT birth (#PCDATA)>

<!-- Informations étude -->
<!ELEMENT study (studyId, projection, pediatric, methodProjection)>
<!ELEMENT studyId (#PCDATA)>
<!ELEMENT projection (#PCDATA)>
<!ELEMENT pediatric (#PCDATA)>
<!ELEMENT methodProjection (#PCDATA)>

<!-- Diagnostic -->
<!ELEMENT diagnosis (labels, localizations, labelsLocalizationsBySentence, methodLabel, reportId)>
<!ELEMENT labels (label*)>
<!ELEMENT label (#PCDATA)>
<!ATTLIST label 
    cui CDATA #IMPLIED>

<!ELEMENT localizations (location*)>
<!ELEMENT location (#PCDATA)>
<!ATTLIST location 
    cui CDATA #IMPLIED>

<!ELEMENT labelsLocalizationsBySentence (#PCDATA)>
<!ELEMENT methodLabel (#PCDATA)>
<!ELEMENT reportId (#PCDATA)>

<!-- Rapport -->
<!ELEMENT report (#PCDATA)>
<!ATTLIST report 
    language CDATA #IMPLIED
    length CDATA #IMPLIED>
```

### Analyse du DTD PadChest

#### 1. **Attribut obligatoire**
```dtd
<!ATTLIST image 
    id CDATA #REQUIRED>  <!-- Conforme aux exigences du projet -->
```

#### 2. **Gestion des tableaux**
```dtd
<!ELEMENT labels (label*)>  <!-- Zéro ou plusieurs labels -->
<!ELEMENT label (#PCDATA)>
<!ATTLIST label 
    cui CDATA #IMPLIED>  <!-- CUI pour chaque label -->
```

#### 3. **Structure hiérarchique**
```
images
└── image (id obligatoire)
    ├── patient
    │   ├── id
    │   └── birth
    ├── study
    │   ├── studyId
    │   ├── projection
    │   ├── pediatric
    │   └── methodProjection
    ├── diagnosis
    │   ├── labels (label*)
    │   ├── localizations (location*)
    │   ├── labelsLocalizationsBySentence
    │   ├── methodLabel
    │   └── reportId
    └── report
```

---

## ✅ Validation XML avec DTD

### 1. Lier un DTD à un XML
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE images SYSTEM "images.dtd">
<images>
  <!-- contenu XML -->
</images>
```

### 2. DTD interne
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE images [
<!ELEMENT images (image+)>
<!ELEMENT image (patient, study, diagnosis, report)>
<!ATTLIST image id CDATA #REQUIRED>
]>
<images>
  <!-- contenu XML -->
</images>
```

### 3. Validation avec xmllint
```bash
xmllint --dtdvalid images.dtd images.xml
```

### 4. Exemple de validation
```xml
<!-- ✅ VALIDE -->
<image id="12345">
  <patient>
    <id>67890</id>
    <birth>1930.0</birth>
  </patient>
  <!-- ... autres éléments ... -->
</image>

<!-- ❌ INVALIDE - manque l'attribut id obligatoire -->
<image>
  <patient>
    <id>67890</id>
    <birth>1930.0</birth>
  </patient>
</image>
```

---

## 🎯 Bonnes pratiques

### 1. **Nommage des éléments**
```dtd
<!-- ✅ BON -->
<!ELEMENT patient (id, birth)>
<!ELEMENT study (studyId, projection)>

<!-- ❌ ÉVITER -->
<!ELEMENT p (i, b)>
<!ELEMENT s (si, proj)>
```

### 2. **Commentaires explicatifs**
```dtd
<!-- Élément patient : données démographiques du patient -->
<!ELEMENT patient (id, birth)>
<!-- Identifiant unique du patient (PatientID du CSV) -->
<!ELEMENT id (#PCDATA)>
```

### 3. **Gestion des attributs**
```dtd
<!-- ✅ BON - attribut obligatoire clairement marqué -->
<!ATTLIST image 
    id CDATA #REQUIRED        <!-- ImageID du CSV - OBLIGATOIRE -->
    imageDir CDATA #IMPLIED>  <!-- ImageDir du CSV - optionnel -->

<!-- ❌ ÉVITER - tous les attributs optionnels -->
<!ATTLIST image 
    id CDATA #IMPLIED
    imageDir CDATA #IMPLIED>
```

### 4. **Structure logique**
```dtd
<!-- ✅ BON - hiérarchie logique -->
<!ELEMENT image (patient, study, diagnosis, report)>

<!-- ❌ ÉVITER - structure confuse -->
<!ELEMENT image (report, patient, study, diagnosis)>
```

### 5. **Gestion des données complexes**
```dtd
<!-- ✅ BON - décomposition des tableaux -->
<!ELEMENT labels (label*)>
<!ELEMENT label (#PCDATA)>
<!ATTLIST label 
    cui CDATA #IMPLIED>

<!-- ❌ ÉVITER - tout dans un seul élément -->
<!ELEMENT labels (#PCDATA)>
```

---

## 🚀 Exercices pratiques

### Exercice 1 : Créer un DTD simple
Créez un DTD pour un catalogue de produits avec :
- Élément racine `catalogue`
- Chaque produit a un `id` (obligatoire), `nom`, `prix`, `categorie`
- La catégorie peut être "livre", "cd", ou "dvd"

### Exercice 2 : Valider un XML
Créez un fichier XML basé sur le DTD PadChest et validez-le.

### Exercice 3 : Modifier le DTD
Ajoutez un élément `technique` au DTD PadChest avec des métadonnées DICOM.

---

## 📚 Résumé

### Points clés à retenir
1. **DTD = Contrat de structure** pour les documents XML
2. **Attribut obligatoire** : `#REQUIRED`
3. **Éléments** : `<!ELEMENT nom (contenu)>`
4. **Attributs** : `<!ATTLIST nom attribut type #obligatoire>`
5. **Validation** : Vérifier la conformité XML ↔ DTD

### Dans le contexte PadChest
- ✅ **16 champs essentiels** sélectionnés
- ✅ **Attribut obligatoire** `id` sur `<image>`
- ✅ **Structure hiérarchique** logique
- ✅ **Gestion des tableaux** (Labels, Localizations)
- ✅ **Commentaires détaillés** pour chaque ligne

---

## 🔗 Ressources complémentaires

- [W3C DTD Tutorial](https://www.w3schools.com/xml/xml_dtd_intro.asp)
- [XML Validator](https://www.w3schools.com/xml/xml_validator.asp)
- [DTD Reference](https://www.w3schools.com/xml/xml_dtd_elements.asp)

---

*Cours créé dans le cadre du projet PadChest XML - HEPL 2024*
