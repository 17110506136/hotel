package cn.mafangui.hotel.controller;

import cn.mafangui.hotel.entity.User;
import cn.mafangui.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/login")
    public int userLogin(String username, String password, HttpServletRequest request){
        int result;
        if (username == null | username == "" | password == null | password == ""){
            return -1;
        }
        if (userService.selectByUsernameAndPassword(username,password) != null){
            result = 1;
            HttpSession session = request.getSession();
            session.setAttribute("userId",username);
            session.getId();
        }
        else result = 0;
        return result;
    }

    /**
     * 用户注册
     * @param username
     * @param password
     * @param name
     * @param gender
     * @param phone
     * @param email
     * @param address
     * @param idcard
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/register")
    public int userRegister(String username,String password,String name,String gender,String phone,String email,String address,String idcard){
        User user = new User(username,password,name,gender,phone,email,address,idcard);
        return userService.insertUser(user);
    }

    /**
     * 用户添加
     * @param username
     * @param password
     * @param name
     * @param gender
     * @param phone
     * @param email
     * @param address
     * @param idcard
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public int userAdd(String username,String password,String name,String gender,String phone,String email,String address,String idcard){
        User user = new User(username,password,name,gender,phone,email,address,idcard);
        return userService.addUser(user);
    }


    /**
     * 更新用户信息
     * @param userId
     * @param name
     * @param gender
     * @param phone
     * @param email
     * @param address
     * @param idcard
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/update")
    public int userUpdate(int userId,String name,String gender,String phone,String email,String address,String idcard){
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        user.setGender(gender);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAddress(address);
        user.setIdcard(idcard);
        return userService.updateUser(user);
    }

    /**
     * 更改密码
     * @param username
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/updatePassword")
    public int updatePassword(String username,String oldPassword,String newPassword){
        User user = userService.selectByUsernameAndPassword(username,oldPassword);
        if (user == null){
            return -1;
        }else {
            user.setPassword(newPassword);
            return userService.updateUser(user);
        }
    }

    /**
     * 所有在记录的用户和客户
     * @return
     */
    @RequestMapping(value = "/all")
    public List<User> getAll(){
        return userService.selectAll();
    }

    /**
     * 所有注册用户
     * @return
     */
    @RequestMapping(value = "/allUser")
    public List<User> getAllUser(){
        return userService.selectAllUser();
    }

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/isUsernameExist")
    public int isUsernameExist(String username){
        int result = 0;
        if (userService.selectByUsername(username) != null){
            result = 1;
        }
        else result = 0;
        return result;
    }

    /**
     * 根据username查找用户
     * @param username
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/withUsername")
    public User getByUsername(String username){
        User user = userService.selectByUsername(username);
        user.setPassword(null);
        return user;
    }

    /**
     * 根据id查找用户
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/withId")
    public User getById(int userId){
        return userService.selectById(userId);
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/delete")
    public int deleteUser(int userId){
        return userService.deleteUser(userId);
    }

}
