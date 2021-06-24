package com.example.StockMarketCharting.repositories;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.StockMarketCharting.entities.StockPrice;

@Repository
@Transactional
public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {

	List<StockPrice> findByStockCode_Id(Long stockCodeId);

	List<StockPrice> findByStockCode_IdAndDateTime(Long stockCodeId, LocalDateTime d);

	Long deleteByStockCode_Id(Long stockCodeId);

	List<StockPrice> findByStockCode_IdAndDateTimeBetween(Long stockCodeId, LocalDateTime d1, LocalDateTime d2);

	Long deleteByStockCode_IdAndDateTimeBetween(Long stockCodeId, LocalDateTime d1, LocalDateTime d2);

	@Query(value = "select  DATE(date_Time ) as d from stock_price where stock_code_id = :id  group by DATE(date_time) having DATE(date_time) between :d1 and :d2", nativeQuery = true)
	Set<Date> findDatesWhereRecordExist(LocalDate d1, LocalDate d2, Long id);

}
