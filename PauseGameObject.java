class PauseGameObject extends IconObject {
    boolean flag;

    void OnClicked() {
        System.out.println("Icon: PauseGame");
        if (!flag) {
            flag = true;
            ((Game_Scene)getLayer().getScene()).pauseStart = true;  // ����: layer.scene.�t�B�[���h
        }
        else {
            flag = false;
            ((Game_Scene)getLayer().getScene()).pauseEnd = true;
        }
    }
}