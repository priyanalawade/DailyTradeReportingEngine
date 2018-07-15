package com.jpmorgan.reports.tradingReport.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Instructions sent by various clients to JP Morgan to execute in the
 * international market.
 * 
 * @author Priya N
 *
 */
public class Instruction {

	private String entity;

	private TradeType tradeType;

	private BigDecimal fxRate;

	private String currency;

	private LocalDate instructionDate;

	private LocalDate settlementDate;

	private int units;

	private BigDecimal pricePerUnit;

	private BigDecimal tradeAmount;

	
	public String getEntity() {
		return entity;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public BigDecimal getFxRate() {
		return fxRate;
	}

	public String getCurrency() {
		return currency;
	}

	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}
	
	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}

	public int getUnits() {
		return units;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public static class Builder {
		private String entity;

		private TradeType tradeType;

		private BigDecimal fxRate;

		private String currency;

		private LocalDate instructionDate;

		private LocalDate settlementDate;

		private int units;

		private BigDecimal pricePerUnit;

		private BigDecimal tradeAmount;
		
		public Builder withEntity(String entity) {
			this.entity = entity;
			return this;
		}

		public Builder withTradeType(TradeType tradeType) {
			this.tradeType = tradeType;
			return this;
		}

		public Builder withFxRate(BigDecimal fxRate) {
			this.fxRate = fxRate;
			return this;
		}

		public Builder withCurrency(String currency) {
			this.currency = currency;
			return this;
		}

		public Builder withInstructionDate(LocalDate instructionDate) {
			this.instructionDate = instructionDate;
			return this;
		}

		public Builder withsettlementDate(LocalDate settlementDate) {
			this.settlementDate = settlementDate;
			return this;
		}

		public Builder withUnits(int units) {
			this.units = units;
			return this;
		}

		public Builder withPricePerUnit(BigDecimal pricePerUnit) {
			this.pricePerUnit = pricePerUnit;
			return this;
		}
		
		public Instruction build() {
            return new Instruction(this);
        }
	}
	
	private Instruction(Builder builder) {
       entity = builder.entity;
       tradeType = builder.tradeType;
       fxRate = builder.fxRate;
       currency = builder.currency;
       instructionDate = builder.instructionDate;
       settlementDate = builder.settlementDate;
       units = builder.units;
       pricePerUnit = builder.pricePerUnit;
       tradeAmount = builder.pricePerUnit.multiply(BigDecimal.valueOf(builder.units)).multiply(builder.fxRate);
    }
	
	@Override
	public String toString() {
		return "Instruction {entity:" + entity + " trade type:" + tradeType
				+ " Trade Amount:" + tradeAmount + "}";
	}
}