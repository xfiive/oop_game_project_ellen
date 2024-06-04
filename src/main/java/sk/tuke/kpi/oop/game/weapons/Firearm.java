package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {

    private int initAmmo;
    private int maxAmmo;

    public Firearm(int initAmmo, int maxAmmo) {
        this.initAmmo = initAmmo;
        this.maxAmmo = maxAmmo;
    }

    public Firearm(int initAmmo) {
        this.initAmmo = initAmmo;
    }

    public int getAmmo() {
        return this.initAmmo;
    }

    public void reload(int amount) {
        if (this.maxAmmo <= this.initAmmo)
            return;

        this.initAmmo += amount;
        if (this.maxAmmo < this.initAmmo)
            this.initAmmo = this.maxAmmo;
    }

    abstract protected Fireable createBullet();

    public Fireable fire() {
        if (this.initAmmo <= 0)
            return null;
        else {
            this.initAmmo -= 1;
            return this.createBullet();
        }
    }


}
