package cn.kidtop.business.pay.facade;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import cn.kidtop.business.pay.dto.CallBackDTO;
import cn.kidtop.business.pay.enumeration.ECallBackFrom;
import cn.kidtop.business.pay.exception.PayException;


public class CallBack {
	
	private static Map<String,Consumer<CallBackDTO>> mapSuccess=new HashMap<String,Consumer<CallBackDTO>>();
	
	private static Map<String,Consumer<CallBackDTO>> mapFail=new HashMap<String,Consumer<CallBackDTO>>();
	
	/**
	 * 新增 回调接口
	 * @param callBackFrom 支付源
	 * @param successConsumer 成功回调接口
	 * @param failConsumer 失败回调接口
	 * @author fushun
	 * @version dev706
	 * @creation 2017年6月2日
	 * @records <p>  fushun 2017年6月2日</p>
	 */
	public static void registerCallBack(ECallBackFrom<String> callBackFrom,Consumer<CallBackDTO> successConsumer,Consumer<CallBackDTO> failConsumer){
		if(getSuccess(callBackFrom)!=null || getFail(callBackFrom)!=null){
			throw new PayException(PayException.Enum.CALLBACK_REGISTERED_EXCEPTION);
		}
		mapSuccess.put(callBackFrom.getCode(), successConsumer);
		mapFail.put(callBackFrom.getCode(), failConsumer);
	}
	
	/**
	 * 获取 成功回调接口
	 * @param eCallBackFrom 注册回调源
	 * @return 返回成功回调接口
	 * @author fushun
	 * @version dev706
	 * @creation 2017年6月2日
	 * @records <p>  fushun 2017年6月2日</p>
	 */
	public static Consumer<CallBackDTO> getSuccess(ECallBackFrom<String> eCallBackFrom){
		return mapSuccess.get(eCallBackFrom.getCode());
	}
	
	/**
	 * 获取 失败回调接口
	 * @param eCallBackFrom 注册回调源
	 * @return 返回失败回调接口
	 * @author fushun
	 * @version dev706
	 * @creation 2017年6月2日
	 * @records <p>  fushun 2017年6月2日</p>
	 */
	public static Consumer<CallBackDTO> getFail(ECallBackFrom<String> eCallBackFrom){
		return mapFail.get(eCallBackFrom.getCode());
	}
	
	/**
	 * 检查是否注册了 回调方法
	 * @param ePayFrom
	 * @author fushun
	 * @version dev706
	 * @creation 2017年6月5日
	 * @records <p>  fushun 2017年6月5日</p>
	 */
	public static void checkCallBack(ECallBackFrom<String> ePayFrom){
		if(getSuccess(ePayFrom)==null || getFail(ePayFrom)==null){
			throw new PayException(null, PayException.Enum.CALLBACK_NO_EXISTS_EXCEPTION);
		}
	}
}
