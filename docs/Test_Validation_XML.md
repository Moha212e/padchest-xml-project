# 🧪 Guide de Test et Validation XML avec DTD

## 📋 Fichiers de test créés

### 1. **Fichier XML valide** : `data/processed/test_images.xml`
- ✅ **3 images** de test avec différents cas
- ✅ **Tous les attributs obligatoires** présents
- ✅ **Structure conforme** au DTD
- ✅ **Données réalistes** basées sur le CSV PadChest

### 2. **Fichier XML invalide** : `data/processed/test_invalid.xml`
- ❌ **4 erreurs différentes** pour tester la validation
- ❌ **Attribut obligatoire manquant**
- ❌ **Éléments manquants**
- ❌ **Ordre incorrect**
- ❌ **Éléments non autorisés**

---

## 🔍 Tests de validation

### **Test 1 : XML valide**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE images SYSTEM "../schema/images.dtd">
<images version="1.0" created="2024-01-15">
  <image id="20536686640136348236148679891455886468_k6ga29.png" imageDir="0">
    <patient>
      <id>839860488694292331637988235681460987</id>
      <birth>1930.0</birth>
    </patient>
    <!-- ... autres éléments ... -->
  </image>
</images>
```

**✅ Résultat attendu** : Validation réussie

### **Test 2 : Attribut obligatoire manquant**
```xml
<image imageDir="0">  <!-- ❌ id manquant -->
  <patient>
    <id>839860488694292331637988235681460987</id>
    <birth>1930.0</birth>
  </patient>
  <!-- ... -->
</image>
```

**❌ Erreur attendue** : `required attribute "id" is missing`

### **Test 3 : Élément manquant**
```xml
<image id="test123">
  <!-- ❌ patient manquant -->
  <study>
    <studyId>123456</studyId>
    <!-- ... -->
  </study>
  <!-- ... -->
</image>
```

**❌ Erreur attendue** : `element "patient" is missing`

### **Test 4 : Ordre des éléments incorrect**
```xml
<image id="test456">
  <study>  <!-- ❌ study avant patient -->
    <!-- ... -->
  </study>
  <patient>
    <!-- ... -->
  </patient>
  <!-- ... -->
</image>
```

**❌ Erreur attendue** : `element "patient" not allowed here`

---

## 🛠️ Outils de validation

### **1. Éditeurs XML avec validation**

#### **Visual Studio Code**
1. Installer l'extension "XML Tools"
2. Ouvrir le fichier XML
3. Utiliser `Ctrl+Shift+P` → "XML: Validate"

#### **Notepad++**
1. Installer le plugin XML Tools
2. Ouvrir le fichier XML
3. Plugins → XML Tools → Validate Now

#### **XMLSpy (commercial)**
1. Ouvrir le fichier XML
2. Validation automatique en temps réel
3. Messages d'erreur détaillés

### **2. Validation en ligne**

#### **W3C Markup Validator**
1. Aller sur https://validator.w3.org/
2. Uploader le fichier XML
3. Vérifier les erreurs

#### **XML Validator Online**
1. Aller sur https://www.xmlvalidation.com/
2. Coller le contenu XML
3. Uploader le DTD
4. Valider

### **3. Validation par ligne de commande**

#### **xmllint (Linux/Mac)**
```bash
xmllint --dtdvalid schema/images.dtd data/processed/test_images.xml
```

#### **PowerShell (Windows)**
```powershell
# Vérifier la syntaxe XML
[xml]$xml = Get-Content "data/processed/test_images.xml"
```

---

## 📊 Cas de test détaillés

### **Cas 1 : Image complète (valide)**
```xml
<image id="20536686640136348236148679891455886468_k6ga29.png" imageDir="0">
  <patient>
    <id>839860488694292331637988235681460987</id>
    <birth>1930.0</birth>
  </patient>
  <study>
    <studyId>20536686640136348236148679891455886468</studyId>
    <projection>PA</projection>
    <pediatric>No</pediatric>
    <methodProjection>Manual review of DICOM fields</methodProjection>
  </study>
  <diagnosis>
    <labels>
      <label cui="C0000001">normal</label>
    </labels>
    <localizations>
    </localizations>
    <labelsLocalizationsBySentence>[['normal'], ['normal']]</labelsLocalizationsBySentence>
    <methodLabel>Physician</methodLabel>
    <reportId>4765777</reportId>
  </diagnosis>
  <report>sin hallazgos patológicos edad paciente</report>
