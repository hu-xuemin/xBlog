package com.huxuemin.xblog.infrastructure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.google.gson.annotations.SerializedName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "success")
@XmlType(name = "success", propOrder = { "code", "message" })
public class SuccessMessage {

    @XmlElement
    @SerializedName("code")
    private int code;

    @XmlElement
    @SerializedName("message")
    private String message;

    public SuccessMessage(){
        this.code = -1;
        this.message = "Successful!";
    }
    
    public SuccessMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return message;
    }
}
