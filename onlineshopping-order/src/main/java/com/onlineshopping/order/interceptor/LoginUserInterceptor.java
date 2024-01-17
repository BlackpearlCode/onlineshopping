package com.onlineshopping.order.interceptor;


import com.google.gson.Gson;
import com.onlineshopping.common.vo.TokenInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;



@Component
public class LoginUserInterceptor implements HandlerInterceptor {


    public static ThreadLocal<TokenInfo> loginUser=new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean match = antPathMatcher.match("/status/**", uri);
        boolean match1 = antPathMatcher.match("/payed/notify", uri);
        if(match || match1){
            return true;
        }
        Object obj = request.getSession().getAttribute("loginUser");
        if(obj==null){
            //没登陆就去登录
            request.getSession().setAttribute("msg","请先登录");
            response.sendRedirect("http://auth.onlineshopping.com/login.html");
            return false;
        }
        Gson gson = new Gson();
        TokenInfo tokenInfo = gson.fromJson(gson.toJson(obj),TokenInfo.class);
        loginUser.set(tokenInfo);
        return true;
    }
}
