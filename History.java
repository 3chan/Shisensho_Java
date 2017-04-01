import java.util.ArrayList;

class History {
    PieceObject hisAll[];
    ArrayList<PieceObject> his;

    History() {  // コンストラクタ
        hisAll = new PieceObject[144];
        his = new ArrayList<PieceObject>();        
    }

    // UndoAll用
    public void setHisAll(PieceObject po[]) {
        for (int i = 0; i < 144; i++) {  // 配列のコピー
            hisAll[i] = po[i];
        }
    }

    // UndoAll用
    public PieceObject[] getHisAll() {
        return hisAll;
    }

    // Undo用
    public void setHis(PieceObject po1, PieceObject po2) {
        his.add(po1);  // .add はディープコピー???
        his.add(po2);
    }

    // Undo用
    public PieceObject getHis() {
        return his.get(his.size() - 1);
    }
}