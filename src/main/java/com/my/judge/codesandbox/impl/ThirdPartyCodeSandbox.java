package com.my.judge.codesandbox.impl;

import com.my.judge.codesandbox.CodeSandbox;
import com.my.judge.codesandbox.model.ExecuteCodeRequest;
import com.my.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱，调用别人现成的代码沙箱
 *
 * 判题服务作为一个远程调用的通用接口，可以作为一个api远程服务
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
