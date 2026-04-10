package com.ktmmobile.mcp.common.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;

import org.krysalis.barcode4j.BarcodeClassResolver;
import org.krysalis.barcode4j.DefaultBarcodeClassResolver;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BarcodeSvcImpl implements BarcodeSvc {

    private  static final Logger logger = LoggerFactory.getLogger(BarcodeSvcImpl.class);

    /** 파일업로드 경로*/
    @Value("${common.fileupload.base}")
    private String fileuploadBase;

    public boolean createBarcode(String barcodeData, String barcodeType,int dpi,String fileName,String fileFormat) {
	    /* 바코드 타입
	    * "codabar", "code39", "postnet", "intl2of5", "ean-128"
	    * "royal-mail-cbc", "ean-13", "itf-14", "datamatrix", "code128"
	    * "pdf417", "upc-a", "upc-e", "usps4cb", "ean-8", "ean-13" */
    	/* 이미지의 dpi */
	    /* 이미지 파일 포맷   * SVG, EPS, TIFF, JPEG, PNG, GIF, BMP */



	    /* 출력될 파일 */
    	String fPer = File.separator;
    	String realDir = fileuploadBase + fPer + "barcode";
    	String outputFile = fileuploadBase+ fPer + "barcode" + fPer + fileName + "." + fileFormat;
    	
    	//폴더 없다면 생성
        File targetDir = new File(realDir);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
	    /* anti-aliasing */
	    boolean isAntiAliasing = false;


	    /* 이미지 생성 */
	    boolean result = createBarcodeProc(barcodeType, barcodeData, fileFormat, isAntiAliasing, dpi, outputFile);

	    return result;
    }
    /**
    * 바코드 생성
    * @param barcodeType
    * @param barcodeData
    * @param dpi
    */
    public boolean createBarcodeProc(String barcodeType, String barcodeData, String fileFormat, boolean isAntiAliasing, int dpi, String outputFile){
    	try {
    		boolean returnValue = false;
		    AbstractBarcodeBean bean = null;
		    BarcodeClassResolver resolver = new DefaultBarcodeClassResolver();
		    Class clazz = resolver.resolveBean(barcodeType);
		    bean = (AbstractBarcodeBean)clazz.newInstance();
		    bean.doQuietZone(true);
		    //Open output file
		    OutputStream out = new FileOutputStream(new File(outputFile));
		    try {
			    //Set up the canvas provider for monochrome JPEG output
			    String mimeType = MimeTypes.expandFormat(fileFormat);
			    int imageType   = BufferedImage.TYPE_BYTE_BINARY;
			    BitmapCanvasProvider canvas = new BitmapCanvasProvider(
			    out, mimeType, dpi, imageType, isAntiAliasing, 0);
			    //Generate the barcode
			    bean.generateBarcode(canvas, barcodeData);
			    //Signal end of generation
			    canvas.finish();
			    returnValue = true;
		    } catch(IOException e) {
				returnValue = false;
			} catch(Exception e) {
		    	returnValue = false;
		    } finally {
		    	out.close();
		    }
	    	return returnValue;
	    } catch (ClassNotFoundException e) {
			return false;
		} catch (Exception e) {
	    	return false;
	    }
    }
}
