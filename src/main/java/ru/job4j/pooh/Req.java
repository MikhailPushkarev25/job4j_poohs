package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] array = content.split("/");
        String httpRequestType = array[0].replaceAll(" ", "");
        String poohMode = array[1];
        String sourceName = array[2].split(" ")[0];
        String param = getParam(content);
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public static String getParam(String content) {
        String[] array = content.split("/");
        if (content.startsWith("GET") && array[1].equals("topic")) {
            return array[3].split(" ")[0];
        }

        if (content.startsWith("GET") && array[1].equals("queue")) {
            return "";
        }
        return array[6].split(System.lineSeparator())[2];
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
