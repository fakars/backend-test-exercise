package rest.api;

public enum Endpoints {

    SEARCH("/search"),
    ITEMS("/items/");

    private final String value;

    private Endpoints(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
