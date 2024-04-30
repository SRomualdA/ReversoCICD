package com.reverso.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class FormatterLog extends Formatter {

    /**
     * Formats a log record.
     *
     * @param record the log record to format
     * @return a string containing the formatted log record
     */
    public String format(LogRecord record) {
        // TODO Auto-generated method stub
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        StringBuilder result = new StringBuilder();

        result.append(format.format(new Date()));
        result.append(" Level : " );
        result.append(record.getLevel());

        result.append(" / Message : ");
        result.append(record.getMessage());

        result.append(" / Classe : ");
        result.append(record.getSourceClassName());

        result.append(" / Méthode : ");
        result.append(record.getSourceMethodName());

        result.append("\n");
        return result.toString();
    }
}
