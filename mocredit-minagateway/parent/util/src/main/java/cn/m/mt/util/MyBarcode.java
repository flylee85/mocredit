package cn.m.mt.util;
import java.io.Serializable;

public class MyBarcode implements Serializable,Comparable<MyBarcode>{
		private String sort;
		private String numbercode;
		private String barcode;
		public String getSort() {
			return sort;
		}
		public void setSort(String sort) {
			this.sort = sort;
		}
		public String getNumbercode() {
			return numbercode;
		}
		public void setNumbercode(String numbercode) {
			this.numbercode = numbercode;
		}
		public String getBarcode() {
			return barcode;
		}
		public void setBarcode(String barcode) {
			this.barcode = barcode;
		}
		@Override
		public int compareTo(MyBarcode o) {
			return this.getSort().compareTo(o.getSort());
		}
		
	}