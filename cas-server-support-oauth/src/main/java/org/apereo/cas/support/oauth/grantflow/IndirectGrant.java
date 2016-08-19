package org.apereo.cas.support.oauth.grantflow;

import org.apereo.cas.support.oauth.web.OAuth20Grant;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangzhenli
 * @since 5.0.0
 */
public abstract class IndirectGrant extends OAuth20Grant {

    public abstract String authorizeResponseType();

    public abstract boolean needPreAuthorization();

    public abstract ModelAndView authorize(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
