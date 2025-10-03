# 📚 Documentation XSD - PadChest XML Schema

## 🎯 Introduction

Le **XSD (XML Schema Definition)** est une alternative avancée au DTD qui offre des fonctionnalités plus puissantes pour la validation XML. Ce document présente le schéma XSD créé pour le projet PadChest.

## 📋 Avantages du XSD vs DTD

| Fonctionnalité | DTD | XSD |
|----------------|-----|-----|
| **Types de données** | Basiques | Avancés (xs:string, xs:decimal, etc.) |
| **Contraintes** | Limitées | Étendues (minLength, pattern, etc.) |
| **Namespace** | Non supporté | Support complet |
| **Héritage** | Non | Oui (extension, restriction) |
| **Validation** | Basique | Avancée avec assertions |
| **Documentation** | Commentaires | Annotations intégrées |

---

## 🏗️ Structure du XSD PadChest

### **1. Déclaration du schéma**
```xml
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
           targetNamespace="http://padchest.hepl.be/schema"
           xmlns:tns="http://padchest.hepl.be/schema"
           elementFormDefault="qualified">
```

**Explication :**
- **Namespace** : `http://padchest.hepl.be/schema`
- **Qualification** : Tous les éléments sont qualifiés
- **Target namespace** : Espace de noms cible pour la validation

### **2. Types simples personnalisés**

#### **ImageIdType**
```xml
<xs:simpleType name="imageIdType">
  <xs:restriction base="xs:string">
    <xs:minLength value="10"/>
    <xs:maxLength value="100"/>
    <xs:pattern value="[a-zA-Z0-9_\-\.]+"/>
  </xs:restriction>
</xs:simpleType>
```
- **Contraintes** : 10-100 caractères, alphanumériques + tirets/points
- **Usage** : Identifiants d'images

#### **PatientIdType**
```xml
<xs:simpleType name="patientIdType">
  <xs:restriction base="xs:string">
    <xs:minLength value="10"/>
    <xs:maxLength value="50"/>
    <xs:pattern value="[0-9]+"/>
  </xs:restriction>
</xs:simpleType>
```
- **Contraintes** : 10-50 chiffres uniquement
- **Usage** : Identifiants de patients

#### **BirthYearType**
```xml
<xs:simpleType name="birthYearType">
  <xs:restriction base="xs:decimal">
    <xs:minInclusive value="1900"/>
    <xs:maxInclusive value="2024"/>
    <xs:fractionDigits value="1"/>
  </xs:restriction>
</xs:simpleType>
```
- **Contraintes** : 1900-2024, 1 décimale
- **Usage** : Années de naissance

#### **ProjectionType**
```xml
<xs:simpleType name="projectionType">
  <xs:restriction base="xs:string">
    <xs:enumeration value="PA"/>
    <xs:enumeration value="AP"/>
    <xs:enumeration value="LATERAL"/>
    <xs:enumeration value="L"/>
    <xs:enumeration value="POSTEROANTERIOR"/>
    <xs:enumeration value="AP_horizontal"/>
  </xs:restriction>
</xs:simpleType>
```
- **Contraintes** : Valeurs DICOM valides uniquement
- **Usage** : Types de projection radiologique

---

## 🔧 Types complexes

### **1. LabelType**
```xml
<xs:complexType name="labelType">
  <xs:simpleContent>
    <xs:extension base="xs:string">
      <xs:attribute name="cui" type="cuiType" use="optional"/>
    </xs:extension>
  </xs:simpleContent>
</xs:complexType>
```
- **Contenu** : Texte + attribut CUI optionnel
- **Usage** : Labels diagnostiques individuels

### **2. PatientType**
```xml
<xs:complexType name="patientType">
  <xs:sequence>
    <xs:element name="id" type="patientIdType"/>
    <xs:element name="birth" type="birthYearType"/>
  </xs:sequence>
  <xs:attribute name="anonymized" type="xs:boolean" default="true"/>
</xs:complexType>
```
- **Éléments** : id (obligatoire), birth (obligatoire)
- **Attributs** : anonymized (optionnel, défaut=true)

### **3. ImageType**
```xml
<xs:complexType name="imageType">
  <xs:sequence>
    <xs:element name="patient" type="patientType"/>
    <xs:element name="study" type="studyType"/>
    <xs:element name="diagnosis" type="diagnosisType"/>
    <xs:element name="report" type="reportType"/>
  </xs:sequence>
  <xs:attribute name="id" type="imageIdType" use="required"/>
  <xs:attribute name="imageDir" type="imageDirType" use="optional"/>
</xs:complexType>
```
- **Structure** : patient → study → diagnosis → report
- **Attributs** : id (obligatoire), imageDir (optionnel)

---

## 🎯 Contraintes avancées (Assertions)

### **1. Contrainte de contenu minimal**
```xml
<xs:assert test="every $img in image satisfies 
                 (count($img/diagnosis/labels/label) > 0 or 
                  count($img/diagnosis/localizations/location) > 0)">
  <xs:annotation>
    <xs:documentation>Chaque image doit avoir au moins un label ou une localisation</xs:documentation>
  </xs:annotation>
</xs:assert>
```
**Fonction** : Vérifie qu'une image a au moins un label ou une localisation

