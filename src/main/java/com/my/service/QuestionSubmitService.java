package com.my.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.my.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.my.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.my.model.entity.User;
import com.my.model.vo.QuestionSubmitVO;

/**
* @author helloworld
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-09-09 08:49:10
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {


    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);


    /**
     * 获取查询条件
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 转VO
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目提交封装
     * @param questionSubmitVOPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitVOPage, User loginUser);
}
