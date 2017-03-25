class PauseGameObject extends IconObject {
    boolean flag;

    void OnClicked() {
        System.out.println("Icon: PauseGame");
        if (!flag) {
            flag = true;
            ((Game_Scene)getLayer().getScene()).pauseStart = true;  // メモ: layer.scene.フィールド
        }
        else {
            flag = false;
            ((Game_Scene)getLayer().getScene()).pauseEnd = true;
        }
    }
}