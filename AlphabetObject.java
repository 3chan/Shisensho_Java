class AlphabetObject extends asd.TextureObject2D {
    int texture;

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
        texture = aTexture;
        setSrc(new asd.RectF(aTexture*35, 0, 34, 35));  // (x, y, �ӂ̒���, �ӂ̒���)
    }

    int getAlphabetTexture() {
        return texture;
    }

    public String toString() {
        return String.valueOf(texture + 65);
    }
}