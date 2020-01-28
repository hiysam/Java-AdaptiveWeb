package com.adaptive.common.exception;

/**
 * Copyright (c) WO
 * 
 * @author WO
 * 
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -5360050904599026394L;

	public enum MessageSeverity {
		INFO, WARN, ERROR, FATAL
	};

	private MessageSeverity messageSeverity = MessageSeverity.ERROR;
	private String messageKey;
	private String[] messageParams;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, boolean isMessageKey) {
		this(MessageSeverity.ERROR, message, isMessageKey);
	}

	public ServiceException(MessageSeverity messageSeverity, String message,
			boolean isMessageKey) {
		super(message);
		this.messageSeverity = messageSeverity;
		if (isMessageKey) {
			this.messageKey = message;
		}
	}

	public ServiceException(String messageKey, String[] messageParams) {
		this(MessageSeverity.ERROR, messageKey, messageParams);
	}

	public ServiceException(MessageSeverity messageSeverity, String messageKey,
			String[] messageParams) {
		super(messageKey);
		this.messageSeverity = messageSeverity;
		this.messageKey = messageKey;
		this.messageParams = messageParams;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public String[] getMessageParams() {
		return messageParams;
	}

	public void setMessageParams(String[] messageParams) {
		this.messageParams = messageParams;
	}

	public MessageSeverity getMessageSeverity() {
		return messageSeverity;
	}

	public void setMessageSeverity(MessageSeverity messageSeverity) {
		this.messageSeverity = messageSeverity;
	}

}
