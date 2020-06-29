package com.xuyang.blog.utils;
/**
 * @Author: XuYang
 * @Date: 2020/6/22 14:49
 * @Description:
 */

import com.alibaba.fastjson.JSONObject;

public class MessageOut {
    /**
     * 返回
     * @param code
     * @param msg
     * @return
     */
    public static JSONObject output(int code, String msg){
        JSONObject js = new JSONObject();
        js.put("code", code);
        js.put("msg", msg);
        return js;
    }

    /**
     * 返回
     * @param code
     * @param msg
     * @param object
     * @return
     */
    public static JSONObject output(int code, String msg, Object object){
        JSONObject js = new JSONObject();
        js.put("code", code);
        js.put("msg", msg);
        js.put("data", object);
        return js;
    }

    /**
     * 成功
     * @param object
     * @return
     */
    public static JSONObject successful(Object object){
        return output(1,"成功", object);
    }
    public static JSONObject successful(){
        return output(1, "成功");
    }
    public static JSONObject successful(String msg){
        return output(1, msg);
    }

    /**
     * 失败
     * @param code
     * @param msg
     * @return
     */
    public static JSONObject failed(int code, String msg){return output(code, msg);}
    public static JSONObject failed(int code, String msg, Object object){return output(code, msg, object);}

}

