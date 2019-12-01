package com.williamssonoma.zipcoderanges.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.williamssonoma.zipcoderanges.exceptions.ValueNotVerifiedException;

@Entity
public class ZipCodeRange {
	
	@Id
	@Column(name="LOW_ZIPCODE")
	private Integer lowZipCode;
	@Column(name="HIGH_ZIPCODE")
	private Integer highZipCode;
	
	@Transient
	private final Logger logger = LoggerFactory.getLogger(ZipCodeRange.class);


	/**
	 * Build this object with a range of valid zip codes. 
	 * 1) 5 digits - between lowestZip and highestZip inclusive
	 * 2) lowZip is less than or equal to highZip
	 * @param lowZip
	 * @param highZip
	 */
	public ZipCodeRange(Integer lowZip, Integer highZip) {
		logger.debug("New ZipCodeRange: " + lowZip + " : " + highZip);
		
		if (lowZip <= highZip) {
			this.lowZipCode = lowZip;
			this.highZipCode = highZip;
		} else {
			throw new ValueNotVerifiedException("Invalid range of zip codes");
		}

	}
	
	public ZipCodeRange() {
	}
	
	public Integer getLowZipCode() {
		return lowZipCode;
	}

	public Integer getHighZipCode() {
		return highZipCode;
	}


}

