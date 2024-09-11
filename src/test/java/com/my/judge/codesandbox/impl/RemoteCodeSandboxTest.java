package com.my.judge.codesandbox.impl;

import com.my.judge.codesandbox.CodeSandbox;
import com.my.judge.codesandbox.CodeSandboxFactory;
import com.my.judge.codesandbox.CodeSandboxProxy;
import com.my.judge.codesandbox.model.ExecuteCodeRequest;
import com.my.judge.codesandbox.model.ExecuteCodeResponse;
import com.my.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
class RemoteCodeSandboxTest {

    @Value("${codesandbox.type:example}")
    private String type;

    @Test
    void executeCode() {
        // 每次new 一个对象，如果改用别的代码沙箱需要修改代码，
        // 使用工厂模式，用户只需传入对于的字符串，然后工厂帮助我们创建
        // 以后只需修改传入的字符串就可以修改代码沙箱，代码实现更加灵活
        CodeSandbox codeSandBox = new RemoteCodeSandbox();
        String code = "int main {}";
        String language = QuestionSubmitLanguageEnum.CPLUSPLUS.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

    @Test
    void executeCodeByValue() {
        CodeSandbox codeSandBox = CodeSandboxFactory.newInstance(type);
        String code = "int main {}";
        String language = QuestionSubmitLanguageEnum.CPLUSPLUS.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

    @Test
    void executeCodeByProxy() {
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String code = "int main {}";
        String language = QuestionSubmitLanguageEnum.CPLUSPLUS.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

    public static void main(String[] args) {
        // 在实际的项目中，不能使用scanner,
        // 可以通过 yml配置文件，让用户自己去自定义代码沙箱类型
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String type = scanner.next();
            CodeSandbox codeSandBox = CodeSandboxFactory.newInstance(type);
            String code = "int main {}";
            String language = QuestionSubmitLanguageEnum.CPLUSPLUS.getValue();
            List<String> inputList = Arrays.asList("1 2", "3 4");
            ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                    .code(code)
                    .language(language)
                    .inputList(inputList)
                    .build();
            ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        }
    }
}