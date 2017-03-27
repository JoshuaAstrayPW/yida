package com.fx.service;

import com.fx.dao.LoginTicketDAO;
import com.fx.model.User;
import com.fx.util.WendaUtil;
import com.fx.model.LoginTicket;
import org.apache.commons.lang.StringUtils;
import com.fx.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by fangxu on 2017/2/6.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;


    //注册
    public Map<String,String> register(String username,String password){
        Map<String,String> map = new HashMap<String,String>();
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;

        }
        User user = userDAO.selectByName(username);
        if(user != null){
            map.put("msg","用户名已被注册");
            return  map;
        }
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;


    }

    //登录
    public Map<String,String> login(String username,String password){
        Map<String,String> map = new HashMap<String,String>();
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;

        }
        User user = userDAO.selectByName(username);
        if(user == null){
            map.put("msg","用户不存在");
            return  map;
        }
       if(!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())){
           map.put("msg","密码错误");
           return map;

       }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(3600*24*100 + now.getTime());
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();


    }
    //注销
    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }

    public User getUser(int id){
        return userDAO.selectById(id);
    }
    public User selectByName(String name) {
        return userDAO.selectByName(name);
    }

}
