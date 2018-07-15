package com.jpmorgan.reports.tradingReport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.reports.tradingReport.model.Instruction;
import com.jpmorgan.reports.tradingReport.model.TradeType;
import com.jpmorgan.reports.tradingReport.util.ReportUtil;

public class ReportUtilTest {
	
	List<Instruction> instructionList = new ArrayList<Instruction>();
	
	
	@Before
	public void setUp() throws Exception {
		instructionList.add(new Instruction.Builder().withEntity("E1").withCurrency("SGP").withFxRate(new BigDecimal("0.22")).withInstructionDate(LocalDate.of(2018, 07, 9))
				.withsettlementDate(LocalDate.of(2018, 07, 10)).withPricePerUnit(new BigDecimal("150.5")).withTradeType(TradeType.BUY).withUnits(450).build());
		
		instructionList.add(new Instruction.Builder().withEntity("E2").withCurrency("EUR").withFxRate(new BigDecimal("0.50")).withInstructionDate(LocalDate.of(2018, 07, 9))
				.withsettlementDate(LocalDate.of(2018, 07, 10)).withPricePerUnit(new BigDecimal("112")).withTradeType(TradeType.BUY).withUnits(400).build());
		
		instructionList.add(new Instruction.Builder().withEntity("E3").withCurrency("SGP").withFxRate(new BigDecimal("0.20")).withInstructionDate(LocalDate.of(2018, 07, 9))
				.withsettlementDate(LocalDate.of(2018, 07, 11)).withPricePerUnit(new BigDecimal("110")).withTradeType(TradeType.SELL).withUnits(100).build());
		
		instructionList.add(new Instruction.Builder().withEntity("E4").withCurrency("EUR").withFxRate(new BigDecimal("0.22")).withInstructionDate(LocalDate.of(2018, 07, 11))
				.withsettlementDate(LocalDate.of(2018, 07, 12)).withPricePerUnit(new BigDecimal("210")).withTradeType(TradeType.BUY).withUnits(250).build());
		
		instructionList.add(new Instruction.Builder().withEntity("E5").withCurrency("EUR").withFxRate(new BigDecimal("0.22")).withInstructionDate(LocalDate.of(2018, 07, 11))
				.withsettlementDate(LocalDate.of(2018, 07, 12)).withPricePerUnit(new BigDecimal("100.25")).withTradeType(TradeType.SELL).withUnits(150).build());
		
		instructionList.add(new Instruction.Builder().withEntity("E6").withCurrency("EUR").withFxRate(new BigDecimal("0.22")).withInstructionDate(LocalDate.of(2018, 07, 12))
				.withsettlementDate(LocalDate.of(2018, 07, 13)).withPricePerUnit(new BigDecimal("150.5")).withTradeType(TradeType.BUY).withUnits(110).build());
		
		instructionList.add(new Instruction.Builder().withEntity("E7").withCurrency("SGP").withFxRate(new BigDecimal("0.80")).withInstructionDate(LocalDate.of(2018, 07, 12))
				.withsettlementDate(LocalDate.of(2018, 07, 13)).withPricePerUnit(new BigDecimal("150.5")).withTradeType(TradeType.SELL).withUnits(210).build());
		
		instructionList.add(new Instruction.Builder().withEntity("E8").withCurrency("SGP").withFxRate(new BigDecimal("0.22")).withInstructionDate(LocalDate.of(2018, 07, 13))
				.withsettlementDate(LocalDate.of(2018, 07, 16)).withPricePerUnit(new BigDecimal("150.5")).withTradeType(TradeType.SELL).withUnits(450).build());
		
		instructionList.add(new Instruction.Builder().withEntity("E1").withCurrency("SGP").withFxRate(new BigDecimal("0.22")).withInstructionDate(LocalDate.of(2018, 07, 16))
				.withsettlementDate(LocalDate.of(2018, 07, 17)).withPricePerUnit(new BigDecimal("150.5")).withTradeType(TradeType.BUY).withUnits(450).build());
		
	}

	@Test
	public void testCalculateSettlementDateForAED() {
		Instruction instruction = new Instruction.Builder().withEntity("E1").withCurrency("AED").withFxRate(new BigDecimal("0.22")).withInstructionDate(LocalDate.of(2018, 07, 12))
				.withsettlementDate(LocalDate.of(2018, 07, 14)).withPricePerUnit(new BigDecimal("150.5")).withTradeType(TradeType.BUY).withUnits(450).build();
		assertEquals(LocalDate.of(2018, 07, 14), instruction.getSettlementDate());
		ReportUtil.calculateSettlementDate(instruction);
		assertEquals(LocalDate.of(2018, 07, 15), instruction.getSettlementDate());
		
	}
	
