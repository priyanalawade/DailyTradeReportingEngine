package com.jpmorgan.reports.tradingReport;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import com.jpmorgan.reports.tradingReport.exception.DailyTradeReportingEngineApplicationException;
import com.jpmorgan.reports.tradingReport.model.Instruction;
import com.jpmorgan.reports.tradingReport.model.TradeType;
import com.jpmorgan.reports.tradingReport.model.builder.InstructionDataProvider;

public class DailyTradeReportingEngineApplicationTest {

	@Test
	public void testInstructionSent() throws DailyTradeReportingEngineApplicationException {
		InstructionDataProvider instructionDataProvider = new InstructionDataProvider();
		List<Instruction> instructionList = instructionDataProvider.readAndParseFile("data/Instructions.csv");
		assertNotNull(instructionList);
		assertEquals(99, instructionList.size());
		assertNotNull(instructionList.get(0).getEntity());
		assertNotNull(instructionList.get(0).getTradeType());
		assertNotNull(instructionList.get(0).getPricePerUnit());
	}
	
	@Test(expected = DailyTradeReportingEngineApplicationException.class)
	public void testInstructionSentFileNotFound() throws DailyTradeReportingEngineApplicationException {
		InstructionDataProvider instructionDataProvider = new InstructionDataProvider();
		instructionDataProvider.readAndParseFile("src/main/resources/data/ABC.csv");
	}
	
	@Test
	public void testCalculateTradeAmount(){
		Instruction instruction = new Instruction.Builder().withEntity("E1").withCurrency("SAR").withFxRate(new BigDecimal("0.22")).withInstructionDate(LocalDate.of(2018, 07, 11))
				.withsettlementDate(LocalDate.of(2018, 07, 12)).withPricePerUnit(new BigDecimal("150.5")).withTradeType(TradeType.BUY).withUnits(450).build();
		assertEquals(new BigDecimal("14899.500"), instruction.getTradeAmount());
	}

}
