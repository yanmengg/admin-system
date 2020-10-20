package com.hsshy.beam.api.sign.converter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hsshy.beam.api.config.properties.BeamRestProperties;
import com.hsshy.beam.api.sign.security.DataSecurityAction;
import com.hsshy.beam.common.constant.RetEnum;
import com.hsshy.beam.common.exception.BeamException;
import com.hsshy.beam.common.utils.MD5Util;
import com.hsshy.beam.common.utils.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * 带签名的http信息转化器
 *
 * @author fengshuonan
 * @date 2017-08-25 15:42
 */
public class WithSignMessageConverter extends FastJsonHttpMessageConverter {


    @Autowired
    BeamRestProperties beamRestProperties;

    @Autowired
    DataSecurityAction dataSecurityAction;

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {



        InputStream in = inputMessage.getBody();

        Object o = JSON.parseObject(in, super.getFastJsonConfig().getCharset(), BaseTransferEntity.class, super.getFastJsonConfig().getFeatures());


        //先转化成原始的对象
        BaseTransferEntity baseTransferEntity = (BaseTransferEntity) o;

        String object = baseTransferEntity.getObject();
        String json = dataSecurityAction.unlock(object);
        String encrypt = MD5Util.encrypt(object + beamRestProperties.getSecret());

        if (encrypt.equals(baseTransferEntity.getSign())) {
        } else {
            logger.error("签名校验失败,数据被改动过!");
            throw new BeamException(RetEnum.SIGN_ERROR);
        }

        //如果是泛型则将其解析为  获得 Class 定义中声明的父类的泛型参数类型
        if(type.toString().equals("Entity")){

            return JSON.parseObject(json, ReflectionUtils.getSuperGenericType(contextClass));
        }
        else {

            //校验签名后再转化成应该的对象
            return JSON.parseObject(json, type);
        }

    }
}
