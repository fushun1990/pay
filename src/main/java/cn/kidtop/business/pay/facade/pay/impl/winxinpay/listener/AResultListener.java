package cn.kidtop.business.pay.facade.pay.impl.winxinpay.listener;

import cn.kidtop.business.pay.exception.PayException;
import com.tencent.business.ResultListener;
import com.tencent.protocol.base_protocol.BaseResData;

import cn.kidtop.framework.util.JsonUtil;

/**
 * 微信 接口 ，监听接口
 * @author fushun
 *
 * @param <T>
 * @version dev706
 * @creation 2017年6月1日
 */
public abstract class AResultListener<T extends BaseResData> implements ResultListener<T> {

	@Override
	public void onFail(T t) {
		throw new PayException(new RuntimeException("接口调用错误："+JsonUtil.classToJson(t)), PayException.Enum.BASECODE_EXCEPTION);
	}

	@Override
	public void onFail(String response) {
		throw new PayException(new RuntimeException("接口调用错误："+response),  PayException.Enum.BASECODE_EXCEPTION);
	}


	@Override
	public void onFailBySignInvalid(T t) {
		throw new PayException(new RuntimeException("签名错误:"+JsonUtil.classToJson(t)),PayException.Enum.BASECODE_EXCEPTION);
	}

	@Override
	public abstract T getResData();



}
