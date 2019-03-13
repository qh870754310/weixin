package com.sjsz.controller;

import com.sjsz.service.WechatService;
import com.sjsz.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2018/1/30.
 */
@Controller
public class weixinController {

    @Autowired
    private WechatService wechatService;

    /**
     * 验证微信服务器
     *
     * @param out
     * @param response
     * @param signature 微信加密签名,signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     */
    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    public void wechatService(PrintWriter out, HttpServletResponse response, @RequestParam(value = "signature", required = false) String signature, @RequestParam String timestamp,
                              @RequestParam String nonce, @RequestParam String echostr) {
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(response);
        }
    }

    /**
     * 接收来自微信发来的消息
     *
     * @param out
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/wechat", method = RequestMethod.POST)
    public void wechatServicePost(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        String responseMessage = wechatService.processRequest(request);
        out.print(responseMessage);
        out.flush();
    }
}
