package yandex.objects;
import lombok.Data;
import java.util.List;

@Data
public class Pet {
    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    @Data
    public static class Category {
        private long id;
        private String name;
    }

    @Data
    public static class Tag {
        private long id;
        private String name;
    }
}