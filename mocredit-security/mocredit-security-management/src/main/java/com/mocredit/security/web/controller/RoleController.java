package com.mocredit.security.web.controller;

import com.mocredit.security.entity.Role;
import com.mocredit.security.service.ResourceService;
import com.mocredit.security.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * <p>User:
 * <p>Date: 14-2-14
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;
    private final Integer pageSize = 10;

    @RequiresPermissions("role:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        model.addAttribute("page", page);
        model.addAttribute("pageTotal", (roleService.findAll().size() + pageSize - 1) / pageSize);
        model.addAttribute("roleList", roleService.findRoleByList(page, pageSize));
        return "role/list";
    }

    @RequiresPermissions("role:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView showCreateForm(Model model) {
        setCommonData(model);
        ModelAndView modelAndView = new ModelAndView("role/edit");
        modelAndView.addObject("role", new Role());
        modelAndView.addObject("op", "新增角色");
        return modelAndView;
    }

    @RequiresPermissions("role:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Role role, RedirectAttributes redirectAttributes) {
        roleService.createRole(role);
        redirectAttributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/role";
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        setCommonData(model);
        model.addAttribute("role", roleService.findOne(id));
        model.addAttribute("op", "修改");
        return "role/edit";
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(Role role, RedirectAttributes redirectAttributes) {
        roleService.updateRole(role);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/role";
    }

//    @RequiresPermissions("role:delete")
//    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
//    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
//        setCommonData(model);
//        model.addAttribute("role", roleService.findOne(id));
//        model.addAttribute("op", "删除");
//        return "role/edit";
//    }

    //    @RequiresPermissions("role:delete")
//    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
//    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
//        roleService.deleteRole(id);
//        redirectAttributes.addFlashAttribute("msg", "删除成功");
//        return "redirect:/role";
//    }
    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        roleService.deleteRole(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/role";
    }

    private void setCommonData(Model model) {
        model.addAttribute("resourceList", resourceService.findAll());
    }

}
