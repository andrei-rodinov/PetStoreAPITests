package yandex.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

    public class PetResponse {

        @Getter
        @Setter
        private long id;

        @Getter
        @Setter
        private Pet.Category category;

        @Getter
        @Setter
        private String name;

        @Getter
        @Setter
        private List<String> photoUrls;

        @Getter
        @Setter
        private List<Pet.Tag> tags;

        @Getter
        @Setter
        private String status;

        @Getter
        @Setter
        private String message;

        public PetResponse() {}

        public PetResponse(long id, Pet.Category category, String name,
                           List<String> photoUrls, List<Pet.Tag> tags, String status) {
            this.id = id;
            this.category = category;
            this.name = name;
            this.photoUrls = photoUrls;
            this.tags = tags;
            this.status = status;
        }

        public PetResponse(String message) {
            this.message = message;
        }
}
