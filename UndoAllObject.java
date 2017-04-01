class UndoAllObject extends IconObject {
    PieceObject hisAll[];
    protected void OnClicked() {
         System.out.println("Icon: UndoAll");
        // 警告窓出したい (希望)

        // his から1組の履歴を呼び出す
        hisAll = ((Game_Scene)getLayer().getScene()).his.getHisAll();  // メモ: layer.scene.フィールド

        // 履歴から obj へ情報をコピー
        for (int i = 0; i < 144; i++) {
            ((Game_Scene)getLayer().getScene()).obj_pieces[i] = hisAll[i];
            System.out.println("hisAll[" + i + "].getPieceTexture() = " + hisAll[i].getPieceTexture() + ", obj_pieces[" + i + "].getPieceTexture() = " + ((Game_Scene)getLayer().getScene()).obj_pieces[i].getPieceTexture());
        }

        // レイヤーにオブジェクトを登録する必要が...ある...??
    }
}