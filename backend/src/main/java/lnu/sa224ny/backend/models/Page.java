package lnu.sa224ny.backend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Page {
    private String url;
    private List<Integer> words;

    public Page() {
        this.words = new ArrayList<>();
    }

    public void addWordId(int wordId) {
        words.add(wordId);
    }

    public PageDTO mapToDTO() {
        PageDTO pageDTO = new PageDTO();
        pageDTO.link = url;
        return pageDTO;
    }
}
