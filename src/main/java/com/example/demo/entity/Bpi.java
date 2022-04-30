package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bpi")
public class Bpi {
	
		@Id
		@Column(name="code")
		private String code;
		
		@Column(name="symbol")
		private String symbol;

		@Column(name="rate")
		private String rate;
		
		@Column(name="description")
		private String description;
		
		@Column(name="rate_float")
		private String rate_float;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getSymbol() {
			return symbol;
		}

		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}

		public String getRate() {
			return rate;
		}

		public void setRate(String rate) {
			this.rate = rate;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getRate_float() {
			return rate_float;
		}

		public void setRate_float(String rate_float) {
			this.rate_float = rate_float;
		}

		
		
}
