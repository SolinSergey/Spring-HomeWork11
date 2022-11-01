package ru.gb.homework11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.homework11.entities.*;
import ru.gb.homework11.service.RoleService;
import ru.gb.homework11.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("/showall")
    public String showListUsers(Model model){
        Iterable<User> userList = userService.findAllUsersIterable();
        model.addAttribute("listusers", userList);
        return "showlistusers";
    }

    @GetMapping("/admin/{id}")
    public String adminUsers(Model model,@PathVariable(value = "id") Long id){
        List<User> userList = userService.findAllUsersList();
        List<UserInfo> userInfoList = new ArrayList<>();
        Collection<Role> list=null;
        UserInfo userInfo=null;
        for (User u:userList){
            list=u.getRoles();
            String role="";
            for (Role r:list){
                role=role+r.getName()+" ";
            }
            userInfoList.add(new UserInfo(u.getId(),u.getUsername(),role,u.getEmail(),u.getPassword()));
        }
        CheckBoxGroup checkBoxGroup=null;
        if (id==0){
            checkBoxGroup=new CheckBoxGroup(true,true,true,true);
            userInfo=new UserInfo(0L,"","","","");
        }else {
            checkBoxGroup=new CheckBoxGroup();
            Optional<User> user=userService.findById(id);
            list=user.get().getRoles();
            for (Role l:list){
                if (l.getName().equals("ROLE_USER")) checkBoxGroup.setUserRole(true);
                if (l.getName().equals("ROLE_ADMIN")) checkBoxGroup.setAdminRole(true);
                if (l.getName().equals("ROLE_MANAGER")) checkBoxGroup.setManagerRole(true);
                if (l.getName().equals("ROLE_ROOT")) checkBoxGroup.setRootRole(true);
            }
            userInfo=new UserInfo(id,user.get().getUsername(),"",user.get().getEmail(),user.get().getPassword());
        }
        model.addAttribute("check",checkBoxGroup);
        model.addAttribute("listusers", userInfoList);
        model.addAttribute("userinfo",userInfo);
        return "adminuserpage";
    }

    @PostMapping("/processForm")
    public String processForm(@ModelAttribute UserInfo userInfo, CheckBoxGroup checkBoxGroup, Model model) {
        userService.addUser(userInfo,checkBoxGroup);
        return "redirect:/user/admin/0";
    }

}
