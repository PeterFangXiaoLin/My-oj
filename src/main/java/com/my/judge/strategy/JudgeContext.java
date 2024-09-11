package com.my.judge.strategy;

import com.my.model.dto.question.JudgeCase;
import com.my.judge.codesandbox.model.JudgeInfo;
import com.my.model.entity.Question;
import com.my.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

@Data
public class JudgeContext {
    private List<String> inputList;
    private List<String> outputList;
    private List<JudgeCase> judgeCaseList;
    private Question question;
    private JudgeInfo judgeInfo;

    /**
     * 为了拿到用户提交的语言类型
     */
    private QuestionSubmit questionSubmit;
}
