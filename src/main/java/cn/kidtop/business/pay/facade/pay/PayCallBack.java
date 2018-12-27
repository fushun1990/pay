package cn.kidtop.business.pay.facade.pay;

import java.util.function.Consumer;

import cn.kidtop.business.pay.exception.PayException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
public class PayCallBack<T> {
	/**
	 * 执行回调方法
	 * @param consumer
	 * @param no
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月10日
	 * @records <p>  fushun 2016年9月10日</p>
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void callBack(Consumer<T> consumer,T no){
		if(consumer==null){
			throw new PayException(PayException.Enum.CALLBACK_URL_ERROR_EXCEPTION);
		}
		consumer.accept(no);
	}
}
