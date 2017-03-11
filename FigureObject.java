class FigureObject extends asd.TextureObject2D
{
    boolean[] isPlaced = new boolean[4];
    int figureTexture;

    protected void OnUpdate()
    {   // シーン・レイヤーに関してはOnUpdated()またはOnUpdating()
    }

    void setFigureTexture(int fTexture)
    {
        // クラス外から駒の絵柄番号を登録
        figureTexture = fTexture;
        // テクスチャから駒1つ分を切り出す
        setSrc(new asd.RectF(fTexture*20, 0, 20, 35));  // (x, y, 辺の長さ, 辺の長さ)
    }

    int getFigureTexture()
    {
        // クラス外から駒の絵柄番号を参照
        return figureTexture;
    }

    void setIsPlaced(int pos, boolean fState)
    {
        isPlaced[pos] = fState;
    }
  
    boolean getIsPlaced(int pos)
    {
        return isPlaced[pos];
    }
}