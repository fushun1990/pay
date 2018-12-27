package cn.kidtop.business.pay.facade.sdk.alipay.sign;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import cn.kidtop.business.pay.exception.PayException;
import org.springframework.util.StringUtils;

import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.StreamUtil;
import com.alipay.api.internal.util.codec.Base64;


public class RSA {
	private static final String ALGORITHM = "RSA";

	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	

	/**
	 * 获取PKCS8 私钥 
	 * @param algorithm  密钥类型（RSA）
	 * @param ins 私钥值
	 * @return
	 * @throws Exception
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月13日
	 * @records <p>  fushun 2017年1月13日</p>
	 */
	private static PrivateKey getPrivateKeyFromPKCS8(String algorithm,
            InputStream ins) throws Exception {
		if (ins == null || StringUtils.isEmpty(algorithm)) {
		return null;
		}
		
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		
		byte[] encodedKey = StreamUtil.readText(ins).getBytes();
		
		encodedKey = Base64.decodeBase64(encodedKey);
		
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
	}
	
	/**
	 * 获取公钥 对象
	 * @param algorithm 密钥类型（RSA）
	 * @param ins 公钥值
	 * @return
	 * @throws Exception
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月13日
	 * @records <p>  fushun 2017年1月13日</p>
	 */
	private static PublicKey getPublicKeyFromX509(String algorithm,
		                   InputStream ins) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		
		byte[] encodedKey = StreamUtil.readText(ins).getBytes();
		
		encodedKey = Base64.decodeBase64(encodedKey);
		
		return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
	}
	
	/**
	 * 签名加密 
	 * 使用 PKCS8  加密方式
	 * @param content  加密字符串
	 * @param privateKey  私钥
	 * @param input_charset  加密字符串编码
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月3日
	 * @records <p>  fushun 2017年1月3日</p>
	 */
	public static String signPKCS8(String content, String privateKey,String input_charset) {
		 try {
	            PrivateKey priKey = getPrivateKeyFromPKCS8(AlipayConstants.SIGN_TYPE_RSA,
	                new ByteArrayInputStream(privateKey.getBytes()));

	            java.security.Signature signature = java.security.Signature
	                .getInstance(AlipayConstants.SIGN_ALGORITHMS);

	            signature.initSign(priKey);

	            if (StringUtils.isEmpty(input_charset)) {
	                signature.update(content.getBytes());
	            } else {
	                signature.update(content.getBytes(input_charset));
	            }

	            byte[] signed = signature.sign();

	            return new String(Base64.encodeBase64(signed));
	        } catch (InvalidKeySpecException ie) {
	            throw new PayException("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私", PayException.PayCustomizeMessageEnum.CUSTOMIZE_MESSAGE_EXCEPTION);
	        } catch (Exception e) {
	            throw new PayException("RSAcontent = " + content + "; charset = " + input_charset,PayException.PayCustomizeMessageEnum.CUSTOMIZE_MESSAGE_EXCEPTION);
	        }
	}


	/**
	 * 验证签名是否正确
	 * 使用 X509 方式解密
	 * @param preSignStr 获取待签名字符串
	 * @param sign  比对的签名结果
	 * @param aliPayPublicKey 支付宝 公钥
	 * @param input_charset 字符编码
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月3日
	 * @records <p>  fushun 2017年1月3日</p>
	 */
	public static boolean verifyX509(String preSignStr, String sign, String aliPayPublicKey, String input_charset) {
		try {
			PublicKey pubKey = getPublicKeyFromX509(ALGORITHM,
			new ByteArrayInputStream(aliPayPublicKey.getBytes()));
			
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
			
			signature.initVerify(pubKey);
			
			if (StringUtils.isEmpty(input_charset)) {
				signature.update(preSignStr.getBytes());
			} else {
				signature.update(preSignStr.getBytes(input_charset));
			}
			return signature.verify(Base64.decodeBase64(sign.getBytes()));
		} catch (Exception e) {
            throw new PayException("RSAcontent = " + preSignStr + ",sign=" + sign + ",charset = " + input_charset,PayException.PayCustomizeMessageEnum.CUSTOMIZE_MESSAGE_EXCEPTION);
		}
	}
}
