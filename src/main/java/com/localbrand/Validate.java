package com.localbrand;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;



public class Validate {
	public static boolean checkPhoneNumber(String sdt){
        Pattern pattern = Pattern.compile("^0[0-9]{9}$");
        Matcher matcher = pattern.matcher(sdt);
        
        return matcher.find();
    }
//     public static boolean checkCCCD(String cccd){
//        Pattern pattern = Pattern.compile("^0[0-9]{11}$");
//        Matcher matcher = pattern.matcher(cccd);
//        
//        return matcher.find();
//    }
//    
//    public static boolean checkEmail(String email){
//       return EmailValidator.getInstance().isValid(email);
//  
//    }
//   
    
}
