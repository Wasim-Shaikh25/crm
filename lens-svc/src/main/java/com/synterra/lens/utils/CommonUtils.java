package com.synterra.lens.utils;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

	public String getNewSequenceNumber(String drfNumber){
		
       String[] parts = drfNumber.split("/");
                int lastNumber = Integer.parseInt(parts[parts.length - 1]);
                int incrementedNumber = lastNumber + 1;
        
        return Integer.toString(incrementedNumber);
        
	}
}
