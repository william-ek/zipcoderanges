package com.williamssonoma.zipcoderanges.validation;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ZipCodeValidator implements ConstraintValidator<ZipCode, Integer> {
	
	private final Logger logger = LoggerFactory.getLogger(ZipCodeValidator.class);
	
	@Value("${zip.lowest}")
	public String lowestZipCodeString;
	
	private Integer lowestZipCode;
	
	@Value("${zip.highest}")
	public String highestZipCodeString;
	
	public Integer highestZipCode;
	
	@PostConstruct
	public void convertValues() {
		logger.debug("Validator post construct: " + lowestZipCodeString + " : " + highestZipCodeString);
		
		this.lowestZipCode = Integer.parseInt(lowestZipCodeString.trim());
		this.highestZipCode = Integer.parseInt(highestZipCodeString.trim());
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		logger.debug("Validator for zip code: " + value);
		
		//leave this case to the @NotNull validator
		if (value == null) return true;
		
		if (value < this.lowestZipCode || value > this.highestZipCode) return false;
		
		return true;
	}

}
