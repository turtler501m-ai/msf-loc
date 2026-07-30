package com.ktmmobile.msf.commons.file.domain.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

// 파일 변형 저장 결과
public record FileVariantResult(
    CommonFile file,
    Map<String, CommonFile> variants
) {

    public FileVariantResult {
        variants = variants == null ? Map.of() : Collections.unmodifiableMap(new LinkedHashMap<>(variants));
    }
}
