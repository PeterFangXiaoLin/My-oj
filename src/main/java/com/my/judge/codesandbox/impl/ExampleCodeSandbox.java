package com.my.judge.codesandbox.impl;

import com.my.judge.codesandbox.CodeSandbox;
import com.my.judge.codesandbox.model.ExecuteCodeRequest;
import com.my.judge.codesandbox.model.ExecuteCodeResponse;
import com.my.judge.codesandbox.model.JudgeInfo;
import com.my.model.enums.JudgeInfoMessageEnum;
import com.my.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱，仅为了跑通项目流程
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        List<String> inputList = request.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        executeCodeResponse.setMessage("执行成功");
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
