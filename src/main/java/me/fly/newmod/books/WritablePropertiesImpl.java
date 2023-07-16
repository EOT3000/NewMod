package me.fly.newmod.books;

public record WritablePropertiesImpl(int pages) implements WritableProperties {
    @Override
    public int getPages() {
        return pages;
    }
}
