import java.util.LinkedList;

class History {
    PieceObject hisAll[];
    LinkedList<PieceObject> his;

    History() { // �R���X�g���N�^
        hisAll = new PieceObject[144];
        his = new LinkedList<PieceObject>();
    }

    // UndoAll�p
    public void setHisAll(PieceObject po[]) {
        for (int i = 0; i < 144; i++) { // �z��̃R�s�[
            hisAll[i] = po[i];
        }
    }

    // UndoAll�p
    public PieceObject[] getHisAll() {
        return hisAll;
    }

    // Undo�p
    public void setHis(PieceObject po1, PieceObject po2) {
        his.addLast(po1);
        his.addLast(po2);
    }

    // Undo�p
    public PieceObject getHis() {
        return his.pollLast();
    }

    // Undo�p
    public PieceObject isNull() {
        return his.peekLast();
    }
}