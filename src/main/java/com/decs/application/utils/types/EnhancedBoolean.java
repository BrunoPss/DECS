package com.decs.application.utils.types;

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