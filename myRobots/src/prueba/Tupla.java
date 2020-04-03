package prueba;

public class Tupla {
    private int x;
    private int y;

    public Tupla(int a, int b) {
        x = a;
        y = b;
    }

    public int getPrimero() {
        return x;
    }

    public int getSegundo() {
        return y;
    }
    public boolean equals (Object o) {
        boolean res = false;
        if (o instanceof Tupla) {
            Tupla p = (Tupla) o;
            res = this.x == p.x && this.y == p.y;
        }
        return res;
    }

    public int hashCode() {return Integer.hashCode(x) + Integer.hashCode(y);}

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
