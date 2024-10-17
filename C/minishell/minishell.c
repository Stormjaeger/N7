#include <stdio.h>
#include <stdlib.h>
#include "readcmd.h"
#include <stdbool.h>
#include <string.h>
#include <sys/wait.h>
#include <signal.h>
#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>

// si cmd_avant_plan > 0 elle contient le pid de la commande en avant plan
// si cmd_avant_plan = 0 c'est qu'il n'y a pas de commande en avant plan
int cmd_avant_plan = 0;

void traiterRedirection(struct cmdline *commande) {
    // Gestion de la redirection des entrées
    if (commande->in != NULL) {
        int redirectionEntree = open(commande->in, O_RDONLY);
        if (redirectionEntree == -1) {
            perror("erreur de l'ouverture du fichier entré dans la redirection");
            exit(10);
        }
        // Redirection de l'entrée
        int retour = dup2(redirectionEntree, 0);
        if (retour == -1) {
            perror("erreur de redirection des entrées");
            exit(11);
        }
    }
    // Gestion de la redirection des sorties
    if (commande->out != NULL) {
        int redirectionSortie = open(commande->out, O_WRONLY | O_CREAT | O_TRUNC, 0644);
        if (redirectionSortie == -1) {
            perror("erreur de l'ouverture du fichier sortie dans la redirection");
            exit(12);
        }
        //Redirection de la sortie
        int retour = dup2(redirectionSortie, 1);
        if (retour == -1) {
            perror("erreur de redirection des sorties");
            exit(13);
        }
    }
}

// Compte le nombre de tubes nécessaires
int checkNombrePipes(struct cmdline *commande) {
    int nbPipes = 0;
    while (commande->seq[nbPipes] != NULL) {
        nbPipes += 1;
    }
    return (nbPipes - 1);
}

// Créer les tubes nécessaires pour le pipeline
int** creationPipes(int nbPipes) {
    if (nbPipes >= 1) {
        int** tab = malloc(sizeof(int*) * nbPipes);
        for (int i = 0; i < nbPipes; i++) {
            tab[i] = malloc(sizeof(int) * 2);
            if (pipe(tab[i]) == -1) {
                printf("Erreur dans la création du pipe %d ... \n", i);
            }
        }
        return tab;
    } else {
        return NULL;
    }
}

// Permet de fermer les tubes
void fermerPipes(int** pipes, int nbPipes) {
    for (int i = 0; i < nbPipes; i++) {
        close(pipes[i][0]);
        close(pipes[i][1]);
    }
}

// Gestion des tubes en fonction de la position de la commande traitée en ligne commande
void preparerCommande(int** pipes, int indexseq, int nbPipes) {
    if (indexseq == 0) { // Première commande
        dup2(pipes[indexseq][1], 1); 
    } else if (indexseq == nbPipes) { // Dernière commande
        dup2(pipes[indexseq - 1][0], 0); 
    } else { // Commandes intermédiaires
        dup2(pipes[indexseq - 1][0], 0); 
        dup2(pipes[indexseq][1], 1);
    }
    fermerPipes(pipes, nbPipes);
}

// Libération des tubes pour éviter les fuites de mémoires
void freePipes(int** pipes, int nbPipes) {
    if (pipes != NULL) {
        for (int i = 0; i < nbPipes; i++) {
            close(pipes[i][0]);
            close(pipes[i][1]);
            free(pipes[i]);
        }
        free(pipes);
    }
}

