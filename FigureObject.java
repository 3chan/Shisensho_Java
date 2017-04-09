class FigureObject extends asd.TextureObject2D {
    int figureTexture;
    boolean isRank;

    protected void OnUpdate() { // シーン・レイヤーに関してはOnUpdated()またはOnUpdating()
        if (!isRank) {
            // ポーズ中はグレー化する
            if (((Game_Scene) getLayer().getScene()).isPause)
                setColor(new asd.Color(100, 100, 100));
            else
                setColor(new asd.Color(255, 255, 255));
        } else {
            // Rank_Scene では何もしない
        }
    }

    void init4timer(int tex, int pos) { // タイマーで使う用
        isRank = false;
        setPosition(new asd.Vector2DF(50 + 30 + pos * 26, 50 + 12 * 40 + 30));
        setFigureTexture(tex);
    }

    void init4rank(int tex, int posx, int posy) { // ランキングで使う用
        isRank = true;
        setPosition(new asd.Vector2DF(580 - 50 - 26 * (5 - posx), 270 + posy * 70));
        setFigureTexture(tex);
    }

    void setFigureTexture(int fTexture) {
        // テクスチャから駒1つ分を切り出す
        setSrc(new asd.RectF(fTexture * 26, 0, 26, 35)); // (x, y, 辺の長さ, 辺の長さ)
    }
}