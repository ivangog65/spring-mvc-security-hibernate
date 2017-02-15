package com.nixsolutions.chursin.springlab.customtag;

import com.nixsolutions.chursin.springlab.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TableTag extends BodyTagSupport {

    private List<User> users;

    @Override
    public int doEndTag() throws JspException {

        pageContext.getRequest().getServerName();

        JspWriter out = pageContext.getOut();

        StringBuilder s = new StringBuilder();

        s.append("<table align=\"center\" border=\"1\">");

        s.append("<tr>");
        s.append("<td>");
        s.append("Login");
        s.append("</td>");
        s.append("<td>");
        s.append("First name");
        s.append("</td>");
        s.append("<td>");
        s.append("Last name");
        s.append("</td>");
        s.append("<td>");
        s.append("Age");
        s.append("</td>");
        s.append("<td>");
        s.append("Role");
        s.append("</td>");
        s.append("<td>");
        s.append("Actions");
        s.append("</td>");
        s.append("</tr>");

        for (User user : users) {
            s.append("<tr>");
            s.append("<td>");
            s.append(user.getLogin());
            s.append("</td>");
            s.append("<td>");
            s.append(user.getFirstName());
            s.append("</td>");
            s.append("<td>");
            s.append(user.getLastName());
            s.append("</td>");
            s.append("<td>");
            s.append(getAge(user.getBirthday()));
            s.append("</td>");
            s.append("<td>");
            s.append(user.getRole().getName().substring(5));
            s.append("</td>");
            s.append("<td>");
            s.append("<a href=\"").append(((HttpServletRequest) pageContext.getRequest())
                    .getContextPath()).append("/edit_user?id=").append(user.getId()).append("\">Edit</a>");
            s.append("  <a href=\"").append(((HttpServletRequest) pageContext.getRequest())
                    .getContextPath()).append("/delete_user?id=").append(user.getId()).append("\" onclick=\"return confirm('Delete ?');\" >Delete</a>");
            s.append("</td>");
            s.append("</tr>");
        }
        s.append("</table>");
        try {
            out.println(s.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    private int getAge(Date dateOfBirth) {
        long timeBetween = new Date().getTime() - dateOfBirth.getTime();
        double yearsBetween = timeBetween / 3.156e+10;
        return (int) Math.floor(yearsBetween);
    }

}