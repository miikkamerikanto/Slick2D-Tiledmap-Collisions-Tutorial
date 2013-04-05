package collisionGame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Game extends BasicGame {

    private TiledMap map;
    private Image hero;
    private float x = 29f, y = 34f;
    private boolean[][] blocked;
    private static final int TILEWIDTH = 29;
    private static final int TILEHEIGHT = 34;
    private static final int NUMBEROFTILESINAROW = 10;
    private static final int NUMBEROFTILESINACOLUMN = 10;
    private static final float SPEED = 0.2f;

    public Game() {
        super("Slick2D TiledMap Collisions");
    }

    public static void main(String[] arguments) {
        try {
            AppGameContainer app = new AppGameContainer(new Game());
            app.setDisplayMode(TILEWIDTH * NUMBEROFTILESINAROW, TILEHEIGHT * NUMBEROFTILESINACOLUMN, false);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        hero = new Image("images/hero.png");
        map = new TiledMap("tiledmap/map.tmx");
        blocked = new boolean[NUMBEROFTILESINAROW][NUMBEROFTILESINACOLUMN];
        initializeBlocked();
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_UP)) {
            if (!isBlocked(x + TILEWIDTH, y - delta * SPEED) && !isBlocked(x, y - delta * SPEED)) {
                y -= delta * SPEED;
            }
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            if (!isBlocked(x + TILEWIDTH, y + TILEHEIGHT + delta * SPEED) && !isBlocked(x, y + TILEHEIGHT + delta * SPEED)) {
                y += delta * SPEED;
            }
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            if (!isBlocked(x - delta * SPEED, y) && !isBlocked(x - delta * SPEED, y + TILEHEIGHT)) {
                x -= delta * SPEED;
            }
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            if (!isBlocked(x + TILEWIDTH + delta * SPEED, y + TILEHEIGHT) && !isBlocked(x + TILEWIDTH + delta * SPEED, y)) {
                x += delta * SPEED;
            }
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        map.render(0, 0);
        hero.draw((int) x, (int) y);
    }

    private boolean isBlocked(float x, float y) {
        int xBlock = (int) x / TILEWIDTH;
        int yBlock = (int) y / TILEHEIGHT;
        return blocked[xBlock][yBlock];
    }

    private void initializeBlocked() {
        for (int x = 0; x < NUMBEROFTILESINAROW; x++) {
            for (int y = 0; y < NUMBEROFTILESINAROW; y++) {
                int tileID = map.getTileId(x, y, 0);
                String value = map.getTileProperty(tileID, "blocked", "false");
                if (value.equals("true")) {
                    blocked[x][y] = true;
                }
            }
        }
    }
}