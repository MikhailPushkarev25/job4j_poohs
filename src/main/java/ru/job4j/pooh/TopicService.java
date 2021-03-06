package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> map = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String http = req.httpRequestType();
        if ("POST".equals(http)) {
            return postProcess(req);
        }

        if ("GET".equals(http)) {
            return getProcess(req);
        }
        return new Resp(null, "204");
    }

    private Resp postProcess(Req req) {
        String topic = req.getSourceName();
        if (map.putIfAbsent(topic, new ConcurrentHashMap<>()) != null) {
            for (ConcurrentLinkedQueue<String> queue : map.get(topic).values()) {
                queue.offer(req.getParam());
            }
            return new Resp("", "200");
        }
        return new Resp("", "204");
    }

    private Resp getProcess(Req req) {
        String topic = req.getSourceName();
        String param = req.getParam();
        if (map.get(topic) != null) {
            ConcurrentLinkedQueue<String> queue = map.get(topic).get(param);
            if (queue != null) {
                return new Resp(queue.poll(), "200");
            } else {
                map.get(topic).putIfAbsent(param, new ConcurrentLinkedQueue<>());
                return new Resp("", "200");
            }
        }
        map.putIfAbsent(topic, new ConcurrentHashMap<>());
        map.get(topic).putIfAbsent(param, new ConcurrentLinkedQueue<>());
        return new Resp("", "200");
    }
}
