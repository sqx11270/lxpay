package com.lx.pay.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lx.pay.web.service.HttpTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private HttpTool httpTool;

    @RequestMapping("/")
    public String index(ModelMap model, HttpServletRequest request) {
        model.addAttribute("name", "Zhudaye");
        return "index";
    }

    @RequestMapping(value="/test", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String jsontest(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "success");
        jsonObject.put("key", "value");
        return jsonObject.toJSONString();
    }

    @RequestMapping(value="/test-post", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String postData(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        JSONObject dataToBePost = new JSONObject();
        dataToBePost.put("device_no", "xxxxx");
        dataToBePost.put("auth_code", "xxxxx");
        dataToBePost.put("goods_desc", "xxxxx");
        dataToBePost.put("pp_trade_no", "xxxxx");

        String rsp = httpTool.httpPost("http://openapi-zc.meituan.com/api/open/pay", dataToBePost.toJSONString());
        if (null == rsp || rsp.isEmpty()) {
            jsonObject.put("msg", "empty rsp");
            return jsonObject.toJSONString();
        }

        JSONObject retJson = JSON.parseObject(rsp);
        if (null == retJson) {
            jsonObject.put("msg", "invalid json response.");
            jsonObject.put("rsp", rsp);
            return jsonObject.toJSONString();
        }

        String code = (String)retJson.get("code");
        String msg = (String)retJson.get("msg");

        jsonObject.put("msg", "success");
        jsonObject.put("recv-code", code);
        jsonObject.put("recv-msg", msg);
        return jsonObject.toJSONString();
    }
}
