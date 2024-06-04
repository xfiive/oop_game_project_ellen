package sk.tuke.kpi.oop.game;

public enum Direction {
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0),
    NORTHWEST(-1, 1),
    NORTHEAST(1, 1),
    SOUTHWEST(-1, -1),
    SOUTHEAST(1, -1),
    NONE(0, 0);
    private int dx;
    private int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    private static Direction getDirBasic(float angle) {
        if (angle >= 337.5 || angle < 22.5) {
            return NORTH;
        } else if (angle >= 67.5 && angle < 112.5) {
            return WEST;
        } else if (angle >= 157.5 && angle < 202.5) {
            return SOUTH;
        } else if (angle >= 247.5 && angle < 292.5) {
            return EAST;
        }

        return null;
    }

    private static Direction getDirComplex(float angle) {
        if (angle >= 22.5 && angle < 67.5) {
            return NORTHWEST;
        } else if (angle >= 112.5 && angle < 157.5) {
            return SOUTHWEST;
        } else if (angle >= 202.5 && angle < 247.5) {
            return SOUTHEAST;
        } else if (angle >= 292.5 && angle < 337.5) {
            return NORTHEAST;
        }

        return null;
    }

    public static Direction fromAngle(float angle) {
        if (angle < 0 || 360 < angle)
            return null;

        Direction newDirection = getDirBasic(angle);

        if (newDirection != null)
            return newDirection;

        newDirection = getDirComplex(angle);

        return newDirection;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    private float getAngleBasic() {
        if (this.dx == 0 && this.dy == 1)
            return 0;
        else if (this.dx == -1 && this.dy == 0)
            return 90;
        else if (this.dx == 0 && this.dy == -1)
            return 180;
        else if (this.dx == 1 && this.dy == 0)
            return 270;
        return -1;
    }

    private float getAngleComplex() {
        if (this.dx == -1 && this.dy == 1)
            return 45;
        else if (this.dx == -1 && this.dy == -1)
            return 135;
        else if (this.dx == 1 && this.dy == -1)
            return 225;
        else if (this.dx == 1 && this.dy == 1)
            return 315;

        return -1;
    }

    public float getAngle() {
        float newAngle = getAngleBasic();

        if (newAngle != -1)
            return newAngle;

        newAngle = getAngleComplex();

        if (newAngle != -1)
            return newAngle;

        return 0;
    }

    private int getIfOneZeroDx(Direction other) {
        if (other.dx == 0)
            return this.dx;
        else
            return other.dx;
    }

    private int getIfOneZeroDy(Direction other) {
        if (other.dy == 0)
            return this.dy;
        else
            return other.dy;
    }

    private int getDxCoordinate(Direction other) {
        if ((this.dx == 0 && other.dx != 0) || (other.dx == 0 && this.dx != 0)) {
            return getIfOneZeroDx(other);
        } else if (this.dx == other.dx)
            return this.dx;
        else if (this.dx == (-1) * other.dx) {
            return 0;
        }

        return 0;
    }

    private int getDyCoordinate(Direction other) {
        if ((this.dy == 0 && other.dy != 0) || (other.dy == 0 && this.dy != 0)) {
            return getIfOneZeroDy(other);
        } else if (this.dy == other.dy)
            return this.dy;
        else if (this.dy == (-1) * other.dy) {
            return 0;
        }

        return 0;
    }


    public Direction combine(Direction other) {
        int combinedDx = getDxCoordinate(other);
        int combinedDy = getDyCoordinate(other);
        for (Direction dir : values()) {
            if (dir.getDx() == combinedDx && dir.getDy() == combinedDy) {
                return dir;
            }
        }
        return NONE;
    }
}
