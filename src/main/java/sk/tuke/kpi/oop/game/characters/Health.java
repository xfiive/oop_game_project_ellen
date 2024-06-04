package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {

    private int initHealth;
    private int maxHealth;
    private boolean isDead;
    private List<FatigueEffect> effectList = new ArrayList<>();

    public Health(int initHealth, int maxHealth) {
        this.initHealth = initHealth;
        this.maxHealth = maxHealth;
        this.isDead = false;
    }

    public Health(int initHealth) {
        this.initHealth = initHealth;
        this.maxHealth = initHealth;
        this.isDead = false;
    }

    public int getValue() {
        return this.initHealth;
    }

    public void refill(int amount) {
        if (amount < 0 || this.maxHealth <= this.initHealth)
            return;

        this.initHealth += amount;

        if (this.maxHealth < this.initHealth)
            this.initHealth = this.maxHealth;
    }

    public void restore() {
        if (this.maxHealth == this.initHealth)
            return;

        this.initHealth = this.maxHealth;
    }

    public void drain(int amount) {
        if (amount < 0 && this.isDead)
            return;

        this.initHealth -= amount;

        if (this.initHealth < 0)
            this.initHealth = 0;

        if (this.initHealth == 0) {
            this.exhaust();
        }
    }

    void exhaust() {
        if (this.isDead)
            return;
        this.initHealth = 0;
        for (FatigueEffect effect : effectList) {
            effect.apply();
        }
        this.isDead = true;
    }

    public void onFatigued(FatigueEffect effect) {
        if (effect == null)
            return;
        effectList.add(effect);
    }

    public Health getHealth() {
        return this;
    }

    @FunctionalInterface
    public interface FatigueEffect {
        void apply();
    }
}
