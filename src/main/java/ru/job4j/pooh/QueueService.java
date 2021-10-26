package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String httpName = req.getSourceName();

        if ("POST".equals(req.httpRequestType())) {
            map.putIfAbsent(httpName, new ConcurrentLinkedQueue<>());
            map.get(httpName).offer(req.getParam());
            return new Resp(req.getParam(), "200");
        }

        if ("GET".equals(req.httpRequestType())) {
            ConcurrentLinkedQueue<String> queue = map.get(httpName);
            if (queue != null) {
                return new Resp(queue.poll(), "200");
            }
        }
        return new Resp(null, "204");
    }
}
