package GameEditor.Map.Tile;

import java.util.Objects;

public class Coordinate {
    private Integer x;
    private Integer y;

    public Coordinate(int new_x, int new_y) {
        this.x = new_x;
        this.y = new_y;
    }

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    /**
     * SUPER IMPORTANT NE PAS MODIFIER SANS EN PARLER A PAUL
     */

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate other = (Coordinate) o;
        if (this.x == other.getX() && this.y == other.getY()) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    /**
     * FIN DU SUPER IMPORTANT
     */
}
