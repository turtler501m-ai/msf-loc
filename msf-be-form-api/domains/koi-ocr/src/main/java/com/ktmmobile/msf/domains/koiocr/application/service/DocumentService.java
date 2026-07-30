package com.ktmmobile.msf.domains.koiocr.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.file.application.dto.FileBase64UploadRequest;
import com.ktmmobile.msf.commons.file.application.dto.FileUploadRequest;
import com.ktmmobile.msf.commons.file.application.dto.FileVariantOptions;
import com.ktmmobile.msf.commons.file.application.port.in.CommonFileService;
import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;
import com.ktmmobile.msf.commons.file.domain.dto.FileVariantResult;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.CaptureDocumentRequest;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.CaptureDocumentResponse;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.IdDocumentRequest;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.IdDocumentResponse;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.OcrApiResponse;
import com.ktmmobile.msf.domains.koiocr.application.port.in.DocumentReader;
import com.ktmmobile.msf.domains.koiocr.application.port.out.DocumentClient;
import com.ktmmobile.msf.domains.koiocr.domain.code.DocumentType;
import com.ktmmobile.msf.domains.koiocr.support.util.DocumentUtils;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressCondition;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressResponse;
import com.ktmmobile.msf.domains.shared.common.address.application.port.in.AddressReader;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DocumentService implements DocumentReader {

    private final DocumentClient documentClient;
    private final CommonFileService commonFileService;
    private final AddressReader addressReader;

    @Override
    public IdDocumentResponse scanIdDocument(IdDocumentRequest request) {

        OcrApiResponse response = documentClient.scanIdDocument(request);

        CommonFile file = null;

        if (Boolean.TRUE.equals(request.returnFile())) {
            file = commonFileService.writeFile(
                new FileBase64UploadRequest(
                    response.maskImage(),
                    "base64_temp.jpeg",
                    request.ocrType().getFileCategory(),
                    FileVariantOptions.empty()
                )
            );
        }

        IdDocumentResponse documentResponse = DocumentUtils.toIdDocumentResponse(request, response, file);

        if (!supportsAddress(request.ocrType())) {
            return documentResponse;
        }

        return documentResponse.toBuilder()
            .zipNo(findZipNo(documentResponse.address()))
            .build();
    }

    private boolean supportsAddress(DocumentType documentType) {
        return switch (documentType) {
            case RESIDENT_REGISTRATION_CARD, DRIVER_LICENSE, NATIONAL_MERIT_CARD -> true;
            default -> false;
        };
    }

    private String findZipNo(String address) {

        if (address == null || address.isBlank()) {
            return "";
        }

        SearchAddressResponse response = addressReader.getListAddress(
            new SearchAddressCondition(1, 1, address)
        );

        if (response == null || response.list() == null) {
            return "";
        }

        return response.list().stream()
            .findFirst()
            .map(SearchAddressResponse.JusoResponse::zipNo)
            .orElse("");
    }

    public CaptureDocumentResponse captureDocument(CaptureDocumentRequest request) {
        FileUploadRequest uploadRequest = request.toFileUploadRequest();

        FileVariantResult result = commonFileService.writeFile(
            uploadRequest.toFileRequest(),
            uploadRequest.toVariantOptions()
        );

        CommonFile tifFile = result.variants().get("tif");

        return CaptureDocumentResponse.of(tifFile);
    }
}
