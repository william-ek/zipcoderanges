package com.williamssonoma.zipcoderanges.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.williamssonoma.zipcoderanges.models.ZipCodeRange;

public interface ZipCodeRangeRepository extends JpaRepository<ZipCodeRange, Integer> {

	
	/**
	 * Find a ZipCodeRange by Zip Code.
	 * The zip code is found if it exists within a range.
	 * 
	 * @param zipCode
	 * @return ZipCodeRange
	 */
	@Query("SELECT z FROM ZipCodeRange z WHERE z.lowZipCode <= :zipCode AND z.highZipCode >= :zipCode")
	public ZipCodeRange findByZipCode(@Param("zipCode") Integer zipCode);
	
	/**
	 * Find and Return ZipCodeRanges that are intersected by a new ZipCodeRange.
	 * The zip code is found if it exists within a range.
	 * 
	 * @param ZipCodeRange
	 * @return List<ZipCodeRange>
	 */
	@Query("SELECT z FROM ZipCodeRange z WHERE (z.lowZipCode <= :lowZipCode AND z.highZipCode >= :lowZipCode) OR (z.lowZipCode <= :highZipCode AND z.highZipCode >= :highZipCode)")
	public List<ZipCodeRange> getZipCodeRanges(@Param("lowZipCode") Integer lowZipCode, @Param("highZipCode") Integer highZipCode);


}
