package io.capgemini.com.model;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    boolean response;
    List<String> authority;
	public boolean isResponse() {
		return response;
	}
	public void setResponse(boolean response) {
		this.response = response;
	}
	public List<String> getAuthority() {
		return authority;
	}
	public void setAuthority(List<String> authority) {
		this.authority = authority;
	}
    
    
}
