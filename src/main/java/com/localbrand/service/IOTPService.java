package com.localbrand.service;

import com.localbrand.dal.entity.OTP;

public interface IOTPService {
	OTP generateOTP(String email);
	boolean validateOTP(String email, int otpCode);
}
