package com.decs.application.utils.types;

/**
 * <b>Enhanced Boolean Enumeration</b>
 * <p>
 *     This enumeration represents an enhanced version of the boolean primitive data type.
 *     It contains natural language translations for each possible value.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public enum EnhancedBoolean {
    TRUE,
    FALSE;

    @Override
    public String toString() {
        return switch (this) {
            case TRUE -> "Yes";
            case FALSE -> "No";
        };
    }
    public boolean value() {
        return switch (this) {
            case TRUE -> true;
            case FALSE -> false;
        };
    }
    public String valueString() {
        return switch (this) {
            case TRUE -> "true";
            case FALSE -> "false";
        };
    }
}