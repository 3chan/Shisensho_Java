class UndoAllObject extends IconObject {
    PieceObject hisAll[];
    protected void OnClicked() {
         System.out.println("Icon: UndoAll");
        // �x�����o������ (��])

        // his ����1�g�̗������Ăяo��
        hisAll = ((Game_Scene)getLayer().getScene()).his.getHisAll();  // ����: layer.scene.�t�B�[���h

        // �������� obj �֏����R�s�[
        for (int i = 0; i < 144; i++) {
            ((Game_Scene)getLayer().getScene()).obj_pieces[i] = hisAll[i];
            System.out.println("hisAll[" + i + "].getPieceTexture() = " + hisAll[i].getPieceTexture() + ", obj_pieces[" + i + "].getPieceTexture() = " + ((Game_Scene)getLayer().getScene()).obj_pieces[i].getPieceTexture());
        }

        // ���C���[�ɃI�u�W�F�N�g��o�^����K�v��...����...??
    }
}