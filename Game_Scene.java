import java.util.*;

class Game_Scene extends asd.Scene
{
	PieceObject[] obj_pieces;
	FigureObject[] obj_figures;
	asd.Layer2D layer;
	boolean cleared = false;
	GameTimer gtimer;
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
		for (int i=0; i<800; i++)
		{
			ShufflePiecePosition();
		}


		// �V�[���Ƀ��C���[��ǉ����A���̃��C���[�ɃI�u�W�F�N�g��ǉ�����B
		for (int i=0; i<144; i++)
		{
			layer.AddObject(obj_pieces[i]);
		}

		// �^�C�}�[�p�̐�����ǂݍ��݁A11�̃I�u�W�F�N�g�ɐݒ肷��
		obj_figures = new FigureObject[11];
		asd.Texture2D tex_figures = asd.Engine.getGraphics().CreateTexture2D("res\\number.png");

		// �����̃e�N�X�`����o�^����
		for (int i=0; i<11; i++) {
			obj_figures[i] = new FigureObject();
			obj_figures[i].setTexture(tex_figures);
			obj_figures[i].setFigureTexture(i);
		}

		// �V�[���Ƀ��C���[��ǉ����A���̃��C���[�ɃI�u�W�F�N�g��ǉ�����B
		for (int i=0; i<11; i++) {
			layer.AddObject(obj_figures[i]);
		}

		// �^�C�}�[���Z�b�g����
		gtimer = new GameTimer();
		gtimer.setStartTime();
	}

	protected void OnUpdated()
	{
		// �^�C�}�[���X�V����
		System.out.println(gtimer.getTime() / 1000);
		DrawTimer(gtimer.getTime() / 1000);

		// �`�[�g���[�h
		if (asd.Engine.getMouse().getRightButton().getButtonState() == asd.MouseButtonState.Push) {
			System.out.println("�`�[�g���[�h");
			for (int i=0; i < obj_pieces.length; i++) {
				layer.RemoveObject(obj_pieces[i]);
				obj_pieces[i] = null;
			}
		}

		// �������Ă���y�A����������������
		CheckPaired();

		// �N���A���͉�ʑJ�ڂ���
		if (CheckClear()) {
			System.out.println("��ʑJ�ڂ��܂�");
			if (cleared == false) {
				asd.Engine.ChangeSceneWithTransition(new Clear_Scene(), new asd.TransitionFade(1.0f, 1.5f));
				cleared = true;
			}
		}
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
		if (obj_pieces[p1].getPieceTexture() != obj_pieces[p2].getPieceTexture())
		{
			obj_pieces[p1].setIsColored(false);
			obj_pieces[p2].setIsColored(false);
			return;
		}

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
	boolean CheckLine(int p1, int p2, int start, int end, boolean isX) {  // p1 < p2
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

		//if (p1 == p2) return true;

		for (int i = p1 /* + add */; i <= p2; i += add)
		{
			// ���ݒ��ׂĂ��� position ����ł���ꍇ
			if (obj_pieces[i] == null || i == start || i == end)
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

		return true;
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
		System.out.println("CheckLine(side1, p1, p1, p2, isX) = " + CheckLine(side1, p1, p1, p2, isX));
		System.out.println("CheckLine(side2, p2, p1, p2, isX) = " + CheckLine(side2, p2, p1, p2, isX));
		System.out.println("CheckLine(side1 + 11, p1, p1, p2, isX) = "+ CheckLine(side1 + addLine*11, p1, p1, p2, isX));
		System.out.println("CheckLine(side2 + 11, p2, p1, p2, isX) = "+ CheckLine(side2 + addLine*11, p2, p1, p2, isX));
		if ((CheckLine(side1, p1, p1, p2, isX) && CheckLine(side2, p2, p1, p2, isX)) || 
			 CheckLine(side1 + addLine*11, p1, p1, p2, isX) && CheckLine(side2 + addLine*11, p2, p1, p2, isX)) {
			return true;
		}

		// 12x12 ��
		System.out.println("12x12 ��");
		for (int i = 0; i < 12; i++) {
			System.out.println("CheckLine(side1, side2, p1, p2, !isX) = " + CheckLine(side1, side2, p1, p2, !isX));
			System.out.println("side1 = " + side1);
			System.out.println("side2 = " + side2);
			if (CheckLine(side1, side2, p1, p2, !isX)) {
				System.out.println("CheckLine(side1, p1, p1, p2, isX) = " + CheckLine(side1, p1, p1, p2, isX));
				System.out.println("CheckLine(side2, p2, p1, p2, isX) = " + CheckLine(side2, p2, p1, p2, isX));
				if (CheckLine(side1, p1, p1, p2, isX) && CheckLine(side2, p2, p1, p2, isX)) return true;
			}
			side1 += addLine;
			side2 += addLine;
		}

		return false;
	}

	boolean CheckClear() {
		for (int i = 0; i < 144; i++) {
			if (obj_pieces[i] != null) return false;
		}
		return true;
	}

	// �^�C�}�[��`�悷��
	void DrawTimer(int nowTime) {
		int d = (int)Math.pow(10, 3);
		int num;

		// DEBUG
		for (int j = 0 ; j < 4; j++) {
			for (int k = 0; k < 11; k++) {
				System.out.print(obj_figures[k].getIsPlaced(j) + " ");
			}
			System.out.println();
		}

		System.out.println("DrawTimer");
		for (int i = 0; i < 4; i++) {
			num = nowTime / d;
			// if (!obj_figures[num].getIsPlaced(i)) {
				System.out.println("num = " + num);
				System.out.println("Drawing: " + num + " at " + i);
				obj_figures[num].setPosition(new asd.Vector2DF(50 + i * 20, 50 + 12 * 40 + 10));
				obj_figures[num].setIsPlaced(i, true);

			// 	if (num == 0) {
			// 		obj_figures[9].setIsPlaced(i, false);
			// 	}
			// 	else {
			// 		obj_figures[num - 1].setIsPlaced(i, false);
			// 	}
			// }
			nowTime %= d;
			d /= 10;
		}
 	}
}