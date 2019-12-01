package com.williamssonoma.zipcoderanges.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.williamssonoma.zipcoderanges.models.ZipCodeRange;

@DataJpaTest
@RunWith(SpringRunner.class)
public class TestZipCodeRangeRepository {
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired 
	private ZipCodeRangeRepository zipCodeRangeRepository;
	
	@Test
	public void injectedComponentsAreNotNull() {
	    assertThat(entityManager).isNotNull();
	    assertThat(zipCodeRangeRepository).isNotNull();
	}
	
	@Test
	public void createThenfindByZipCodeSuccessful() {
		zipCodeRangeRepository.save(new ZipCodeRange(10001, 20000));
		ZipCodeRange range = zipCodeRangeRepository.findByZipCode(10100);
		assertThat(range).isNotNull();
		assertEquals(range.getLowZipCode(), new Integer(10001));
		assertEquals(range.getHighZipCode(), new Integer(20000));
	}
	
	@Test
	public void createThenfindByZipCodeNotFound() {
		zipCodeRangeRepository.save(new ZipCodeRange(10001, 20000));
		ZipCodeRange range = zipCodeRangeRepository.findByZipCode(501);
		assertThat(range).isNull();
	}
	
	@Test
	public void createThenfindRangesSuccessful() {
		zipCodeRangeRepository.save(new ZipCodeRange(501, 10000));
		zipCodeRangeRepository.save(new ZipCodeRange(10001, 20000));
		List<ZipCodeRange> ranges = zipCodeRangeRepository.getZipCodeRanges(600, 10100);
		assertThat(ranges).isNotNull();
		assertEquals(ranges.size(), 2);
		assertEquals(ranges.get(0).getLowZipCode(), new Integer(501));
		assertEquals(ranges.get(0).getHighZipCode(), new Integer(10000));
		assertEquals(ranges.get(1).getLowZipCode(), new Integer(10001));
		assertEquals(ranges.get(1).getHighZipCode(), new Integer(20000));
	}
	
	@Test
	public void createThenfindRangesNotFound() {
		zipCodeRangeRepository.save(new ZipCodeRange(501, 10000));
		zipCodeRangeRepository.save(new ZipCodeRange(10001, 20000));
		List<ZipCodeRange> ranges = zipCodeRangeRepository.getZipCodeRanges(20001, 30000);
		assertThat(ranges).isNotNull();
		assertEquals(ranges.size(), 0);
	}
	  
}

