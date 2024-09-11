package com.my.judge.strategy;

import com.my.judge.codesandbox.model.JudgeInfo;

/**
 * 策略模式
 *
 * 判题策略
 *
 * 不同的编程语言执行的内存和时间消耗是不一样的，同样的一份代码，不同语言的差别可能会很大
 * 比如：c++和Java，为了增加合理性，给Java语言提供更多的内存和时间
 * 为了给不同的语言提供不同的判题服务，需要使用策略模式
 */
public interface JudgeStrategy {
    /**
     * 执行判题
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