### **2. Contrainte de cohérence des compteurs**
```xml
<xs:assert test="every $labels in //labels satisfies 
                 (not($labels/@count) or $labels/@count = count($labels/label))">
  <xs:annotation>
    <xs:documentation>Le compteur de labels doit correspondre au nombre réel</xs:documentation>
  </xs:annotation>
</xs:assert>
```
**Fonction** : Vérifie que l'attribut `count` correspond au nombre réel d'éléments

### **3. Contrainte de longueur de rapport**
```xml
<xs:assert test="every $report in //report satisfies 
                 (not($report/@length) or $report/@length = string-length($report))">
  <xs:annotation>
    <xs:documentation>La longueur du rapport doit correspondre à la longueur réelle</xs:documentation>
  </xs:annotation>
</xs:assert>
```
**Fonction** : Vérifie que l'attribut `length` correspond à la longueur réelle du texte

---

## 📊 Exemple d'utilisation

### **XML valide avec XSD**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<images xmlns="http://padchest.hepl.be/schema"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://padchest.hepl.be/schema ../schema/images.xsd"
        version="1.0" 
        created="2024-01-15">
  
  <image id="20536686640136348236148679891455886468_k6ga29.png" imageDir="0">
    <patient anonymized="true">
      <id>839860488694292331637988235681460987</id>
      <birth>1930.0</birth>
    </patient>
    <study studyId="20536686640136348236148679891455886468">
      <studyId>20536686640136348236148679891455886468</studyId>
      <projection>PA</projection>
      <pediatric>No</pediatric>
      <methodProjection>Manual review of DICOM fields</methodProjection>
    </study>
    <diagnosis confidence="0.95">
      <labels count="1">
        <label cui="C0000001">normal</label>
      </labels>
      <localizations count="0">
      </localizations>
      <labelsLocalizationsBySentence>[['normal'], ['normal']]</labelsLocalizationsBySentence>
      <methodLabel>Physician</methodLabel>
      <reportId>4765777</reportId>
    </diagnosis>
    <report language="es" length="45">sin hallazgos patológicos edad paciente</report>
  </image>
  
</images>
```

---

## 🚨 Validation et erreurs

### **Types d'erreurs détectées par le XSD**

#### **1. Erreurs de type**
```xml
<!-- ❌ ERREUR : Année invalide -->
<birth>1800.0</birth>  <!-- < 1900 -->

<!-- ❌ ERREUR : Projection invalide -->
<projection>INVALID</projection>  <!-- Pas dans l'énumération -->

<!-- ❌ ERREUR : CUI invalide -->
<label cui="INVALID">test</label>  <!-- Ne correspond pas au pattern -->
```

#### **2. Erreurs de contrainte**
```xml
<!-- ❌ ERREUR : Aucun label ni localisation -->
<diagnosis>
  <labels></labels>
  <localizations></localizations>
  <!-- ... -->
</diagnosis>

<!-- ❌ ERREUR : Compteur incorrect -->
<labels count="5">
  <label>test1</label>
  <label>test2</label>
  <!-- Seulement 2 labels, pas 5 -->
</labels>
```

#### **3. Erreurs de structure**
```xml
<!-- ❌ ERREUR : Élément manquant -->
<image id="test">
  <patient>...</patient>
  <!-- study manquant -->
  <diagnosis>...</diagnosis>
</image>
```

---

## 🛠️ Outils de validation XSD

### **1. Validation en ligne**
- **W3C XSD Validator** : https://www.w3.org/2001/03/webdata/xsv
- **XML Validator** : https://www.xmlvalidation.com/

### **2. Éditeurs XML**
- **XMLSpy** : Validation XSD intégrée
- **Oxygen XML Editor** : Support XSD complet
- **Visual Studio Code** + XML Tools

### **3. Validation par code**
```java
// Java - Validation XSD
SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
Schema schema = factory.newSchema(new File("images.xsd"));
Validator validator = schema.newValidator();
validator.validate(new StreamSource(new File("test.xml")));
```

---

## 📈 Avantages du XSD pour PadChest

### **1. Validation robuste**
- **Types stricts** : Empêche les données incorrectes
- **Contraintes métier** : Vérifie la cohérence des données
- **Messages d'erreur** : Diagnostics précis

### **2. Documentation intégrée**
- **Annotations** : Documentation dans le schéma
- **Types explicites** : Clarification des attentes
- **Exemples** : Cas d'usage documentés

### **3. Évolutivité**
- **Namespace** : Support des versions futures
- **Extension** : Ajout facile de nouveaux types
- **Compatibilité** : Rétrocompatibilité assurée

---

## 🎯 Résumé

Le XSD PadChest offre :

✅ **Types de données avancés** avec contraintes strictes  
✅ **Validation métier** avec assertions XPath  
✅ **Documentation intégrée** avec annotations  
✅ **Support namespace** pour l'évolutivité  
✅ **Contraintes de cohérence** pour la qualité des données  

**Niveau** : Pro/Expert - Fonctionnalités avancées pour une validation robuste ! 🚀

