import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

public enum ZombieFactory implements SpriteFactory {
    INSTANCE;

    final BufferedImage tape;

    ZombieFactory() {
        try {
            this.tape = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/walkingdead.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Sprite newSprite(int x, int y) {
        double scale = (new Random().nextDouble() * 2) % 1.4 + 0.2; // dostosowalem przedzial tak aby wizualnie bardziej mi odpowiadal
        Zombie z;
        try {
            z = new Zombie(x, y, scale, tape);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return z;
    }
}