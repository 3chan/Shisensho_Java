import java.util.ArrayList;

class History {
    PieceObject hisAll[];
    ArrayList<PieceObject> his;

    History() {  // �R���X�g���N�^
        hisAll = new PieceObject[144];
        his = new ArrayList<PieceObject>();        
    }

    // UndoAll�p
    public void setHisAll(PieceObject po[]) {
        for (int i = 0; i < 144; i++) {  // �z��̃R�s�[
            hisAll[i] = po[i];
        }
    }

    // UndoAll�p
    public PieceObject[] getHisAll() {
        return hisAll;
    }

    // Undo�p
    public void setHis(PieceObject po1, PieceObject po2) {
        his.add(po1);  // .add �̓f�B�[�v�R�s�[???
        his.add(po2);
    }

    // Undo�p
    public PieceObject getHis() {
        return his.get(his.size() - 1);
    }
}