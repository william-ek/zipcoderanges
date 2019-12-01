package com.williamssonoma.zipcoderanges.models;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.williamssonoma.zipcoderanges.ZipcoderangesApplication;
import com.williamssonoma.zipcoderanges.exceptions.ValueNotVerifiedException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ZipcoderangesApplication.class)
@SpringBootTest
public class TestZipCodeRange {
	
	@Mock
	ZipCodeRange zipCodeRange;
	
	
	@Before
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void getZipCodeRangeNotValid1() {
		
        exception.expect(ValueNotVerifiedException.class);
		
        zipCodeRange = new ZipCodeRange(2, 1);
		
	}
	
	@Test
	public void getZipCodeRangeNotValid2() {
		
        exception.expect(ValueNotVerifiedException.class);
		
        zipCodeRange = new ZipCodeRange(12000, 10000);
		
	}
	
	@Test
	public void getZipCodeRangeNotValid3() {
		
        exception.expect(ValueNotVerifiedException.class);
		
        zipCodeRange = new ZipCodeRange(12000, 1);
		
	}

}
