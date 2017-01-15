class PieceObject extends asd.TextureObject2D
{
    boolean isSelected;
    protected void OnUpdate() {  // �V�[���E���C���[�Ɋւ��Ă�OnUpdated()�܂���OnUpdating()
        if((asd.Engine.getMouse().getLeftButton().getButtonState() != asd.MouseButtonState.Push)) return;  // return ����������ɏ����� {} �������Ȃ��Ă悢

        // ��̍��W���擾���āAmousePosition�Ɣ�r
        asd.Vector2DF mousePos = asd.Engine.getMouse().getPosition();
        asd.Vector2DF pos0 = getPosition();  // ��̍���̍��W
        asd.Vector2DF pos1 = asd.Vector2DF.Add(getPosition(), new asd.Vector2DF(40, 40));  // ��̉E���̍��W
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