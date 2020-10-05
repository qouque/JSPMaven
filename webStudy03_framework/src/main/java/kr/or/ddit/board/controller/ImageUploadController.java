package kr.or.ddit.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.filter.wrapper.PartWrapper;
import kr.or.ddit.listener.SampleListener;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;

@CommandHandler
public class ImageUploadController {
	
	String saveFolderUrl = "/boardImages";
	File saveFolder;
	{
		String realPath = SampleListener.currentContext.getRealPath(saveFolderUrl);
		saveFolder = new File(realPath);
		if(!saveFolder.exists()) {
			saveFolder.mkdirs();
		}
	}
	
	
	@URIMapping(value = "/board/imageUpload.do", method=HttpMethod.POST)
	public String imageUpload(
			HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Map<String, Object> resultMap = new HashMap<>();
		if(req instanceof FileUploadRequestWrapper) {
			PartWrapper imageFile = ((FileUploadRequestWrapper) req).getPartWrapper("upload");
			
			if(imageFile != null && imageFile.getContentType().startsWith("image/")) {
				resultMap.put("fileName",imageFile.getOrginalFilename());
				resultMap.put("uploaded", 1);
				
				imageFile.saveToRealPath(saveFolder);
				String url = req.getContextPath()+saveFolderUrl+"/"+imageFile.getSaveName();
				resultMap.put("url", url);
			}
		}
		resp.setContentType("application/json;charset=UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		try(
			PrintWriter out = resp.getWriter();
			){
			mapper.writeValue(out, resultMap);
		}
		
		return null;
	}
	
	
}
