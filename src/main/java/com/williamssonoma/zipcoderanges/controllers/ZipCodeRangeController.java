package com.williamssonoma.zipcoderanges.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.williamssonoma.zipcoderanges.exceptions.ValueNotVerifiedException;
import com.williamssonoma.zipcoderanges.models.SubmissionDTO;
import com.williamssonoma.zipcoderanges.models.ZipCodeRange;
import com.williamssonoma.zipcoderanges.services.ZipCodeService;


@RestController
@RequestMapping  ("/zipcoderanges")
public class ZipCodeRangeController {
	
    private final Logger logger = LoggerFactory.getLogger(ZipCodeRangeController.class);
    
    @Autowired
    private ZipCodeService zipCodeService;
    
	@ResponseStatus(HttpStatus.OK)
    @GetMapping ()
    public List<ZipCodeRange> getZipCodeRanges() {
    	logger.debug("Get Zip Codes");
    	
    	List<ZipCodeRange> ranges = zipCodeService.getZipCodeRanges();
    	
    	return ranges;
    	
    }
    
	@ResponseStatus(HttpStatus.OK)
    @GetMapping ("/{zipCode}")
    public ZipCodeRange getZipCodeRange(@PathVariable String zipCode) {
    	logger.debug("Get Zip Code: " + zipCode);

    	return zipCodeService.getZipCodeRange(zipCode);
    	
    }
    
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping ()
    public ZipCodeRange postZipCodeRange(@Valid @RequestBody SubmissionDTO submission, BindingResult bindingResult) {
    	logger.debug("Submit Zip Codes");
    	
        if (bindingResult.hasErrors()) {
        	StringBuilder errorMessages = new StringBuilder();
        	bindingResult.getAllErrors().forEach(error -> {
        		errorMessages.append(error.getDefaultMessage()  + "; ");
        	});
            throw new ValueNotVerifiedException(errorMessages.toString());
        }
    	
    	return zipCodeService.addZipCodeRange(submission);

    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping ()
    public List<ZipCodeRange> deleteZipCodeRanges() {
    	logger.debug("Delete Zip Codes");
    	
    	return zipCodeService.deleteZipCodeRanges();
    	
    }

}
