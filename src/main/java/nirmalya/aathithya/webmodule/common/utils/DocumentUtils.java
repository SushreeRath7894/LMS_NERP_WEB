package nirmalya.aathithya.webmodule.common.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentUtils {
	
	Logger logger = LoggerFactory.getLogger(DocumentUtils.class);
	
	@Autowired
	EnvironmentVaribles env;

	public byte[] decodeBase64Image(String base64Image) {
        if (base64Image.startsWith("data:image") || base64Image.startsWith("data:application")) {
            base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
        }
        return Base64.getDecoder().decode(base64Image);
    }
	
	public String getImageExtension(String base64Image) {
        if (base64Image.startsWith("data:image/")) {
            String[] parts = base64Image.split(";")[0].split("/");
            return parts.length > 1 ? parts[1] : "jpg";
        }
        if (base64Image.startsWith("data:application/")) {
        	String[] parts = base64Image.split(";")[0].split("/");
        	return parts.length > 1 ? parts[1] : "pdf";
        }
        return "jpg";
    }
	
	public String uploadImageToFolder(byte[] imageBytes, String ext, String filePath) {
		logger.info("Method : uploadImageToFolder starts");
		
		String imageName = null;
		
		try {
			
			if(imageBytes!=null) {
				long nowTime = new Date().getTime();
				if(ext.contentEquals("jpeg")) {
					imageName = nowTime+".jpg";
				} else {
					imageName = nowTime+"."+ext;
				}
			}
			System.out.println(imageName);
			Path path = Paths.get(filePath + imageName);
			if(imageBytes !=null) {
				System.out.println(imageBytes);
				Files.write(path, imageBytes);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : uploadImageToFolder ends");
		return imageName;
	}
	
	public String uploadImageToFolder2(byte[] imageBytes, String ext, String filePath, String fileName) {
		logger.info("Method : uploadImageToFolder starts");
		
		String imageName = null;
		
		try {
			
			Path path = Paths.get(filePath + fileName);
			if(imageBytes !=null) {
				Files.write(path, imageBytes);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : uploadImageToFolder ends");
		return imageName;
	}
}
