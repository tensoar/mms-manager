package ink.labrador.mmsmanager.util;

import ink.labrador.mmsmanager.support.RegExpSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class DateTimeUtil {
    static String TS_PATTERN_STR = "yyyy-MM-dd HH:mm:ss";
    public static DateTimeFormatter TS_FORMATTER = DateTimeFormatter.ofPattern(TS_PATTERN_STR);

    public static LocalDateTime fromCommonStr(String value) {
        LocalDateTime dt;
        if (Pattern.matches(RegExpSupport.SecondEndedTimeStr.Reg, value)) {
            dt = fromTsStr(value);
        } else if (Pattern.matches(RegExpSupport.MinuteEndedTimeStr.Reg, value)) {
            dt = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else if (Pattern.matches(RegExpSupport.HourEndedTimeStr.Reg, value)) {
            dt = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));
        } else if (Pattern.matches(RegExpSupport.DayEndedTimeStr.Reg, value)) {
            dt = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        } else if (Pattern.matches(RegExpSupport.MonthEndedTimeStr.Reg, value)) {
            dt = LocalDate.parse(value + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        } else if (Pattern.matches(RegExpSupport.YearEndedTimeStr.Reg, value)) {
            dt = LocalDateTime.of(Integer.parseInt(value), 1, 1, 0, 0, 0);
        } else {
            throw new RuntimeException("Invalid data str: " + value);
        }
        return dt;
    }

    public static LocalDateTime fromTsStr(String ts) {
        return LocalDateTime.parse(ts, TS_FORMATTER);
    }

    public static String normalizeDateStr(String dateStr) {
        if (!StrUtil.hasLength(dateStr)) {
            return dateStr;
        }

        // 日期时间分开处理
        final List<String> dateAndTime = Arrays.stream(dateStr.split(" "))
                .map(String::trim)
                .toList();
        final int size = dateAndTime.size();
        if (size < 1 || size > 2) {
            // 非可被标准处理的格式
            return dateStr;
        }

        final StringBuilder builder = new StringBuilder();

        // 日期部分（"\"、"/"、"."、"年"、"月"都替换为"-"）
        String datePart = dateAndTime.get(0).replaceAll("[/.年月]", "-");
        datePart = StrUtil.removeSuffix(datePart, "日");
        builder.append(datePart);

        // 时间部分
        if (size == 2) {
            builder.append(' ');
            String timePart = dateAndTime.get(1).replaceAll("[时分秒]", ":");
            timePart = StrUtil.removeSuffix(timePart, ":");
            //将ISO8601中的逗号替换为.
            timePart = timePart.replace(',', '.');
            builder.append(timePart);
        }

        return builder.toString();
    }
}
