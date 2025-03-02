package ink.labrador.mmsmanager.support;

public class RegExpSupport {
    public static class NormalName {
        public static final String Reg = "^[a-zA-Z0-9_\\u4E00-\\u9FA5!~#$%^&?`'\"@\\(\\)*._\\-\\|\\/<>,.:;+=\\[\\]\\{\\}\\\\]{1,30}$";
        public static final String Hint = "必须为数字、英文、汉字、下划线或半角特殊符号的组合,1~30位";
    }

    public static class Address {
        public static final String Reg = "^[a-zA-Z0-9_\\u4E00-\\u9FA5!~#$%&'\"@\\(\\)*._\\-\\|\\/<>,.:\\[\\]\\\\]{1,60}$";
        public static final String Hint = "必须为数字、英文、汉字、下划线或半角特殊符号的组合,1~60位";
    }


    public static class Password {
        public static final String Reg = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![!~#$%^&'\"@*._`]+$\\-\\|\\/<>,.:;+=\\[\\]\\{\\}\\\\)[\\da-zA-Z!#~$%^&'\"@*._`\\-\\|\\/<>,.:;+=\\[\\]\\{\\}\\\\]{6,20}$";
        public static final String Hint = "至少6位至多20位,不能包含中文、空格及全角字符,至少包含数字、字母、特殊符号中的两种";
    }

    public static class PhoneNumber {
        public static final String Reg = "^1[3-9]\\d{9}$";
        public static final String Hint = "必须为合法的11位手机号";
    }

    public static class DeviceNumber {
        public static final String Reg = "^[a-zA-Z0-9]{5,20}$";
        public static final String Hint = "必须为数字、字母组成,5~20位";
    }

    public static class YearEndedTimeStr {
        public static final String Reg = "^(?:(?!0000)[0-9]{4})$";
        public static final String Hint = "必须是4位合法的年份";
    }

    public static class MonthEndedTimeStr {
        public static final String Reg = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])))$";
        public static final String Hint = "必须是yyyy-mm格式的日期字符串";
    }

    public static class DayEndedTimeStr {
        public static final String Reg = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
        public static final String Hint = "必须是yyyy-mm-dd格式的日期字符串";
    }

    public static class HourEndedTimeStr {
        public static final String Reg = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\\s([0-1][0-9]|2[0-3])$";
        public static final String Hint = "必须是yyyy-mm-dd hh格式的日期字符串";
    }

    public static class MinuteEndedTimeStr {
        public static final String Reg = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\\s([0-1][0-9]|2[0-3]):([0-5][0-9])$";
        public static final String Hint = "必须是yyyy-mm-dd hh:MM格式的日期字符串";
    }

    public static class SecondEndedTimeStr {
        public static final String Reg = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\\s([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        public static final String Hint = "必须是yyyy-mm-dd hh:MM:ss格式的日期字符串";
    }

    public static class Domain {
        public static final String Reg = "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$";
        public static final String Hint = "域名格式非法";
    }

    public static class IPV4 {
        public static final String Reg = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";
        public static final String Hint = "IPV4格式非法";
    }
}