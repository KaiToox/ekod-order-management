# PLAN DE TEST - Application de Gestion des Commandes

## 1. OBJECTIF DU PLAN DE TEST

Ce plan de test vise à valider le bon fonctionnement de l'application de gestion des commandes en testant chaque composant individuellement et en vérifiant leur intégration.

## 2. ÉLÉMENTS CLÉS À TESTER

### 2.1 Classe Product
**Responsabilité** : Gestion des informations d'un produit (nom, prix, stock)

**Tests à effectuer** :
- ✅ Création d'un produit avec des valeurs valides
- ✅ Récupération des informations (nom, prix, stock)
- ✅ Décrémentation du stock (avec gestion des exceptions)
- ✅ Incrémentation du stock
- ✅ Gestion du stock à zéro (exception OutOfStockException)

### 2.2 Classe ShoppingCart
**Responsabilité** : Gestion du panier d'achat

**Tests à effectuer** :
- ✅ Création d'un panier vide
- ✅ Ajout de produits au panier
- ✅ Suppression de produits du panier
- ✅ Calcul du prix total
- ✅ Gestion des exceptions de stock
- ✅ Récupération de la liste des produits
- ✅ Comptage du nombre d'articles

### 2.3 Classe Order
**Responsabilité** : Gestion des commandes et calculs

**Tests à effectuer** :
- ✅ Création d'une commande
- ✅ Calcul du total avec frais de livraison
- ✅ Application de remises
- ✅ Gestion des frais de livraison
- ✅ Validation des données (remise entre 0-100%, frais positifs)

### 2.4 Classe Invoice
**Responsabilité** : Génération de factures

**Tests à effectuer** :
- ✅ Génération de facture avec panier vide
- ✅ Génération de facture avec produits
- ✅ Génération de facture avec remise
- ✅ Formatage correct de la facture

### 2.5 Tests d'Intégration
**Responsabilité** : Validation du flux complet

**Tests à effectuer** :
- ✅ Flux complet : Produit → Panier → Commande → Facture
- ✅ Gestion des exceptions en cascade
- ✅ Validation des données de commande

## 3. STRATÉGIE DE TEST

### 3.1 Tests Unitaires
- **Outils** : JUnit 5
- **Couverture** : Chaque méthode publique de chaque classe
- **Données de test** : Valeurs simples et cas limites

### 3.2 Tests d'Intégration
- **Objectif** : Vérifier l'interaction entre les composants
- **Scénarios** : Flux de commande complet

### 3.3 Tests de Validation
- **Objectif** : Vérifier la robustesse des entrées
- **Cas** : Valeurs négatives, valeurs hors limites

## 4. CRITÈRES DE SUCCÈS

- ✅ Tous les tests unitaires passent
- ✅ Couverture de code ≥ 80% avec JaCoCo
- ✅ Gestion correcte des exceptions
- ✅ Calculs financiers précis
- ✅ Formatage des factures correct

## 5. OUTILS UTILISÉS

- **Framework de test** : JUnit 5
- **Couverture de code** : JaCoCo
- **Build tool** : Maven
- **IDE** : Compatible avec IntelliJ IDEA, Eclipse, VS Code

## 6. EXÉCUTION DES TESTS

```bash
# Compilation et tests
mvn clean test

# Génération du rapport de couverture
mvn jacoco:report

# Rapport disponible dans : target/site/jacoco/index.html
```

## 7. STRUCTURE DES TESTS

```
src/test/java/fr/ekod/
├── ProductTest.java          # Tests de la classe Product
├── ShoppingCartTest.java     # Tests de la classe ShoppingCart
├── OrderTest.java            # Tests de la classe Order
├── InvoiceTest.java          # Tests de la classe Invoice
└── IntegrationTest.java      # Tests d'intégration
```

## 8. PROCHAINES ÉTAPES

1. ✅ **Plan de test** (ce document)
2. 🔄 **Implémentation des tests unitaires**
3. 🔄 **Exécution des tests et rapport de couverture**
4. 🔄 **Bilan de tests final** 