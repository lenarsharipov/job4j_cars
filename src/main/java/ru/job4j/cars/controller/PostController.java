package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cars.model.Make;
import ru.job4j.cars.service.MakeService;
import ru.job4j.cars.service.ModelService;
import ru.job4j.cars.service.PostService;

/**
 * Post Controller
 * @author Lenar Sharipov
 * @version 1.0
 */
@ThreadSafe
@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final MakeService makeService;
    private final ModelService modelService;
    private static final String MESSAGE = "message";

    @GetMapping()
    public String getAll(Model model) {
        var posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("/setMake")
    public String viewSetMake(Model model) {
        model.addAttribute("makes", makeService.findAll());
        return "posts/setMake";
    }

    @PostMapping("/chooseMake")
    public String setMake(@ModelAttribute Make make,
                          RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("makeId", make.getId());
        return "redirect:/posts/setModel";
    }

    @GetMapping("/setModel")
    public String viewSetModel(Model model) {
        var makeId = (Integer) model.getAttribute("makeId");
        var makeOptional = makeService.findById(makeId);
        if (makeOptional.isEmpty()) {
            model.addAttribute(MESSAGE, "UNABLE TO FIND MAKE BY SPECIFIED ID");
            return "errors/404";
        }
        var models = makeOptional.get().getModels();
        model.addAttribute("models", models);
        System.out.println("lenar 1");
        return "posts/setModel";
    }

    @PostMapping("/chooseModel")
    public String setModel(@ModelAttribute ru.job4j.cars.model.Model carModel,
                          RedirectAttributes redirectAttributes) {
        System.out.println("lenar 2");
        redirectAttributes.addFlashAttribute("modelId", carModel.getId());
        System.out.println("lenar 3");
        return "redirect:/posts/setBodyStyle";
    }

    @GetMapping("/setBodyStyle")
    public String viewSetBodyStyle(Model model) {
        var makeId = (Integer) model.getAttribute("makeId");
        var modelId = (Integer) model.getAttribute("modelId");
        System.out.println(makeId);
        System.out.println(modelId);

        var modelOptional = modelService.findById(modelId);
        if (modelOptional.isEmpty()) {
            model.addAttribute(MESSAGE, "UNABLE TO FIND MODEL BY SPECIFIED ID");
            return "errors/404";
        }
        var bodyStyles = modelOptional.get().getBodyStyles();
        model.addAttribute("bodyStyles", bodyStyles);
        return "posts/setBodyStyle";
    }

}