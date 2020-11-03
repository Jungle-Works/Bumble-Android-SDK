package com.bumble.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IpResponse implements Serializable
{

@SerializedName("ip")
@Expose
private String ip;

public String getIp() {
return ip;
}

public void setIp(String ip) {
this.ip = ip;
}

}