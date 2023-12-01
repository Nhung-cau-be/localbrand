package com.localbrand.service;

public interface IEmailService {
	void sendEmail(String to, String subject, String text);
	void sendOTPEmail(String to, int otpCode);
	void deleteExistingEmail(String email);
	boolean existEmail(String email);
}
