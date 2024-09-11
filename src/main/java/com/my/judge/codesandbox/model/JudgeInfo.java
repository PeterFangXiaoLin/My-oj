package com.my.judge.codesandbox.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 判题信息
 */

@Data
public class JudgeInfo implements Serializable {

    /**
     * 执行时间
     */
    private Long time;

    /**
     * 内存消耗
     */
    private Long memory;

    /**
     * 执行信息
     */
    private String message;

    private static final long serialVersionUID = -7398699578979820880L;
}
