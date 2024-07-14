package com.decs.application.data.distribution;

/**
 * <b>Distribution Type Enumeration</b>
 * <p>
 *     Enumeration of the supported distribution methods
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public enum DistributionType {
    /**
     * No distribution
     */
    LOCAL,
    /**
     * Distributed Evaluation
     */
    DIST_EVAL,
    /**
     * Island Model
     */
    ISLANDS;
}