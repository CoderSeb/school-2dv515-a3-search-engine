package lnu.sa224ny.backend.repositories;

import lnu.sa224ny.backend.models.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PageRepository {
    private HashMap<String, Integer> wordToId;
    private List<Page> pages;

    public PageRepository() {
        this.pages = new ArrayList<>();
        this.wordToId = new HashMap<String, Integer>();
    }

    public List<Page> getPagesByWordId(String word) {
        int wordId = 0;
        List<Page> result = new ArrayList<>();
        if (wordToId.containsKey(word)) {
            wordId = wordToId.get(word);
        } else {
            return result;
        }

        int finalWordId = wordId;
        pages.forEach(page -> {
            if (page.getWords().contains(finalWordId)) {
                result.add(page);
            }
        });

        return result;
    }

    public int getIdForWord(String word) {
        if (wordToId.containsKey(word)) {
            return wordToId.get(word);
        } else {
            int id = wordToId.size();
            wordToId.put(word, id);
            return id;
        }
    }

    public void addPage(Page newPage) {
        pages.add(newPage);
    }

    public void addWordIdToPage(Page page, String word) {
        int wordId = getIdForWord(word);
        page.addWordId(wordId);
    }

    public void printPageList() {
        pages.forEach(page -> System.out.println(page.getUrl()));
    }

    public List<String> getAllUrls() {
        List<String> result = new ArrayList<>();
        pages.forEach(page -> result.add(page.getUrl()));
        return result;
    }
}
