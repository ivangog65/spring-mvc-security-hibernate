package com.nixsolutions.chursin.springlab.bean;

/**
 * Created by chursin on 01.08.16.
 */
public class SignUpUserBean extends UserBean {

    private String captcha;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public String toString() {
        return "SignUpUserBean{" +
                "captcha='" + captcha + '\'' +
                '}';
    }
}
