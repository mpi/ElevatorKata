package tdd;

public interface Engine {

    public class MalfunctionException extends Exception {
        private static final long serialVersionUID = 1L;
    }

    public void up() throws MalfunctionException;

    public void down() throws MalfunctionException;

    public void stop();

}
