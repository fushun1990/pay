package cn.kidtop.business.pay.facade.pay.impl.alipay;

import java.util.Map;

import cn.kidtop.business.pay.exception.PayException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alipay.api.AlipayResponse;

import cn.kidtop.business.pay.facade.sdk.alipay.util.AlipayCore;
import cn.kidtop.framework.exception.BaseException;

/**
 * 验证服务器的返回结果 
 * @author fushun
 *
 * @version V3.0商城
 * @creation 2016年9月18日
 */
@Component
public class ValidatorServiceResponse {
	/**
	 * 验证返回参数
	 * @param alipayResponse 返回对象
	 * @param errorMap 接口自定义错误信息
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月18日
	 * @records <p>  fushun 2016年9月18日</p>
	 */
	public void validatorServiceResponse(AlipayResponse alipayResponse,Map<String,String> errorMap) {
		try{
			Object code=alipayResponse.getCode();
			//支付失败
			if(code==null){
				throw new PayException(PayException.Enum.PAY_RETURN_STATUS_ERROR_EXCEPTION);
			}
			// 支付失败
			if(!"10000".equals(code)){
				errorCode( String.valueOf(code),alipayResponse.getSubCode(),errorMap);
				return;
			}
			return ;
		}catch(BaseException e){
			throw e;
		}catch(Exception e){
			throw new PayException(e, PayException.Enum.BASECODE_EXCEPTION);
		}
	}
	
	
	/**
	 * 解析错误码
	 * @param code
	 * @param sub_code
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月13日
	 * @records <p>  fushun 2016年9月13日</p>
	 */
	private void errorCode(String code,String sub_code,Map<String,String> errorMap){
		String error=errorMap.get(sub_code);
		if(!StringUtils.isEmpty(error)){
			throw new PayException(error, PayException.PayCustomizeMessageEnum.CUSTOMIZE_MESSAGE_EXCEPTION);
		}
		error=AlipayCore.errorMessage(code, sub_code);
		if(!StringUtils.isEmpty(error)){
			throw new PayException(error, PayException.PayCustomizeMessageEnum.CUSTOMIZE_MESSAGE_EXCEPTION);
		}
		throw new PayException(PayException.Enum.BASECODE_EXCEPTION);
	}

}
