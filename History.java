import java.util.LinkedList;

class History {
    PieceObject hisAll[];
    LinkedList<PieceObject> his;

    History() { // コンストラクタ
        hisAll = new PieceObject[144];
        his = new LinkedList<PieceObject>();
    }

    // UndoAll用
    public void setHisAll(PieceObject po[]) {
        for (int i = 0; i < 144; i++) { // 配列のコピー
            hisAll[i] = po[i];
        }
    }

    // UndoAll用
    public PieceObject[] getHisAll() {
        return hisAll;
    }

    // Undo用
    public void setHis(PieceObject po1, PieceObject po2) {
        his.addLast(po1);
        his.addLast(po2);
    }

    // Undo用
    public PieceObject getHis() {
        return his.pollLast();
    }

    // Undo用
    public PieceObject isNull() {
        return his.peekLast();
    }
}