package com.sunkz.common.util;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class DailyUtil {

    private static final List<String> NUMS = Arrays.asList("一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十");
    private static boolean IS_TODAY_REST_DAY = false;
    public static LocalDateTime BOOT_TIME = LocalDateTime.now();

    public static String parseDaily(MultipartFile file) throws IOException {
        ExcelReader reader;
        try {
            reader = cn.hutool.poi.excel.ExcelUtil.getReader(file.getInputStream(), "日报");
        } catch (Exception e) {
            reader = ExcelUtil.getReader(file.getInputStream());
        }
        String title = getTitle(reader.read(0, 0).get(0));
        List<List<String>> rows = removeRepeat(reader.read(1));
        IS_TODAY_REST_DAY = isRestDay(rows.get(1));
        List<String> users = getUsers(transpose(rows));
        return title + "\n" + String.join("\n", users);
    }

    private static boolean isRestDay(List<String> rows) {
        int i = 0;
        for (String row : rows) {
            if (row.isEmpty() || "休息".equals(row)) {
                i++;
            }
        }
        return i >= 5;
    }

    private static List<List<String>> removeRepeat(List<List<Object>> rows) {
        List<List<String>> list = rows.stream().map(innerList -> innerList.stream().map(Object::toString).map(String::trim).collect(Collectors.toList())).collect(Collectors.toList());
        if (list.size() <= 1) {
            return list;
        }
        int pos = -1;
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < list.get(0).size(); i++) {
            String s = list.get(0).get(i);
            if (set.contains(s)) {
                pos = i;
                break;
            }
            set.add(s);
        }
        if (pos == -1) {
            return list;
        }
        List<List<String>> result = new ArrayList<>();
        for (List<String> strings : list) {
            result.add(strings.subList(0, pos));
        }
        return result;
    }

    private static Integer getSplit(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("明日行程")) {
                return i;
            }
        }
        return 0;
    }

    private static List<String> getUsers(List<List<String>> transpose) {
        Integer split = getSplit(transpose.get(0));
        List<String> users = new ArrayList<>();
        for (int i = 1; i < transpose.size(); i++) {
            List<String> items = transpose.get(i);
            StringBuilder user = new StringBuilder(NUMS.get(i - 1) + "．" + items.get(0) + "\n");

            boolean hasTodayItem = false;
            user.append("今日总结：\n");
            int m = 1;
            for (int j = 1; j < split; j++) {
                if (!items.get(j).isEmpty()) {
                    hasTodayItem = true;
                    user.append(m++).append(".").append(items.get(j)).append("\n");
                }
            }

            if (!hasTodayItem) {
                if (items.get(0).equals("吴同")) {
                    if (IS_TODAY_REST_DAY) {
                        user.append("1.休息\n");
                    } else {
                        user.append("1.应聘候选人面试\n");
                        user.append("2.邀约筛选候选人\n");
                    }
                } else {
                    user.append("1.休息\n");
                }
            }

            String splitItem = String.valueOf(items.get(split));
            String[] splitItemSplits;
            if (splitItem.contains("、")) {
                splitItemSplits = splitItem.split("、");
            } else if (splitItem.contains(",")) {
                splitItemSplits = splitItem.split(",");
            } else if (splitItem.contains("，")) {
                splitItemSplits = splitItem.split("，");
            } else if (splitItem.contains(" ")) {
                splitItemSplits = splitItem.split(" ");
            } else {
                splitItemSplits = new String[]{splitItem};
            }

            user.append("明日计划：\n");
            int insertItemCount = 0;
            boolean isWTDefinitelyRestTomorrow = false;
            if (splitItemSplits.length == 1 && splitItemSplits[0].equals("休息")) {
                user.append("1.休息\n");
                insertItemCount = 1;
                if (items.get(0).equals("吴同")) {
                    isWTDefinitelyRestTomorrow = true;
                }
            }

            if (splitItemSplits.length == 1 && !splitItemSplits[0].equals("休息") && !splitItemSplits[0].equals("西三旗") && !splitItemSplits[0].isEmpty()) {
                user.append("1.").append(splitItemSplits[0]).append("\n");
                insertItemCount = 1;
            }

            boolean isOnlyRestTomorrowAfternoon = false;
            if (splitItemSplits.length == 2) {
                if (splitItemSplits[0].equals("休息") && splitItemSplits[1].equals("休息")) {
                    user.append("1.休息\n");
                    insertItemCount = 1;
                }
                if (splitItemSplits[0].equals("休息") && !splitItemSplits[1].equals("休息")) {
                    user.append("1.上午休息\n");
                    user.append("2.下午").append(splitItemSplits[1]).append("\n");
                    insertItemCount = 2;
                }
                if (splitItemSplits[1].equals("休息") && !splitItemSplits[0].equals("休息")) {
                    user.append("1.上午").append(splitItemSplits[0]).append("\n");
                    isOnlyRestTomorrowAfternoon = true;
                    insertItemCount = 1;
                }
                if (!splitItemSplits[0].equals("休息") && !splitItemSplits[1].equals("休息")) {
                    user.append("1.上午").append(splitItemSplits[0]).append("\n");
                    user.append("2.下午").append(splitItemSplits[1]).append("\n");
                    insertItemCount = 2;
                }
            }
            boolean hasTomorrowItem = false;
            int n = 1;
            for (int j = split + 1; j < items.size(); j++) {
                if (!items.get(j).isEmpty()) {
                    user.append((n++) + insertItemCount).append(".").append(items.get(j)).append("\n");
                    hasTomorrowItem = true;
                }
            }

            if (items.get(0).equals("吴同") && !isWTDefinitelyRestTomorrow && !hasTomorrowItem) {
                user.append(1 + insertItemCount).append(".应聘候选人面试\n");
                user.append(2 + insertItemCount).append(".邀约筛选候选人\n");
            }

            if (isOnlyRestTomorrowAfternoon) {
                user.append(n + insertItemCount).append(".下午休息\n");
            }

            users.add(user.toString());
        }
        return users;
    }

    private static String getTitle(List<Object> row) {
        String date = null;
        try {
            for (Object o : row) {
                String s = String.valueOf(o);
                if (s.isEmpty()) {
                    continue;
                }
                if (s.contains("(")) {
                    s = s.substring(0, s.indexOf('('));
                } else if (s.contains("（")) {
                    s = s.substring(0, s.indexOf('（'));
                }
                String[] split;
                if (s.contains("/")) {
                    split = s.split("/");
                } else if (s.contains("-")) {
                    split = s.split("-");
                } else if (s.contains("．")) {
                    split = s.split("．");
                } else if (s.contains(".")) {
                    split = s.split("\\.");
                } else {
                    split = new String[]{String.valueOf(LocalDate.now().getMonthValue()), String.valueOf(LocalDate.now().getDayOfMonth())};
                }
                List<String> monthDay = new ArrayList<>();
                if (split.length > 1) {
                    for (String s1 : split) {
                        if (Integer.parseInt(s1) > 31) {
                            continue;
                        }
                        monthDay.add(s1);
                    }
                }
                date = monthDay.get(0) + "." + monthDay.get(1);
            }

        } catch (Exception e) {
            date = LocalDate.now().getMonthValue() + "." + LocalDate.now().getDayOfMonth();
        }
        return "成品综合＋财务" + date + "日报";
    }

    private static List<List<String>> transpose(List<List<String>> list) {
        List<List<String>> result = new ArrayList<>();
        int cols = list.get(0).size();
        for (int i = 0; i < cols; i++) {
            List<String> row = new ArrayList<>();
            for (List<String> strings : list) {
                row.add(strings.get(i));
            }
            result.add(row);
        }
        result.removeIf(array -> array.get(0).isEmpty());
        return result;
    }

}
