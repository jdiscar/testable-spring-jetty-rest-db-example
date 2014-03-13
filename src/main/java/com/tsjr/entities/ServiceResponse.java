package com.tsjr.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "response")
@XmlSeeAlso({Comment.class, ListWrapper.class})
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceResponse<T> {
	@XmlElement
	private String status = "200";

	@XmlAnyElement
	private T message = null;

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the message.
	 */
	public T getMessage() {
		return this.message;
	}

	/**
	 * @param message The message to set.
	 */
	public void setMessage(T message) {
		this.message = message;
	}
}