package com.localbrand.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localbrand.dal.entity.Customer;
import com.localbrand.dal.entity.OTP;
import com.localbrand.dal.repository.ICustomerRepository;
import com.localbrand.dal.repository.IOTPRepository;
import com.localbrand.service.IOTPService;

@Service
public class OTPServiceImpl implements IOTPService{
    @Autowired
    private IOTPRepository otpRepository;

    @Override
	public OTP generateOTP(String email) {
        try {
        	Random random = new Random();
            int otpCode = 100000 + random.nextInt(900000);
            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);
            
            OTP otp = new OTP();
            otp.setId(UUID.randomUUID().toString());
            otp.setOtpCode(otpCode);
            otp.setEmail(email);
            otp.setExpirationTime(expirationTime);
            return otpRepository.save(otp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
    
    @Override
    public boolean validateOTP(String email, int otpCode) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Optional<OTP> optionalOTP = otpRepository.findValidOTP(email, currentDateTime);
        if (optionalOTP.isPresent()) {
            if (optionalOTP.get().getOtpCode() == otpCode) {
                return true;
            }
        }
        return false;
    }

}
