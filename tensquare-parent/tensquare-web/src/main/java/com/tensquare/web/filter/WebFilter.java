package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {

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
        System.out.println("前端Zuul过滤器执行......");
        //得到请求上下文对象
        RequestContext context = RequestContext.getCurrentContext();
        //通过请求上下文得到请求对象
        HttpServletRequest request = context.getRequest();
        //得到请求头
        String authorization = request.getHeader("Authorization");
        //如果请求头不为空，把请求头信息添加到网关Zuul的请求头上
        if (null != authorization) {
            System.out.println("找到请求头:" + authorization);
            context.addZuulRequestHeader("Authorization", authorization);
        } else {
            System.out.println("没有找到请求头:" + authorization);
        }
        System.out.println("前端过滤器执行结束......");
        return null;
    }
}
