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
        // テクスチャから駒1つ分を切り出す
        setSrc(new asd.RectF(aTexture*35, 0, 34, 35));  // (x, y, 辺の長さ, 辺の長さ)
    }
}