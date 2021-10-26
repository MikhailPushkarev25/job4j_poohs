package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class QueueServiceTest {

    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        /* Добавляем данные в очередь weather. Режим queue */
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        /* Забираем данные из очереди weather. Режим queue */
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("temperature=18"));
    }

    @Test
    public void whenGetThenQueue() {
        QueueService queue = new QueueService();
        Resp resp = queue.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(resp.status(), is("204"));
    }

    @Test
    public void whenPostThenGetQueueRES() {
        QueueService queueService = new QueueService();
        String one = "temperature=18";
        String two = "temperature=19";
        String three = "temperature=20";
        queueService.process(
                new Req("POST", "queue", "weather", one)
        );
        Resp result1 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        queueService.process(
                new Req("POST", "queue", "weather", two)
        );
        Resp result2 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        queueService.process(
                new Req("POST", "queue", "weather", three)
        );
        Resp result3 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result1.text(), is("temperature=18"));
        assertThat(result2.text(), is("temperature=19"));
        assertThat(result3.text(), is("temperature=20"));
    }

    @Test
    public void whenPostThenGetQueueStatus() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.status(), is("200"));
    }
}