class FigureObject extends asd.TextureObject2D
{
    int figureTexture;

    protected void OnUpdate() {  // �V�[���E���C���[�Ɋւ��Ă�OnUpdated()�܂���OnUpdating()
        // �|�[�Y���̓O���[������
        if (((Game_Scene)getLayer().getScene()).isPause) setColor(new asd.Color(100,100,100));
        else setColor(new asd.Color(255,255,255));
    }

    void init4timer(int tex, int pos) {  // �^�C�}�[�Ŏg���p
        setPosition(new asd.Vector2DF(50 + 30 + pos * 26, 50 + 12 * 40 + 30));
        setFigureTexture(tex);
    }

    void init4rank() {  // �����L���O�Ŏg���p
    
    }

    void setFigureTexture(int fTexture) {
        // �e�N�X�`�������1����؂�o��
        setSrc(new asd.RectF(fTexture*26, 0, 26, 35));  // (x, y, �ӂ̒���, �ӂ̒���)
    }
}