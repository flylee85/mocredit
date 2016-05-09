package cn.m.mt.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
public class Base64File {
	@SuppressWarnings("restriction")
	public static String encoderFile(File file) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		try {
			StringBuilder pictureBuffer = new StringBuilder();
			InputStream input = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] temp = new byte[1024];
			for (int len = input.read(temp); len != -1; len = input.read(temp)) {
				out.write(temp, 0, len);
				pictureBuffer.append(encoder.encode(out.toByteArray()));
				out.reset();
			}
			return pictureBuffer.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	public static String encoderFileName(String filename) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		try {
			StringBuilder pictureBuffer = new StringBuilder();
			InputStream input = new FileInputStream(new File(filename));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] temp = new byte[1024];
			for (int len = input.read(temp); len != -1; len = input.read(temp)) {
				out.write(temp, 0, len);
				pictureBuffer.append(encoder.encode(out.toByteArray()));
				out.reset();
			}
			return pictureBuffer.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	public static boolean GenerateImage(String imgStr)
    {//对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try 
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            String imgFilePath = "d:\\222.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);    
            out.write(b);
            out.flush();
            out.close();
            return true;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }
	public static String GetImageBase64Str(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;
		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		String str = encoder.encode(data);// 返回Base64编码过的字节数组字符串
		return str;
	}
	public static void main (String args[]){
//		GenerateImage("/9j/4AAQSkZJRgABAgAAZABkAAD/7AARRHVja3kAAQAEAAAAPAAA/+4AJkFkb2JlAGTAAAAAAQMAFQQDBgoNAAABiQAAAaoAAAHaAAAB+//bAIQABgQEBAUEBgUFBgkGBQYJCwgGBggLDAoKCwoKDBAMDAwMDAwQDA4PEA8ODBMTFBQTExwbGxscHx8fHx8fHx8fHwEHBwcNDA0YEBAYGhURFRofHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8f/8IAEQgABQAFAwERAAIRAQMRAf/EAG4AAQAAAAAAAAAAAAAAAAAAAAcBAQAAAAAAAAAAAAAAAAAAAAEQAQAAAAAAAAAAAAAAAAAAAAARAQAAAAAAAAAAAAAAAAAAAAASAQAAAAAAAAAAAAAAAAAAAAATAQAAAAAAAAAAAAAAAAAAAAD/2gAMAwEAAhEDEQAAAUgP/9oACAEBAAEFAn//2gAIAQIAAQUCf//aAAgBAwABBQJ//9oACAECAgY/An//2gAIAQMCBj8Cf//aAAgBAQEGPwJ//9oACAEBAwE/IX//2gAIAQIDAT8hf//aAAgBAwMBPyF//9oADAMBAAIRAxEAABB//9oACAEBAwE/EH//2gAIAQIDAT8Qf//aAAgBAwMBPxB//9k");
		System.out.println(GetImageBase64Str("d:\\jpg.jpg"));
	}
}