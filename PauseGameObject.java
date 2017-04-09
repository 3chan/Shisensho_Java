class PauseGameObject extends IconObject {
    boolean flag;

    void OnClicked() {
        System.out.println("Icon: PauseGame");
        if (!flag) {
            flag = true;
            ((Game_Scene) getLayer().getScene()).pauseStart = true; // メモ: layer.scene.フィールド
        } else {
            flag = false;
            isColored = false;
            ((Game_Scene) getLayer().getScene()).pauseEnd = true;
        }
    }

    void setPauseTexture(int tex) {
        // テクスチャから駒1つ分を切り出す
        setSrc(new asd.RectF(tex * 41, 0, 41, 35)); // (x, y, 辺の長さ, 辺の長さ)
    }
}