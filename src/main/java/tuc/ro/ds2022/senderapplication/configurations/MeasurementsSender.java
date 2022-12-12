package tuc.ro.ds2022.senderapplication.configurations;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tuc.ro.ds2022.senderapplication.components.MeasurementsCsvReader;
import tuc.ro.ds2022.senderapplication.dtos.MeasurementDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class MeasurementsSender {

    private final AmqpTemplate rabbitTemplate;
    private final MeasurementsCsvReader reader;

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingKey;

    @Autowired
    public MeasurementsSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.reader = new MeasurementsCsvReader("src/main/resources/sensor.csv"); // il facem final si ii dam direct
        // ca parametru numele fisierului nostru.
    }

    // fixedDelay e delay-ul intre finalul unei executii a metodei send si inceputul urmatoarei executii.
    // initialDelay e delay-ul de la pornirea aplicatiei pana la prima executie a metodei send.

    //@Scheduled(fixedDelay = 600000, initialDelay = 1000) // il setam sa trimita datele odata la 10 minute.
    @Scheduled(fixedDelay = 10000, initialDelay = 1000) // il setam sa trimita datele odata la 10 secunde -> pt testare.
    public void send(){
        double readValue = reader.readMeasurementTupleFromFile();
        if(readValue != -1){
            // trimitem tuplele pentru un device ce apartine user-ului ana.muresan@yahoo.com din db -> pentru a stii
            // pentru cine sa vizualizam notificarile.
            MeasurementDTO payload = new MeasurementDTO(LocalDateTime.now(), UUID.fromString("1552a10b-7eee-4e9d-b019-0b436b1470d5"), readValue);
            rabbitTemplate.convertAndSend(exchange, routingKey, payload);
            System.err.println("S-a transmis mesajul cu valoarea: " + payload.toString());
        }
    }

}
