package nirmalya.aathithya.webmodule.recruitment.controller;

import java.io.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class JdPdfGenerator {
	public static void generatePdfFromHtml(String html, String outputPath) throws Exception {
		try (OutputStream outputStream = new FileOutputStream(outputPath)) {
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(html);
			renderer.layout();
			renderer.createPDF(outputStream);
		}
	}

	public static void main(String[] args) {
		try {
			// Read your HTML content (you can get this from your JavaScript)
			String htmlContent = "..."; // Your HTML content here

			// Generate PDF
			generatePdfFromHtml(htmlContent, "job_description.pdf");
			System.out.println("PDF generated successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
