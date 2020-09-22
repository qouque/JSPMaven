package kr.or.ddit.vo;

import java.io.File;
import java.io.Serializable;

import kr.or.ddit.servlet04.ServerExplorerServlet.CommandType;
/**
 * 자바빈 예제(VO)
 * @author PC-06
 *
 */
public class FileCommandVO implements Serializable {
	
	private CommandType command;
	private File srcFile;
	private File destFolder;
	
	@Override
	public String toString() {
		return "FileCommandVO [command=" + command + ", srcFile=" + srcFile + ", destFolder=" + destFolder + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((command == null) ? 0 : command.hashCode());
		result = prime * result + ((destFolder == null) ? 0 : destFolder.hashCode());
		result = prime * result + ((srcFile == null) ? 0 : srcFile.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileCommandVO other = (FileCommandVO) obj;
		if (command != other.command)
			return false;
		if (destFolder == null) {
			if (other.destFolder != null)
				return false;
		} else if (!destFolder.equals(other.destFolder))
			return false;
		if (srcFile == null) {
			if (other.srcFile != null)
				return false;
		} else if (!srcFile.equals(other.srcFile))
			return false;
		return true;
	}
	public CommandType getCommand() {
		return command;
	} 
	public void setCommand(CommandType command) {
		this.command = command;
	}
	
	public File getSrcFile() {
		return srcFile;
	}
	public void setSrcFile(File srcFile) {
		this.srcFile = srcFile;
	}
	
	public File getDestFolder() {
		return destFolder;
	}
	
	public void setDestFolder(File destFolder) {
		this.destFolder = destFolder;
	}
	
	
}





















