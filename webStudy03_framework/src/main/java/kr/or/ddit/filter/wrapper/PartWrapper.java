package kr.or.ddit.filter.wrapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.UUID;

import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;

public class PartWrapper implements Part {
	
	public PartWrapper(Part part) {
		this.part = part;
		
		String headerValue = part.getHeader("Content-Disposition");
		int index = headerValue.indexOf("filename");
		index = headerValue.indexOf("=", index);
		headerValue = headerValue.substring(index+1);
		this.orginalFilename = headerValue.replace("\"","");
		
		saveName = UUID.randomUUID().toString();
	}
	
	private Part part;
	
	private String orginalFilename;
	
	private String saveName;
	
	public String getOrginalFilename() {
		return orginalFilename;
	}
	
	public String getSaveName() {
		return saveName;
	}
	
	
	public void saveToRealPath(File folder) throws IOException {
		File saveFile = new File(folder, saveName);
		try(
			InputStream is = getInputStream();	
		){
			FileUtils.copyInputStreamToFile(is, saveFile);
		}
	}



	@Override
	public InputStream getInputStream() throws IOException {
		return part.getInputStream();
	}



	@Override
	public String getContentType() {
		return part.getContentType();
	}



	@Override
	public String getName() {
		return part.getName();
	}



	@Override
	public long getSize() {
		return part.getSize();
	}



	@Override
	public void write(String fileName) throws IOException {
		
	}



	@Override
	public void delete() throws IOException {
		part.delete();
		
	}



	@Override
	public String getHeader(String name) {
		return part.getHeader(name);
	}



	@Override
	public Collection<String> getHeaders(String name) {
		return part.getHeaders(name);
	}



	@Override
	public Collection<String> getHeaderNames() {
		return part.getHeaderNames();
	}
}
