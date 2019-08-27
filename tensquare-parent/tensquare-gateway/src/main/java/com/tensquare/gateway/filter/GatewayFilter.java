package com.tensquare.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.tensquare.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class GatewayFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 定义过滤器的执行时机：
     *     pre：代表在执行网关路由前被调用
     *     post：代表在执行route（路由）和error（处理请求时发生错误）之后执行
     *     error：如果处理请求时发生错误，会被调用
     *     route：路由请求时被调用
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 定义过滤器的执行顺序，返回值越小过滤器越先执行
     * @return 0
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 定义过滤器是否执行：
     *     true：执行
     *     false：不执行
     * @return true
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 定义过滤器的具体行为
     * 注意：无论该方法返回值是什么（哪怕是null），只要是Object类型都会继续往下执行。
     *
     * 该方法进行一下操作：
     *     过滤请求头信息，转发给微服务。
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //得到请求上下文对象
        RequestContext context = RequestContext.getCurrentContext();
        //通过请求上下文得到请求对象
        HttpServletRequest request = context.getRequest();
        //当我们发送请求到网关时时两次请求，需要放行
        System.out.println("method:" + request.getMethod());
        if (request.getMethod().equals("OPTIONS")) {
            return null;
        }
        //如果当前请求是登录请求，需要放行
        System.out.println("request url:" + request.getRequestURI().toString());
        if (request.getRequestURI().toString().contains("login")) {
            return null;
        }
        //获得请求头
        String authorization = request.getHeader("Authorization");
        System.out.println("authorization:" + authorization);
        if (null != authorization && authorization.startsWith("Bearer")) {
            //截取请求头信息得到token
            String token = authorization.substring(7);
            //解析token得到Claims对象
            Claims claims = jwtUtil.parseToken(token);
            if (null != claims) {
                //得到角色
                String roles = (String) claims.get("roles");
                //具有管理员权限才能进行以下操作
                if ("admin".equals(roles)) {
                    //将自己的请求头信息添加到网关请求，转发到微服务
                    context.addZuulRequestHeader("Authorization",authorization);
                    return null;
                }
            }
        }
        context.setSendZuulResponse(false);//设置请求不再继续执行
        context.setResponseStatusCode(403);//设置响应码为403或401
        context.setResponseBody("无权访问");//设置响应体内容
        context.getResponse().setContentType("text/html;charset=utf-8");//设置响应体内容类型
        return null;
    }
}
