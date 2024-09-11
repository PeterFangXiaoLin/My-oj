package com.my.judge.codesandbox;

import com.my.judge.codesandbox.model.ExecuteCodeRequest;
import com.my.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口
 *
 * 提高通用性，只调用接口，不调用具体的实现类
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param request
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest request);
}
