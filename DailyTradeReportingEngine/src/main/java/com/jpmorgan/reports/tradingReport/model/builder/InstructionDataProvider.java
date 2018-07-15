package com.jpmorgan.reports.tradingReport.model.builder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jpmorgan.reports.tradingReport.exception.DailyTradeReportingEngineApplicationException;
import com.jpmorgan.reports.tradingReport.model.Instruction;
import com.jpmorgan.reports.tradingReport.model.TradeType;

public class InstructionDataProvider {

	private static final String DATE_FORMATTER_PATTERN = "dd/MM/yyyy";
	private final List<Instruction> instructionList = new ArrayList<Instruction>();
	
	public List<Instruction> readAndParseFile(String fileName) throws DailyTradeReportingEngineApplicationException {
		Scanner scanner = null;
		try {
			final BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName)));
			scanner = new Scanner(reader);
			scanner.useDelimiter(",");
			scanner.nextLine();
			while (scanner != null && scanner.hasNextLine()) {
				String s[] = scanner.nextLine().split(",");
				instructionList.add(createInstruction(s));
			}
		} catch(Exception e){
			throw new DailyTradeReportingEngineApplicationException(e.getMessage());
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		return instructionList;
	}

	private Instruction createInstruction(String[] s) {
		Instruction instruction = new Instruction.Builder()
				.withEntity(s[0])
				.withTradeType(getTradeType(s[1]))
				.withFxRate(new BigDecimal(s[2]))
				.withCurrency(s[3])
				.withInstructionDate(LocalDate.parse(s[4], DateTimeFormatter.ofPattern(DATE_FORMATTER_PATTERN)))
				.withsettlementDate(LocalDate.parse(s[5], DateTimeFormatter.ofPattern(DATE_FORMATTER_PATTERN)))
				.withUnits(Integer.parseInt(s[6]))
				.withPricePerUnit(new BigDecimal(s[7])).build();
		return instruction;
	}

	private TradeType getTradeType(String data) {
		switch (data) {
		case "Buy":
			return TradeType.BUY;
		case "Sell":
			return TradeType.SELL;
		default:
			return TradeType.UNKNOWN;
		}
	}
}
