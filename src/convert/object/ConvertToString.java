package convert.object;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.jersey.core.header.FormDataContentDisposition;

public class ConvertToString {
	/*
	 * Method get two parameters, one for file details and one for the file byte
	 * array. Method
	 */
	public String saveToDisc(InputStream uploadedInputStream, FormDataContentDisposition fileDetail) {
		String uploadFileLocation = "C://image//" + fileDetail.getFileName();
		try {
			OutputStream out = new FileOutputStream(new File(uploadFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return uploadFileLocation;

	}

	public String imageToAngular(String image) {
		String base64 = null;
		byte[] data = null;
		Path path = Paths.get(image);
		try {
			data = Files.readAllBytes(path);
			base64 = javax.xml.bind.DatatypeConverter.printBase64Binary(data);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return base64;

	}
}
