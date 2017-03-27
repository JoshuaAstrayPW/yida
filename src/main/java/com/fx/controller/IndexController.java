package com.fx.controller;

import com.fx.model.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by fangxu on 2017/2/5.
 */
//@Controller
public class IndexController {
    @RequestMapping("/")
    @ResponseBody
    public String Index(){
        return "hello fangxu";
    }

    @RequestMapping("/profile/{groupID}/{userID}")
    @ResponseBody
    public String profile(@PathVariable("userID") int userID,
                          @PathVariable("groupID") String groupID,
                          @RequestParam(value = "type",defaultValue = "1") int type,
                          @RequestParam(value = "key",required = false) String key){
        return String.format("profile Page of %s / %d  t:%d k:%s",groupID ,userID,type,key);

    }

    @RequestMapping(path = {"/vm"},method = {RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("value1", "vvvvvvv1");
        List<String> colors = Arrays.asList(new String[]{"RED","GREEM","BLUE"});
        model.addAttribute("colors",colors);

        Map<String,String> map = new HashMap<>();
        for(int i = 0; i < 4; i++)
            map.put(String.valueOf(i),String.valueOf(i*i));
        model.addAttribute("map",map);
        model.addAttribute("User",new User("xu"));
        return "home";
    }
    @RequestMapping(path = {"/request"},method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model, HttpServletResponse response,
                          HttpServletRequest request,
                          HttpSession httpSession){
        StringBuilder sb  = new StringBuilder();
        sb.append(request.getMethod() + "<br>");
        sb.append(request.getQueryString() + "<br>");
        sb.append(request.getPathInfo() + "<br>");
        sb.append(request.getRequestURI() + "<br>");
        return  sb.toString();
    }
}
