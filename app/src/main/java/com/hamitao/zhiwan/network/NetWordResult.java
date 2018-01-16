package com.hamitao.zhiwan.network;

/**
 * Created by linjianwen on 2018/1/5.
 */

public class NetWordResult {
    /**
     * 执行结果是否成功
     */
    private byte status ;

    /**
     * 错误信息, 返回一个错误
     */
    private Error error;

    /**
     * 信息提示
     */
    private String tips;

    /**
     * 数据， 可以为空
     */
    private Object result;

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isOk() {
        return status == 0;
    }
}
