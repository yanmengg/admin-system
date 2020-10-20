package com.hsshy.beam.admin.common.captcha;
import lombok.Data;
/**
 * 登录验证码配置信息
 */
@Data
public class LoginCode {
    /**
     * 验证码配置
     */
    private LoginCodeEnum codeType;
    /**
     * 验证码内容长度
     */
    private int length = 2;
    /**
     * 验证码宽度
     */
    private int width = 111;
    /**
     * 验证码高度
     */
    private int height = 36;

}
