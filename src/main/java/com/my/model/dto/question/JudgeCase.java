package com.my.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * 判题用例
 */

@Data
public class JudgeCase implements Serializable {

    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;

    private static final long serialVersionUID = -7398699578979820880L;
}
