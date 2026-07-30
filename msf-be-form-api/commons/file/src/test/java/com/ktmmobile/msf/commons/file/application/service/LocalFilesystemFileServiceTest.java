package com.ktmmobile.msf.commons.file.application.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.ktmmobile.msf.commons.file.support.properties.LocalFilesystemProperties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("로컬 파일시스템 파일 서비스")
class LocalFilesystemFileServiceTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("지정한 디렉토리 하위 파일 목록을 조회한다")
    void listFilesReturnsFilesUnderDirectory() throws Exception {
        Path docs = tempDir.resolve("docs");
        Files.createDirectories(docs.resolve("nested"));
        Files.writeString(docs.resolve("a.txt"), "a");
        Files.writeString(docs.resolve("nested/b.txt"), "b");
        Files.writeString(tempDir.resolve("outside.txt"), "outside");

        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        assertThat(service.listFiles("docs"))
            .extracting("filePath")
            .containsExactlyInAnyOrder("docs/a.txt", "docs/nested/b.txt");
    }

    @Test
    @DisplayName("지정한 최대 개수만 파일 목록을 조회한다")
    void listFilesReturnsFilesWithinLimit() throws Exception {
        Path docs = tempDir.resolve("docs");
        Files.createDirectories(docs);
        Files.writeString(docs.resolve("a.txt"), "a");
        Files.writeString(docs.resolve("b.txt"), "b");
        Files.writeString(docs.resolve("c.txt"), "c");

        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        assertThat(service.listFiles("docs", 2)).hasSize(2);
    }

    @Test
    @DisplayName("없는 디렉토리는 빈 목록을 반환한다")
    void listFilesReturnsEmptyListWhenDirectoryDoesNotExist() {
        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        assertThat(service.listFiles("missing")).isEmpty();
    }

    @Test
    @DisplayName("절대 경로 형태 요청도 기본 경로 하위로 조회한다")
    void listFilesResolvesAbsolutePathUnderBasePath() throws Exception {
        Path docs = tempDir.resolve("docs");
        Files.createDirectories(docs);
        Files.writeString(docs.resolve("a.txt"), "a");

        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        assertThat(service.listFiles("/docs"))
            .extracting("filePath")
            .containsExactly("docs/a.txt");
    }

    @Test
    @DisplayName("기본 경로 밖으로 벗어나는 경로는 허용하지 않는다")
    void listFilesThrowsExceptionWhenPathEscapesBasePath() {
        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        assertThatThrownBy(() -> service.listFiles("../outside"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("파일 경로는 기본 경로 하위여야 합니다.");
    }

    @Test
    @DisplayName("파일 목록 최대 개수는 1 이상이어야 한다")
    void listFilesThrowsExceptionWhenLimitIsLessThanOne() {
        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        assertThatThrownBy(() -> service.listFiles("docs", 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("limit은 1 이상이어야 합니다.");
    }

    @Test
    @DisplayName("파일 목록 최대 개수는 1000 이하여야 한다")
    void listFilesThrowsExceptionWhenLimitIsGreaterThanDefaultLimit() {
        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        assertThatThrownBy(() -> service.listFiles("docs", 1_001))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("limit은 1000 이하여야 합니다.");
    }

    @Test
    @DisplayName("기준일 이전에 수정된 파일을 삭제한다")
    void removeFilesModifiedBeforeDeletesOlderFiles() throws Exception {
        Path docs = tempDir.resolve("docs");
        Files.createDirectories(docs.resolve("nested"));
        Path oldFile = docs.resolve("old.txt");
        Path oldNestedFile = docs.resolve("nested/old-nested.txt");
        Path baseDateFile = docs.resolve("base.txt");
        Path newFile = docs.resolve("new.txt");
        Files.writeString(oldFile, "old");
        Files.writeString(oldNestedFile, "old-nested");
        Files.writeString(baseDateFile, "base");
        Files.writeString(newFile, "new");

        Instant baseDateTime = Instant.parse("2026-06-19T00:00:00Z");
        Files.setLastModifiedTime(oldFile, FileTime.from(Instant.parse("2026-06-18T23:59:59Z")));
        Files.setLastModifiedTime(oldNestedFile, FileTime.from(Instant.parse("2026-06-18T00:00:00Z")));
        Files.setLastModifiedTime(baseDateFile, FileTime.from(baseDateTime));
        Files.setLastModifiedTime(newFile, FileTime.from(Instant.parse("2026-06-19T00:00:01Z")));

        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        int deletedCount = service.removeFilesModifiedBefore("docs", baseDateTime);

        assertThat(deletedCount).isEqualTo(2);
        assertThat(Files.exists(oldFile)).isFalse();
        assertThat(Files.exists(oldNestedFile)).isFalse();
        assertThat(Files.exists(baseDateFile)).isTrue();
        assertThat(Files.exists(newFile)).isTrue();
    }

    @Test
    @DisplayName("지정한 최대 개수만 기준일 이전 파일을 삭제한다")
    void removeFilesModifiedBeforeDeletesOlderFilesWithinLimit() throws Exception {
        Path docs = tempDir.resolve("docs");
        Files.createDirectories(docs);
        Path oldFile1 = docs.resolve("old-1.txt");
        Path oldFile2 = docs.resolve("old-2.txt");
        Path oldFile3 = docs.resolve("old-3.txt");
        Files.writeString(oldFile1, "old-1");
        Files.writeString(oldFile2, "old-2");
        Files.writeString(oldFile3, "old-3");

        Instant baseDateTime = Instant.parse("2026-06-19T00:00:00Z");
        Files.setLastModifiedTime(oldFile1, FileTime.from(Instant.parse("2026-06-18T00:00:00Z")));
        Files.setLastModifiedTime(oldFile2, FileTime.from(Instant.parse("2026-06-18T00:00:01Z")));
        Files.setLastModifiedTime(oldFile3, FileTime.from(Instant.parse("2026-06-18T00:00:02Z")));

        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        int deletedCount = service.removeFilesModifiedBefore("docs", baseDateTime, 2);

        assertThat(deletedCount).isEqualTo(2);
        assertThat(service.listFiles("docs")).hasSize(1);
    }

    @Test
    @DisplayName("기준일 이전 파일을 일 단위로 삭제한다")
    void removeFilesModifiedBeforeDeletesOlderFilesByBaseDate() throws Exception {
        Path docs = tempDir.resolve("docs");
        Files.createDirectories(docs);
        Path oldFile = docs.resolve("old.txt");
        Path baseDateFile = docs.resolve("base-date.txt");
        Files.writeString(oldFile, "old");
        Files.writeString(baseDateFile, "base-date");

        LocalDate baseDate = LocalDate.now().minusMonths(1);
        Instant baseDateTime = baseDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Files.setLastModifiedTime(oldFile, FileTime.from(baseDateTime.minusSeconds(1)));
        Files.setLastModifiedTime(baseDateFile, FileTime.from(baseDateTime));

        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        int deletedCount = service.removeFilesModifiedBefore("docs", baseDate, 10);

        assertThat(deletedCount).isEqualTo(1);
        assertThat(Files.exists(oldFile)).isFalse();
        assertThat(Files.exists(baseDateFile)).isTrue();
    }

    @Test
    @DisplayName("보관기간과 일 단위 기준 이전 파일을 삭제한다")
    void removeFilesModifiedBeforeDeletesFilesByRetentionDurationAndDaysUnit() throws Exception {
        Path docs = tempDir.resolve("docs");
        Files.createDirectories(docs);
        Path oldFile = docs.resolve("old.txt");
        Path retainedFile = docs.resolve("retained.txt");
        Files.writeString(oldFile, "old");
        Files.writeString(retainedFile, "retained");

        Files.setLastModifiedTime(oldFile, FileTime.from(Instant.now().minus(Duration.ofDays(31))));
        Files.setLastModifiedTime(retainedFile, FileTime.from(Instant.now()));

        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        int deletedCount = service.removeFilesModifiedBefore("docs", Duration.ofDays(30), ChronoUnit.DAYS, 10);

        assertThat(deletedCount).isEqualTo(1);
        assertThat(Files.exists(oldFile)).isFalse();
        assertThat(Files.exists(retainedFile)).isTrue();
    }

    @Test
    @DisplayName("보관기간과 시간 단위 기준 이전 파일을 삭제한다")
    void removeFilesModifiedBeforeDeletesFilesByRetentionDurationAndTruncateUnit() throws Exception {
        Path docs = tempDir.resolve("docs");
        Files.createDirectories(docs);
        Path oldFile = docs.resolve("old.txt");
        Path retainedFile = docs.resolve("retained.txt");
        Files.writeString(oldFile, "old");
        Files.writeString(retainedFile, "retained");

        Files.setLastModifiedTime(oldFile, FileTime.from(Instant.now().minus(Duration.ofDays(31))));
        Files.setLastModifiedTime(retainedFile, FileTime.from(Instant.now()));

        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        int deletedCount = service.removeFilesModifiedBefore("docs", Duration.ofHours(1), ChronoUnit.HOURS, 10);

        assertThat(deletedCount).isEqualTo(1);
        assertThat(Files.exists(oldFile)).isFalse();
        assertThat(Files.exists(retainedFile)).isTrue();
    }

    @Test
    @DisplayName("보관기간 조정 단위는 일 또는 시간 기반이어야 한다")
    void removeFilesModifiedBeforeThrowsExceptionWhenTruncateUnitIsNotDayOrTimeBased() {
        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        assertThatThrownBy(() -> service.removeFilesModifiedBefore("docs", Duration.ofDays(30), ChronoUnit.MONTHS, 10))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("truncateUnit은 일 또는 시간 기반 단위여야 합니다.");
    }

    @Test
    @DisplayName("없는 디렉토리는 삭제하지 않는다")
    void removeFilesModifiedBeforeReturnsZeroWhenDirectoryDoesNotExist() {
        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        int deletedCount = service.removeFilesModifiedBefore("missing", Instant.parse("2026-06-19T00:00:00Z"));

        assertThat(deletedCount).isZero();
    }

    @Test
    @DisplayName("기준일 이전 파일 삭제 최대 개수는 1 이상이어야 한다")
    void removeFilesModifiedBeforeThrowsExceptionWhenLimitIsLessThanOne() {
        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        assertThatThrownBy(() -> service.removeFilesModifiedBefore("docs", Instant.parse("2026-06-19T00:00:00Z"), 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("limit은 1 이상이어야 합니다.");
    }

    @Test
    @DisplayName("기준일 이전 파일 삭제 최대 개수는 1000 이하여야 한다")
    void removeFilesModifiedBeforeThrowsExceptionWhenLimitIsGreaterThanDefaultLimit() {
        LocalFilesystemFileService service = new LocalFilesystemFileService(
            null,
            new LocalFilesystemProperties(tempDir.toString()),
            null
        );

        assertThatThrownBy(() -> service.removeFilesModifiedBefore("docs", Instant.parse("2026-06-19T00:00:00Z"), 1_001))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("limit은 1000 이하여야 합니다.");
    }
}
