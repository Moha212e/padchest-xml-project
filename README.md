# 📘 Projet XML - PadChest

## Description du projet
Ce projet consiste en l'analyse et le traitement de données médicales issues du dataset PadChest, avec conversion CSV vers XML et développement d'outils d'analyse.

## Structure du projet

```
padchest-xml-project/
├── data/                    # Données du projet
│   ├── raw/                 # Fichier CSV brut
│   └── processed/           # Fichier XML généré
├── schema/                  # Définitions de structure
│   ├── images.dtd          # Document Type Definition
│   └── images.xsd          # XML Schema Definition
├── parser/                  # Parser Java
│   └── src/main/java/      # Code source Java
├── xslt/                    # Transformations XSLT
│   ├── view.xsl            # Feuille de style XSLT
│   └── index.html          # Page HTML générée
├── xquery/                  # Requêtes XQuery
│   └── queries.xq          # Requêtes BaseX
├── docs/                    # Documentation
└── deliverables/            # Livrables finaux
```

## Phases de réalisation

### Phase 1 - Mise en place & organisation ✅
- [x] Structure du projet créée
- [x] Dossiers organisés selon le plan
- [x] README initial préparé

### Phase 2 - Conversion CSV → XML
- [ ] Identification des champs à conserver
- [ ] Définition de la structure XML
- [ ] Programme de conversion
- [ ] Génération de images.xml

### Phase 3 - Définition de la structure
- [ ] Rédaction de images.dtd
- [ ] Création de images.xsd (optionnel)
- [ ] Validation du XML

### Phase 4 - Validation & Parsing Java
- [ ] Implémentation du parser SAX/DOM
- [ ] Calcul des statistiques
- [ ] Génération de stats.txt

### Phase 5 - Transformation XSLT → HTML
- [ ] Création de view.xsl
- [ ] Génération d'index.html
- [ ] Mise en forme CSS/JS

### Phase 6 - BaseX & XQuery
- [ ] Import dans BaseX
- [ ] Requêtes XQuery
- [ ] Intégration webservice (pro/expert)

### Phase 7 - Intégration & Livraison
- [ ] Rassemblement des livrables
- [ ] Documentation finale
- [ ] Vérification croisée

## Barème
- Conversion XML : 5 pts
- DTD/XSD : 4 pts
- Parser + stats : 6 pts
- XSLT → HTML : 5 pts
- XQuery : 5 pts
- **Total : 25 pts**

## Équipe
- [Nom du membre 1] : [Rôles assignés]
- [Nom du membre 2] : [Rôles assignés]

## Instructions d'utilisation
*À compléter selon l'avancement du projet*

---
*Projet réalisé dans le cadre du cours XML - [Année académique]*
