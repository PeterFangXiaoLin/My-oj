package com.my.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {
    private List<String> outputList;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 接口信息（额外的信息：比如接口是否正常）
     */
    private String message;

    /**
     * 执行状态
     */
    private Integer status;

}
