package yandex.objects;
import lombok.Data;

    @Data
    public class Order {
        private long id;
        private long petId;
        private int quantity;
        private String shipDate;
        private String status;
        private boolean complete;
    }
