package geekbrainscourse.libgdxgame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import geekbrainscourse.libgdxgame.base.Ship;
import geekbrainscourse.libgdxgame.pool.BulletPool;
import geekbrainscourse.libgdxgame.utils.ShipSounds;

public class PlayerShip extends Ship {

    private final float SPEED = 0.5f;

    public PlayerShip(float x, float y, TextureAtlas atlas, BulletPool bulletPool, ShipSounds sound) {
        super(x, y, atlas.findRegion("spSpaceship"), 1, 2, 2,
                bulletPool, sound);
        bulletRegion = atlas.findRegion("bullet");
        bulletVelocity = new Vector2(0, 0.5f);
        autoFire = false;
        damage = 1;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        checkStop(delta);
        keyboardControls(delta);
    }

    public void checkStop(float delta) {
        boolean isOutsideRight = getRight() > worldBounds.getRight();
        boolean isOutsideLeft = getLeft() < worldBounds.getLeft();
        boolean isOutsideTop = getTop() > worldBounds.getTop();
        boolean isOutsideBottom = getBottom() < worldBounds.getBottom();

        if (isOutsideLeft) {
            addDestination(SPEED *delta, 0);
        }
        if (isOutsideRight) {
            addDestination(-SPEED *delta, 0);
        }
        if (isOutsideTop) {
            addDestination(0, -SPEED *delta);
        }
        if (isOutsideBottom) {
            addDestination(0, SPEED *delta);
        }
    }

    public void keyboardControls(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            addDestination(-SPEED *delta, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            addDestination(SPEED *delta, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            addDestination(0, SPEED *delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            addDestination(0, -SPEED *delta);
        }

        if (fireTimer.isCool() && (Gdx.input.isKeyPressed(Input.Keys.SPACE) || autoFire)) {
            shoot();
            fireTimer.reset(autoFireCoolDown);
        }
    }

    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.B) {
            autoFire = !autoFire;
        }
        return false;
    }
}
