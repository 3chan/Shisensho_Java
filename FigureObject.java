class FigureObject extends asd.TextureObject2D
{
    int figureTexture;

    protected void OnUpdate()
    {   // �V�[���E���C���[�Ɋւ��Ă�OnUpdated()�܂���OnUpdating()
    }

    void init4timer(int tex, int pos) {  // �^�C�}�[�Ŏg���p
        setPosition(new asd.Vector2DF(50 + 10 + pos * 26, 50 + 12 * 40 + 30));
        setFigureTexture(tex);
    }

    void init4rank() {  // �����L���O�Ŏg���p
    
    }

    void setFigureTexture(int fTexture)
    {
        // �e�N�X�`�������1����؂�o��
        setSrc(new asd.RectF(fTexture*26, 0, 26, 35));  // (x, y, �ӂ̒���, �ӂ̒���)
    }
}