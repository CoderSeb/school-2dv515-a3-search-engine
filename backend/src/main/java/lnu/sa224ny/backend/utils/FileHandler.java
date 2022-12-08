package lnu.sa224ny.backend.utils;

import lnu.sa224ny.backend.models.Page;
import lnu.sa224ny.backend.repositories.PageRepository;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class FileHandler {
    public List<Page> loadFilesToPages(String path) {
        File directory = new File(path);
        System.out.println("Reading files in path " + path);

        if (directory.exists() && directory.isDirectory()) {
            Path directoryPath = Paths.get(path);

            try {
                List<Page> result = new ArrayList<>();
                Files.list(directoryPath).forEach(file -> {
                    Page newPage = new Page();
                    newPage.setUrl(String.valueOf(file.getFileName()));
                    result.add(newPage);
                });
                return result;
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public PageRepository loadFiles() {
        PageRepository pageRepository = new PageRepository();
        List<Page> gamePages = loadFilesToPages("src/files/wikipedia/Words/Games");
        List<Page> programmingPages = loadFilesToPages("src/files/wikipedia/Words/Programming");

        gamePages.forEach(pageRepository::addPage);

        programmingPages.forEach(pageRepository::addPage);

        return pageRepository;
    }
}
