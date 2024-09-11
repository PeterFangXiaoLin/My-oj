package com.my.judge;

import com.my.judge.strategy.DefaultJudgeStrategy;
import com.my.judge.strategy.JavaLanguageJudgeStrategy;
import com.my.judge.strategy.JudgeContext;
import com.my.judge.strategy.JudgeStrategy;
import com.my.judge.codesandbox.model.JudgeInfo;
import com.my.model.entity.QuestionSubmit;
import com.my.model.enums.QuestionSubmitLanguageEnum;
import org.springframework.stereotype.Service;

/**
 * 策略选择
 *
 * 根据用户提交的语言类型进行策略的选择，而不是交给用户自己选择策略
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if (QuestionSubmitLanguageEnum.JAVA.getValue().equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
