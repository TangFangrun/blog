package com.tfr.blog.blog.controller.admin;

import com.tfr.blog.blog.pojo.Type;
import com.tfr.blog.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @description:
 * @author: rain
 * @date: Created in 2020/5/1 19:38
 * @version: 1.0
 * @modified By:
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * @param
     * @return
     * @description Pageable 是Spring Data库中定义的一个接口，用于构造翻页查询，是所有分页相关信息的一个抽象，通过该接口，我们可以得到和分页相关所有信息（例如pageNumber、pageSize等），这样，Jpa就能够通过pageable参数来得到一个带分页信息的Sql语句。
     * @author rain
     * @date 2020/5/1 19:43
     */
    @GetMapping("/types")
    public String list(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                               Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName != null) {
            result.rejectValue("name", "nameError", "分类名重复，请重新添加");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type saveType = typeService.saveType(type);
        if (saveType == null) {
            attributes.addFlashAttribute("message", "新增失败，请重试！！！");
        } else {
            attributes.addFlashAttribute("message", "新增分类成功！！！");
        }
        return "redirect:/admin/types";
    }


    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName != null) {
            result.rejectValue("name", "nameError", "分类名重复，请重新添加");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type saveType = typeService.updateType(id, type);
        if (saveType != null) {
            attributes.addFlashAttribute("message", "更新失败，请重试！！！");
        } else {
            attributes.addFlashAttribute("message", "更新成功！！！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }


    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","它已经永久的离开了你");
        return "redirect:/admin/types";
    }
}
