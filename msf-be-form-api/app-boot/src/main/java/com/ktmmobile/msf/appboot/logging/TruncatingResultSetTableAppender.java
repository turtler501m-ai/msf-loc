package com.ktmmobile.msf.appboot.logging;

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import org.slf4j.LoggerFactory;

public class TruncatingResultSetTableAppender extends ConsoleAppender<ILoggingEvent> {

    private static final int MAX_DATA_ROWS = 200;

    @Override
    protected void subAppend(ILoggingEvent event) {
        if (event == null) {
            return;
        }

        String message = event.getFormattedMessage();
        if (message == null) {
            return;
        }

        LoggingEvent copy = new LoggingEvent();
        copy.setLoggerContext((LoggerContext) LoggerFactory.getILoggerFactory());
        copy.setLoggerName(event.getLoggerName());
        copy.setLevel(event.getLevel());
        copy.setMessage(truncateMessage(message));
        copy.setThreadName(event.getThreadName());
        copy.setTimeStamp(event.getTimeStamp());
        copy.setArgumentArray(null);
        copy.setLoggerContextRemoteView(event.getLoggerContextVO());
        copy.setMDCPropertyMap(event.getMDCPropertyMap());
        super.subAppend(copy);
    }

    private String truncateMessage(String message) {
        if (message == null) {
            return message;
        }

        String[] lines = message.split("\\R");
        List<String> result = new ArrayList<>(lines.length);
        int dataRows = 0;
        int pipeLinesSeen = 0;

        for (String line : lines) {
            if (line.startsWith("|")) {
                pipeLinesSeen++;
                if (pipeLinesSeen > 2 && dataRows >= MAX_DATA_ROWS) {
                    result.add("... truncated after " + MAX_DATA_ROWS + " rows ...");
                    break;
                }
                if (pipeLinesSeen > 2 && isDataRow(line)) {
                    dataRows++;
                }
                result.add(line);
                continue;
            }

            result.add(line);
        }

        return String.join(System.lineSeparator(), result);
    }

    private boolean isDataRow(String line) {
        int cellCount = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '|') {
                cellCount++;
            }
        }
        return cellCount >= 2;
    }
}
