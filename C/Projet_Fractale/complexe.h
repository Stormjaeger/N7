#ifndef COMPLEX_H
#define COMPLEX_H

// Type utilisateur complexe_t
struct complexe_t {
    double p_reelle; //la partie réelle
    double p_imaginaire; //la partie imaginaire
};

typedef struct complexe_t complexe_t; //alias complexe_t

// Fonctions reelle et imaginaire
/**
 * reelle
 *
 * Cette fonction donne la partie réelle d'un nombre complexe
 *
 * Paramètres:
 * c               [in] un complexe de type complexe_t
 *
 * Retour : retourne sa partie réelle
 *
 * Pré-conditions : 
 * c est initialiser (non vide)
 * 
 * Post-condition :
 *reelle(c) et c.p_reelle sont égaux
 * 
 * Cas d'erreur :  aucun
 */
double reelle (complexe_t c);

/**
 * imaginaire
 *
 * Cette fonction donne la partie imaginaire d'un nombre complexe
 *
 * Paramètres:
 * c                 [in] un complexe de type complexe_t
 *
 * Retour : retourne sa partie imaginaire
 *
 * Pré-conditions : 
 * c est initialiser (non vide)
 * 
 * Post-condition :
 * imaginaire(c) et c.p_imaginaire sont égaux
 * 
 * Cas d'erreur :  aucun
 */
double imaginaire(complexe_t c);

// Procédures set_reelle, set_imaginaire et init
/**
 * set_reelle
 *
 * Cette fonction modifie la partie reélle d'un nombre complexe donné avec un nombre réel donné
 *
 * Paramètres:
 * p_c                 [in/out] un pointeur vers un complexe de type complexe_t
 * nouveau_reelle      [in] un réel de type double
 *
 * Retour : X 
 *
 * Pré-conditions : 
 * X
 * 
 * Post-condition :
 * nouveau_reelle et (*p_c).p_reelle sont égaux à la sortie
 * 
 * Cas d'erreur :  aucun
 */
void set_reelle (complexe_t* p_c, double nouveau_reelle);

/**
 * set_imaginaire
 *
 * Cette fonction modifie la partie imaginaire d'un nombre complexe donné avec un nombre réel donné
 *
 * Paramètres:
 * p_c                [in/out] un pointeur vers un complexe de type complexe_t
 * nouveau_imaginaire [in] un réel de type double
 *
 * Retour : X 
 *
 * Pré-conditions : 
 * X
 * 
 * Post-condition :
 * nouveau_imaginaire et (*p_c).p_imaginaire sont égaux à la sortie
 * 
 * Cas d'erreur :  aucun
 */
void set_imaginaire (complexe_t* p_c, double nouveau_imaginaire);

/**
 * init
 *
 * Cette fonction modifie la partie réelle et la partie imaginaire d'un nombre complexe donné avec deux nombre réels donnés
 *
 * Paramètres:
 * p_c                 [in/out] un pointeur vers un complexe de type complexe_t
 * nouveau_reelle      [in] un double
 * nouveau_imaginaire  [in] un double 
 *
 * Retour : X 
 *
 * Pré-conditions : 
 * X
 * 
 * Post-condition :
 * Modifie directement le nombre complexe enregistré dans un case mémoire
 * nouveau_reelle et (*p_c).p_reelle sont egaux
 * nouveau_imaginaire et (*p_c).p_imaginaire sont égaux
 * 
 * Cas d'erreur :  aucun
 */
void init (complexe_t* p_c, double nouveau_reelle, double nouveau_imaginaire);

// Procédure copie
/**
 * copie
 * Copie les composantes du complexe donné en second argument dans celles du premier
 * argument
 *
 * Paramètres :
 *   resultat       [out] Complexe dans lequel copier les composantes
 *   autre          [in]  Complexe à copier
 *
 * Pré-conditions : resultat non null
 * Post-conditions : resultat et autre ont les mêmes composantes
 */
void copie(complexe_t* resultat, complexe_t autre);

// Algèbre des nombres complexes
/**
 * conjugue
 * Calcule le conjugué du nombre complexe op et le sotocke dans resultat.
 *
 * Paramètres :
 *   resultat       [out] Résultat de l'opération
 *   op             [in]  Complexe dont on veut le conjugué
 *
 * Pré-conditions : resultat non-null
 * Post-conditions : reelle(*resultat) = reelle(op), complexe(*resultat) = - complexe(op)
 */
