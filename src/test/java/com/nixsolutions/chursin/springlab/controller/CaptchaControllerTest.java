package com.nixsolutions.chursin.springlab.controller;

import com.google.code.kaptcha.Producer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ImageIO.class)
public class CaptchaControllerTest {

    @Mock
    private Producer captchaProducer;
    @InjectMocks
    private CaptchaController captchaController;

    public CaptchaControllerTest() {
    }

    @Before
    public void setUp() throws Exception {
        captchaController.setCaptchaProducer(captchaProducer);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandleRequest() throws Exception {
        String text = "TEXT";
        when(captchaProducer.createText()).thenReturn(text);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        ServletOutputStream os = mock(ServletOutputStream.class);
        when(req.getSession()).thenReturn(session);
        when(resp.getOutputStream()).thenReturn(os);
        PowerMockito.mockStatic(ImageIO.class);
        when(ImageIO.write(any(BufferedImage.class), anyString(), any(ServletOutputStream.class))).thenReturn(true);
        captchaController.handleRequest(req, resp);
        verify(resp, times(1)).setDateHeader(anyString(), eq(0L));
        verify(resp, times(2)).setHeader(anyString(), anyString());
        verify(resp, times(1)).addHeader(anyString(), anyString());
        verify(resp, times(1)).setContentType(anyString());
        verify(captchaProducer, times(1)).createText();
        verify(req, times(1)).getSession();
        verify(session, times(1)).setAttribute(anyString(), eq(text));
        verify(captchaProducer, times(1)).createImage(text);
        verify(resp, times(1)).getOutputStream();
        verify(os, times(1)).flush();
        verify(os, times(1)).close();
        verifyStatic();
        ImageIO.write(any(BufferedImage.class), anyString(), any(ServletOutputStream.class));
    }
}
