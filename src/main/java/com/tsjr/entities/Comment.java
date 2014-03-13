package com.tsjr.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE )
@XmlRootElement(name = "comment")
@XmlAccessorType(XmlAccessType.FIELD)
public class Comment {
	private int id;
	@XmlElement private String version;
	@XmlElement private String message;

	public Comment() {}

	/**
	 * @return Returns the version.
	 */
	public int getId() {
		return this.id;
	}	

	/**
	 * @param version The version to set.
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return Returns the version.
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @param version The version to set.
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}