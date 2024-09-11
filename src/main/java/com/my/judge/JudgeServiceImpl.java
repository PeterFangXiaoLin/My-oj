package com.my.judge;

import cn.hutool.json.JSONUtil;
import com.my.common.ErrorCode;
import com.my.exception.BusinessException;
import com.my.judge.codesandbox.CodeSandbox;
import com.my.judge.codesandbox.CodeSandboxFactory;
import com.my.judge.codesandbox.CodeSandboxProxy;
import com.my.judge.codesandbox.model.ExecuteCodeRequest;
import com.my.judge.codesandbox.model.ExecuteCodeResponse;
import com.my.judge.strategy.JudgeContext;
import com.my.model.dto.question.JudgeCase;
import com.my.judge.codesandbox.model.JudgeInfo;
import com.my.model.entity.Question;
import com.my.model.entity.QuestionSubmit;
import com.my.model.enums.QuestionSubmitStatusEnum;
import com.my.service.QuestionService;
import com.my.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Value("${codesandbox.type:example}")
    private String type;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private QuestionService questionService;

    @Resource
    private JudgeManager judgeManager;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1) 传入提交记录的id，根据提交记录的id拿到代码，语言
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        // 如果提交记录的状态不为等待中，直接返回，避免重复提交
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 修改提交记录的判题状态为运行中
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新提交记录状态失败");
        }

        // 2) 根据题目id，拿到题目的测试用例
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);

        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        // 3) 调用代码沙箱执行判题服务
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        // 4) 从代码沙箱的返回结果中拿到outputList,比对outputList是否相等
        List<String> outputList = executeCodeResponse.getOutputList();

        // 5) 调用代码沙箱执行判题
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfoResult = judgeManager.doJudge(judgeContext);
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoResult));
        // 6) 修改数据库状态
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }
        return questionSubmitService.getById(questionSubmitId);
    }
}
