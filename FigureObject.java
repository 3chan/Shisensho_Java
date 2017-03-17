class FigureObject extends asd.TextureObject2D
{
    int figureTexture;

    protected void OnUpdate()
    {   // シーン・レイヤーに関してはOnUpdated()またはOnUpdating()
    }

    void init4timer(int tex, int pos) {  // タイマーで使う用
        setPosition(new asd.Vector2DF(50 + 10 + pos * 26, 50 + 12 * 40 + 30));
        setFigureTexture(tex);
    }

    void init4rank() {  // ランキングで使う用
    
    }

    void setFigureTexture(int fTexture)
    {
        // テクスチャから駒1つ分を切り出す
        setSrc(new asd.RectF(fTexture*26, 0, 26, 35));  // (x, y, 辺の長さ, 辺の長さ)
    }
}