package com.williamssonoma.zipcoderanges.services;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.williamssonoma.zipcoderanges.exceptions.ValueNotVerifiedException;
import com.williamssonoma.zipcoderanges.models.SubmissionDTO;
import com.williamssonoma.zipcoderanges.models.ZipCodeRange;
import com.williamssonoma.zipcoderanges.repository.ZipCodeRangeRepository;

@Service
public class ZipCodeService {
	
	private final Logger logger = LoggerFactory.getLogger(ZipCodeService.class);
	
	@Value("${zip.notfound}")
	String notFoundMessage;
	
	@Value("${zip.rangenotvalid}")
	String notValidMessage;
	
	@Autowired
	private ZipCodeRangeRepository zipCodeRangeRepository;
	
	public List<ZipCodeRange> getZipCodeRanges() {
		logger.debug("getZipCodeRanges: ");

		return zipCodeRangeRepository.findAll();

	}
	
	public ZipCodeRange getZipCodeRange(String zipCode) {
		logger.debug("getZipCodeRange: " + zipCode);
		
		try {
			return zipCodeRangeRepository.findByZipCode(Integer.parseInt(zipCode));
		} catch (NumberFormatException e) {
			throw new ValueNotVerifiedException(notValidMessage);
		}

	}
	
	@Transactional
	public ZipCodeRange addZipCodeRange(SubmissionDTO submission) {
		logger.debug("addZipCodeRange: " + submission.getLowZipCode() + " ; " + submission.getHighZipCode());
		
		Integer lowZipCode = submission.getLowZipCode();
		Integer highZipCode = submission.getHighZipCode();
		
		if (lowZipCode > highZipCode) {
			throw new ValueNotVerifiedException(notValidMessage);
		}

		for (ZipCodeRange range : zipCodeRangeRepository.getZipCodeRanges(lowZipCode, highZipCode)) {
			zipCodeRangeRepository.deleteById(range.getLowZipCode());
			if (range.getLowZipCode() < lowZipCode) {
				lowZipCode = range.getLowZipCode();
			}
			if (range.getHighZipCode() > highZipCode) {
				highZipCode = range.getHighZipCode();
			}
		};
		
		ZipCodeRange range = new ZipCodeRange(lowZipCode, highZipCode);
		zipCodeRangeRepository.save(range);
		
		return range;
		
	}
	
	public List<ZipCodeRange> deleteZipCodeRanges() {
		logger.debug("deleteZipCodeRanges: ");
		
		zipCodeRangeRepository.deleteAll();
		
		return zipCodeRangeRepository.findAll();
		
	}
	

}
