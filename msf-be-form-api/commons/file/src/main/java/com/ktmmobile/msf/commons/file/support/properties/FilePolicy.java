package com.ktmmobile.msf.commons.file.support.properties;

import java.util.List;

import org.springframework.util.unit.DataSize;

public interface FilePolicy {

    DataSize maxFileSize();

    List<String> allowedExtensions();

    List<String> allowedMimeTypes();
}
