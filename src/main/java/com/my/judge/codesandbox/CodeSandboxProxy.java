package com.my.judge.codesandbox;

import com.my.judge.codesandbox.model.ExecuteCodeRequest;
import com.my.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用静态代理对代码沙箱进行增强
 * 代码沙箱代理类，对代码沙箱进行增强，比如打印日志等功能（类比中介）
 *
 * 代理模式的实现原理
 * 1. 实现被代理的接口
 * 2. 通过构造函数传入一个实现代理接口的实现类
 * 3. 调用被代理的接口实现类，在前后进行增强
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandbox {

    private final CodeSandbox codeSandbox;

    // 这里也可以使用lombok @AllArgsConstructor注解
    public CodeSandboxProxy(CodeSandbox codeSandBox) {
        this.codeSandbox = codeSandBox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        log.info("代码沙箱请求信息：" + request.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(request);
        log.info("代码沙箱响应信息：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
