package com.zhiwan.hamitao.xg.push;

import java.io.Serializable;

/**
 * <p>
 * 艾艾推送透传bean
 *
 * @author lian
 */
public class TransPush implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -77169435654435746L;
	
	/**
	 * 透传的类型
	 */
	private int transType;
	
	/**
	 * 透传信息的bean的json串
	 */
	private String content;

	public int getTransType() {
		return transType;
	}

	public void setTransType(int transType) {
		this.transType = transType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
