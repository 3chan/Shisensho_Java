class UndoOnceObject extends IconObject {
    PieceObject his;
    int pos;

    protected void OnClicked() {
         System.out.println("Icon: UndoOnce");

         // ��S�Č����Ă��鎞�͉������Ȃ�
         if (((Game_Scene)getLayer().getScene()).his.isNull() == null) {
            setIsColored(false);
            return;
         }

         // �������� obj �֏����R�s�[���A���C���[�ɃI�u�W�F�N�g���ēo�^
        for (int i = 0; i < 2; i++) {
            his = ((Game_Scene)getLayer().getScene()).his.getHis();  // ����: layer.scene.�t�B�[���h
            pos = his.getPiecePosition();
            System.out.println("pos = " + pos);
            ((Game_Scene)getLayer().getScene()).obj_pieces[pos] = his;
            ((Game_Scene)getLayer().getScene()).layer.AddObject(his);
            // System.out.println("his[" + pos + "].getPieceTexture() = " + his.getPieceTexture() + ", obj_pieces[" + i + "].getPieceTexture() = " + ((Game_Scene)getLayer().getScene()).obj_pieces[i].getPieceTexture());
        }

        setIsColored(false);
    }
}