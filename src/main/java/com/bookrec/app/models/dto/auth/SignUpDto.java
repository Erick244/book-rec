package com.bookrec.app.models.dto.auth;

public record SignUpDto(String username, String password, String confirmPassword) {

	public boolean passwordIsValid() {
		int passwordLength = password.length();
		
		boolean passwordIsValid = passwordLength >= 8 || passwordLength <= 16;

		return passwordIsValid;
	}
	
	public boolean passwordsMatch() {
		boolean passwordsMatch = password.equals(confirmPassword);
		
		return passwordsMatch;
	}
}
