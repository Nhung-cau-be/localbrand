package com.localbrand;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;



public class Validate {
	public static boolean checkPhone(String phone){
        Pattern pattern = Pattern.compile("^0[0-9]{9}$");
        Matcher matcher = pattern.matcher(phone);
        
        return matcher.find();
    }

}
