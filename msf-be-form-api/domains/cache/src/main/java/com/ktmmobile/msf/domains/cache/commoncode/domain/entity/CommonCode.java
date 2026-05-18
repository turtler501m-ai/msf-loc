package com.ktmmobile.msf.domains.cache.commoncode.domain.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.data.type.UseYn;

@Getter
@NoArgsConstructor
public class CommonCode {

    private String groupId;
    private String code;
    private String title;
    private UseYn useYn = UseYn.getInvalidValue();
    private Detail detail;


    @Getter
    @NoArgsConstructor
    public static class Detail {

        private static final DateTimeFormatter VALID_PERIOD_DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;

        private String abbrName;
        private String description;
        private String upperGroupCode;
        private String filePathName;
        private String imageName;
        private int sortOrder;
        private String etcValue1;
        private String etcValue2;
        private String etcValue3;
        private String etcValue4;
        private String etcValue5;
        private String etcValue6;
        private String startDate;
        private String endDate;

        public Optional<LocalDate> getStartLocalDate() {
            return toLocalDate(startDate);
        }

        public Optional<LocalDate> getEndLocalDate() {
            return toLocalDate(endDate);
        }

        public boolean isWithinValidPeriod(LocalDate currentDate) {
            return isStarted(currentDate) && isNotEnded(currentDate);
        }

        private boolean isStarted(LocalDate currentDate) {
            if (!StringUtils.hasText(startDate)) {
                return true;
            }
            return getStartLocalDate()
                .map(date -> !currentDate.isBefore(date))
                .orElse(false);
        }

        private boolean isNotEnded(LocalDate currentDate) {
            if (!StringUtils.hasText(endDate)) {
                return true;
            }
            return getEndLocalDate()
                .map(date -> !currentDate.isAfter(date))
                .orElse(false);
        }

        private Optional<LocalDate> toLocalDate(String value) {
            if (!StringUtils.hasText(value)) {
                return Optional.empty();
            }

            String digits = value.replaceAll("\\D", "");
            if (digits.length() < 8) {
                return Optional.empty();
            }

            try {
                return Optional.of(LocalDate.parse(digits.substring(0, 8), VALID_PERIOD_DATE_FORMATTER));
            } catch (DateTimeParseException ex) {
                return Optional.empty();
            }
        }
    }

    public boolean isUsed() {
        return this.useYn.isUsed();
    }

    public boolean isUnused() {
        return this.useYn.isUnused();
    }
}
