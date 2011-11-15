package ai;

import game.Time;
import graphics.Square;
import org.lwjgl.util.vector.Vector2f;

public class BounceAgent {

    private Square square;
    private final float speed = 0.003f;
    private float xSpeed = speed;
    private float ySpeed = speed;

    public BounceAgent(Square sq) {
        square = sq;
    }

    public void tick() {
        Vector2f loc = square.getLocation();
        Vector2f scale = square.getScale();

        if ((loc.x + scale.x) > 1 && Math.signum(xSpeed) != -1.0f) {
            xSpeed = -xSpeed;
        } else if ((loc.x - scale.x) < -1 && Math.signum(xSpeed) != 1.0f) {
            xSpeed = -xSpeed;
        }

        if ((loc.y + scale.y) > 1 && Math.signum(ySpeed) != -1.0f) {
            ySpeed = -ySpeed;
        } else if ((loc.y - scale.y) < -1 && Math.signum(ySpeed) != 1.0f) {
            ySpeed = -ySpeed;
        }

        int delta = Time.getFrameDelta();
        square.translate(xSpeed * delta, ySpeed * delta);
    }
}
