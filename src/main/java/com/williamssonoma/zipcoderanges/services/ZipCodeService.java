package com.williamssonoma.zipcoderanges.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.williamssonoma.zipcoderanges.models.SubmissionDTO;
import com.williamssonoma.zipcoderanges.models.ZipCodeRange;
import com.williamssonoma.zipcoderanges.repository.ZipCodeRangeAccumulator;

@Service
public class ZipCodeService {
	
	private final Logger logger = LoggerFactory.getLogger(ZipCodeService.class);
	
	@Autowired
	private ZipCodeRangeAccumulator zipCodeRangeAccumulator;
	
	public List<ZipCodeRange> getZipCodeRanges() {
		logger.debug("getZipCodeRanges: ");

		return zipCodeRangeAccumulator.getZipCodeRanges();

	}
	
	public ZipCodeRange getZipCodeRange(String zipCode) {
		logger.debug("getZipCodeRange: " + zipCode);
		
		return zipCodeRangeAccumulator.getZipCodeRange(zipCode);

	}
	
	public ZipCodeRange addZipCodeRange(SubmissionDTO submission) {
		logger.debug("addZipCodeRange: " + submission.getLowZipCode() + " ; " + submission.getHighZipCode());
		
		ZipCodeRange range = new ZipCodeRange(submission.getLowZipCode(), submission.getHighZipCode());
		return zipCodeRangeAccumulator.addZipCodeRange(range);
		
	}
	
	public List<ZipCodeRange> deleteZipCodeRanges() {
		logger.debug("deleteZipCodeRanges: ");
		
		return zipCodeRangeAccumulator.deleteZipCodeRanges();
		
	}
	

}
