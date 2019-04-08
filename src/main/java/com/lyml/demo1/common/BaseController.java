package com.lyml.demo1.common;

import com.lyml.demo1.model.Role;
import com.lyml.demo1.model.User;
import com.lyml.demo1.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IRoleService roleService;

    protected String getStringPar(String key) {
        return request.getParameter(key);
    }

    protected Integer getIntPar(String key) {
        int par = 0;
        try {
            par = Integer.parseInt(request.getParameter(key));
        } catch (Exception e) {

        }
        return par;
    }

    protected Integer getIntPar(String key, int defaultValue) {
        int par = 0;
        try {
            par = Integer.parseInt(request.getParameter(key));
            return par;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    protected Long getLongPar(String key) {
        long par = 0L;
        try {
            par = Long.parseLong(request.getParameter(key));
        } catch (Exception e) {

        }
        return par;
    }

    protected User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            Role role = roleService.selectOneByUserId(user.getId());
            user.setGroup(role != null ? role.getName() : "");
            return user;
        } else {
            return null;
        }
    }

    /**
     * 渲染失败数据
     *
     * @return result
     */
    protected JsonResult renderError() {
        JsonResult result = new JsonResult();
        result.setSuccess(false);
        result.setCode(500);
        result.setStatus("500");
        return result;
    }

    /**
     * 渲染失败数据（带消息）
     *
     * @param msg 需要返回的消息
     * @return result
     */
    protected JsonResult renderError(String msg) {
        JsonResult result = renderError();
        result.setMsg(msg);
        return result;
    }

    /**
     * 渲染成功数据
     *
     * @return result
     */
    protected JsonResult renderSuccess() {
        JsonResult result = new JsonResult();
        result.setSuccess(true);
        result.setStatus("200");
        result.setCode(0);
        return result;
    }

    /**
     * 渲染成功数据（带信息）
     *
     * @param msg 需要返回的信息
     * @return result
     */
    protected JsonResult renderSuccess(String msg) {
        JsonResult result = renderSuccess();
        result.setMsg(msg);
        return result;
    }

    /**
     * 渲染成功数据（带数据）
     *
     * @param obj 需要返回的对象
     * @return result
     */
    protected JsonResult renderSuccess(Object obj) {
        JsonResult result = renderSuccess();
        result.setData(obj);
        return result;
    }

    /**
     * 渲染成功数据（带数据）
     *
     * @param obj 需要返回的对象
     * @return result
     */
    protected JsonResult renderSuccess(Long count, Object obj) {
        JsonResult result = renderSuccess();
        result.setCount(count);
        result.setData(obj);
        return result;
    }
}
