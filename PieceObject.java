class PieceObject extends asd.TextureObject2D
{
    boolean isSelected;
    protected void OnUpdate() {  // シーン・レイヤーに関してはOnUpdated()またはOnUpdating()
        if((asd.Engine.getMouse().getLeftButton().getButtonState() != asd.MouseButtonState.Push)) return;  // return する条件を先に書くと {} が増えなくてよい

        // 駒の座標を取得して、mousePositionと比較
        asd.Vector2DF mousePos = asd.Engine.getMouse().getPosition();
        asd.Vector2DF pos0 = getPosition();  // 駒の左上の座標
        asd.Vector2DF pos1 = asd.Vector2DF.Add(getPosition(), new asd.Vector2DF(40, 40));  // 駒の右下の座標
        if (mousePos.X < pos0.X || pos1.X <= mousePos.X || mousePos.Y < pos0.Y || pos1.Y <= mousePos.Y) return;
        //if (mousePos.X < pos0.X || pos0.X + 40 <= mousePos.X || mousePos.Y < pos0.Y || pos0.Y + 40 <= mousePos.Y) return;

        if (!isSelected)
        {
            isSelected = true;
            setColor(new asd.Color(255,0,0));
        }
        else
        {
            isSelected = false;
            setColor(new asd.Color(255,255,255));
        }
    }
}