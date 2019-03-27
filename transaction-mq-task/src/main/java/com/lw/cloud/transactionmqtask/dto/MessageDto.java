package com.lw.cloud.transactionmqtask.dto;

/**
 * 消息数据传输
 * @author lw
 *
 */
public class MessageDto {
	/**
	 * 消息ID
	 */
	private Long messageId;
	
	/**
	 * 消息内容
	 */
	private String message;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
