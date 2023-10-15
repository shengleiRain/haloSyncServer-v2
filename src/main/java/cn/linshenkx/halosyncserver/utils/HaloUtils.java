package cn.linshenkx.halosyncserver.utils;

import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Slf4j
public class HaloUtils {
    private HaloUtils() {
        throw new RuntimeException("should not be init");
    }

    public static String generateUUIDByTitleAndDate(String title, String date, boolean isDraft) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tempDate = date;
        if (!isDraft) {
            tempDate = date.split("\\.")[0].replace("T", " ");
        }
        try {
            long time = dateFormat.parse(tempDate).getTime();
            return MD5.create().digestHex(title + time);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return "";
    }

    public static String toSlug(String input) {
        final Slugify slg = Slugify.builder().transliterator(true).build();
        return slg.slugify(input);
    }

    public static String toMd5(String filePath) {
        if (StringUtils.isBlank(filePath)) return "";
        return MD5.create().digestHex(filePath);
    }
}
