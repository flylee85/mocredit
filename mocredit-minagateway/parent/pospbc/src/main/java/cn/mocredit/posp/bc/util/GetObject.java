package cn.mocredit.posp.bc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class GetObject {
	private static Logger logger = Logger.getLogger(GetObject.class);
	
	public static void main(String[] args) {
		
	}
	
	public static void a(Object o) {
		System.out.println(o.toString());
	}
}
