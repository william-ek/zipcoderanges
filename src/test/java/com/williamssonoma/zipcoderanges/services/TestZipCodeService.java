package com.williamssonoma.zipcoderanges.services;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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

import com.williamssonoma.zipcoderanges.ZipcoderangesApplication;
import com.williamssonoma.zipcoderanges.exceptions.NotFoundException;
import com.williamssonoma.zipcoderanges.exceptions.ValueNotVerifiedException;
import com.williamssonoma.zipcoderanges.models.SubmissionDTO;
import com.williamssonoma.zipcoderanges.models.ZipCodeRange;
import com.williamssonoma.zipcoderanges.repository.ZipCodeRangeRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ZipcoderangesApplication.class)
@SpringBootTest
public class TestZipCodeService {
	
	List<ZipCodeRange> list;
	
	@InjectMocks
	ZipCodeService service;
	
	@Mock
	private ZipCodeRangeRepository repository;
	
	@Mock
	private ZipCodeRange zipCodeRange;
	
	@Mock
	SubmissionDTO submission;
	
	@Before
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		
		list = new ArrayList<>();
		
		list.add(new ZipCodeRange(501, 625));
		list.add(new ZipCodeRange(10000, 31006));
		list.add(new ZipCodeRange(91000, 99950));
		
		
	}
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void getZipCodeRangesSucceeds() throws Exception {
		
		when(repository.findAll()).thenReturn(list);
		
        List<ZipCodeRange> zipList = service.getZipCodeRanges();
         
        assertEquals(3, zipList.size());
		
	}
	
	@Test
	public void getZipCodeRangeSucceeds() throws Exception {
		
		when(repository.findByZipCode(new Integer(10001))).thenReturn(new ZipCodeRange(10000, 31006));
		
        ZipCodeRange zipCodeRange = service.getZipCodeRange("10001");
         
        assertEquals(new Integer(10000), zipCodeRange.getLowZipCode());
        assertEquals(new Integer(31006), zipCodeRange.getHighZipCode());
		
	}
	
	@Test
	public void getZipCodeRangeNotFound() throws Exception {
		
		when(repository.findByZipCode(new Integer(32000))).thenThrow(new NotFoundException("Zip Code is not in a Range"));
		
        exception.expect(NotFoundException.class);
		
        service.getZipCodeRange("32000");
        
	}
	
	@Test
	public void getZipCodeRangeNotValid() throws Exception {
				
        exception.expect(ValueNotVerifiedException.class);
		
        service.getZipCodeRange("abcde");
        
	}
	
	@Test
	public void deleteZipCodeRangeSucceeds() throws Exception {
		
		when(repository.findAll()).thenReturn(new ArrayList<ZipCodeRange>());
		
		List<ZipCodeRange> zipList = service.deleteZipCodeRanges();
         
        assertEquals(0, zipList.size());
        
	}
	
	@Test
	public void addZipCodeRangeSucceeds() throws Exception {
		
		submission = new SubmissionDTO();
		submission.setLowZipCode(600);
		submission.setHighZipCode(99900);
		
		when(repository.getZipCodeRanges(600, 99900)).thenReturn(list);
		
		ZipCodeRange newZips = service.addZipCodeRange(submission);
         
        assertEquals(new Integer(501), newZips.getLowZipCode());
        assertEquals(new Integer(99950), newZips.getHighZipCode());
        
	}
	
	@Test
	public void addZipCodeRangeNotValid() throws Exception {
		
		submission = new SubmissionDTO();
		submission.setLowZipCode(35000);
		submission.setHighZipCode(31000);
				
        exception.expect(ValueNotVerifiedException.class);
		
        service.addZipCodeRange(submission);
        
	}

}
