package com.luv.face2face.service.session;

public enum SessionCloseReason {

	/**
	 * 强制终端
	 */
	FORCE_DISCONNECT,

	/** 正常退出 */
	NORMAL,
	
	/** 链接超时 */
	OVER_TIME,
	

}
