class PauseGameObject extends IconObject {
    boolean flag;

    void OnClicked() {
        System.out.println("Icon: PauseGame");
        if (!flag) {
            flag = true;
            ((Game_Scene) getLayer().getScene()).pauseStart = true; // ����: layer.scene.�t�B�[���h
        } else {
            flag = false;
            isColored = false;
            ((Game_Scene) getLayer().getScene()).pauseEnd = true;
        }
    }

    void setPauseTexture(int tex) {
        // �e�N�X�`�������1����؂�o��
        setSrc(new asd.RectF(tex * 41, 0, 41, 35)); // (x, y, �ӂ̒���, �ӂ̒���)
    }
}