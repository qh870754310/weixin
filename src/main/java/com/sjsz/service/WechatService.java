package com.sjsz.service;

import com.sjsz.message.resp.TextMessage;
import com.sjsz.util.WechatMessageUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/30.
 */
@Service
public class WechatService {

    private static Logger logger = Logger.getLogger(WechatService.class);

    public String processRequest(HttpServletRequest request) {
        Map<String, String> map = WechatMessageUtil.xmlToMap(request);
        logger.info(map);

        //发送方账号（一个OpenID）
        String fromUserName = map.get("FromUserName");
        //开发者微信号
        String toUserName = map.get("ToUserName");
        //消息类型
        String msgType = map.get("MsgType");
        //默认回复一个"success"
        String responseMessage = "success";
        //对消息进行处理
        if (WechatMessageUtil.RESP_MESSAGE_TYPE_TEXT.equals(msgType)) {  //文本类型
            TextMessage textMessage = new TextMessage();
            textMessage.setMsgType(WechatMessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(System.currentTimeMillis());
            textMessage.setContent("我已经受到你发来的消息了");
            responseMessage = WechatMessageUtil.textMessageToXml(textMessage);
        }
        logger.info(responseMessage);
        return responseMessage;
    }
}
