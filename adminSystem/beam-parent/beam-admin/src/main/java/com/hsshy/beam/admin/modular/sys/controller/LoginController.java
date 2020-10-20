package com.hsshy.beam.admin.modular.sys.controller;
import com.hsshy.beam.admin.common.log.LogManager;
import com.hsshy.beam.admin.common.log.factory.LogTaskFactory;
import com.hsshy.beam.admin.common.shiro.ShiroUtils;
import com.hsshy.beam.admin.config.properties.BeamAdminProperties;
import com.hsshy.beam.admin.modular.sys.dto.LoginForm;
import com.hsshy.beam.common.utils.R;
import com.hsshy.beam.common.utils.redis.RedisUtil;
import com.hsshy.beam.common.utils.support.HttpKit;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
public class LoginController  {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BeamAdminProperties beamAdminProperties;

    @PostMapping(value = "/login")
    @ResponseBody
    public R login(@RequestBody LoginForm loginForm){
        if(beamAdminProperties.getCaptchaOpen()){
            String captcha = ShiroUtils.getSessionAttribute(beamAdminProperties.getCaptchaKey()).toString();
            if(!loginForm.getCaptcha().equalsIgnoreCase(captcha)){
                return R.fail("验证码不正确");
            }
        }
        try{
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(loginForm.getUsername(), loginForm.getPassword());
            subject.login(token);
            LogManager.me().executeLog(LogTaskFactory.loginLog(ShiroUtils.getUserId(), HttpKit.getIp()));

        }catch (UnknownAccountException e) {
            return R.fail(e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return R.fail("账号或者密码不正确");
        }catch (LockedAccountException e) {
            return R.fail("账号已被锁定,请联系管理员");
        }catch (AuthenticationException e) {
            return R.fail("账户验证失败");
        }
        return R.ok(ShiroUtils.getUserEntity());
    }



    /**
     * 退出
     */
    @GetMapping(value = "/logout")
    @ResponseBody
    public Object logout() {
        ShiroUtils.logout();
        return R.ok("退出成功");
    }

    /**
     * 清除缓存
     */
    @GetMapping(value = "/clearCache")
    @RequiresPermissions("sys:user:clearCache")
    @ResponseBody
    public R clearCache() {
        redisUtil.clearCache();
        return R.ok("清除成功");
    }






}
