package com.ktmmobile.mcp.rate.guide.service;

import com.google.common.util.concurrent.RateLimiter;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.rate.guide.dao.RateGuideDao;
import com.ktmmobile.mcp.rate.guide.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import static com.ktmmobile.mcp.common.util.StringUtil.*;

@Service
public class RateGuideVersionService {
    private static final Logger logger = LoggerFactory.getLogger(RateGuideVersionService.class);

    @Value("${admin.fileupload.path}")
    private String fileUploadPath;

    private Path nasUploadPath;

    private final RateGuideDao rateGuideDao;

    @PostConstruct
    public void init() throws IOException {
        nasUploadPath = Paths.get(fileUploadPath, "rateAdsvcVer");
        try {
            if (Files.notExists(nasUploadPath)) {
                Files.createDirectories(nasUploadPath);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            Arrays.stream(e.getStackTrace()).forEach(str -> logger.error(str.toString()));
            throw e;
        }
    }

    public RateGuideVersionService(RateGuideDao rateGuideDao) {
        this.rateGuideDao = rateGuideDao;
    }

    public BathHistDto invalidateNotLatestVersions() {
        List<Long> notLatestVersionSeqs = rateGuideDao.getNotLatestVersionSeqs();

        int successCascnt = 0;
        int failCascnt = 0;
        for (Long versionSeq : notLatestVersionSeqs) {
            try {
                rateGuideDao.updateGdncVersionExpired(versionSeq);
                successCascnt++;
            } catch (Exception e) {
                logger.error(e.getMessage());
                Arrays.stream(e.getStackTrace()).forEach(str -> logger.error(str.toString()));
                this.updateGdncVersionFailed(versionSeq, e.getMessage());
                failCascnt++;
            }
        }

        BathHistDto bathHistDto = new BathHistDto();
        bathHistDto.setSuccessCascnt(successCascnt);
        bathHistDto.setFailCascnt(failCascnt);
        return bathHistDto;
    }

    public BathHistDto cleanupExpiredVersions() {
        List<RateAdsvcGdncVersionDto> expiredVersions = rateGuideDao.getExpiredVersions();

        int successCascnt = 0;
        int failCascnt = 0;
        for (RateAdsvcGdncVersionDto expiredVersion : expiredVersions) {
            try {
                this.cleanupExpiredVersion(expiredVersion.getVersion());
                rateGuideDao.updateGdncVersionDeleted(expiredVersion.getSeq());
                successCascnt++;
            } catch (Exception e) {
                logger.error(e.getMessage());
                Arrays.stream(e.getStackTrace()).forEach(str -> logger.error(str.toString()));
                this.updateGdncVersionFailed(expiredVersion.getSeq(), e.getMessage());
                failCascnt++;
            }
        }

        BathHistDto bathHistDto = new BathHistDto();
        bathHistDto.setSuccessCascnt(successCascnt);
        bathHistDto.setFailCascnt(failCascnt);
        return bathHistDto;
    }

    private void cleanupExpiredVersion(String version) throws IOException {
        Path versionPath = nasUploadPath.resolve(version);

        if (!Files.isDirectory(versionPath)) {
            return;
        }

        List<Path> files = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(versionPath)) {
            for (Path path : stream) {
                files.add(path);
            }
        }

        RateLimiter limiter = RateLimiter.create(20);
        for (Path file : files) {
            limiter.acquire();
            try {
                Files.delete(file);
            } catch (NoSuchFileException ignored) {
            }
        }

        if (versionPath.toFile().isDirectory()) {
            versionPath.toFile().delete();
        }
    }

    private void updateGdncVersionFailed(long seq, String msg) {
        RateAdsvcGdncVersionDto versionDto = new RateAdsvcGdncVersionDto();
        versionDto.setSeq(seq);
        versionDto.setMsg(subStrBytes(msg, 500));
        rateGuideDao.updateGdncVersionFailed(versionDto);
    }
}
