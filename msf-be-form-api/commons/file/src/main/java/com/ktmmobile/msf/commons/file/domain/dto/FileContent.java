package com.ktmmobile.msf.commons.file.domain.dto;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.http.MediaType;

public record FileContent(
    CommonFile commonFile,
    byte[] content
) {

    public static FileContent noContent() {
        return new FileContent(null, null);
    }

    public MediaType getContentType() {
        if (commonFile == null) {
            return null;
        }
        return commonFile.rawFile().mediaType();
    }

    public long getContentLength() {
        if (commonFile == null) {
            return 0;
        }
        return commonFile.rawFile().size();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof FileContent(CommonFile file, byte[] content1))) {
            return false;
        }
        return Objects.equals(commonFile, file)
            && Arrays.equals(content, content1);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(commonFile);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }

    @Override
    public String toString() {
        return "FileContent[commonFile=%s, content=%s]".formatted(
            commonFile,
            Arrays.toString(content)
        );
    }
}
