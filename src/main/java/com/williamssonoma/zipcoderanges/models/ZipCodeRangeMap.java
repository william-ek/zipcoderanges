package com.williamssonoma.zipcoderanges.models;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ZipCodeRangeMap extends TreeMap<ZipCodeRange, ZipCodeRange> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * To combine ranges we must recognize that the range to be added may encompass more than one existing range
	 * and therefore have more than one equal key. We handle this by removing an object after it has been retrieved
	 *  as equal.
	 *  
	 *  A Map is used for this very reason, only the retrieved value will tell us what the existing range is since equal
	 *  doesn't mean identical.
	 *   
	 * @param key
	 * @return
	 */
    public ZipCodeRange getCombinedRange(ZipCodeRange key) {
		
		List<ZipCodeRange> existingRanges = new ArrayList<>();
		
		ZipCodeRange existingRange = super.get(key);
		
		if (existingRange == null) {
			return key;
		}
		
		while (existingRange != null) {
			existingRanges.add(existingRange);
			super.remove(existingRange);
			existingRange = super.get(key);
		}
		
		return key.combineRanges(existingRanges);

    }
    

}

