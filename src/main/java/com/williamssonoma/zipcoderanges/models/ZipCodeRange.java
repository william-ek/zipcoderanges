package com.williamssonoma.zipcoderanges.models;


import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.williamssonoma.zipcoderanges.exceptions.ValueNotVerifiedException;

public class ZipCodeRange implements Comparable<ZipCodeRange> {
	
	private Integer[] range = {0,0};
	
	private final Logger logger = LoggerFactory.getLogger(ZipCodeRange.class);

	/**
	 * We'll tell Map that two key objects are equal if there are any intersections. High and Low are determined by the
	 * lower bound, using the higher bound other would work as well.
	 */
	public int compareTo(ZipCodeRange zipCodes) {
		
		if ((zipCodes.range[0] >= this.range[0] && zipCodes.range[0] <= this.range[1]	) 
			||
			(zipCodes.range[1] >= this.range[0] && zipCodes.range[0] <= this.range[1]	)) {
			return 0;
		} else {
			if (zipCodes.range[0] < this.range[0]) {
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
		
		if (isZipValid(lowZip) && isZipValid(highZip) && lowZip <= highZip) {
			this.range[0] = lowZip;
			this.range[1] = highZip;
		} else {
			throw new ValueNotVerifiedException("Invalid range of zip codes");
		}

	}
	
	@Override
	public String toString() {
		return "ZipCodeRange [range=" + Arrays.toString(range) + "]";
	}

	private boolean isZipValid(Integer zip) {
		
		logger.debug("isZipValid: " + zip);
		
		if (zip >= 501 && zip <= 99950) return true;
		return false;
	}
	
	public Integer[] getRange() {
		return range;
	}

	/**
	 * Create a union of this Object's range and the input Object's range. No rocket science here.
	 * 
	 * @param zipCodeRange
	 * @return
	 */
	public ZipCodeRange combineRanges(List<ZipCodeRange> zipCodeRanges) {
		
		int lowZip = range [0];
		int highZip = range[1];
		
		for (ZipCodeRange zip : zipCodeRanges) {
			if (zip.getRange()[0] < lowZip) lowZip = zip.getRange()[0];
			if (zip.getRange()[1] > highZip) highZip = zip.getRange()[1];
		}
		
		return new ZipCodeRange(lowZip, highZip);
		
	}

}

