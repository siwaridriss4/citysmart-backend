package tn.smartcity.model.enums;

/**
 * Statuts possibles d'une réclamation
 */
public enum ClaimStatus {
    /**
     * En attente de traitement
     */
    EN_ATTENTE,
    
    /**
     * En cours de traitement par un agent
     */
    EN_COURS,
    
    /**
     * Réclamation résolue
     */
    RESOLU,
    
    /**
     * Réclamation rejetée (doublon, invalide, etc.)
     */
    REJETE
}
