package com.hsshy.beam.api.sign.security.impl;
import com.hsshy.beam.api.sign.security.DataSecurityAction;
import com.hsshy.beam.common.constant.RetEnum;
import com.hsshy.beam.common.exception.BeamException;
import org.springframework.util.Base64Utils;
/**
 * 对数据进行base64编码的方式
 */
public class Base64SecurityAction implements DataSecurityAction {

    @Override
    public String doAction(String beProtected) {
        return Base64Utils.encodeToString(beProtected.getBytes());
    }

    @Override
    public String unlock(String securityCode) {
        try {
            byte[] bytes = Base64Utils.decodeFromString(securityCode);
            return new String(bytes);
        }
        catch (Exception e){
            throw new BeamException(RetEnum.SIGN_ERROR);
        }
    }
}
