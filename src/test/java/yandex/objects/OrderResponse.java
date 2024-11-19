package yandex.objects;

import lombok.Getter;
import lombok.Setter;

public class OrderResponse {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private long petId;

    @Getter
    @Setter
    private int quantity;

    @Getter
    @Setter
    private String shipDate;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private boolean complete;

    public OrderResponse() {}

    public OrderResponse(long id, long petId, int quantity,
                         String shipDate, String status, boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }
}
