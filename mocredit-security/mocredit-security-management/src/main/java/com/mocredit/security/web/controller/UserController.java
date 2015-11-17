package com.mocredit.security.web.controller;

import com.mocredit.security.entity.Authorization;
import com.mocredit.security.entity.User;
import com.mocredit.security.service.AuthorizationService;
import com.mocredit.security.service.OrganizationService;
import com.mocredit.security.service.RoleService;
import com.mocredit.security.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>User:
 * <p>Date: 14-2-14
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthorizationService athorizationService;
    private final Integer pageSize = 10;

    @RequiresPermissions("user:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        setCommonData(model);
        model.addAttribute("user", new User());
        model.addAttribute("op", "新增用户");
        model.addAttribute("userList", userService.findUserByList(page, pageSize));
        model.addAttribute("roleList", roleService.findAll());
        model.addAttribute("page", page);
        model.addAttribute("pageTotal", (userService.findUserCount() + pageSize - 1) / pageSize);
        return "user/list";
    }

    @RequestMapping(value = "/list")
    public void listData(Model model, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, Integer pageSize) {
        model.addAttribute("list", userService.findAll());
        model.addAttribute("total", userService.findAll().size());
    }

    @RequiresPermissions("user:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        setCommonData(model);
        model.addAttribute("user", new User());
        model.addAttribute("op", "新增");
        return "user/edit";
    }

    @RequiresPermissions("user:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(User user, final Long roleId, RedirectAttributes redirectAttributes) {
        if ("".equals(user.getUsername())) {
            redirectAttributes.addFlashAttribute("msg", "用户名不能为空");
            return "redirect:/user";
        }
        if ("".equals(user.getPassword())) {
            redirectAttributes.addFlashAttribute("msg", "密码不能为空");
            return "redirect:/user";
        }
        User oUser = userService.findByUsername(user.getUsername());
        if (oUser != null) {
            redirectAttributes.addFlashAttribute("msg", "新增失败当前用户名已存在");
        } else {
            userService.createUser(user);
            Authorization authorization = new Authorization();
            authorization.setAppId(0L);
            authorization.setUserId(user.getId());
            authorization.setRoleIds(new ArrayList<Long>() {{
                add(roleId);
            }});
            athorizationService.createAuthorization(authorization);
            redirectAttributes.addFlashAttribute("msg", "新增成功");
        }
        return "redirect:/user";
    }

    /*@RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        setCommonData(model);
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "修改");
        return "user/edit";
    }*/
    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        setCommonData(model);
        model.addAttribute("user", userService.findOne(id));
        List<Long> roleIdList = roleService.findRoleIdByUserId(id);
        model.addAttribute("roleList", roleService.findAll());
        if (!roleIdList.isEmpty()) {
            model.addAttribute("roleId", roleIdList.get(0));
        } else {
            model.addAttribute("roleId", "");
        }
        return "user/update";
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(User user, Long roleId, RedirectAttributes redirectAttributes) {
        User oUser = userService.findByUsername(user.getUsername());
        if (oUser != null && !oUser.getId().equals(user.getId())) {
            redirectAttributes.addFlashAttribute("msg", "修改失败当前用户名已存在");
            return "user/update";
        } else {
            userService.updateUser(user, oUser);
            athorizationService.updateAuthorizationRoleByUserId(user.getId(), roleId);
            redirectAttributes.addFlashAttribute("msg", "修改成功");
            return "redirect:/user";
        }
    }

    /*
        @RequiresPermissions("user:delete")
        @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
        public String showDeleteForm(@PathVariable("id") Long id, Model model) {
            setCommonData(model);
            model.addAttribute("user", userService.findOne(id));
            model.addAttribute("op", "删除");
            return "user/edit";
        }

        @RequiresPermissions("user:delete")
        @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
        public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("msg", "删除成功");
            return "redirect:/user";
        }
    */
    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/user";
    }


    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
    public String showChangePasswordForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "修改密码");
        return "user/changePassword";
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
    public String changePassword(@PathVariable("id") Long id, String newPassword, RedirectAttributes redirectAttributes) {
        userService.changePassword(id, newPassword);
        redirectAttributes.addFlashAttribute("msg", "修改密码成功");
        return "redirect:/user";
    }

    private void setCommonData(Model model) {
        model.addAttribute("organizationList", organizationService.findAll());
    }
}
