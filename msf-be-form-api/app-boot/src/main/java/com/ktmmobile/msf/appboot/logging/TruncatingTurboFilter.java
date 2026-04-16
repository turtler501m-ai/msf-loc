package com.ktmmobile.msf.appboot.logging;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * jdbc.resultsettable 로그가 길 경우 maxLines 줄로 잘라서 출력하는 PatternLayout.
 * logback-spring.xml ConsoleAppender의 layout으로 등록한다.
 */
public class TruncatingTurboFilter extends PatternLayout {

    private static final String RESULT_SET_LOGGER = "jdbc.resultsettable";

    private int maxLines = 100;

    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        String formatted = super.doLayout(event);

        if (!RESULT_SET_LOGGER.equals(event.getLoggerName())) {
            return formatted;
        }

        String[] lines = formatted.split("\n", -1);
        if (lines.length <= maxLines) {
            return formatted;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxLines; i++) {
            sb.append(lines[i]).append('\n');
        }
        sb.append("... (").append(lines.length - maxLines).append(" lines truncated)\n");
        return sb.toString();
    }
}
