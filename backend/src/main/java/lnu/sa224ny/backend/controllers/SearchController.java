package lnu.sa224ny.backend.controllers;


import lnu.sa224ny.backend.models.PageDTO;
import lnu.sa224ny.backend.models.SearchLevel;
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
    public List<PageDTO> search(@RequestParam String query, @RequestParam SearchLevel searchLevel) {
        String result = query.replaceAll("\"", "").toLowerCase();
        return pageService.search(result, searchLevel);
    }

    @RequestMapping("/api/pages")
    public List<String> getPages() {
        return pageService.getAllPages();
    }
}
