class UndoAllObject extends IconObject {
    PieceObject hisAll[];
    protected void OnClicked() {
         System.out.println("Icon: UndoAll");
        // 警告窓出したい (希望)

        // his から1組の履歴を呼び出す
        hisAll = ((Game_Scene)getLayer().getScene()).his.getHisAll();  // メモ: layer.scene.フィールド

        // レイヤーから全ての Pieceobject を取り除く
        for (int i = 0; i < 144; i++) {
            if (((Game_Scene)getLayer().getScene()).obj_pieces[i] == null) continue;
            ((Game_Scene)getLayer().getScene()).layer.RemoveObject(hisAll[i]);
        }

        // 履歴から obj へ情報をコピーし、レイヤーにオブジェクトを再登録
        for (int i = 0; i < 144; i++) {
            ((Game_Scene)getLayer().getScene()).obj_pieces[i] = hisAll[i];
            ((Game_Scene)getLayer().getScene()).layer.AddObject(hisAll[i]);
            System.out.println("hisAll[" + i + "].getPieceTexture() = " + hisAll[i].getPieceTexture() + ", obj_pieces[" + i + "].getPieceTexture() = " + ((Game_Scene)getLayer().getScene()).obj_pieces[i].getPieceTexture());
        }

        setIsColored(false);
    }
}