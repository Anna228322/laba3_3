package com.example.laba3_3;

public class FileModel {
    private final boolean _isDir;
    private final String _path;

    public FileModel(String path, boolean isDir) {
        this._path = path;
        this._isDir = isDir;
    }

    public String getPath() {
        return _path;
    }

    public boolean isDir() {
        return _isDir;
    }
}
