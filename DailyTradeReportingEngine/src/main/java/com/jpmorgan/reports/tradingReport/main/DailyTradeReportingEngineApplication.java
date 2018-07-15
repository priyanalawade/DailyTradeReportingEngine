package com.jpmorgan.reports.tradingReport.main;

import java.util.List;

import com.jpmorgan.reports.tradingReport.exception.DailyTradeReportingEngineApplicationException;
import com.jpmorgan.reports.tradingReport.model.Instruction;
import com.jpmorgan.reports.tradingReport.model.builder.InstructionDataProvider;
import com.jpmorgan.reports.tradingReport.service.GenerateReport;

/**
 * Daily Trading Report Application
 *
 */
public class DailyTradeReportingEngineApplication {
	public static void main(String[] args) throws DailyTradeReportingEngineApplicationException {
		InstructionDataProvider instructionDataProvider = new InstructionDataProvider();
		List<Instruction> instructionList = instructionDataProvider.readAndParseFile("data/Instructions.csv");
		GenerateReport generateReport = new GenerateReport();
		generateReport.generateReport(instructionList);
	}
}
