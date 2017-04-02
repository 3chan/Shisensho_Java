abstract class IconObject extends asd.TextureObject2D
{
    boolean isColoredGray;
    boolean isColored;

    protected void OnUpdate() {  // �V�[���E���C���[�Ɋւ��Ă�OnUpdated()�܂���OnUpdating()
        // �|�[�Y�����ǂ���
        isColoredGray = ((Game_Scene)getLayer().getScene()).isPause;

        if ((asd.Engine.getMouse().getLeftButton().getButtonState() != asd.MouseButtonState.Push)) {  // return ����������ɏ����� {} �������Ȃ��Ă悢
            if (isColoredGray) setColor(new asd.Color(100,100,100));
            else if (!isColored) setColor(new asd.Color(255,255,255));
            else setColor(new asd.Color(100,100,100));
            return;
        }

        // ��̍��W���擾���āAmousePosition�Ɣ�r
        asd.Vector2DF mousePos = asd.Engine.getMouse().getPosition();
        asd.Vector2DF pos0 = getPosition();  // ��̍���̍��W
        asd.Vector2DF pos1 = asd.Vector2DF.Add(getPosition(), new asd.Vector2DF(41, 35));  // ��̉E���̍��W
        if (mousePos.X < pos0.X || pos1.X <= mousePos.X || mousePos.Y < pos0.Y || pos1.Y <= mousePos.Y) return;

        // ��̐F��ύX
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

        // �N���b�N���ꂽ���̏���
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
        setSrc(new asd.RectF(tex*41, 0, 41, 35));  // (x, y, �ӂ̒���, �ӂ̒���)
    }

    abstract void OnClicked();
}