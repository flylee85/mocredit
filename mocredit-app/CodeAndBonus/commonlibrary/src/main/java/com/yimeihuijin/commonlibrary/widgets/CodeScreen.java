package com.yimeihuijin.commonlibrary.widgets;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

public class CodeScreen extends TextView implements  Cloneable{

	private StringBuffer code = new StringBuffer("");
	private StringBuffer drawCode = new StringBuffer("");

	private String hint;

	private int codeColor;

	private int offset = 10;

	private float codeSize;

	private int codeGroupNum = 4;

	private char codeSplit = ' ';

	private final Paint paint = new Paint();

	private Rect textSizeRect = new Rect();

	public CodeScreen(Context context){
		this(context,null);
	}

	public CodeScreen(Context context, AttributeSet attrs) {
		super(context, attrs);
		codeSize = getTextSize();
		hint = getText().toString();
		// TODO Auto-generated constructor stub
	}

	public void setCodeSize(int size){
		codeSize = size;
		resetView();
	}

	public void inputCode(char c) {
		if (code.length() >= 20) {
			return;
		}
		synchronized (code) {
			if (code.length() % codeGroupNum == 0 && code.length() > 0) {
				drawCode.append(codeSplit);
			}
			code.append(c);
			drawCode.append(c);
		}
		resetView();
	}
	
	public void inputString(String s){
		for(char c:s.toCharArray()){
			inputCode(c);
		}
	}

	public void deleteBackCode() {
		if (code.length() <= 1) {
			clearScreen();
			return;
		}
		synchronized (code) {
			code.deleteCharAt(code.length() - 1);
			drawCode.deleteCharAt(drawCode.length() - 1);
			if (codeSplit == drawCode.charAt(drawCode.length() - 1)) {
				drawCode.deleteCharAt(drawCode.length() - 1);
			}
		}
		resetView();
	}

	public void clearScreen() {
		synchronized (code) {
			code = new StringBuffer("");
			drawCode = new StringBuffer("");
		}
		resetView();
	}

	private void resetView() {
		if (drawCode.length() < 1) {
			this.setTextSize(TypedValue.COMPLEX_UNIT_PX, codeSize);
			this.setText(hint);
			return;
		}
		int w = getMeasuredWidth();
		for (float i = codeSize * 2; i > 0; i -= 0.5f) {
			paint.setTextSize(i);
			float[] widths = new float[30];
			paint.getTextWidths(drawCode.toString(), widths);
			paint.getTextBounds(drawCode.toString(), 0, drawCode.length(),
					textSizeRect);
			int tw = textSizeRect.left + textSizeRect.right;
			if (tw < w) {
				this.setTextSize(TypedValue.COMPLEX_UNIT_PX, i - 2);
				this.setText(drawCode.toString());
				return;
			}
		}
	}

	public String getCode() {
		return code.toString();
	}


	public void setHints(String hint){
		this.hint = hint;
		resetView();
	}

	@Override
	public CodeScreen clone(){
		CodeScreen code = null;
		try {
			code = (CodeScreen) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return code;
	}
}
