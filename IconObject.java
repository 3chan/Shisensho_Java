abstract class IconObject extends asd.TextureObject2D
{
    boolean isColoredGray;
    boolean isColored;

    protected void OnUpdate() {  // シーン・レイヤーに関してはOnUpdated()またはOnUpdating()
        // ポーズ中かどうか
        isColoredGray = ((Game_Scene)getLayer().getScene()).isPause;

        if ((asd.Engine.getMouse().getLeftButton().getButtonState() != asd.MouseButtonState.Push)) {  // return する条件を先に書くと {} が増えなくてよい
            if (isColoredGray) setColor(new asd.Color(100,100,100));
            else if (!isColored) setColor(new asd.Color(255,255,255));
            else setColor(new asd.Color(100,100,100));
            return;
        }

        // 駒の座標を取得して、mousePositionと比較
        asd.Vector2DF mousePos = asd.Engine.getMouse().getPosition();
        asd.Vector2DF pos0 = getPosition();  // 駒の左上の座標
        asd.Vector2DF pos1 = asd.Vector2DF.Add(getPosition(), new asd.Vector2DF(41, 35));  // 駒の右下の座標
        if (mousePos.X < pos0.X || pos1.X <= mousePos.X || mousePos.Y < pos0.Y || pos1.Y <= mousePos.Y) return;

        // 駒の色を変更
        if (!isColoredGray) {
            if (!isColored) {
                isColored = true;
                setColor(new asd.Color(100,100,100));
            }
            else {
                isColored = false;
                setColor(new asd.Color(255,255,255));
            }
        }

        // クリックされた時の処理
        OnClicked();
    }

    void setIsColored(boolean pState)
    {
        isColored = pState;
    }
  
    boolean getIsColored()
    {
        return isColored;
    }

    void init(int tex, int pos) {
        // setPosition(new asd.Vector2DF(50 + 10 + 5 * 26 + 50 + pos * 41, 50 + 12 * 40 + 30));
        // setPosition(new asd.Vector2DF(580 - 100 - 10 - 41 * (4 - pos), 50 + 12 * 40 + 30));
        setPosition(new asd.Vector2DF(580 - 50 - 10 - 51 * (4 - pos), 50 + 12 * 40 + 30));
        setSrc(new asd.RectF(tex*41, 0, 41, 35));  // (x, y, 辺の長さ, 辺の長さ)
    }

    abstract void OnClicked();
}