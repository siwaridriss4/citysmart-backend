package tn.smartcity.model.enums;

/**
 * Niveaux de priorité d'une réclamation
 */
public enum ClaimPriority {
    /**
     * Priorité faible - Problème mineur
     */
    FAIBLE,
    
    /**
     * Priorité moyenne - Problème standard
     */
    MOYENNE,
    
    /**
     * Priorité haute - Problème important
     */
    HAUTE,
    
    /**
     * Priorité urgente - Danger immédiat
     * Ex: câble électrique au sol, fuite de gaz
     */
    URGENTE
}
