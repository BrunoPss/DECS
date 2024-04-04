package com.decs.application.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ProblemType {
    TWO_BOX (new ParameterGroupType[]{
            ParameterGroupType.SIMPLE, ParameterGroupType.KOZA},
            "TBox",
            "Two Box - No ADFs",
            "GP"
    ),
    ARTIFICIAL_ANT_SANTA_FE (new ParameterGroupType[]{
            ParameterGroupType.SIMPLE, ParameterGroupType.KOZA, ParameterGroupType.ANT},
            "AASantaFe",
            "Artificial Ant - Santa Fe Trail",
            "GP"
    ),
    LAWN_MOWER_8X8 (new ParameterGroupType[]{
            ParameterGroupType.SIMPLE, ParameterGroupType.KOZA},
            "LM8x8",
            "Lawn Mower - 8x8",
            "GP"),
    BOOL_11_MULTIPLEXER_FAST (new ParameterGroupType[]{
            ParameterGroupType.SIMPLE, ParameterGroupType.KOZA},
            "B11MFast",
            "Boolean 11 Multiplexer (fast)",
            "GP"
    );

    private final ParameterGroupType[] parameterGroupList;
    private final String code, fullName, type;

    ProblemType(ParameterGroupType[] parameterGroupList, String code, String fullName, String type) {
        this.parameterGroupList = parameterGroupList;
        this.code = code;
        this.fullName = fullName;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.fullName;
    }

    public ParameterGroupType[] getParameterGroups() {
        return this.parameterGroupList;
    }

    public static List<String> getValueList() {
        return Arrays.stream(ProblemType.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

    public String getCode() { return this.code; }
}