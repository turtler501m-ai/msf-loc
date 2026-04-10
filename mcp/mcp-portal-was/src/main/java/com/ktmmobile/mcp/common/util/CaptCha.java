package com.ktmmobile.mcp.common.util;

import static nl.captcha.Captcha.NAME;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.gimpy.DropShadowGimpyRenderer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.NumbersAnswerProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;

public class CaptCha {
    private static final long serialVersionUID = 1L;
    private static int width = 140; //이미지 가로크기
    private static int height = 50; //이미지 높이

    @Deprecated
    private static final Logger logger = LoggerFactory.getLogger(CaptCha.class);


    public static void getCaptCha(HttpServletRequest req, HttpServletResponse res) throws IOException {
     // 폰트 설정 부분 =============================================
        List<Font> fontList = new ArrayList<Font>();
        fontList.add(new Font("Pristina", Font.HANGING_BASELINE, 40));
        fontList.add(new Font("Pristina", Font.ITALIC, 30));
        fontList.add(new Font("Pristina", Font.HANGING_BASELINE, 40));

        List<Color> colorList = new ArrayList<Color>();
        colorList.add(Color.MAGENTA); //또는  colorList.add(Color.green), colorList.add(Color.blue) 등으로 설정
        //=========================================================

        Captcha captcha = new Captcha.Builder( width, height)
               //.addText() 또는 아래와 같이 정의
               //5자리 숫자와 폰트를 설정한다.
               .addText(new NumbersAnswerProducer(5), new DefaultWordRenderer(colorList, fontList))
//             .gimp(new DropShadowGimpyRenderer()).gimp()
               // BlockGimpyRenderer,FishEyeGimpyRenderer,RippleGimpyRenderer,ShearGimpyRenderer,StretchGimpyRenderer
//             .addNoise().addBorder()
//             .addBackground(new GradiatedBackgroundProducer())
               .addBackground()
               // FlatColorBackgroundProducer,SquigglesBackgroundProducer,TransparentBackgroundProducer
               .build();

         //JSP에서 Captcha 객체에 접근할 수 있도록 Session에 저장한다.

         req.getSession().setAttribute(NAME, captcha); //NAME = Captcha.NAME = 'simpleCaptcha'
         CaptchaServletUtil.writeImage(res, captcha.getImage());
    }



}
