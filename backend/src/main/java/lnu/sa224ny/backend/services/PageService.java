package lnu.sa224ny.backend.services;

import lnu.sa224ny.backend.models.Page;
import lnu.sa224ny.backend.models.PageDTO;
import lnu.sa224ny.backend.repositories.PageRepository;
import lnu.sa224ny.backend.utils.FileHandler;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {
    private final PageRepository pageRepository;


    public PageService() {
        FileHandler fileHandler = new FileHandler();
        this.pageRepository = fileHandler.loadFiles();
    }

    public List<String> getAllPages() {
        return pageRepository.getAllUrls();
    }

    public List<PageDTO> search(String word) {
        List<Page> pageResult = pageRepository.getPagesByWordId(word);
        return null;
    }
}
