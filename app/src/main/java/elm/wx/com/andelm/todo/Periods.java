package elm.wx.com.andelm.todo;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangwei on 2018/12/18.
 */
public class Periods {

    /**
     * 格式化时间段
     *
     * @param timeSpanInMills 时间段
     * @param inFormat        格式化格式， 支持 d（天）,h （小时）,m （分钟）,s（秒） 标签
     */
    public static String formatPeriod(@IntRange(from = 0) long timeSpanInMills, @NonNull String inFormat) {
        StringBuilder s = new StringBuilder(inFormat);
        int count;

        int len = inFormat.length();

        boolean containsDays = inFormat.indexOf('d') != -1;
        boolean containsHour = inFormat.indexOf('h') != -1;
        boolean containsMinute = inFormat.indexOf('m') != -1;

        for (int i = 0; i < len; i += count) {
            count = 1;
            int c = s.charAt(i);

            while ((i + count < len) && (s.charAt(i + count) == c)) {
                count++;
            }

            String replacement;
            switch (c) {
                case 'd':
                    replacement = zeroPad(getDays(timeSpanInMills), count);
                    break;
                case 'h':
                    replacement = zeroPad(getHours(timeSpanInMills, containsDays), count);
                    break;
                case 'm':
                    replacement = zeroPad(getMinute(timeSpanInMills, containsHour), count);
                    break;
                case 's':
                    replacement = zeroPad(getSeconds(timeSpanInMills, containsMinute), count);
                    break;
                default:
                    replacement = null;
                    break;
            }

            if (replacement != null) {
                s.replace(i, i + count, replacement);
                count = replacement.length(); // CARE: count is used in the for loop above
                len = s.length();
            }
        }

        return s.toString();
    }

    private static int getDays(long timeSpanInMills) {
        return ((int) TimeUnit.DAYS.convert(timeSpanInMills, TimeUnit.MILLISECONDS));
    }

    private static int getHours(long timeSpanInMills, boolean excludeDays) {

        if (excludeDays) {
            final long days = TimeUnit.DAYS.convert(timeSpanInMills, TimeUnit.MILLISECONDS);
            return ((int) TimeUnit.HOURS.convert(timeSpanInMills - TimeUnit.DAYS.toMillis(days), TimeUnit.MILLISECONDS));
        } else {
            return ((int) TimeUnit.HOURS.convert(timeSpanInMills, TimeUnit.MILLISECONDS));
        }
    }

    private static int getMinute(long timeSpanInMills, boolean excludeHours) {

        if (excludeHours) {
            final long hours = TimeUnit.HOURS.convert(timeSpanInMills, TimeUnit.MILLISECONDS);
            return ((int) TimeUnit.MINUTES.convert(timeSpanInMills - TimeUnit.HOURS.toMillis(hours), TimeUnit.MILLISECONDS));
        } else {
            return ((int) TimeUnit.MINUTES.convert(timeSpanInMills, TimeUnit.MILLISECONDS));
        }
    }

    private static int getSeconds(long timeSpanInMills, boolean excludeMinutes) {

        if (excludeMinutes) {
            final long minutes = TimeUnit.MINUTES.convert(timeSpanInMills, TimeUnit.MILLISECONDS);
            return ((int) TimeUnit.SECONDS.convert(timeSpanInMills - TimeUnit.MINUTES.toMillis(minutes), TimeUnit.MILLISECONDS));
        } else {
            return ((int) TimeUnit.SECONDS.convert(timeSpanInMills, TimeUnit.MILLISECONDS));
        }

    }

    private static String zeroPad(int inValue, int inMinDigits) {
        return String.format(Locale.getDefault(), "%0" + inMinDigits + "d", inValue);
    }
}
