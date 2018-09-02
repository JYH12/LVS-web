package com.unnet.triangle.lvs.master.controller.restful;

/**
 * 
 * @author john
 *
 * @param <T>
 */
public class RestResult<T> {

    /**
     * 返回状态码：成功(0)， 失败(其他)
     */
    private int code;
    /**
     * 状态码对应信息
     */
    private String message;
    /**
     * 包装的对象
     */
    private T data;

    public RestResult() {
        this.code = 0;
        this.message = "success";
        this.data = null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
