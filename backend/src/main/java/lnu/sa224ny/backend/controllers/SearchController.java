package lnu.sa224ny.backend.controllers;


import lnu.sa224ny.backend.services.PageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class SearchController {

    private PageService pageService;

    @RequestMapping("/api/search")
    public String search(@RequestParam String word) {
        return "Search endpoint for word: " + word;
    }

    @RequestMapping("/api/pages")
    public List<String> getPages() {
        return pageService.getAllPages();
    }
}
