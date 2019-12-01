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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.williamssonoma.zipcoderanges.ZipcoderangesApplication;
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

		mvc.perform(get("/zipcoderanges"))
			.andExpect(jsonPath("$.[*].lowZipCode", hasItems(501, 10000, 91000)))
			.andExpect(jsonPath("$.[*].highZipCode", hasItems(625, 31006, 99950)))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getZipCodeRangeSucceeds() throws Exception {

		when(service.getZipCodeRange("10001")).thenReturn(list.get(1));

		mvc.perform(get("/zipcoderanges/10001"))
			.andExpect(jsonPath("$.lowZipCode", equalTo(10000)))
			.andExpect(jsonPath("$.highZipCode", equalTo(31006)))
			.andExpect(status().isOk());
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
	
	@Test
	public void addZipCodeRangeFailsLowZipInvalid() throws Exception {
		
		submission.setLowZipCode(300);
		submission.setHighZipCode(400);
		
		mvc.perform(post("/zipcoderanges")
				.content(submission.toJson())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void addZipCodeRangeFailsHighZipInvalid() throws Exception {
		
		submission.setLowZipCode(99999);
		submission.setHighZipCode(99999);
		
		mvc.perform(post("/zipcoderanges")
				.content(submission.toJson())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void addZipCodeRangeFailsZipsMissing() throws Exception {
		
		SubmissionDTO submission = new SubmissionDTO();
		
		mvc.perform(post("/zipcoderanges")
				.content(submission.toJson())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}
	
}
