class AlphabetObject extends asd.TextureObject2D {
    protected void OnUpdate() {
        
    }

    void init4clear(int tex, int pos) {
        setPosition(new asd.Vector2DF(115 + pos * 35, 400));
        setAlphabetTexture(tex);
    }

    void init4rank() {

    }

    void setAlphabetTexture(int aTexture) {
        // �e�N�X�`�������1����؂�o��
        setSrc(new asd.RectF(aTexture*35, 0, 34, 35));  // (x, y, �ӂ̒���, �ӂ̒���)
    }
}