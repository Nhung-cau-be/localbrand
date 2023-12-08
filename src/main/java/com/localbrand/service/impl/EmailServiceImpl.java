package com.localbrand.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.localbrand.dal.entity.Category;
import com.localbrand.dal.entity.OTP;
import com.localbrand.dal.repository.ICustomerRepository;
import com.localbrand.dal.repository.IOTPRepository;
import com.localbrand.mappers.ICategoryDtoMapper;
import com.localbrand.service.IEmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements IEmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
    private IOTPRepository otpRepository;
    
    @Autowired
    private ICustomerRepository customerRepository;
    
	@Override
	public void sendEmail(String to, String subject, String text)
	{
        try {
        	MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); 
            javaMailSender.send(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void sendOTPEmail(String to, int otpCode) {
        try {
        	 SimpleMailMessage message = new SimpleMailMessage();
             message.setTo(to);
             message.setSubject("Xác nhận OTP");
             message.setText("Mã OTP của bạn là: " + otpCode);
             javaMailSender.send(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Override
    public void deleteExistingEmail(String email) {
    	try {
    		OTP otp = otpRepository.findByEmail(email);
        	otpRepository.delete(otp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
	
	@Override
	public boolean existEmail(String email) {
		int count = customerRepository.countByEmail(email);
		if (count != 0) {
			return true;
		}
		return false;
	}
}
