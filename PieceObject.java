class PieceObject extends asd.TextureObject2D
{
    boolean isColored;
    int pieceTexture;  // 36種類中どの絵柄かを 0～35 で表す
    int piecePosition;  // 駒の盤上の位置を 0~143 で表す
    protected void OnUpdate()
    {   // シーン・レイヤーに関してはOnUpdated()またはOnUpdating()
        if((asd.Engine.getMouse().getLeftButton().getButtonState() != asd.MouseButtonState.Push)) return;  // return する条件を先に書くと {} が増えなくてよい

        // 駒の座標を取得して、mousePositionと比較
        asd.Vector2DF mousePos = asd.Engine.getMouse().getPosition();
        asd.Vector2DF pos0 = getPosition();  // 駒の左上の座標
        asd.Vector2DF pos1 = asd.Vector2DF.Add(getPosition(), new asd.Vector2DF(40, 40));  // 駒の右下の座標
        if (mousePos.X < pos0.X || pos1.X <= mousePos.X || mousePos.Y < pos0.Y || pos1.Y <= mousePos.Y) return;

        // 駒の色を変更
        if (!isColored)
        {
            isColored = true;
            setColor(new asd.Color(255,0,0));
        }
        else
        {
            isColored = false;
            setColor(new asd.Color(255,255,255));
        }
    }

    void setPieceTexture(int pTexture)
    {
        // クラス外から駒の絵柄番号を登録
        pieceTexture = pTexture;
        // テクスチャから駒1つ分を切り出す
        setSrc(new asd.RectF(pTexture*40, 0, 40, 40));  // (x, y, 辺の長さ, 辺の長さ)
    }

    int getPieceTexture()
    {
        // クラス外から駒の絵柄番号を参照
        return pieceTexture;
    }

    void setPiecePosition(int pPosition)
    {
        // クラス外から駒の位置番号を登録
        piecePosition = pPosition;
        // 駒の描画位置を設定する
        setPosition(new asd.Vector2DF(50 + (pPosition % 12) * 40, 50 + (pPosition / 12) * 40));
    }

    int getPiecePosition()
    {
        // クラス外から駒の位置を参照
        return piecePosition;
    }

    void setIsColored(boolean pState)
    {
        isColored = pState;
    }
  
    boolean getIsColored()
    {
        return isColored;
    }
}