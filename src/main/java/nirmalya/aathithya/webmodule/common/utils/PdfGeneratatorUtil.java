/**
 * @NirmalyaLabs
 */
package nirmalya.aathithya.webmodule.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.FileInputStream;
import java.io.InputStream;

import com.itextpdf.text.pdf.BaseFont;
/**
 * @author NirmalyaLabs
 *
 */
@Component
public class PdfGeneratatorUtil {
	@Autowired
	private TemplateEngine templateEngine;
	Logger logger = LoggerFactory.getLogger(PdfGeneratatorUtil.class);
	@SuppressWarnings("rawtypes")
	public File createPdf(String templateName,Map map)throws Exception{
		logger.info("Method : PdfGeneratatorUtil starts");
		Assert.notNull(templateName,"The templateName can not be null");
		Context ctx = new Context();
		if(map != null){
			Iterator iteratorMap = map.entrySet().iterator();
			while(iteratorMap.hasNext()){
				Map.Entry pair = (Map.Entry) iteratorMap.next();
				ctx.setVariable(pair.getKey().toString(), pair.getValue());
			}
		}
		String processedHTML = templateEngine.process(templateName, ctx);
		FileOutputStream os = null;
		String fileName = UUID.randomUUID().toString();
		final File outputFile = new File(fileName);
		try{
			os = new FileOutputStream(outputFile);
			ITextRenderer renderer= new ITextRenderer();
			renderer.getFontResolver().addFont("static/assets/fonts/themify.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			renderer.getFontResolver().addFont("static/assets/fonts/vivian/Vivian.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			renderer.getFontResolver().addFont("static/assets/css/lib/fontAwesome6/webfonts/fa-solid-900.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			renderer.setDocumentFromString(processedHTML);
			renderer.layout();
			renderer.createPDF(os,false);
			renderer.finishPDF();
		}finally{
			if(os !=null){
				try{
					os.close();
				}catch(IOException e){}
			}
		}
		logger.info("Method : PdfGeneratatorUtil starts");
		return outputFile;
	}
	
	/* Added by Pankaj to convert a PDF file into a byte array for further processing */
	public byte[] createPdfAsBytes(String templateName, Map<String, Object> data) throws IOException {
	    File pdfFile;
	    try {
	        pdfFile = createPdf(templateName, data);
	    } catch (Exception e) {
	        throw new IOException("Failed to generate PDF file.", e); 
	    }

	    if (pdfFile == null || !pdfFile.exists()) {
	        throw new IOException("Failed to generate PDF file.");
	    }

	    try (InputStream in = new FileInputStream(pdfFile)) {
	        return IOUtils.toByteArray(in); 
	    }
	}


	
	
}
