package cn.edu.xmu.software.binarykang.util;

/**
 * 存储文件信息，也就是一个模块的 <br>
 * <tr>
 * savePath
 * </tr>
 * <tr>
 * saveName
 * </tr>
 * <tr>
 * readPath
 * </tr>
 * <br>
 * 3大文件属性的设置 该类是JavaBean形式，通过get，set设置值
 * 
 * @author xmujx
 * @since 2014-07-19
 */
public final class FileInfo
{
	public static FileInfo INSTANCE = new FileInfo();
	public String basePath;
	private String saveName;
	private String readPath;
	private String savePath;

	private FileInfo()
	{

	}

	public String getSaveName()
	{
		return saveName;
	}

	public String getReadPath()
	{
		return readPath;
	}

	public void setReadPath(String readPath)
	{
		this.readPath = readPath;
	}

	public void setSaveName(String saveName)
	{
		this.saveName = saveName;
	}

	public String getSavePath()
	{
		return savePath;
	}

	public void setSavePath(String savePath)
	{
		this.savePath = savePath;
	}

	public String getBasePath()
	{
		return basePath;
	}

}
