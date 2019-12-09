package com.aite.a.model;

public class Wxlogininfo {
	public Wxlogininfo() {
	}
//微信第一步
	private String access_token;
	private String expires_in;
	private String refresh_token;
	private String openid;
	private String scope;
	private String unionid;
	//微信第二步
	private String access_token2;
	private String expires_in2;
	private String refresh_token2;
	private String openid2;
	private String scope2;
	//微信第三步
	private String openid3;
	private String nickname3;
	private String sex3;
	private String language3;
	private String city3;
	private String province3;
	private String country3;
	private String headimgurl3;
	private String privilege3;
	private String unionid3;
	
	public String getOpenid3() {
		return openid3;
	}

	public String getNickname3() {
		return nickname3;
	}

	public String getSex3() {
		return sex3;
	}

	public String getLanguage3() {
		return language3;
	}

	public String getCity3() {
		return city3;
	}

	public String getProvince3() {
		return province3;
	}

	public String getCountry3() {
		return country3;
	}

	public String getHeadimgurl3() {
		return headimgurl3;
	}

	public String getPrivilege3() {
		return privilege3;
	}

	public String getUnionid3() {
		return unionid3;
	}

	public void setOpenid3(String openid3) {
		this.openid3 = openid3;
	}

	public void setNickname3(String nickname3) {
		this.nickname3 = nickname3;
	}

	public void setSex3(String sex3) {
		this.sex3 = sex3;
	}

	public void setLanguage3(String language3) {
		this.language3 = language3;
	}

	public void setCity3(String city3) {
		this.city3 = city3;
	}

	public void setProvince3(String province3) {
		this.province3 = province3;
	}

	public void setCountry3(String country3) {
		this.country3 = country3;
	}

	public void setHeadimgurl3(String headimgurl3) {
		this.headimgurl3 = headimgurl3;
	}

	public void setPrivilege3(String privilege3) {
		this.privilege3 = privilege3;
	}

	public void setUnionid3(String unionid3) {
		this.unionid3 = unionid3;
	}

	public String getAccess_token2() {
		return access_token2;
	}

	public String getExpires_in2() {
		return expires_in2;
	}

	public String getRefresh_token2() {
		return refresh_token2;
	}

	public String getOpenid2() {
		return openid2;
	}

	public String getScope2() {
		return scope2;
	}
	public void setAccess_token2(String access_token2) {
		this.access_token2 = access_token2;
	}

	public void setExpires_in2(String expires_in2) {
		this.expires_in2 = expires_in2;
	}

	public void setRefresh_token2(String refresh_token2) {
		this.refresh_token2 = refresh_token2;
	}

	public void setOpenid2(String openid2) {
		this.openid2 = openid2;
	}

	public void setScope2(String scope2) {
		this.scope2 = scope2;
	}

	
	
	public String getAccess_token() {
		return access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public String getOpenid() {
		return openid;
	}

	public String getScope() {
		return scope;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	@Override
	public String toString() {
		return "Wxlogininfo [access_token=" + access_token + ", expires_in="
				+ expires_in + ", refresh_token=" + refresh_token + ", openid="
				+ openid + ", scope=" + scope + ", unionid=" + unionid + "]";
	}

	public Wxlogininfo(String access_token, String expires_in,
			String refresh_token, String openid, String scope, String unionid) {
		super();
		this.access_token = access_token;
		this.expires_in = expires_in;
		this.refresh_token = refresh_token;
		this.openid = openid;
		this.scope = scope;
		this.unionid = unionid;
	}

	public Wxlogininfo(String access_token2, String expires_in2,
			String refresh_token2, String openid2, String scope2) {
		super();
		this.access_token2 = access_token2;
		this.expires_in2 = expires_in2;
		this.refresh_token2 = refresh_token2;
		this.openid2 = openid2;
		this.scope2 = scope2;
	}

	public Wxlogininfo(String openid3, String nickname3, String sex3,
			String language3, String city3, String province3, String country3,
			String headimgurl3, String privilege3, String unionid3) {
		super();
		this.openid3 = openid3;
		this.nickname3 = nickname3;
		this.sex3 = sex3;
		this.language3 = language3;
		this.city3 = city3;
		this.province3 = province3;
		this.country3 = country3;
		this.headimgurl3 = headimgurl3;
		this.privilege3 = privilege3;
		this.unionid3 = unionid3;
	}
}
