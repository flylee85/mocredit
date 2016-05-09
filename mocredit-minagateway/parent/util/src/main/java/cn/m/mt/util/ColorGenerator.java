package cn.m.mt.util;

import java.awt.Color;
import java.util.Random;

public   class   ColorGenerator{ 

    private   Random   random=new   Random(); 
    private   int         adjustNumber=140;       //对比度调整数值 
    private   Color     lastColor=new   Color(random.nextInt(256),random.nextInt(256),random.nextInt(256)); 
    private   Color     newColor; 

    public   Color   getColor(){ 
int   red=(255-lastColor.getRed()+random.nextInt(adjustNumber))%255; 
int   green=(255-lastColor.getGreen()+random.nextInt(adjustNumber))%255; 
int   blue=(255-lastColor.getBlue()+random.nextInt(adjustNumber))%255; 

newColor=new   Color(red,green,blue); 
lastColor=newColor; 
return   newColor; 
    } 

    public   static   void   main(String[]   args) 
{ 
//    ColorGenerator   cg=new   ColorGenerator(); 
//	Color cg = new Color(1);
    for(int i=1;i<21;i++){
    	int rgb = Color.HSBtoRGB(0.05f*i,0.9f,0.9f); 
    	Color cg = new Color(rgb);
    	//System.out.println("#"+Integer.toHexString(cg.getRed())+Integer.toHexString(cg.getGreen())+Integer.toHexString(cg.getBlue())); 
    }
} 
}