	@Test
	public void testCalculateSettlementDateForSAR() {
		Instruction instruction = new Instruction.Builder().withEntity("E1").withCurrency("SAR").withFxRate(new BigDecimal("0.22")).withInstructionDate(LocalDate.of(2018, 07, 12))
				.withsettlementDate(LocalDate.of(2018, 07, 13)).withPricePerUnit(new BigDecimal("150.5")).withTradeType(TradeType.BUY).withUnits(450).build();
		assertEquals(LocalDate.of(2018, 07, 13), instruction.getSettlementDate());
		ReportUtil.calculateSettlementDate(instruction);
		assertEquals(LocalDate.of(2018, 07, 15), instruction.getSettlementDate());
		
	}
	
	@Test
	public void testCalculateSettlementDateForSARNoNeedToAdjustSettlementDate() {
		Instruction instruction = new Instruction.Builder().withEntity("E1").withCurrency("SAR").withFxRate(new BigDecimal("0.22")).withInstructionDate(LocalDate.of(2018, 07, 11))
				.withsettlementDate(LocalDate.of(2018, 07, 12)).withPricePerUnit(new BigDecimal("150.5")).withTradeType(TradeType.BUY).withUnits(450).build();
		assertEquals(LocalDate.of(2018, 07, 12), instruction.getSettlementDate());
		ReportUtil.calculateSettlementDate(instruction);
		assertEquals(LocalDate.of(2018, 07, 12), instruction.getSettlementDate());
		
	}
	
	@Test
	public void testCalculateSettlementDateForSGP() {
		Instruction instruction = new Instruction.Builder().withEntity("E1").withCurrency("SGP").withFxRate(new BigDecimal("0.22")).withInstructionDate(LocalDate.of(2018, 07, 5))
				.withsettlementDate(LocalDate.of(2018, 07, 8)).withPricePerUnit(new BigDecimal("150.5")).withTradeType(TradeType.BUY).withUnits(450).build();
		assertEquals(LocalDate.of(2018, 07, 8), instruction.getSettlementDate());
		ReportUtil.calculateSettlementDate(instruction);
		assertEquals(LocalDate.of(2018, 07, 9), instruction.getSettlementDate());
		
	}
	
	@Test
	public void testCalculateSettlementDateForSGPNoNeedToAdjustSettlementDate() {
		Instruction instruction = new Instruction.Builder().withEntity("E1").withCurrency("SGP").withFxRate(new BigDecimal("0.22")).withInstructionDate(LocalDate.of(2018, 07, 9))
				.withsettlementDate(LocalDate.of(2018, 07, 10)).withPricePerUnit(new BigDecimal("150.5")).withTradeType(TradeType.BUY).withUnits(450).build();
		assertEquals(LocalDate.of(2018, 07, 10), instruction.getSettlementDate());
		ReportUtil.calculateSettlementDate(instruction);
		assertEquals(LocalDate.of(2018, 07, 10), instruction.getSettlementDate());
		
	}
	
	@Test
	public void calculateDailySettledAmountForBuy() {
		Map<LocalDate, BigDecimal> dailySettledAmountMap = ReportUtil.calculateDailySettledAmountForTradeType(instructionList, TradeType.BUY);
		assertEquals(4, dailySettledAmountMap.size());
		assertEquals(new BigDecimal("37299.500"), dailySettledAmountMap.get(LocalDate.of(2018, 07, 10)));		
	}
	
	@Test
	public void calculateDailySettledAmountForSell() {
		Map<LocalDate, BigDecimal> dailySettledAmountMap = ReportUtil.calculateDailySettledAmountForTradeType(instructionList, TradeType.SELL);
		assertEquals(4, dailySettledAmountMap.size());
		assertEquals(new BigDecimal("14899.500"), dailySettledAmountMap.get(LocalDate.of(2018, 07, 16)));		
	}

	@Test
	public void calculateEntityRankingForBuy() {
		Map<String, BigDecimal> dailySettledAmountMap = ReportUtil.calculateEntityRankingForTradeType(instructionList, TradeType.BUY);
		assertEquals(4, dailySettledAmountMap.size());
		assertTrue(dailySettledAmountMap.keySet().iterator().next().equals("E1"));		
	}
}