</image>
```

**✅ Points de validation :**
- Attribut `id` présent (obligatoire)
- Attribut `imageDir` présent (optionnel)
- Tous les éléments requis dans le bon ordre
- Structure conforme au DTD

### **Cas 2 : Image minimale (valide)**
```xml
<image id="113855343774216031107737439268243531979_3k951l.png">
  <patient>
    <id>50783093527901818115346441867348318648</id>
    <birth>1925.0</birth>
  </patient>
  <study>
    <studyId>113855343774216031107737439268243531979</studyId>
    <projection>POSTEROANTERIOR</projection>
    <pediatric>No</pediatric>
    <methodProjection>Manual review of DICOM fields</methodProjection>
  </study>
  <diagnosis>
    <labels>
      <label cui="C0742362">chronic changes</label>
    </labels>
    <localizations>
      <location cui="C1522601">loc cardiac</location>
      <location cui="C0025066">loc mediastinum</location>
      <location cui="C0230151">loc costophrenic angle</location>
    </localizations>
    <labelsLocalizationsBySentence>[['chronic changes'], ['chronic changes']]</labelsLocalizationsBySentence>
    <methodLabel>Physician</methodLabel>
    <reportId>4955977</reportId>
  </diagnosis>
  <report>silueta cardíaca mediastino dentro normal</report>
</image>
```

**✅ Points de validation :**
- Attribut `id` présent (obligatoire)
- Attribut `imageDir` absent (optionnel) - OK
- Structure conforme au DTD

---

## 🚨 Erreurs communes et solutions

### **1. Attribut obligatoire manquant**
```xml
<!-- ❌ ERREUR -->
<image imageDir="0">
  <!-- contenu -->
</image>

<!-- ✅ CORRECTION -->
<image id="12345" imageDir="0">
  <!-- contenu -->
</image>
```

### **2. Élément manquant**
```xml
<!-- ❌ ERREUR -->
<image id="12345">
  <study>
    <!-- contenu -->
  </study>
  <!-- patient manquant -->
</image>

<!-- ✅ CORRECTION -->
<image id="12345">
  <patient>
    <id>67890</id>
    <birth>1930.0</birth>
  </patient>
  <study>
    <!-- contenu -->
  </study>
</image>
```

### **3. Ordre des éléments incorrect**
```xml
<!-- ❌ ERREUR -->
<image id="12345">
  <study><!-- ... --></study>
  <patient><!-- ... --></patient>  <!-- patient après study -->
</image>

<!-- ✅ CORRECTION -->
<image id="12345">
  <patient><!-- ... --></patient>
  <study><!-- ... --></study>
</image>
```

### **4. Élément non autorisé**
```xml
<!-- ❌ ERREUR -->
<image id="12345">
  <patient><!-- ... --></patient>
  <study><!-- ... --></study>
  <diagnosis><!-- ... --></diagnosis>
  <report><!-- ... --></report>
  <elementNonAutorise>test</elementNonAutorise>  <!-- Non autorisé -->
</image>

<!-- ✅ CORRECTION -->
<image id="12345">
  <patient><!-- ... --></patient>
  <study><!-- ... --></study>
  <diagnosis><!-- ... --></diagnosis>
  <report><!-- ... --></report>
</image>
```

---

## 🎯 Checklist de validation

### **Avant la validation**
- [ ] Fichier XML bien formé (syntaxe correcte)
- [ ] DTD accessible et valide
- [ ] Déclaration DOCTYPE correcte
- [ ] Encodage UTF-8 spécifié

### **Pendant la validation**
- [ ] Tous les attributs obligatoires présents
- [ ] Tous les éléments requis présents
- [ ] Ordre des éléments respecté
- [ ] Aucun élément non autorisé
- [ ] Types de contenu respectés

### **Après la validation**
- [ ] Aucune erreur de validation
- [ ] Structure conforme au DTD
- [ ] Données cohérentes
- [ ] Prêt pour le traitement

---

## 📚 Ressources utiles

### **Éditeurs XML recommandés**
- **Visual Studio Code** + XML Tools
- **XMLSpy** (commercial, très complet)
- **Oxygen XML Editor** (commercial, professionnel)
- **Notepad++** + XML Tools plugin

### **Validation en ligne**
- W3C Markup Validator
- XML Validator Online
- FreeFormatter XML Validator

### **Documentation**
- W3C XML Specification
- DTD Tutorial (W3Schools)
- XML Validation Best Practices

---

*Guide créé pour le projet PadChest XML - HEPL 2024*
