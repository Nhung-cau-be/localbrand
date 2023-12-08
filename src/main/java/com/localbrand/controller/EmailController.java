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
import java.util.Arrays;
import com.localbrand.dal.entity.OTP;
import com.localbrand.dal.repository.ICustomerRepository;
import com.localbrand.dtos.response.EmailDto;
import com.localbrand.dtos.response.ResponseDto;
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
	public ResponseEntity<?> sendEmail(@RequestBody EmailDto emailDto) { 
		try {
            String to = emailDto.getTo();
            String subject = emailDto.getSubject();
            String htmlContent = emailDto.getText();

            emailService.sendEmail(to, subject, htmlContent);

            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
	}
	
	@GetMapping("/send-otp/{email}")
    public void sendOTP(@PathVariable String email) {
		System.out.println("Da tim thay email");
		emailService.deleteExistingEmail(email);
	    OTP otp = otpService.generateOTP(email);
	    emailService.sendOTPEmail(email, otp.getOtpCode());	
	}
	
	@GetMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestParam String email, @RequestParam int otpCode) {
        boolean validOTP = otpService.validateOTP(email, otpCode);
        System.out.println(validOTP);
        ResponseEntity<?> res  = validOTP == true ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), validOTP))
                 : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("OTP không chính xác !"), HttpStatus.BAD_REQUEST.value(), ""));
        System.out.println(res);
    	return res;
    }
	
	@GetMapping("/exist-email")
	public ResponseEntity<?> existEmail(@RequestParam String email) {
		int checkIfEmailExists = customerRepository.countByEmail(email);
		ResponseEntity<?> res  = checkIfEmailExists != 0 ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), checkIfEmailExists))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Không tìm thấy email !"), HttpStatus.BAD_REQUEST.value(), ""));
		return res;
	}
}
