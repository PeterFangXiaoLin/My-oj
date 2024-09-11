package com.my.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目配置
 */

@Data
public class JudgeConfig implements Serializable {

    /**
     * 时间限制
     */
    private Long timeLimit;

    /**
     * 内存限制
     */
    private Long memoryLimit;

    private static final long serialVersionUID = -7398699578979820880L;
}