void conjugue(complexe_t* resultat, complexe_t op);

/**
 * ajouter
 * Réalise l'addition des deux nombres complexes gauche et droite et stocke le résultat
 * dans resultat.
 *
 * Paramètres :
 *   resultat       [out] Résultat de l'opération
 *   gauche         [in]  Opérande gauche
 *   droite         [in]  Opérande droite
 *
 * Pré-conditions : resultat non-null
 * Post-conditions : *resultat = gauche + droite
 */
void ajouter(complexe_t* resultat, complexe_t gauche, complexe_t droite);

/**
 * soustraire
 * Réalise la soustraction des deux nombres complexes gauche et droite et stocke le résultat
 * dans resultat.
 *
 * Paramètres :
 *   resultat       [out] Résultat de l'opération
 *   gauche         [in]  Opérande gauche
 *   droite         [in]  Opérande droite
 *
 * Pré-conditions : resultat non-null
 * Post-conditions : *resultat = gauche - droite
 */
void soustraire(complexe_t* resultat, complexe_t gauche, complexe_t droite);

/**
 * multiplier
 * Réalise le produit des deux nombres complexes gauche et droite et stocke le résultat
 * dans resultat.
 *
 * Paramètres :
 *   resultat       [out] Résultat de l'opération
 *   gauche         [in]  Opérande gauche
 *   droite         [in]  Opérande droite
 *
 * Pré-conditions : resultat non-null
 * Post-conditions : *resultat = gauche * droite
 */
void multiplier(complexe_t* resultat, complexe_t gauche, complexe_t droite);

/**
 * echelle
 * Calcule la mise à l'échelle d'un nombre complexe avec le nombre réel donné (multiplication
 * de op par le facteur réel facteur).
 *
 * Paramètres :
 *   resultat       [out] Résultat de l'opération
 *   op             [in]  Complexe à mettre à l'échelle
 *   facteur        [in]  Nombre réel à multiplier
 *
 * Pré-conditions : resultat non-null
 * Post-conditions : *resultat = op * facteur
 */
void echelle(complexe_t* resultat, complexe_t op, double facteur);

/**
 * puissance
 * Calcule la puissance entière du complexe donné et stocke le résultat dans resultat.
 *
 * Paramètres :
 *   resultat       [out] Résultat de l'opération
 *   op             [in]  Complexe dont on veut la puissance
 *   exposant       [in]  Exposant de la puissance
 *
 * Pré-conditions : resultat non-null, exposant >= 0
 * Post-conditions : *resultat = op * op * ... * op
 *                                 { n fois }
 */
void puissance(complexe_t* resultat, complexe_t op, int exposant);

// Module et argument
/**
 * module_carre
 *
 * Cette fonction donne le module au carré d'un nombre complexe
 *
 * Paramètres:
 * c               [in] un complexe de type complexe_t
 *
 * Retour : retourne le carré du module
 *
 * Pré-conditions : 
 * c est initialiser (non vide)
 * 
 * Post-condition :
 * module_carre(c) vaut la partie réelle de c au carré plus la partie imaginaire de c au carré 
 * module_carre(c) >= 0
 * 
 * Cas d'erreur :  aucun
 */
double module_carre(complexe_t c);

/**
 * module
 *
 * Cette fonction donne le module d'un nombre complexe
 *
 * Paramètres:
 * c               [in] un complexe de type complexe_t
 *
 * Retour : retourne le module
 *
 * Pré-conditions : 
 * c est initialiser (non vide)
 * 
 * Post-condition :
 * module(c) et racine de module_carre(c) sont égaux
 * module(c) >= 0
 * 
 * Cas d'erreur :  aucun
 */
double module(complexe_t c);

/**
 * argument
 *
 * Cette fonction donne l'argument d'un nombre complexe
 *
 * Paramètres:
 * c               [in] un complexe de type complexe_t
 *
 * Retour : retourne l'argument d'un nombre complexe
 *
 * Pré-conditions : 
 * c est initialiser (non vide)
 * 
 * Post-condition :
 * 0 <= argument(c) < 2*pi
 * 
 * Cas d'erreur :  aucun
 */
double argument(complexe_t c);


#endif // COMPLEXE_H



