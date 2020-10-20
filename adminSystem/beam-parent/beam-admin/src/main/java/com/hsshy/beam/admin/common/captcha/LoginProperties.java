package com.hsshy.beam.admin.common.captcha;
import com.hsshy.beam.common.exception.BeamException;
import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import lombok.Data;
import java.util.Objects;
/**
 * 配置文件读取
 */
@Data
public class LoginProperties {

    /**
     * 账号单用户 登录
     */
    private boolean singleLogin = false;

    private LoginCode loginCode;


}
