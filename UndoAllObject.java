class UndoAllObject extends IconObject {
    PieceObject hisAll[];
    protected void OnClicked() {
         System.out.println("Icon: UndoAll");
        // �x�����o������ (��])

        // his ����1�g�̗������Ăяo��
        hisAll = ((Game_Scene)getLayer().getScene()).his.getHisAll();  // ����: layer.scene.�t�B�[���h

        // ���C���[����S�Ă� Pieceobject ����菜��
        for (int i = 0; i < 144; i++) {
            if (((Game_Scene)getLayer().getScene()).obj_pieces[i] == null) continue;
            ((Game_Scene)getLayer().getScene()).layer.RemoveObject(hisAll[i]);
        }

        // �������� obj �֏����R�s�[���A���C���[�ɃI�u�W�F�N�g���ēo�^
        for (int i = 0; i < 144; i++) {
            ((Game_Scene)getLayer().getScene()).obj_pieces[i] = hisAll[i];
            ((Game_Scene)getLayer().getScene()).layer.AddObject(hisAll[i]);
            System.out.println("hisAll[" + i + "].getPieceTexture() = " + hisAll[i].getPieceTexture() + ", obj_pieces[" + i + "].getPieceTexture() = " + ((Game_Scene)getLayer().getScene()).obj_pieces[i].getPieceTexture());
        }

        setIsColored(false);
    }
}