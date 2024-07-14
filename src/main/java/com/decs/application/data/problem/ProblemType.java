package com.decs.application.data.problem;

import com.decs.application.data.parameter.ParameterGroupType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <b>Problem Type Enumeration</b>
 * <p>
 *     Enumeration of the supported evolutionary problems
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public enum ProblemType {
    /**
     * Newcomb's paradox (two box problem)
     */
    TWO_BOX (new ParameterGroupType[]{
            ParameterGroupType.SIMPLE, ParameterGroupType.KOZA},
            "TBox",
            "Two Box - No ADFs",
            "GP"
    ),
    /**
     * Santa Fe Trail problem using artificial ant optimization
     */
    ARTIFICIAL_ANT_SANTA_FE (new ParameterGroupType[]{
            ParameterGroupType.SIMPLE, ParameterGroupType.KOZA, ParameterGroupType.ANT},
            "AASantaFe",
            "Artificial Ant - Santa Fe Trail",
            "GP"
    ),
    /**
     * Lawn Mower problem with 8 by 8 blocks
     */
    LAWN_MOWER_8X8 (new ParameterGroupType[]{
            ParameterGroupType.SIMPLE, ParameterGroupType.KOZA},
            "LM8x8",
            "Lawn Mower - 8x8",
            "GP"),
    /**
     * 11-bit Multiplexer problem (fast algorithm)
     */
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

    public String getCode() { return this.code; }
}