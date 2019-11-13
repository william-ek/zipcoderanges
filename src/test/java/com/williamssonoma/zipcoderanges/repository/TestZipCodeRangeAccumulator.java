package com.williamssonoma.zipcoderanges.repository;



import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.williamssonoma.zipcoderanges.app.ZipcoderangesApplication;
import com.williamssonoma.zipcoderanges.exceptions.NotFoundException;
import com.williamssonoma.zipcoderanges.exceptions.ValueNotVerifiedException;
import com.williamssonoma.zipcoderanges.models.ZipCodeRange;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ZipcoderangesApplication.class)
public class TestZipCodeRangeAccumulator {
	
	@InjectMocks
	ZipCodeRangeAccumulator accumulator;
	
	@Mock
	private ZipCodeRange zipCodeRange;
	
	@Before
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		
		accumulator.deleteZipCodeRanges();
		
		zipCodeRange = new ZipCodeRange(10001, 12000);
		accumulator.addZipCodeRange(zipCodeRange);
		
		zipCodeRange = new ZipCodeRange(30001, 40000);
		accumulator.addZipCodeRange(zipCodeRange);
		
		zipCodeRange = new ZipCodeRange(50001, 70000);
		accumulator.addZipCodeRange(zipCodeRange);
		
	}
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void getZipCodeRangeSucceeds() {
		
		ZipCodeRange zip = accumulator.getZipCodeRange("11000");
		
		assertEquals(new Integer(10001), zip.getRange()[0]);
		assertEquals(new Integer(12000), zip.getRange()[1]);
		
	}
	
	@Test
	public void getZipCodeRangeNotFound() {
		
        exception.expect(NotFoundException.class);
		
		accumulator.getZipCodeRange("10000");
		
		
	}
	
	@Test
	public void getZipCodeRangesSucceeds() {
		
		List<ZipCodeRange> zips = accumulator.getZipCodeRanges();
		
		assertEquals(3, zips.size());
		
	}
	
	@Test
	public void addZipCodeRangesNewRangeSucceeds() {
		
		zipCodeRange = new ZipCodeRange(80001, 90000);
		
		ZipCodeRange zip = accumulator.addZipCodeRange(zipCodeRange);
		
		assertEquals(new Integer(80001), zip.getRange()[0]);
		assertEquals(new Integer(90000), zip.getRange()[1]);
		
	}
	
	@Test
	public void addZipCodeRangesNewRangeNotValid() {
		
        exception.expect(ValueNotVerifiedException.class);
		
		zipCodeRange = new ZipCodeRange(99999, 99999);
		
		zipCodeRange = new ZipCodeRange(1, 1);
		
		zipCodeRange = new ZipCodeRange(662, 501);
		
	}
	
	@Test
	public void addZipCodeRangesCombinedRangeSucceeds() {
		
		zipCodeRange = new ZipCodeRange(11000, 35000);
		
		ZipCodeRange zip = accumulator.addZipCodeRange(zipCodeRange);
		
		assertEquals(new Integer(10001), zip.getRange()[0]);
		assertEquals(new Integer(40000), zip.getRange()[1]);
		
	}
	
	@Test
	public void deletsZipCodeRangesRangesSucceeds() {
		
		List<ZipCodeRange> zips = accumulator.deleteZipCodeRanges();
		
		assertEquals(0, zips.size());
		
	}

}
