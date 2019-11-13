package com.williamssonoma.zipcoderanges.controllers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.williamssonoma.zipcoderanges.app.ZipcoderangesApplication;
import com.williamssonoma.zipcoderanges.exceptions.NotFoundException;
import com.williamssonoma.zipcoderanges.models.SubmissionDTO;
import com.williamssonoma.zipcoderanges.models.ZipCodeRange;
import com.williamssonoma.zipcoderanges.services.ZipCodeService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ZipcoderangesApplication.class)
@WebMvcTest(ZipCodeRangeController.class)
public class TestZipCodeRangeController {
	
	List<ZipCodeRange> list;
	
	private SubmissionDTO submission = new SubmissionDTO();

	@MockBean
	private ZipCodeService service;

	@Autowired
	private MockMvc mvc;
	
	@Before
	public void setup() {
		
		list = new ArrayList<>();
		
		list.add(new ZipCodeRange(501, 625));
		list.add(new ZipCodeRange(10000, 31006));
		list.add(new ZipCodeRange(91000, 99950));
		
	}
	
	
	@Test
	public void getZipCodeRangesSucceeds() throws Exception {

		when(service.getZipCodeRanges()).thenReturn(list);

		mvc.perform(get("/zipcoderanges")).andExpect(status().isOk());
	}
	
	@Test
	public void getZipCodeRangeSucceeds() throws Exception {

		when(service.getZipCodeRange("10001")).thenReturn(list.get(1));

		mvc.perform(get("/zipcoderanges/10001")).andExpect(status().isOk());
	}
	
	@Test
	public void getZipCodeRangeNotFound() throws Exception {

		when(service.getZipCodeRange("35000")).thenThrow(new NotFoundException("Zip code is not in a range"));

		mvc.perform(get("/zipcoderanges/35000")).andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteZipCodeRangesSucceeds() throws Exception {

		when(service.deleteZipCodeRanges()).thenReturn(new ArrayList<ZipCodeRange>());

		mvc.perform(delete("/zipcoderanges")).andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void addZipCodeRangeSucceeds() throws Exception {
		
		submission.setLowZipCode(32000);
		submission.setHighZipCode(33000);
		
		ZipCodeRange newRange = new ZipCodeRange(32000, 33000);

		when(service.addZipCodeRange(submission)).thenReturn(newRange);
		
		mvc.perform(post("/zipcoderanges")
				.content(submission.toJson())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
}