void traitant_SIGCHLD(int signum) {
    int status;
    pid_t pid_fils;

    if (signum != SIGCHLD) {
        printf("Erreur dans la récupération du signal \n");
    } else {
        while ((pid_fils = waitpid(-1, &status, WNOHANG | WUNTRACED | WCONTINUED)) > 0) {
            if (WIFEXITED(status)) {
                //printf("mon fils %d s'est terminé avec exit %d \n", pid_fils, WEXITSTATUS(status));
                if (cmd_avant_plan == pid_fils) cmd_avant_plan = 0;
            } else if (WIFSIGNALED(status)) {
                //printf("mon fils %d a été tué par le signal %d \n", pid_fils, WTERMSIG(status));
                if (cmd_avant_plan == pid_fils) cmd_avant_plan = 0;
            } else if (WIFSTOPPED(status)) {
                //printf("mon fils a été suspendu \n");
                if (cmd_avant_plan == pid_fils) cmd_avant_plan = 0;
            } else if (WIFCONTINUED(status)) {
                //printf("mon fils a été relancé \n");
                if (cmd_avant_plan == pid_fils) cmd_avant_plan = 0;
            } else {
                printf("erreur dans le waitpid \n");
            }
        }
    }
    //printf("processus fils terminé \n");
}

void setTraitant(void (*traitant)(int), int num_sig) {
    // Utilisation du signal SIGCHLD
    struct sigaction action;
    action.sa_handler = traitant;
    sigemptyset(&action.sa_mask);
    action.sa_flags = SA_RESTART;

    if (sigaction(num_sig, &action, NULL) == -1) {
        printf("erreur dans l'assignation du signal \n");
    }
}

// Étape 13.1
void traitant_SIGINT(int num_sig) {
    printf("\n Ctrl C \n");
}

void traitant_SIGTSTP(int num_sig) {
    printf("\n Ctrl Z \n");
}

// Exécution d'une commande
void ExecCommande(char** cmd, struct cmdline *commande, int** pipes, int nbPipes, int indexseq) {
    pid_t retour = fork(); // Création du fils
    char *backgrounded = commande->backgrounded;

    if (retour == -1) {
        printf("erreur fork \n");
        exit(1);
    } else if (retour == 0) { // Fils
        if (pipes != NULL) {
            preparerCommande(pipes, indexseq, nbPipes);
        }
        // Traitement des redirections
        traiterRedirection(commande);

        // On rétablit le traitant par défaut
        if (backgrounded == NULL) {
            setTraitant(SIG_DFL, SIGINT);
            setTraitant(SIG_DFL, SIGTSTP);
        }
        execvp(cmd[0], cmd);
        // Échec
        printf("commande non exécutée \n");
        exit(EXIT_FAILURE);
    } else { // Père, ie minishell
        if (pipes != NULL) {
            if (indexseq > 0) {
                close(pipes[indexseq - 1][0]);
                close(pipes[indexseq - 1][1]);
            }
        }
        if (backgrounded == NULL) {
            cmd_avant_plan = retour;
            while (cmd_avant_plan > 0) {
                pause();
            }
        } else {
            printf("commande en tâche de fond \n");
        }
    }
}

int main(void) {
    bool fini = false;

    // Définition du traitant
    setTraitant(traitant_SIGCHLD, SIGCHLD);

    // Étape 13.1
    /**
    * setTraitant(traitant_SIGINT, SIGINT);
    * setTraitant(traitant_SIGTSTP, SIGTSTP);
    */

    // Définir traitant étape 13.2
    setTraitant(SIG_IGN, SIGINT);
    setTraitant(SIG_IGN, SIGTSTP);

    // Boucle principale du minishell
    while (!fini) {
        printf("> ");
        struct cmdline *commande = readcmd();
        if (commande == NULL) {
            // commande == NULL -> erreur readcmd()
            perror("erreur lecture commande \n");
            exit(EXIT_FAILURE);
        } else {
            int nbPipes = checkNombrePipes(commande);
            int** pipes = creationPipes(nbPipes);

            if (commande->err) {
                // commande->err != NULL -> commande->seq == NULL
                printf("erreur saisie de la commande : %s\n", commande->err);
            } else {
                int indexseq = 0;
                char** cmd;
                while ((cmd = commande->seq[indexseq])) {
                    if (cmd[0]) {
                        if (strcmp(cmd[0], "exit") == 0) {
                            fini = true;
                            printf("Au revoir ...\n");
                        } else {
                            ExecCommande(cmd, commande, pipes, nbPipes, indexseq);
                        }
                        indexseq++;
                    }
                }
                freePipes(pipes, nbPipes);
            }
        }
    }
    return EXIT_SUCCESS;
}