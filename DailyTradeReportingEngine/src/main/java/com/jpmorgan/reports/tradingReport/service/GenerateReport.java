package com.jpmorgan.reports.tradingReport.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.jpmorgan.reports.tradingReport.model.Instruction;
import com.jpmorgan.reports.tradingReport.model.TradeType;
import com.jpmorgan.reports.tradingReport.util.ReportUtil;

public class GenerateReport {
	private int rankCounter =1;

	public void generateReport(List<Instruction> instructionList){
		
		ReportUtil.calculateSettlementDate(instructionList);
		
	    Map<LocalDate, BigDecimal> calculateDailySettledOutgoigAmount = ReportUtil.calculateDailySettledAmountForTradeType(instructionList, TradeType.BUY);
	    
	    Map<LocalDate, BigDecimal> calculateDailySettledIncomingAmount = ReportUtil.calculateDailySettledAmountForTradeType(instructionList, TradeType.SELL);
	    
	    Map<String, BigDecimal> calculateEntityRankingForIncomingAmount = ReportUtil.calculateEntityRankingForTradeType(instructionList, TradeType.SELL);
	    
	    Map<String, BigDecimal> calculateEntityRankingForOutgoingAmount = ReportUtil.calculateEntityRankingForTradeType(instructionList, TradeType.BUY);
	    
	    PrintReport(calculateDailySettledOutgoigAmount,
				calculateDailySettledIncomingAmount,
				calculateEntityRankingForIncomingAmount,
				calculateEntityRankingForOutgoingAmount);
		   
	}

	private void PrintReport(
			Map<LocalDate, BigDecimal> calculateDailySettledOutgoigAmount,
			Map<LocalDate, BigDecimal> calculateDailySettledIncomingAmount,
			Map<String, BigDecimal> calculateEntityRankingForIncomingAmount,
			Map<String, BigDecimal> calculateEntityRankingForOutgoingAmount) {
		System.out.println("*****Daily Trading Report*****");	    
	    System.out.println("-----Amount in USD settled incoming everyday-----");	    
	    System.out.println("Settlement Date   |        Amount");	    
	    calculateDailySettledIncomingAmount.keySet().forEach(i -> System.out.println(i+"        |        "+ calculateDailySettledIncomingAmount.get(i)));
	    System.out.println("-----Amount in USD settled outgoing everyday-----");	    
	    System.out.println("Settlement Date   |        Amount");
	    calculateDailySettledOutgoigAmount.keySet().forEach(i -> System.out.println(i+"        |        "+ calculateDailySettledOutgoigAmount.get(i)));
	    System.out.println("-----Ranking of entities based on incoming amount-----");
	    calculateEntityRankingForIncomingAmount.keySet().forEach(i -> System.out.println(i+"("+ calculateEntityRankingForIncomingAmount.get(i)+") -> Rank "+rankCounter ++));
	    rankCounter = 1;
	    System.out.println("-----Ranking of entities based on outgoing amount-----");
	    calculateEntityRankingForOutgoingAmount.keySet().forEach(i -> System.out.println(i+"("+ calculateEntityRankingForOutgoingAmount.get(i)+") -> Rank "+rankCounter ++));
	}

}
