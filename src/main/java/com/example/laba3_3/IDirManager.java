package com.example.laba3_3;

import java.util.List;

public interface IDirManager {
    List<FileModel> getUserFileSystem(String path, String login);
}
