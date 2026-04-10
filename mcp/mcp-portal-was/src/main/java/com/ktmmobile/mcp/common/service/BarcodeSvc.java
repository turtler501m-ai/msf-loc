package com.ktmmobile.mcp.common.service;

public interface BarcodeSvc {

	public boolean createBarcode(String barcodeData, String barcodeType,int dpi,String fileName,String fileFormat);
	
	public boolean createBarcodeProc(String barcodeType, String barcodeData, String fileFormat, boolean isAntiAliasing, int dpi, String outputFile);


}