class PieceObject extends asd.TextureObject2D
{
    boolean isColored;
    int pieceTexture;  // 36��ޒ��ǂ̊G������ 0�`35 �ŕ\��
    int piecePosition;  // ��̔Տ�̈ʒu�� 0~143 �ŕ\��
    protected void OnUpdate()
    {   // �V�[���E���C���[�Ɋւ��Ă�OnUpdated()�܂���OnUpdating()
        if((asd.Engine.getMouse().getLeftButton().getButtonState() != asd.MouseButtonState.Push)) return;  // return ����������ɏ����� {} �������Ȃ��Ă悢

        // ��̍��W���擾���āAmousePosition�Ɣ�r
        asd.Vector2DF mousePos = asd.Engine.getMouse().getPosition();
        asd.Vector2DF pos0 = getPosition();  // ��̍���̍��W
        asd.Vector2DF pos1 = asd.Vector2DF.Add(getPosition(), new asd.Vector2DF(40, 40));  // ��̉E���̍��W
        if (mousePos.X < pos0.X || pos1.X <= mousePos.X || mousePos.Y < pos0.Y || pos1.Y <= mousePos.Y) return;

        // ��̐F��ύX
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
        // �N���X�O�����̊G���ԍ���o�^
        pieceTexture = pTexture;
        // �e�N�X�`�������1����؂�o��
        setSrc(new asd.RectF(pTexture*40, 0, 40, 40));  // (x, y, �ӂ̒���, �ӂ̒���)
    }

    int getPieceTexture()
    {
        // �N���X�O�����̊G���ԍ����Q��
        return pieceTexture;
    }

    void setPiecePosition(int pPosition)
    {
        // �N���X�O�����̈ʒu�ԍ���o�^
        piecePosition = pPosition;
        // ��̕`��ʒu��ݒ肷��
        setPosition(new asd.Vector2DF(50 + (pPosition % 12) * 40, 50 + (pPosition / 12) * 40));
    }

    int getPiecePosition()
    {
        // �N���X�O�����̈ʒu���Q��
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