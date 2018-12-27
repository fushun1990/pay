package cn.kidtop.business.pay.cmp;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.kidtop.framework.base.BaseCMP;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;




/**
 * 支付异常记录
 * @author codeMaker
 *
 * @version 
 * @creation 2016年09月17日
 */
@Entity
@Table(name="kidtop_common_record_pay_exception_message")
@DynamicInsert
@DynamicUpdate
public class RecordPayExceptionMessageCMP extends BaseCMP implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	* id
	*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")    
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")  
	private java.lang.String id;
	
	/**
	* 方法名称
	*/
	private java.lang.String methodName;
	
	/**
	* 方法参数
	*/
	private java.lang.String methodParams;
	
	/**
	* 错误信息
	*/
	private java.lang.String errorMessage;
	
	
	

	
	
	/**
	* 获取id
	*/
	public java.lang.String getId(){
		return this.id;
	}
	
	/**
	* 设置id
	*/
	public void setId(java.lang.String id){
		this.id = id;
	}
	
	/**
	* 获取方法名称
	*/
	public java.lang.String getMethodName(){
		return this.methodName;
	}
	
	/**
	* 设置方法名称
	*/
	public void setMethodName(java.lang.String methodName){
		this.methodName = methodName;
	}
	
	/**
	* 获取方法参数
	*/
	public java.lang.String getMethodParams(){
		return this.methodParams;
	}
	
	/**
	* 设置方法参数
	*/
	public void setMethodParams(java.lang.String methodParams){
		this.methodParams = methodParams;
	}
	
	/**
	* 获取错误信息
	*/
	public java.lang.String getErrorMessage(){
		return this.errorMessage;
	}
	
	/**
	* 设置错误信息
	*/
	public void setErrorMessage(java.lang.String errorMessage){
		this.errorMessage = errorMessage;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("RecordPayExceptionMessageCMP  [");
		sb.append("id=").append(id).append(", ");
		sb.append("methodName=").append(methodName).append(", ");
		sb.append("methodParams=").append(methodParams).append(", ");
		sb.append("errorMessage=").append(errorMessage).append(", ");
		
		sb.append("]");
		 
		 return sb.toString();
	}
	
}