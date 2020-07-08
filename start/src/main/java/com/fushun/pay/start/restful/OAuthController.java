package com.fushun.pay.start.restful;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthController {

//    @Autowired
//    private OAuthServiceI oAuthServiceI;
//
//    /**
//     * 获取access_token,openId
//     * @param weixinOauth20CO
//     * @return
//     */
//    @PostMapping("/oauth20/weixingzh/authorize")
//    public WeiXinOAuth20ResponseVO getWeixOauth(@RequestBody WeixinOauth20CO weixinOauth20CO){
//        Oauth20WeixinCmd oauth20WeixinCmd=new Oauth20WeixinCmd();
//        Context context = new Context();
//        context.setBizCode(BizCode.OAUTH20_WEIXIN);
//        oauth20WeixinCmd.setContext(context);
//        oauth20WeixinCmd.setWeixinOauth20CO(weixinOauth20CO);
//        SingleResponse<OAuth20ResponseVO> singleResponse= oAuthServiceI.oahth20(oauth20WeixinCmd);
//        if(!singleResponse.isSuccess()){
//            throw new BadRequestException(singleResponse.getErrMessage());
//        }
//        return (WeiXinOAuth20ResponseVO)singleResponse.getData();
//    }
//
//
//    /**
//     * 获取用户信息
//     * @param weixinOauth20CO
//     * @return
//     */
//    @PostMapping("/oauth20/weixingzh/userinfo")
//    public WeixinUserInfoResponseVO getWeixOauth(@RequestBody WeixinUserInfoCO weixinOauth20CO){
//        UserInfoWeixinCmd userInfoWeixinCmd=new UserInfoWeixinCmd();
//        Context context = new Context();
//        context.setBizCode(BizCode.USER_INFO_WEIXIN);
//        userInfoWeixinCmd.setContext(context);
//        userInfoWeixinCmd.setWeixinUserInfoCO(weixinOauth20CO);
//        SingleResponse<OAuth20ResponseVO> singleResponse= oAuthServiceI.oahth20(userInfoWeixinCmd);
//        if(!singleResponse.isSuccess()){
//            throw new BadRequestException(singleResponse.getErrMessage());
//        }
//        return (WeixinUserInfoResponseVO)singleResponse.getData();
//    }
//
//    public static void main(String[] args) {
//        String s="{\"openid\":\"oiExM1A8hngxRGz8QW3-GEnDgdxA\",\"nickname\":\"王福顺\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"朝阳\",\"province\":\"北京\",\"country\":\"中国\",\"headimgurl\":\"http:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/vi_32\\/7qKHK7zohEWnZPqcsDckiaMsejFwJm3clqiaaWrZxMqUb1JGkqCzmJg690ibROgXciboJtwl3LI2lUZxZ1bQTeKpGQ\\/132\",\"privilege\":[]}";
//        UserInfoResData userInfoResData= JsonUtil.jsonToClass(s,UserInfoResData.class);
//        WeixinUserInfoResponseVO weiXinOAuth20RssonseVO = new WeixinUserInfoResponseVO();
//        ConverterUtil.convert(userInfoResData,weiXinOAuth20RssonseVO);
//        System.out.println(weiXinOAuth20RssonseVO);
//    }

}
