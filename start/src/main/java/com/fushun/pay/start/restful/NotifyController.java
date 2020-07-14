package com.fushun.pay.start.restful;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.BizScenario;
import com.fushun.framework.util.util.IOUtils;
import com.fushun.framework.web.annotations.NoApiResult;
import com.fushun.pay.client.api.PayServiceI;
import com.fushun.pay.client.dto.cmd.notify.PayNotifyWeixinGZHCmd;
import com.fushun.pay.dto.clientobject.notify.PayNotifyWeixinGZHDTO;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/notify")
public class NotifyController {

    @Autowired
    private PayServiceI payServiceI;

    /**
     * 微信公众号 支付异步通知功能
     */
    @PostMapping("/weixin/gzh")
    @NoApiResult
    public void payWeixinGZHNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader( "Content-type", "text/html;charset=UTF-8" );
        PrintWriter out = response.getWriter();
        InputStream in = request.getInputStream();
        BufferedReader br = new BufferedReader( new InputStreamReader( in, "UTF-8" ) );
        String result=IOUtils.toString(in,"UTF-8");

        PayNotifyWeixinGZHCmd payNotifyCmd=new PayNotifyWeixinGZHCmd();
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.PAY_SCENARIO_WEIXIN_GZH);
        payNotifyCmd.setBizScenario(bizScenario);

        PayNotifyWeixinGZHDTO payNotifyWeixinGZHDTO=new PayNotifyWeixinGZHDTO();
        payNotifyWeixinGZHDTO.setNotifyContent(result);
        payNotifyCmd.setPayNotifyWeixinGZHDTO(payNotifyWeixinGZHDTO);
        SingleResponse<String> singleResponse= payServiceI.payNotifyAlipayReust(payNotifyCmd);
        out.write(singleResponse.getData());
    }
}
