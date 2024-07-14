package com.decs.application.utils.confFile;

/**
 * <b>File Configuration Attributes Enumeration</b>
 * <p>
 *     Enumeration of the problem configuration (.conf) file information fields.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public enum FileConfigAttr {
    /**
     * Main parameter file
     */
    PARAMS_FILE,
    /**
     * Short code
     */
    CODE,
    /**
     * Full name
     */
    FULL_NAME,
    /**
     * Evolutionary type
     */
    TYPE,
    /**
     * Creation origin (Factory or User)
     */
    ORIGIN,
    /**
     * Type of distribution
     */
    DISTRIBUTION,
    /**
     * Identification of the server island
     */
    SERVER_ISLAND,
    /**
     * List of all islands
     */
    ISLAND_LIST;
}