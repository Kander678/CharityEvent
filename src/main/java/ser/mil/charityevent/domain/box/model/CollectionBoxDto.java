package ser.mil.charityevent.domain.box.model;

public class CollectionBoxDto {
    private final String id;
    private final boolean isEmpty;
    private final boolean isAssigned;

    public CollectionBoxDto(String id, boolean isEmpty, boolean isAssigned) {
        this.id = id;
        this.isEmpty = isEmpty;
        this.isAssigned = isAssigned;
    }

    public String getId() {
        return id;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isAssigned() {
        return isAssigned;
    }
}
