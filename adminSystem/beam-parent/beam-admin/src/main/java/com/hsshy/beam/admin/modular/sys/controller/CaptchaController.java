package com.hsshy.beam.admin.modular.sys.controller;
import com.hsshy.beam.admin.config.properties.BeamAdminProperties;
import com.hsshy.beam.common.utils.R;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 验证码生成
 *
 * @author fengshuonan
 * @date 2017-05-05 23:10
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private BeamAdminProperties beamAdminProperties;

    @RequestMapping("/open")
    public R isOpen() {
        return R.ok(beamAdminProperties.getCaptchaOpen());
    }

    /**
     * 生成验证码
     */
    @RequestMapping("/pic.jpg")
    public void pic(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        Captcha captcha = beamAdminProperties.getCaptcha();
        session.setAttribute(beamAdminProperties.getCaptchaKey(), captcha.text());
        try {
            captcha.out(response.getOutputStream());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
