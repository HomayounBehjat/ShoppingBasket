public enum ShoppingItems {
    Apple("Apple"),
    Orange("Orange");

    private final String value;

    /**
     * @param value
     */
    ShoppingItems(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}