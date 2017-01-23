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
		
		// �I�𒆂̋�𑖍�
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
			
		if (p1/12 == p2/12)
		{
			// y���W�������ꍇ 2��Ԃ��N���A��"�s"�𒲂ׂ�
			System.out.println("y���W�������ꍇ");
			paired = CheckLine(p1, p2, true);
		}
		else if (p1%12 == p2%12)
		{
			// x���W�������ꍇ 2��Ԃ��N���A��"��"�𒲂ׂ�
			System.out.println("x���W�������ꍇ");
			paired = CheckLine(p1, p2, false);
		}
		else
		{
			// 2��Ԃ�1�p�ȏ゠��ꍇ�𒲂ׂ�
			paired = CheckLines(p1, p2);
		}

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

		for (int i = p1 + add; i <= p2; i += add)
		{
			// 2�̋�ׂ荇���Ă����ꍇ
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

	boolean CheckLines(int p1, int p2) {
		boolean slash;  // 2��Ίp�Ɉʒu���钷���`�����Ƃ��A2��̔z�u���E��-�����ł��邩
		int point0, point1, point2, point3;  // ����, �E��, ����, �E��

		if (p2%12 < p1%12) slash = true;
		else slash = false;

		if (slash) {
			point0 = p1 - (p1%12 - p2%12);
			point1 = p1;
			point2 = p2;
			point3 = p2 + (p1%12 - p2%12);
		}
		else {
			point0 = p1;
			point1 = p1 - (p1%12 - p2%12);
			point2 = p2 + (p1%12 - p2%12);
			point3 = p2;
		}

		int l = point0;
		int r = point1;
		for (int i = point0; i <= point3; i += 12) {
			if (CheckLine(l, r, true)) {
				if (slash) {
					if (CheckLine(point1, r, false) && CheckLine(l, point2, false)) return true;
				}
				else {
					if (CheckLine(point0, l, false) && CheckLine(r, point3, false)) return true;
				}
			}
			l += 12;
			r += 12;
		}

		int a = point0;
		int b = point2;
		for (int i = point0; i <= point1; i++) {
			if (CheckLine(a, b, false)) {
				if (slash) {
					if (CheckLine(a, point1, true) && CheckLine(point2, b, true)) return true;
				}
				else {
					if (CheckLine(point0, a, true) && CheckLine(b, point3, true)) return true;
				}
			}
			a++;
			b++;
		}

		return false;
	}
}