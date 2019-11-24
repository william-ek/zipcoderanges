package com.williamssonoma.zipcoderanges.models;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.williamssonoma.zipcoderanges.exceptions.ValueNotVerifiedException;

public class ZipCodeRange implements Comparable<ZipCodeRange> {
	
	private Integer lowZipCode;
	private Integer highZipCode;
	
	private final Logger logger = LoggerFactory.getLogger(ZipCodeRange.class);

	/**
	 * We'll tell Map that two key objects are equal if there are any intersections. High and Low are determined by the
	 * lower bound, using the higher bound other would work as well.
	 */
	public int compareTo(ZipCodeRange zipCodes) {
		
		if ((zipCodes.lowZipCode >= this.lowZipCode && zipCodes.lowZipCode <= this.highZipCode	) 
			||
			(zipCodes.highZipCode >= this.lowZipCode && zipCodes.lowZipCode <= this.highZipCode	)) {
			return 0;
		} else {
			if (zipCodes.lowZipCode < this.lowZipCode) {
				return -1;
			} else {
				return 1;
			}
		}
	}
	

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
	

	public Integer getLowZipCode() {
		return lowZipCode;
	}

	public Integer getHighZipCode() {
		return highZipCode;
	}

	/**
	 * Create a union of this Object's range and the input Object's range. No rocket science here.
	 * 
	 * @param zipCodeRange
	 * @return
	 */
	public ZipCodeRange combineRanges(List<ZipCodeRange> zipCodeRanges) {
		
		int lowZip = lowZipCode;
		int highZip = highZipCode;
		
		for (ZipCodeRange zip : zipCodeRanges) {
			if (zip.getLowZipCode() < lowZip) lowZip = zip.getLowZipCode();
			if (zip.getHighZipCode() > highZip) highZip = zip.getHighZipCode();
		}
		
		return new ZipCodeRange(lowZip, highZip);
		
	}

}

