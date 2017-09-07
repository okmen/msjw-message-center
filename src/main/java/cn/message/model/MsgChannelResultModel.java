package cn.message.model;

import java.io.Serializable;

public class MsgChannelResultModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1365638825823100071L;

	private int errcode;	//48001：api未授权； 40097：参数错误
	private String errmsg;	//api unauthorized：api未授权；invalid args:参数错误
	private String result_page_url;	//结果页url，需跳转到该url替代原有的服务结果页面
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getResult_page_url() {
		return result_page_url;
	}
	public void setResult_page_url(String result_page_url) {
		this.result_page_url = result_page_url;
	}
}
