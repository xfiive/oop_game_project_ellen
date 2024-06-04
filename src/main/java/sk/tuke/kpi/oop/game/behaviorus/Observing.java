package sk.tuke.kpi.oop.game.behaviorus;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.function.Predicate;

public class Observing<T, A extends Actor> implements Behaviour<A> {

    private Topic<T> topic;
    private Predicate<T> predicate;
    private Behaviour<A> delegate;

    public Observing(Topic<T> topic, Predicate<T> predicate, Behaviour<A> delegate) {
        this.topic = topic;
        this.predicate = predicate;
        this.delegate = delegate;
    }

    public Topic<T> getTopic() {
        return topic;
    }

    public Predicate<T> getPredicate() {
        return predicate;
    }

    public Behaviour<A> getDelegate() {
        return delegate;
    }

    @Override
    public void setUp(A actor) {

    }
}
