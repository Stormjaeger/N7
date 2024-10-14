#include "complexe.h"
#include <math.h>           // Pour certaines fonctions trigo notamment

// Implantations de reelle et imaginaire
double reelle (complexe_t c){
    return (double) c.p_reelle;
}

double imaginaire (complexe_t c){
    return (double) c.p_imaginaire;
}

// Implantations de set_reelle et set_imaginaire
void set_reelle (complexe_t* p_c, double nouveau_reelle){
    (*p_c).p_reelle = nouveau_reelle;
}

void set_imaginaire (complexe_t* p_c, double nouveau_imaginaire){
    (*p_c).p_imaginaire = nouveau_imaginaire;
}

void init (complexe_t* p_c, double nouveau_reelle, double nouveau_imaginaire){
    (*p_c).p_reelle = nouveau_reelle;
    (*p_c).p_imaginaire = nouveau_imaginaire;
}

// Implantation de copie
void copie(complexe_t* resultat, complexe_t autre){
    set_reelle (resultat, autre.p_reelle);
    set_imaginaire (resultat, autre.p_imaginaire);
}

// Implantations des fonctions algébriques sur les complexes
void conjugue(complexe_t* resultat, complexe_t op){
    set_reelle (resultat, op.p_reelle);
    set_imaginaire (resultat, - op.p_imaginaire);
}

void ajouter(complexe_t* resultat, complexe_t gauche, complexe_t droite){
    resultat->p_reelle = gauche.p_reelle + droite.p_reelle;
    resultat->p_imaginaire = gauche.p_imaginaire + droite.p_imaginaire;
}

void soustraire(complexe_t* resultat, complexe_t gauche, complexe_t droite){
    resultat->p_reelle = gauche.p_reelle - droite.p_reelle;
    resultat->p_imaginaire = gauche.p_imaginaire - droite.p_imaginaire;
}

void multiplier(complexe_t* resultat, complexe_t gauche, complexe_t droite){
    resultat->p_reelle = gauche.p_reelle * droite.p_reelle - gauche.p_imaginaire * droite.p_imaginaire;
    resultat->p_imaginaire = gauche.p_reelle * droite.p_imaginaire + gauche.p_imaginaire * droite.p_reelle;
}

void echelle(complexe_t* resultat, complexe_t op, double facteur){
    resultat->p_reelle = op.p_reelle * facteur;
    resultat->p_imaginaire = op.p_imaginaire * facteur;
}

void puissance(complexe_t* resultat, complexe_t op, int exposant){
    for (int i = 1; i<=exposant; i++){
        multiplier(resultat, *resultat, op);
    }
}

// Implantations du module et de l'argument
double module_carre(complexe_t c){
    return pow(c.p_reelle, 2) + pow(c.p_imaginaire,2);
}

double module(complexe_t c){
    return sqrt(module_carre(c));
}

double argument(complexe_t c){
    if (c.p_reelle >0){
        return atan(c.p_imaginaire / c.p_reelle);
    }else if (c.p_reelle < 0){
        return atan(c.p_imaginaire / c.p_reelle) + M_PI;
    }else{
        return 0;
    }
}


