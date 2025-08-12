package nirmalya.aathithya.webmodule.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

public class DownloadDocumentUrl {
	public static String downloadDocumentUrl(URL toDownload) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			URLConnection urlConn = toDownload.openConnection();
			urlConn.addRequestProperty("User-Agent", "Chrome");
			// String contentType = urlConn.getContentType();
			// System.out.println("contentType:" + contentType);
			byte[] chunk = new byte[4096];
			int bytesRead;
			InputStream stream = urlConn.getInputStream();
			while ((bytesRead = stream.read(chunk)) > 0) {
				outputStream.write(chunk, 0, bytesRead);
			}
			byte[] encoded = Base64.getEncoder().encode(outputStream.toByteArray());
			return new String(encoded);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
