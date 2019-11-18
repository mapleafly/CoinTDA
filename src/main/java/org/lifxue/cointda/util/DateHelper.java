/*
 * Copyright 2019 xuelf.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lifxue.cointda.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.util.StringConverter;

/**
 * Date Helper
 *
 * @author xuelf
 */
public class DateHelper {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_FORMATTER
            = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final StringConverter<LocalDate> CONVERTER = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

        @Override
        public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }

        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
    };

    /**
     * 
     * @param date 
     * @return formatted string
     */
    public static String toString(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * 
     * @param date
     * @return the date object or null if it could not be converted
     */
    public static LocalDate fromString(String date) {
        try {
            return DATE_FORMATTER.parse(date, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * valid date
     *
     * @param date
     * @return true if the String is a valid date
     */
    public static boolean validDate(String date) {
        // Try to parse the String.
        return DateHelper.fromString(date) != null;
    }
}
