# Scripts d'exécution pour le projet PadChest

## Scripts disponibles

### Parser Java
- `run_parser.sh` (Linux/Mac)
- `run_parser.ps1` (Windows)

### BaseX
- `run_basex.sh` (Linux/Mac)  
- `run_basex.ps1` (Windows)

## Prérequis
- Java 11+ installé
- Maven installé (pour le parser)
- BaseX installé (pour les requêtes XQuery)

## Utilisation
1. Placer le fichier CSV dans `data/raw/`
2. Exécuter la conversion CSV → XML
3. Valider avec DTD/XSD
4. Exécuter le parser Java
5. Générer la page HTML avec XSLT
6. Importer dans BaseX et exécuter les requêtes

## Notes
- Adapter les chemins selon votre environnement
- Vérifier les versions des outils requis
