package com.ktis.msp.batch.manager.common.excel;

public class SfExcelAttribute {
	int min, max, width, customWidth;
	
	public SfExcelAttribute(int min, int max, int width, int customWidth) {
		super();
		this.min = min;
		this.max = max;
		this.width = width;
		this.customWidth = customWidth;
	}

	/**
	 * @return the min
	 */
	public int getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(int min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the customWidth
	 */
	public int getCustomWidth() {
		return customWidth;
	}

	/**
	 * @param customWidth the customWidth to set
	 */
	public void setCustomWidth(int customWidth) {
		this.customWidth = customWidth;
	}
	
}
