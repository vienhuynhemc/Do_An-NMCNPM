package com.vientamthuong.chronotrigger.presonMap;

import android.view.ViewManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;

public class BoatPresonMap implements Observer {

    private final ImageView imageView;
    // Tọa độ thông thường
    private final int x;
    private final int y;
    // Tạo đô để vẽ
    private int xDraw;
    private int yDraw;
    private final int width;
    private final int height;
    private Animation animation;
    private final AppCompatActivity appCompatActivity;
    private final GameWorldPresonMap gameWorldPresonMap;

    public BoatPresonMap(ImageView imageView, int x, int y, int width, int height, AppCompatActivity appCompatActivity, GameWorldPresonMap gameWorldPresonMap) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gameWorldPresonMap = gameWorldPresonMap;
        this.appCompatActivity = appCompatActivity;
        init();
    }

    private void init() {
        animation = SourceAnimation.getInstance().getAnimation("preson_map_boat");
        // Set lại tọa độ theo camera
        xDraw = x - gameWorldPresonMap.getCamera().getX();
        yDraw = y - gameWorldPresonMap.getCamera().getY();
        // Cập nhật lại tọa độ của background theo camera
        imageView.setX(xDraw);
        imageView.setY(yDraw);
    }

    @Override
    public void update() {
        // Set lại tọa độ theo camera
        xDraw = x - gameWorldPresonMap.getCamera().getX();
        yDraw = y - gameWorldPresonMap.getCamera().getY();
        // Cập nhật lại tọa độ của background theo camera
        appCompatActivity.runOnUiThread(() -> {
            imageView.setX(xDraw);
            imageView.setY(yDraw);
        });
    }

    @Override
    public void draw() {
        // update and draw animation
        animation.update();
        animation.draw(imageView, appCompatActivity);
    }

    @Override
    public void outToLayout() {
        appCompatActivity.runOnUiThread(() -> ((ViewManager) imageView.getParent()).removeView(imageView));
    }

    @Override
    public boolean isOutCamera() {
        return gameWorldPresonMap.getCamera().isOutCamera(x, y, width, height);
    }

}
