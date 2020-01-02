package com.williamssonoma.zipcoderanges.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.williamssonoma.zipcoderanges.app.ZipcoderangesApplication;
import com.williamssonoma.zipcoderanges.exceptions.NotFoundException;
import com.williamssonoma.zipcoderanges.exceptions.ValueNotVerifiedException;
import com.williamssonoma.zipcoderanges.models.SubmissionDTO;
import com.williamssonoma.zipcoderanges.models.ZipCodeRange;
import com.williamssonoma.zipcoderanges.repository.ZipCodeRangeAccumulator;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ZipcoderangesApplication.class)
@SpringBootTest
public class TestZipCodeService {
	
	Set<ZipCodeRange> set;
	List<ZipCodeRange> list;
	
	@InjectMocks
	ZipCodeService service;
	
	@Mock
	private ZipCodeRangeAccumulator accumulator;
	
	@Mock
	private ZipCodeRange zipCodeRange;
	
	@Mock
	SubmissionDTO submission;
	
	@Before
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		
		set = new TreeSet<>();
		
		set.add(new ZipCodeRange(501, 625));
		set.add(new ZipCodeRange(10000, 31006));
		set.add(new ZipCodeRange(91000, 99950));
		
		list = new ArrayList<>(set);
		
	}
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void getZipCodeRangesSucceeds() throws Exception {
		
		when(accumulator.getZipCodeRanges()).thenReturn(list);
		
        List<ZipCodeRange> zipList = service.getZipCodeRanges();
         
        assertEquals(3, zipList.size());
		
	}
	
	@Test
	public void getZipCodeRangeSucceeds() throws Exception {
		
		when(accumulator.getZipCodeRange("10001")).thenReturn(new ZipCodeRange(10000, 31006));
		
        ZipCodeRange zipCodeRange = service.getZipCodeRange("10001");
         
        assertEquals(new Integer(10000), zipCodeRange.getLowZipCode());
        assertEquals(new Integer(31006), zipCodeRange.getHighZipCode());
		
	}
	
	@Test
	public void getZipCodeRangeNotFound() throws Exception {
		
		when(accumulator.getZipCodeRange("32000")).thenThrow(new NotFoundException("Zip Code is not in a Range"));
		
        exception.expect(NotFoundException.class);
		
        service.getZipCodeRange("32000");
        
	}
	
	@Test
	public void deleteZipCodeRangeSucceeds() throws Exception {
		
		when(accumulator.deleteZipCodeRanges()).thenReturn(new ArrayList<ZipCodeRange>());
		
		List<ZipCodeRange> zipList = service.deleteZipCodeRanges();
         
        assertEquals(0, zipList.size());
        
	}
	
	@Test
	public void addZipCodeRangeSucceeds() throws Exception {
		
		submission = new SubmissionDTO();
		submission.setLowZipCode(31005);
		submission.setHighZipCode(35000);
		
		when(accumulator.addZipCodeRange(any(ZipCodeRange.class))).thenReturn(new ZipCodeRange(10000, 35000));
		
		ZipCodeRange newZips = service.addZipCodeRange(submission);
         
        assertEquals(new Integer(10000), newZips.getLowZipCode());
        assertEquals(new Integer(35000), newZips.getHighZipCode());
        
	}
	
	@Test
	public void addZipCodeRangeNotValid() throws Exception {
		
		submission = new SubmissionDTO();
		submission.setLowZipCode(35000);
		submission.setHighZipCode(31000);
		
		when(accumulator.addZipCodeRange(any(ZipCodeRange.class))).thenThrow(new ValueNotVerifiedException("Values are not a valid zip code range"));
		
        exception.expect(ValueNotVerifiedException.class);
		
        service.addZipCodeRange(submission);
        
	}

}
