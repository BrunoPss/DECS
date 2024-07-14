package com.decs.application.data.parameter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <b>Parameter Group Type Enumeration</b>
 * <p>
 *     Enumeration of ECJ's evolutionary parameter groups
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public enum ParameterGroupType {
    /**
     * Base parameters
     */
    EC,
    /**
     * Simple parameters
     */
    SIMPLE,
    /**
     * Genetic programming parameters
     */
    KOZA,
    /**
     * Ant colony optimization parameters
     */
    ANT;

    @Override
    public String toString() {
        return switch (this) {
            case EC -> "EC";
            case SIMPLE -> "Simple";
            case KOZA -> "Koza";
            case ANT -> "Ant";
        };
    }
}