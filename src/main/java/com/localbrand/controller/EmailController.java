package com.localbrand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.localbrand.dal.entity.OTP;
import com.localbrand.dal.repository.ICustomerRepository;
import com.localbrand.dtos.response.EmailDto;
import com.localbrand.service.IEmailService;
import com.localbrand.service.impl.OTPServiceImpl;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "http://localhost:4200")
public class EmailController {
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Autowired
	private IEmailService emailService;
	
	@Autowired
    private OTPServiceImpl otpService;
	
	@PostMapping("/send-email")
	public void sendEmail(@RequestBody EmailDto emailDto) { 
		emailService.sendEmail(emailDto.getTo(), emailDto.getSubject(), emailDto.getText());
	}
	
	@PostMapping("/send-otp/{email}")
    public void sendOTP(@PathVariable String email) {
		int checkIfEmailExists = customerRepository.countByEmail(email);
        if (checkIfEmailExists != 0) {
			System.out.println("Da tim thay email");
        	
			emailService.deleteExistingEmail(email);
	    	OTP otp = otpService.generateOTP(email);
	        emailService.sendOTPEmail(email, otp.getOtpCode());	
        }
        else {
			System.out.println("Khong tim thay email");
		}
	}
	
	@GetMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestParam String email, @RequestParam int otpCode) {
        if (otpService.validateOTP(email, otpCode)) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
    }
}
