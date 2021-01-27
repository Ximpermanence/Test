package com.example.demo.enums;

import lombok.Getter;

/**
 * @description:
 * @author: chenhao
 * @create:2021/1/4 17:15
 **/
@Getter
public enum StudentSortEnum {

    /**
     * a
     */
    STUDENT_SORT_A("2", "a"),

    /**
     * b
     */
    STUDENT_SORT_B("1", "b"),

    /**
     * c
     */
    STUDENT_SORT_E("4", "e"),

    /**
     * d
     */
    STUDENT_SORT_D("5", "d"),

    /**
     * v
     */
    STUDENT_SORT_V("3", "v");


    private final String value;
    private final String label;

    StudentSortEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
