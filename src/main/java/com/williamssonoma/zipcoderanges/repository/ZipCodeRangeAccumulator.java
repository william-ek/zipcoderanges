package com.williamssonoma.zipcoderanges.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.williamssonoma.zipcoderanges.exceptions.NotFoundException;
import com.williamssonoma.zipcoderanges.models.ZipCodeRange;
import com.williamssonoma.zipcoderanges.models.ZipCodeRangeMap;

@Component
public class ZipCodeRangeAccumulator {
	
	private static final ZipCodeRangeMap zipCodeMap = new ZipCodeRangeMap();
	
	@Value("${zip.notfound}")
	public String notFoundMessage;

	
	/**
	 * Add a range to the map.
	 * 
	 * @param zipCodeRange
	 */
	public ZipCodeRange addZipCodeRange(ZipCodeRange zipCodeRange) {
		
		ZipCodeRange combinedZip = zipCodeMap.getCombinedRange(zipCodeRange);
		zipCodeMap.put(combinedZip, combinedZip);
		return combinedZip;
	}
	
	/**
	 * Return a range that includes the argument zip code.
	 * @param zipCode
	 * @return
	 */
	public ZipCodeRange getZipCodeRange(String zipCode) {
		
		Integer searchZipCode;
		try {
			searchZipCode = Integer.parseInt(zipCode);
		} catch (NumberFormatException e) {
			throw new NotFoundException(notFoundMessage);
		}
		ZipCodeRange searchZipCodeRange = new ZipCodeRange(searchZipCode, searchZipCode);
		ZipCodeRange zipCodeRange = zipCodeMap.get(searchZipCodeRange);
		
		if (zipCodeRange == null) {
			throw new NotFoundException(notFoundMessage);
		}
		
		return zipCodeRange;
	}
	
	public List<ZipCodeRange> getZipCodeRanges() {
		return new ArrayList<ZipCodeRange>(getZipCodeSet());
	}
	
	/**
	 * A TreeSet will go a little faster than the Map.
	 * 
	 * @return
	 */
	public TreeSet<ZipCodeRange> getZipCodeSet() {
		
		Set<ZipCodeRange> rangeSet = zipCodeMap.keySet();
		return new TreeSet<ZipCodeRange>(rangeSet);
	}
	
	public List<ZipCodeRange> deleteZipCodeRanges() {
		
		zipCodeMap.clear();
		return new ArrayList<ZipCodeRange>(getZipCodeSet());
	}

}

