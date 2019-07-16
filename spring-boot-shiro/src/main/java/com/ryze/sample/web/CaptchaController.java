package com.ryze.sample.web;

import com.ryze.sample.util.CaptchaUtil;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 验证码
 * Created by xueLai on 2019/2/23.
 */
public class CaptchaController {
    public static final String KEY_CAPTCHA = "KEY_CAPTCHA";

    @RequestMapping("/Captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        // 设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpeg");
        // 不缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        try {

            HttpSession session = request.getSession();

            CaptchaUtil tool = new CaptchaUtil();
            StringBuffer code = new StringBuffer();
            BufferedImage image = tool.genRandomCodeImage(code);
            session.removeAttribute(KEY_CAPTCHA);
            session.setAttribute(KEY_CAPTCHA, code.toString());

            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", response.getOutputStream());
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", new FileOutputStream(new File(
                    "D:\\Idea_work\\SpringBootSample\\random-code.jpg")));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
