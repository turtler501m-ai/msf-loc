package com.ktmmobile.msf.commons.file.application.dto;

import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.file.support.util.FileUtils;

public record FilePathRequest(
    String fileCategory,
    String path
) {

    public String pathFileName() {
        String normalizedPath = FileUtils.normalizePathFileName(path);
        String normalizedFileCategory = FileUtils.normalizeDirectoryPath(fileCategory);

        if (!StringUtils.hasText(normalizedFileCategory)) {
            return normalizedPath;
        }
        if (!StringUtils.hasText(normalizedPath)) {
            return normalizedFileCategory;
        }
        if (containsPath(normalizedPath, normalizedFileCategory)) {
            return normalizedPath;
        }
        return FileUtils.concat(normalizedFileCategory, normalizedPath);
    }

    private static boolean containsPath(String normalizedPath, String normalizedFileCategory) {
        return normalizedPath.equals(normalizedFileCategory)
            || normalizedPath.startsWith(normalizedFileCategory + "/")
            || normalizedPath.contains("/" + normalizedFileCategory + "/")
            || normalizedPath.endsWith("/" + normalizedFileCategory);
    }
}
