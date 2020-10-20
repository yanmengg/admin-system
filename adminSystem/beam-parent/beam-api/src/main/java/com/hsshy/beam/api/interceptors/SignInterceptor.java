package com.hsshy.beam.api.interceptors;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hsshy.beam.api.config.properties.BeamRestProperties;
import com.hsshy.beam.api.sign.security.DataSecurityAction;
import com.hsshy.beam.api.sign.util.ApiUtil;
import com.hsshy.beam.common.annotion.IgnoreSignAuth;
import com.hsshy.beam.common.constant.RetEnum;
import com.hsshy.beam.common.utils.MD5Util;
import com.hsshy.beam.common.utils.R;
import com.hsshy.beam.common.utils.RenderUtil;
import com.hsshy.beam.common.utils.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
/**
 * 小程序拦截器
 */
@Slf4j
@Component
public class SignInterceptor implements HandlerInterceptor {

    @Autowired
    private BeamRestProperties beamRestProperties;

    @Autowired
    private DataSecurityAction dataSecurityAction;

    /**
     * 执行完控制器后调用，即离开时
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception e) {
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        // application/json Post请求
        log.info(request.getMethod());
        log.info(request.getContentType());
        if("POST".equals(request.getMethod())){
            IgnoreSignAuth annotation;
            if(handler instanceof HandlerMethod) {
                annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreSignAuth.class);
            }else{
                return true;
            }
            //如果有@IgnoreUTokenAuth注解，则不验证utoken
            if(annotation != null){
                log.info("不验证签名： {}", ((HandlerMethod) handler).getMethod());
                return true;
            }
            String sign = request.getHeader("sign");
            if(ToolUtil.isEmpty(sign)){
                RenderUtil.renderJson(response, R.fail(RetEnum.SIGN_ERROR.getRet(), RetEnum.SIGN_ERROR.getMsg()));
                return false;
            }
            if("application/json".equals(request.getContentType())){
                String body = getBody(request);
                JSONObject jsonObject = JSON.parseObject(body, JSONObject.class);
                body = dataSecurityAction.doAction(jsonObject.toJSONString());
                String encrypt = MD5Util.encrypt(body + beamRestProperties.getSecret());
                if(!encrypt.equals(sign)){
                    RenderUtil.renderJson(response, R.fail(RetEnum.SIGN_ERROR.getRet(), RetEnum.SIGN_ERROR.getMsg()));
                    return false;
                }
            }
            else if("application/x-www-form-urlencoded".equals(request.getContentType())){
                String encrypt = ApiUtil.createSign(request,beamRestProperties.getSecret());
                if(!encrypt.equals(sign)){
                    RenderUtil.renderJson(response, R.fail(RetEnum.SIGN_ERROR.getRet(), RetEnum.SIGN_ERROR.getMsg()));
                    return false;
                }
            }
        }
        return true;
    }

    public String getBody(HttpServletRequest request){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
