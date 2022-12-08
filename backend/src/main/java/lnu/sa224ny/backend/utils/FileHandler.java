package lnu.sa224ny.backend.utils;

import lnu.sa224ny.backend.models.Page;
import lnu.sa224ny.backend.repositories.PageRepository;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@NoArgsConstructor
public class FileHandler {
    public void loadFilesToPages(String path, PageRepository pageRepository) {
        File directory = new File(path);
        System.out.println("Reading files in path " + path);

        if (directory.exists() && directory.isDirectory()) {
            Path directoryPath = Paths.get(path);

            try {
                Files.list(directoryPath).forEach(file -> {
                    Page newPage = new Page();
                    newPage.setUrl(String.valueOf(file.getFileName()));
                    try {
                        List<String> words = readFile(path + "/" + newPage.getUrl());
                        words.forEach(word -> pageRepository.addWordIdToPage(newPage, word));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    pageRepository.addPage(newPage);
                });
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public PageRepository loadFiles() {
        PageRepository pageRepository = new PageRepository();
        loadFilesToPages("src/files/wikipedia/Words/Games", pageRepository);
        loadFilesToPages("src/files/wikipedia/Words/Programming", pageRepository);

        return pageRepository;
    }

    public List<String> readFile(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        scanner.useDelimiter(" ");
        List<String> records = new ArrayList<>();

        while (scanner.hasNext()) {
            records.add(scanner.next());
        }

        scanner.close();

        return records;
    }
}
