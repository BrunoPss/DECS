package com.decs.application.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ProblemType {
    TWO_BOX, ARTIFICIAL_ANT_SANTA_FE, LAWN_MOWER_8X8, BOOL_11_MULTIPLEXER_FAST;

    @Override
    public String toString() {
        return switch (this) {
            case TWO_BOX -> "Two Box";
            case ARTIFICIAL_ANT_SANTA_FE -> "Artificial Ant, Santa Fe Trail";
            case LAWN_MOWER_8X8 -> "Lawnmower";
            case BOOL_11_MULTIPLEXER_FAST -> "Boolean 11 Multiplexer";
        };
    }

    public static List<String> getValueList() {
        return Arrays.stream(ProblemType.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }
}