package com.example.laba3_3;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirManager {

    public List<FileModel> getUserFileSystem(String path, String login) {
        try {
            String root = Utils.PROJ_DIR + Utils.SHARED_DIR_NAME + "\\" + login;
            new File(root).mkdir();
            new File(root + "/demodir").mkdir();
            new File(root + "/demo.txt").createNewFile();

            path = root + path.replace("/", "\\");
            return Stream.of(new File(path).listFiles())
                    .map(file -> new FileModel(
                            file.getName().replace("\\", "/"),
                            file.isDirectory()
                    ))
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            System.out.println("Failed to enum dirs:\n\t" + e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }
}
