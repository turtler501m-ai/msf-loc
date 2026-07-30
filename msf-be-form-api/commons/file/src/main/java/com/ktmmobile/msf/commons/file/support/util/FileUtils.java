package com.ktmmobile.msf.commons.file.support.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    public static String generateFileName(String originalName) {
        String fileNameOnly = UUID.randomUUID().toString();
        String fileExtension = StringUtils.getFilenameExtension(originalName);
        if (fileExtension == null) {
            return fileNameOnly;
        }
        return fileNameOnly + "." + fileExtension;
    }

    public static String generateStoredFileName(String originalName) {
        return generateFileName(originalName);
    }

    public static String concat(String directoryPath, String fileName) {
        if (!StringUtils.hasText(directoryPath)) {
            return normalizeFilePath(fileName);
        }
        return Paths.get(directoryPath, fileName).toString().replace("\\", "/");
    }

    public static String normalizeDirectoryPath(String directoryPath) {
        if (!StringUtils.hasText(directoryPath)) {
            return "";
        }

        String normalizedPath = directoryPath.trim().replace("\\", "/");
        if ("/".equals(normalizedPath)) {
            return "";
        }

        normalizedPath = normalizedPath.replaceAll("^/+", "");
        normalizedPath = normalizedPath.replaceAll("/+$", "");
        return normalizedPath;
    }

    public static String normalizeFilePath(String filePath) {
        if (!StringUtils.hasText(filePath)) {
            return "";
        }

        String normalizedFilePath = filePath.trim().replace("\\", "/");
        normalizedFilePath = normalizedFilePath.replaceAll("^/+", "");
        return normalizedFilePath;
    }

    public static String getDirNameUsingDate() {
        return getDirNameUsingDate(true);
    }

    public static String getDirNameUsingDate(boolean seperatePath) {
        LocalDate today = LocalDate.now();
        if (seperatePath) {
            return String.format("%04d/%02d/%02d",
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        }
        return String.format("%04d%02d%02d", today.getYear(), today.getMonthValue(), today.getDayOfMonth());
    }

    public static String getDirNameUsingYearMonth() {
        return getDirNameUsingYearMonth(true);
    }

    public static String getDirNameUsingYearMonth(boolean seperatePath) {
        LocalDate today = LocalDate.now();
        if (seperatePath) {
            return String.format("%04d/%02d", today.getYear(), today.getMonthValue());
        }
        return String.format("%04d%02d", today.getYear(), today.getMonthValue());
    }

    public static String getDirNameUsingYear() {
        LocalDate today = LocalDate.now();
        return String.format("%04d", today.getYear());
    }

    public static String getUrlEncodedFileName(String fileName) {
        return URLEncoder.encode(fileName, StandardCharsets.UTF_8)
            .replace("+", " ");
    }
}
