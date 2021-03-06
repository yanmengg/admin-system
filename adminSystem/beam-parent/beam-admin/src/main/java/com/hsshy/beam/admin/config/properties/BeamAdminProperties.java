package com.hsshy.beam.admin.config.properties;
import com.hsshy.beam.admin.common.captcha.LoginCode;
import com.hsshy.beam.admin.common.captcha.LoginCodeEnum;
import com.hsshy.beam.common.exception.BeamException;
import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * 项目相关配置
 *
 * @author fengshuonan
 * @date 2017年10月23日16:44:15
 */
@Data
@Configuration
@ConfigurationProperties(prefix = BeamAdminProperties.BEAM_REST_PREFIX)
public class BeamAdminProperties {

    public static final String BEAM_REST_PREFIX = "beam.admin";

    public String captchaKey = "CAPTCHA_SESSION_KEY";

    private Boolean captchaOpen = false;

    private Boolean swaggerOpen = false;

    // 账号单用户 登录
//    private Boolean singleLogin = false;

    private LoginCode loginCode;



    /**
     * 获取验证码生产类
     *
     * @return /
     */
    public Captcha getCaptcha() {
        if (Objects.isNull(loginCode)) {
            loginCode = new LoginCode();
            if (Objects.isNull(loginCode.getCodeType())) {
                loginCode.setCodeType(LoginCodeEnum.arithmetic);
            }
        }
        return switchCaptcha(loginCode);
    }

    /**
     * 依据配置信息生产验证码
     *
     * @param loginCode 验证码配置信息
     * @return /
     */
    private Captcha switchCaptcha(LoginCode loginCode) {
        Captcha captcha;
        synchronized (this) {
            switch (loginCode.getCodeType()) {
                case arithmetic:
                    // 算术类型 https://gitee.com/whvse/EasyCaptcha
                    captcha = new ArithmeticCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    // 几位数运算，默认是两位
                    captcha.setLen(loginCode.getLength());
                    break;
                case chinese:
                    captcha = new ChineseCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                case chinese_gif:
                    captcha = new ChineseGifCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                case gif:
                    captcha = new GifCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                case spec:
                    captcha = new SpecCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                default:
                    throw new BeamException("验证码配置信息错误！！！正确配置查看 me.zhengjie.modules.security.config.bean.LoginCodeEnum ");
            }
        }
        return captcha;
    }
}
