package lnu.sa224ny.backend.services;

import lnu.sa224ny.backend.models.Page;
import lnu.sa224ny.backend.models.PageDTO;
import lnu.sa224ny.backend.repositories.PageRepository;
import lnu.sa224ny.backend.utils.FileHandler;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<PageDTO> search(String query) {
        String[] words = query.split(" ");
        System.out.println(Arrays.toString(words));
        int[] wordIds = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            wordIds[i] = pageRepository.getIdForWord(words[i]);
        }
        List<Page> pageResult = pageRepository.getPagesByWordIds(wordIds);
        System.out.println(pageResult.size());
        return pageResult.stream().map(Page::mapToDTO).collect(Collectors.toList());
    }
}
