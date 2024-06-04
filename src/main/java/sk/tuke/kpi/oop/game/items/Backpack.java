package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Backpack implements ActorContainer<Collectible> {

    private final String name;
    private final int capacity;
    private int size;
    private List<Collectible> content;

    public Backpack(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.content = new ArrayList<>(capacity);
        this.size = 0;
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        if (this.content == null)
            return null;

        return List.copyOf(this.content);
    }

    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public int getSize() {
        return this.content.size();
    }

    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if (this.content == null)
            throw new IllegalStateException(this.name + " is full");

        if (this.capacity <= this.content.size())
            throw new IllegalStateException(this.name + " is full");

        content.add(actor);
        this.size += 1;
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        if (this.content == null)
            return;
        content.remove(actor);
    }

    @Override
    public @Nullable Collectible peek() {
        if (content == null || content.isEmpty())
            return null;

        return this.content.get(this.size - 1);
    }

    @Override
    public void shift() {
        if (this.content == null)
            return;
        Collections.rotate(content, 1);
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        if (this.content == null)
            return null;
        return content.iterator();
    }
}
