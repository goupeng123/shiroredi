package com.my.shiroredis.sys.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
/**
 * 
 * @ClassName: Yzm
 * @Description: 生成验证码
 * @author: goupeng
 * @date: 2016-2-1 上午10:59:25
 */
public class Yzm {
	   /**
	    * 
	    * @Title: getYzm
	    * @Description: 生成验证码
	    * @return: void
	    */
	   @RequestMapping(value="getYzm")
       public void getYzm(HttpServletResponse response){
    	   BufferedImage image = new BufferedImage(120, 30, BufferedImage.TYPE_INT_RGB);
    	   Graphics grap = image.getGraphics();
    	   grap.setColor(Color.GRAY);
    	   grap.fillRect(0, 0, 120, 30);
    	   grap.setColor(Color.ORANGE);
    	   grap.drawRect(0, 0, 120-1, 30-1);
    	   grap.setFont(new Font("宋体", Font.BOLD, 20));
    	   Graphics2D graphics2d = (Graphics2D)grap;
    	   String str = "ABC0DE1FG2HI3JK4LM5NO6PQ7RST8UVW9XYZ";
    	   StringBuffer sb = new StringBuffer();
    	   Random random = new Random();
    	   int x = 5;
    	   for(int i=0;i<4;i++){
    		   int index = random.nextInt(36);
    		   String v = String.valueOf(str.charAt(index));
    		   sb.append(v);
    		   double thera = random.nextInt(30)*Math.PI/180;
    		   graphics2d.rotate(thera, x, 18);
    		   graphics2d.drawString(v, x, 18);
    		   graphics2d.rotate(-thera, x, 18);
    		   x = x + 30;
    	   }
    	   SecurityUtils.getSubject().getSession().setAttribute("yzm", sb.toString());
    	   grap.setColor(Color.GREEN);
    	   for(int i=0;i<4;i++){
    		   int x1 = random.nextInt(120);
    		   int x2 = random.nextInt(120);
    		   int y1 = random.nextInt(30);
    		   int y2 = random.nextInt(30);
    		   grap.drawLine(x1, y1, x2, y2);
    	   }
    	   grap.dispose();
    	   try {
			ImageIO.write(image, "jpg", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
}
