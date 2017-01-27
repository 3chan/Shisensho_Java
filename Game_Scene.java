import java.util.*;

class Game_Scene extends asd.Scene
{
	PieceObject[] obj_pieces;
	asd.Layer2D layer;
    protected void OnRegistered()
    {
		// ���C���[�����B
 		layer = new asd.Layer2D();
		AddLayer(layer);
 
		// �w�i�摜��ǂݍ��� (��) ���C���[�ɃI�u�W�F�N�g��ǉ�����B
		PieceObject obj = new PieceObject();
		asd.Texture2D tex = asd.Engine.getGraphics().CreateTexture2D("res\\game.png");
		obj.setTexture(tex);
		layer.AddObject(obj);

		// ��̉摜��ǂݍ��݁A144�̃I�u�W�F�N�g�ɐݒ肷��B
		obj_pieces = new PieceObject[144];
		asd.Texture2D tex_pieces = asd.Engine.getGraphics().CreateTexture2D("res\\pieces.png");

		// ��̈ʒu�����������A�e�N�X�`����o�^����
		for (int i=0; i<144; i++)
		{
			obj_pieces[i] = new PieceObject();
			obj_pieces[i].setPiecePosition(i);
			obj_pieces[i].setTexture(tex_pieces);
			obj_pieces[i].setPieceTexture(i/4);
		}

		// ��̕`��ʒu�������_���ɂ���
		for (int i=0; i<1000; i++)
		{
			ShufflePiecePosition();
		}

		for (int i=0; i<144; i++)
		{
			// �V�[���Ƀ��C���[��ǉ����A���̃��C���[�ɃI�u�W�F�N�g��ǉ�����B
			layer.AddObject(obj_pieces[i]);
		}
	}

	protected void OnUpdated()
	{
		CheckPaired();
		// �}�E�X�̍��{�^�����������̂�҂B
		// �t�F�[�h�A�E�g�E�C���ɂ��V�[���J�ڂ��J�n����B
		// 1�b�����ăt�F�[�h�A�E�g���A1.5�b�����ăt�F�[�h�C���B
		//asd.Engine.ChangeSceneWithTransition(new Clear_Scene(), new asd.TransitionFade(1.0f, 1.5f));
    }

	void ShufflePiecePosition()
	{
		Random rnd = new Random();
		int rnd1 = rnd.nextInt(144);
		int rnd2 = rnd.nextInt(144);
		int buf = obj_pieces[rnd1].getPieceTexture();
		obj_pieces[rnd1].setPieceTexture(obj_pieces[rnd2].getPieceTexture());
		obj_pieces[rnd2].setPieceTexture(buf);
	}

	void CheckPaired()
	{
		int p1 = -1;
		int p2 = -1;
		boolean paired = false;
		
		// �I�𒆂̋�����o
		for (int i=0; i<144; i++) {
			if (obj_pieces[i] == null || !obj_pieces[i].isColored) continue;
			if (p1 == -1)
			{
				p1 = i;
				System.out.println("p1 = " + p1);
			}
			else
			{
				p2 = i;
				System.out.println("p2 = " + p2);
			}
		}
		
		// �I���Ȃ��܂���1��̂ݑI�𒆂̏ꍇ�������Ȃ�
		if (p1 == -1 || p2 == -1)
		{
			return;
		}
		// 2��̃e�N�X�`�����قȂ�ꍇ�I������������
		else if (obj_pieces[p1].getPieceTexture() != obj_pieces[p2].getPieceTexture())
		{
			obj_pieces[p1].setIsColored(false);
			obj_pieces[p2].setIsColored(false);
			return;
		}

		// y���W�������ꍇ 2��Ԃ��N���A��"�s"�𒲂ׂ�	
		if (p1/12 == p2/12)
		{
			System.out.println("y���W�������ꍇ");
			paired = CheckLine(p1, p2, true);
		}

		// x���W�������ꍇ 2��Ԃ��N���A��"��"�𒲂ׂ�
		if (p1%12 == p2%12)
		{
			System.out.println("x���W�������ꍇ");
			paired = CheckLine(p1, p2, false);
			// paired = CheckLines(p1, p2, true);
		}

		// 2��Ԃ�1�p�ȏ゠��ꍇ�𒲂ׂ�
		if (!paired) paired = CheckLines(p1, p2, true);
		if (!paired) paired = CheckLines(p1, p2, false);

		if (paired) {
			System.out.println("��������܂�");
			layer.RemoveObject(obj_pieces[p1]);
			layer.RemoveObject(obj_pieces[p2]);
			obj_pieces[p1] = null;
			obj_pieces[p2] = null;
			System.out.println("��������܂���");
		}
		else {
			obj_pieces[p1].setIsColored(false);
			obj_pieces[p2].setIsColored(false);
		}
	}
	
	// x�܂���y���W��������2�_�Ԃɋ���邩���ׂ�
	boolean CheckLine(int p1, int p2, boolean isX) {  // p1 < p2
		int add;

		// �������������߂�
		if (isX)
		{
			add = 1;
		}
		else
		{
			add = 12;
		}

		// p1 < p2 �Ƃ���
		if (p2 < p1) {
			int buff = p1;
			p1 = p2;
			p2 = buff;
		}

		if (p1 == p2) return true;

		for (int i = p1 + add; i <= p2; i += add)
		{
			// �y�A�����������ꍇ
			if (i == p2)
			{
				return true;
			}
			// ���ݒ��ׂĂ��� position ����ł���ꍇ
			else if (obj_pieces[i] == null)
			{
				System.out.println("��");
				continue;
			}
			// ���ݒ��ׂĂ��� position �ɑ��̋����ꍇ
			else
			{
				System.out.println("�g�p��");
				return false;
			}
		}

		// ���B���Ȃ��͂�
		return false;
	}

	boolean CheckLines(int p1, int p2, boolean isX) {
		int add, addLine;
		int side1, side2;

		// �������������߂�
		if (isX) {
			add = 12;
			addLine = 1;
			side1 = p1 - p1%12;
			side2 = p2 - p2%12;
		}
		else {
			add = 1;
			addLine = 12;
			side1 = p1%12;
			side2 = p2%12;
		}

		// 12x12 �O
		System.out.println("12x12 �O");
		System.out.println("CheckLine(side1, p1, isX) = " + CheckLine(side1, p1, isX));
		System.out.println("CheckLine(side2, p2, isX) = " + CheckLine(side2, p2, isX));
		System.out.println("CheckLine(side1 + 11, p1, isX) = "+ CheckLine(side1 + 11, p1, isX));
		System.out.println("CheckLine(side2 + 11, p2, isX) = "+ CheckLine(side2 + 11, p2, isX));
		if ((CheckLine(side1, p1, isX) && CheckLine(side2, p2, isX)) || 
			 CheckLine(side1 + addLine*11, p1, isX) && CheckLine(side2 + addLine*11, p2, isX)) {
			return true;
		}

		// 12x12 ��
		System.out.println("12x12 ��");
		for (int i = 0; i < 12; i++) {
			System.out.println("CheckLine(side1, side2, !isX) = " + CheckLine(side1, side2, !isX));
			if (CheckLine(side1, side2, !isX)) {
				System.out.println("CheckLine(side1, p1, isX) = " + CheckLine(side1, p1, isX));
				System.out.println("CheckLine(side2, p2, isX) = " + CheckLine(side2, p2, isX));
				if (CheckLine(side1, p1, isX) && CheckLine(side2, p2, isX)) return true;
				side1 += addLine;
				side2 += addLine;
			}
		}

		return false;
	}
}