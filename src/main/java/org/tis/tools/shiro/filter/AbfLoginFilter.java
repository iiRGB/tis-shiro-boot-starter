package org.tis.tools.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.tis.tools.webapp.util.AjaxUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


public class AbfLoginFilter extends AdviceFilter {



    /**
     * 在访问controller前判断是否登录，返回错误信息，不进行重定向。
     * @param request
     * @param response
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        // 验证是否登录
        if(subject.getPrincipal() == null) {
            AjaxUtils.ajaxJsonAuthMessage((HttpServletResponse) response);
            return false;
        }
        // 验证行为权限
        return true;

    }

}