package com.ktmmobile.msf.commons.file.support.util;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import com.ktmmobile.msf.commons.file.application.dto.FileVariantOptions;

public final class FileImagePathUtils {

    private static final String VARIANTS_DIRECTORY_NAME = "variants";
    private static final String SMALL_DIRECTORY_NAME = "small";
    private static final String MEDIUM_DIRECTORY_NAME = "medium";

    private FileImagePathUtils() {
    }

    public static String resolveBaseImageFilePath(String filePath) {
        String normalizedFilePath = FileUtils.normalizeFilePath(filePath);
        String[] parts = normalizedFilePath.split("/");
        if (parts.length >= 3) {
            int fileNameIndex = parts.length - 1;
            int variantsIndex = parts.length - 3;
            if (VARIANTS_DIRECTORY_NAME.equals(parts[variantsIndex])) {
                return FileUtils.concat(String.join("/", Arrays.copyOf(parts, variantsIndex)), parts[fileNameIndex]);
            }
        }
        int separatorIndex = normalizedFilePath.lastIndexOf('.');
        if (separatorIndex < 0) {
            return stripResizeSuffix(normalizedFilePath);
        }
        String baseName = normalizedFilePath.substring(0, separatorIndex);
        String extension = normalizedFilePath.substring(separatorIndex);
        return stripResizeSuffix(baseName) + extension;
    }

    public static String resolveSmallImageFilePath(String filePath) {
        return resolveVariantImageFilePath(filePath, SMALL_DIRECTORY_NAME);
    }

    public static String resolveMediumImageFilePath(String filePath) {
        return resolveVariantImageFilePath(filePath, MEDIUM_DIRECTORY_NAME);
    }

    public static String resolveVariantImageFilePath(String filePath, String variantName) {
        String baseFilePath = resolveBaseImageFilePath(filePath);
        int separatorIndex = baseFilePath.lastIndexOf('/');
        String directoryPath = separatorIndex < 0 ? "" : baseFilePath.substring(0, separatorIndex);
        String fileName = separatorIndex < 0 ? baseFilePath : baseFilePath.substring(separatorIndex + 1);
        return FileUtils.concat(FileUtils.concat(FileUtils.concat(directoryPath, VARIANTS_DIRECTORY_NAME), variantName), fileName);
    }

    public static Set<String> resolveImageFilePaths(String filePath) {
        String baseFilePath = resolveBaseImageFilePath(filePath);
        Set<String> targets = new LinkedHashSet<>();
        targets.add(baseFilePath);
        targets.add(resolveSmallImageFilePath(baseFilePath));
        targets.add(resolveMediumImageFilePath(baseFilePath));
        for (FileVariantOptions.ImageVariantFormat format: FileVariantOptions.ImageVariantFormat.values()) {
            targets.add(resolveVariantImageFilePath(replaceExtension(baseFilePath, format.extension()), format.variantName()));
        }
        return targets;
    }

    private static String stripResizeSuffix(String filePath) {
        if (filePath.endsWith("_small")) {
            return filePath.substring(0, filePath.length() - "_small".length());
        }
        if (filePath.endsWith("_medium")) {
            return filePath.substring(0, filePath.length() - "_medium".length());
        }
        return filePath;
    }

    private static String replaceExtension(String filePath, String extension) {
        int directorySeparatorIndex = filePath.lastIndexOf('/');
        int extensionSeparatorIndex = filePath.lastIndexOf('.');
        if (extensionSeparatorIndex <= directorySeparatorIndex) {
            return filePath + "." + extension;
        }
        return filePath.substring(0, extensionSeparatorIndex + 1) + extension;
    }
}
