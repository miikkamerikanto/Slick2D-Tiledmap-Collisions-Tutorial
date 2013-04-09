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
    private static final int NUMBEROFLAYERS = 2;
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
            if (!isBlocked(x + TILEWIDTH -1, y - delta * SPEED) && !isBlocked(x + 1, y - delta * SPEED)) {
                y -= delta * SPEED;
            }
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            if (!isBlocked(x + TILEWIDTH - 1, y + TILEHEIGHT + delta * SPEED) && !isBlocked(x + 1, y + TILEHEIGHT + delta * SPEED)) {
                y += delta * SPEED;
            }
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            if (!isBlocked(x - delta * SPEED, y + 1) && !isBlocked(x - delta * SPEED, y + TILEHEIGHT - 1)) {
                x -= delta * SPEED;
            }
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            if (!isBlocked(x + TILEWIDTH + delta * SPEED, y + TILEHEIGHT - 1) && !isBlocked(x + TILEWIDTH + delta * SPEED, y + 1)) {
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
        for (int l = 0; l < NUMBEROFLAYERS; l++) {
            String layerValue = map.getLayerProperty(l, "blocked", "false");
            if (layerValue.equals("true")) {
                for (int c = 0; c < NUMBEROFTILESINACOLUMN; c++) {
                    for (int r = 0; r < NUMBEROFTILESINAROW; r++) {
                        if (map.getTileId(c, r, l) != 0) {
                            blocked[c][r] = true;
                        }
                    }
                }
            }
        }
    }
}