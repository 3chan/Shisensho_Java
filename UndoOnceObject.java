class UndoOnceObject extends IconObject {
    PieceObject his;
    int pos;

    protected void OnClicked() {
         System.out.println("Icon: UndoOnce");

         // 駒が全て見えている時は何もしない
         if (((Game_Scene)getLayer().getScene()).his.isNull() == null) {
            setIsColored(false);
            return;
         }

         // 履歴から obj へ情報をコピーし、レイヤーにオブジェクトを再登録
        for (int i = 0; i < 2; i++) {
            his = ((Game_Scene)getLayer().getScene()).his.getHis();  // メモ: layer.scene.フィールド
            pos = his.getPiecePosition();
            System.out.println("pos = " + pos);
            ((Game_Scene)getLayer().getScene()).obj_pieces[pos] = his;
            ((Game_Scene)getLayer().getScene()).layer.AddObject(his);
            // System.out.println("his[" + pos + "].getPieceTexture() = " + his.getPieceTexture() + ", obj_pieces[" + i + "].getPieceTexture() = " + ((Game_Scene)getLayer().getScene()).obj_pieces[i].getPieceTexture());
        }

        setIsColored(false);
    }
}