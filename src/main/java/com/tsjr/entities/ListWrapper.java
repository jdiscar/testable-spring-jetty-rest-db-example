package com.tsjr.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE )
@XmlRootElement(name = "message")
public class ListWrapper<T> {
	private List<T> list;

	public ListWrapper() {}	

	public ListWrapper(List<T> list) {
		this.list = list;
	}

	@XmlElements({ 
		@XmlElement(name="comments", type=Comment.class)
	})
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
