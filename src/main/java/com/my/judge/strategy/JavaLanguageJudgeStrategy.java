package com.my.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.my.model.dto.question.JudgeCase;
import com.my.model.dto.question.JudgeConfig;
import com.my.judge.codesandbox.model.JudgeInfo;
import com.my.model.entity.Question;
import com.my.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * java 判题策略
 */

public class JavaLanguageJudgeStrategy implements JudgeStrategy {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Question question = judgeContext.getQuestion();
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();

        JudgeInfo judgeInfoResult = new JudgeInfo();
        judgeInfoResult.setMemory(memory);
        judgeInfoResult.setTime(time);

        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResult.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResult;
        }
        for (int i = 0; i < outputList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!outputList.get(i).equals(judgeCase.getOutput())) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResult.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResult;
            }
        }
        // 5) 答案如果正确，验证时间和空间有没有超出
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long memoryLimit = judgeConfig.getMemoryLimit();
        Long timeLimit = judgeConfig.getTimeLimit();

        if (memory > memoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResult.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResult;
        }

        // java 程序额外提供 10秒时间
        final long JAVA_PROGRAM_TIME_COST = 10000L;
        if (time - JAVA_PROGRAM_TIME_COST > timeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResult.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResult;
        }
        judgeInfoResult.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResult;
    }
}
