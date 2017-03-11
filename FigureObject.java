class FigureObject extends asd.TextureObject2D
{
    boolean[] isPlaced = new boolean[4];
    int figureTexture;

    protected void OnUpdate()
    {   // �V�[���E���C���[�Ɋւ��Ă�OnUpdated()�܂���OnUpdating()
    }

    void setFigureTexture(int fTexture)
    {
        // �N���X�O�����̊G���ԍ���o�^
        figureTexture = fTexture;
        // �e�N�X�`�������1����؂�o��
        setSrc(new asd.RectF(fTexture*20, 0, 20, 35));  // (x, y, �ӂ̒���, �ӂ̒���)
    }

    int getFigureTexture()
    {
        // �N���X�O�����̊G���ԍ����Q��
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