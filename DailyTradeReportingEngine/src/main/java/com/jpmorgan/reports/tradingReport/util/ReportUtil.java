package com.jpmorgan.reports.tradingReport.util;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.jpmorgan.reports.tradingReport.model.Instruction;
import com.jpmorgan.reports.tradingReport.model.TradeType;

public class ReportUtil {
	
	public static void calculateSettlementDate(List<Instruction> instructionList){
		instructionList.stream().map(ReportUtil::calculateSettlementDate).collect(Collectors.toList());
	}
	
	public static Instruction calculateSettlementDate(Instruction instruction){
		String currency = instruction.getCurrency();
		LocalDate settlementDate = instruction.getSettlementDate();
		LocalDate newSettlmentDate = settlementDate;
		if ("AED".equals(currency) || "SAR".equals(currency)) {
			if (settlementDate.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
				newSettlmentDate = settlementDate.plus(Period.ofDays(2));
			} else if (settlementDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
				newSettlmentDate = settlementDate.plus(Period.ofDays(1));
			}
		} else {
			if (settlementDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
				newSettlmentDate = settlementDate.plus(Period.ofDays(2));
			} else if (settlementDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				newSettlmentDate = settlementDate.plus(Period.ofDays(1));
			}
		}
		instruction.setSettlementDate(newSettlmentDate);
		return instruction;
	}
	
	public static Map<LocalDate, BigDecimal> calculateDailySettledAmountForTradeType(List<Instruction> instructionList, TradeType tradeType) {
		Map<LocalDate, BigDecimal> dailySettledAmountMap = instructionList.stream()
				.filter(i -> tradeType.equals(i.getTradeType()))
				.collect(Collectors.groupingBy(Instruction::getSettlementDate,
								Collectors.mapping(Instruction::getTradeAmount,
										Collectors.reducing(BigDecimal.ZERO,
												BigDecimal::add))))
				.entrySet()
				.stream()
				.sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1,
								e2) -> e1, LinkedHashMap::new));
		;

		return dailySettledAmountMap;
	}
	
	public static Map<String, BigDecimal> calculateEntityRankingForTradeType(List<Instruction> instructionList, TradeType tradeType) {
		Map<String, BigDecimal> collect = instructionList.stream().filter(i -> tradeType.equals(i.getTradeType())).collect(Collectors.groupingBy(Instruction:: getEntity, Collectors.mapping(Instruction::getTradeAmount,
										Collectors.reducing(BigDecimal.ZERO,
												BigDecimal::add)))).entrySet()
												.stream()
												.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
												.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1,
																e2) -> e1, LinkedHashMap::new));
		
		return collect;
		
	}
	

}
