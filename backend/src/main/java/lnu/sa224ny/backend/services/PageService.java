package lnu.sa224ny.backend.services;

import lnu.sa224ny.backend.models.Page;
import lnu.sa224ny.backend.models.PageDTO;
import lnu.sa224ny.backend.models.Scores;
import lnu.sa224ny.backend.models.SearchLevel;
import lnu.sa224ny.backend.repositories.PageRepository;
import lnu.sa224ny.backend.utils.FileHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<PageDTO> search(String query, SearchLevel searchLevel) {
        String[] words = query.split(" ");
        int[] wordIds = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            wordIds[i] = pageRepository.getIdForWord(words[i]);
        }

        List<Page> pageResult = pageRepository.getPagesByWordIds(wordIds);

        Scores scores = new Scores(pageResult.size());

        List<PageDTO> result = new ArrayList<>();

        switch (searchLevel) {
            case LOW -> {
                for (int i = 0; i < pageResult.size(); i++) {
                    Page currentPage = pageResult.get(i);
                    scores.content[i] = getFrequencyScore(currentPage, wordIds);
                }
                normalize(scores.content, false);
            }
            case MEDIUM -> {
                calculateScores(wordIds, pageResult, scores);
            }
            case HIGH -> {
                calculatePageRank(pageResult);
                calculateScores(wordIds, pageResult, scores);
            }
        }

        for (int i = 0; i < pageResult.size(); i++) {
            Page currentPage = pageResult.get(i);

            PageDTO pageDTO = new PageDTO();
            pageDTO.link = currentPage.getUrl();
            pageDTO.content = scores.content[i];
            pageDTO.location = searchLevel != SearchLevel.LOW ? 0.8 * scores.location[i] : 0.0;
            pageDTO.pageRank = searchLevel == SearchLevel.HIGH ? 0.5 * currentPage.getPageRank() : 0.0;
            pageDTO.score = pageDTO.content + pageDTO.location + pageDTO.pageRank;
            result.add(pageDTO);
        }

        return result.stream().sorted(Comparator.comparing(PageDTO::getScore).reversed()).toList();
    }

    private void calculateScores(int[] wordIds, List<Page> pageResult, Scores scores) {
        for (int i = 0; i < pageResult.size(); i++) {
            Page currentPage = pageResult.get(i);
            scores.content[i] = getFrequencyScore(currentPage, wordIds);
            scores.location[i] = getLocationScore(currentPage, wordIds);
        }
        normalize(scores.content, false);
        normalize(scores.location, true);
    }

    private double getFrequencyScore(Page page, int[] wordIds) {
        double score = 0;
        List<Integer> wordList = page.getWords();
        for (int i = 0; i < wordList.size(); i++) {
            for (int j = 0; j < wordIds.length; j++) {
                if (wordList.get(i) == wordIds[j]) {
                    score++;
                }
            }
        }
        return score;
    }

    private double getLocationScore(Page page, int[] wordIds) {
        double score = 0;
        List<Integer> wordList = page.getWords();
        for (int wordId : wordIds) {
            boolean found = false;

            for (int i = 0; i < wordList.size(); i++) {
                if (wordList.get(i) == wordId) {
                    score += i + 1;
                    found = true;
                    break;
                }
            }
            if (!found) {
                score += 100000;
            }
        }
        return score;
    }

    private void normalize(double[] list, boolean smallIsBetter) {
        if (list.length > 0) {
            if (smallIsBetter) {
                double min = getMin(list);

                for (int i = 0; i < list.length; i++) {
                    list[i] = min / Math.max(list[i], 0.00001);
                }
            } else {
                double max = getMax(list);

                for (int i = 0; i < list.length; i++) {
                    list[i] = list[i] / max;
                }
            }
        }
    }

    private double getMin(double[] list) {
        double min = list[0];
        for (int i = 1; i < list.length; i++) {
            min = Math.min(min, list[i]);
        }
        return min;
    }

    private double getMax(double[] list) {
        double max = list[0];
        for (int i = 1; i < list.length; i++) {
            max = Math.max(max, list[i]);
        }
        return max;
    }

    private void calculatePageRank(List<Page> allPages) {
        int maxIterations = 20;
        double[] pageRanks = new double[allPages.size()];
        for (int i = 0; i < maxIterations; i++) {
            for (int j = 0; j < allPages.size(); j++) {
                pageRanks[j] = iteratePageRank(allPages, allPages.get(j));
            }


        }
        normalize(pageRanks, false);
        for (int i = 0; i < allPages.size(); i++) {
            allPages.get(i).setPageRank(pageRanks[i]);
        }
    }

    private double iteratePageRank(List<Page> allPages, Page page) {
        double pageRank = 0;

        for (Page currentPage : allPages) {
            if (currentPage.hasLinkTo(page.getUrl())) {
                pageRank += currentPage.getPageRank() / currentPage.getNumberOfLinks();
            }
        }
        return 0.85 * pageRank + 0.15;
    }
}
