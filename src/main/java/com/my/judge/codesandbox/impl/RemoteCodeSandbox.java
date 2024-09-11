package com.my.judge.codesandbox.impl;

import com.my.judge.codesandbox.CodeSandbox;
import com.my.judge.codesandbox.model.ExecuteCodeRequest;
import com.my.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱，实际调用接口的沙箱
 *
 * 判题服务作为一个远程调用的通用接口，可以作为一个api远程服务
 */
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
